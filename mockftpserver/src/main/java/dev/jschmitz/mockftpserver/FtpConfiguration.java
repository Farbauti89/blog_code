package dev.jschmitz.mockftpserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dev.jschmitz.mockftpserver.processing.OrderReceived;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.event.outbound.ApplicationEventPublishingMessageHandler;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class FtpConfiguration {

    @Bean
    public DefaultFtpSessionFactory defaultFtpSessionFactory(@Value("${ftp.username}") String username, @Value("${ftp.password}") String password,
                                                             @Value("${ftp.host}") String host, @Value("${ftp.port}") Integer port) {
        var defaultFtpSessionFactory = new DefaultFtpSessionFactory();
        defaultFtpSessionFactory.setHost(host);
        defaultFtpSessionFactory.setPort(port);
        defaultFtpSessionFactory.setUsername(username);
        defaultFtpSessionFactory.setPassword(password);
        defaultFtpSessionFactory.setBufferSize(100000);
        defaultFtpSessionFactory.setFileType(FTP.ASCII_FILE_TYPE);
        defaultFtpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
        return defaultFtpSessionFactory;
    }

    @Bean
    @ServiceActivator(inputChannel = "orderFtpServer")
    public MessageHandler messageHandler(DefaultFtpSessionFactory defaultFtpSessionFactory) {
        var handler = new FtpMessageHandler(defaultFtpSessionFactory);
        handler.setRemoteDirectoryExpressionString("headers['directory']");
        handler.setUseTemporaryFileName(false);
        handler.setFileNameGenerator(message -> Objects.requireNonNull(((File) message.getPayload())).getName());

        return handler;
    }

    @Bean
    public ApplicationEventPublishingMessageHandler eventHandler() {
        ApplicationEventPublishingMessageHandler handler = new ApplicationEventPublishingMessageHandler();
        handler.setPublishPayload(true);
        return handler;
    }

    @Bean
    IntegrationFlow inbound(DefaultFtpSessionFactory ftpSf, ApplicationEventPublishingMessageHandler applicationEventPublishingMessageHandler) throws IOException {
        var localDirectory = Files.createTempDirectory("orders").toFile();
        var spec = Ftp
                .inboundAdapter(ftpSf)
                .autoCreateLocalDirectory(true)
                .patternFilter("*.xml")
                .localDirectory(localDirectory)
                .deleteRemoteFiles(true);

        return IntegrationFlows
                .from(spec, pc -> pc.poller(pm -> pm.fixedRate(1000, TimeUnit.MILLISECONDS)))
                .transform(source -> new OrderReceived((File) source))
                .handle(applicationEventPublishingMessageHandler)
                .get();
    }

    @MessagingGateway
    public interface FtpHandler {

        @Gateway(requestChannel = "orderFtpServer")
        void upload(@Payload File order);

    }

}

package dev.jschmitz.mockftpserver;

import java.io.File;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway
public interface FtpHandler {

    @Gateway(requestChannel = "orderFtpServer")
    void upload(@Payload File order);

}

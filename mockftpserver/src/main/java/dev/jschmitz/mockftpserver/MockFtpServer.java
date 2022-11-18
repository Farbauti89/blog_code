package dev.jschmitz.mockftpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class MockFtpServer {

    public static void main(String[] args) {
        SpringApplication.run(MockFtpServer.class, args);
    }

}

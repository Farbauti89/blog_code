package dev.jschmitz.mockftpserver.shop;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {"ftp.port=12021"})
class OrderServiceIT {

    private FakeFtpServer fakeFtpServer;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setup() {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(12021);
        fakeFtpServer.addUserAccount(new UserAccount("admin", "admin", "/"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/"));
        fakeFtpServer.setFileSystem(fileSystem);

        fakeFtpServer.start();
    }

    @AfterEach
    void cleanup() {
        fakeFtpServer.stop();
    }

    @Test
    void orderFile_isTransmittedSuccessfully() throws IOException {

        final var customerId = UUID.fromString("add46359-60b0-44c5-b00b-f22367c0533d");
        final var itemId = UUID.fromString("80cea71c-b024-4e84-8c1c-af9580728132");
        final var orderId = orderService.place(customerId, itemId);

        final var orderXml = fakeFtpServer.getFileSystem().getEntry("/" + orderId + ".xml");
        assertNotNull(orderXml);
    }
}

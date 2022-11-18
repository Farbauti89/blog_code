package dev.jschmitz.mockftpserver.processing;

import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {"ftp.port=12021"})
class OrderProcessingIT {

    private FakeFtpServer fakeFtpServer;

    @Autowired
    private OrderRepository orderRepository;

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
    void orderFile_isProcessedSuccessfully() {
        final var orderId = UUID.fromString("221f5cb1-65f0-4688-8d8e-4176bf37423e");
        final var customerId = UUID.fromString("c98d9bfa-fd98-4166-8596-9ccc0f7f06b8");
        final var itemId = UUID.fromString("caec7bae-b6e8-482c-bc39-c1bb2b9f7b8a");

        final var orderFileName = "/" + orderId + ".xml";
        final var orderFileContent = """
                                     <order>
                                         <id>%s</id>
                                         <customer-id>%s</customer-id>
                                         <item-id>%s</item-id>
                                     </order>
                                     """.formatted(orderId, customerId, itemId);

        fakeFtpServer.getFileSystem().add(new FileEntry(orderFileName, orderFileContent));

        await().atMost(Duration.ofSeconds(5))
               .until(() -> orderRepository.count() == 1L);

        final var processedOrder = orderRepository.findById(orderId);
        assertTrue(processedOrder.isPresent());
        assertEquals(customerId, processedOrder.get().getCustomerId());
        assertEquals(itemId, processedOrder.get().getItemId());

        await().atMost(Duration.ofSeconds(5))
               .until(() -> fakeFtpServer.getFileSystem().getEntry(orderFileName) == null);

    }
}

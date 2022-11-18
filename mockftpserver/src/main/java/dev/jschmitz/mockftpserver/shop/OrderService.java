package dev.jschmitz.mockftpserver.shop;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import dev.jschmitz.mockftpserver.FtpHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
class OrderService {

    private static final String ORDER_XML_TEMPLATE = """
                                                     <order>
                                                        <id>%s</id>
                                                        <customer-id>%s</customer-id>
                                                        <item-id>%s</item-id>
                                                     </order>
                                                     """;
    private final FtpHandler ftpHandler;

    public OrderService(FtpHandler ftpHandler) {
        this.ftpHandler = ftpHandler;
    }

    public UUID place(UUID customerId, UUID itemId) throws IOException {
        var orderId = UUID.randomUUID();
        var tempDir = new File(System.getProperty("java.io.tmpdir"));
        var tempFile = new File(tempDir.getAbsolutePath() + File.separator + orderId + ".xml");
        tempFile.deleteOnExit();

        FileCopyUtils.copy(ORDER_XML_TEMPLATE.formatted(orderId, customerId, itemId).getBytes(), tempFile);

        ftpHandler.upload(tempFile);

        return orderId;
    }
}

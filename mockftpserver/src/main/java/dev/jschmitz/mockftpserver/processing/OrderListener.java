package dev.jschmitz.mockftpserver.processing;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
class OrderListener {

    private final OrderProcessingService orderProcessingService;

    public OrderListener(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @EventListener
    public void handleOrderReceived(OrderReceived event) {
        var file = event.file();
        try (var inputStream = new FileInputStream(file)) {

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(inputStream);

            var orderId = UUID.fromString(xmlDocument.getElementsByTagName("id").item(0).getTextContent());
            var customerId = UUID.fromString(xmlDocument.getElementsByTagName("customer-id").item(0).getTextContent());
            var itemId = UUID.fromString(xmlDocument.getElementsByTagName("item-id").item(0).getTextContent());

            var order = new Order(orderId, customerId, itemId);

            orderProcessingService.process(order);

            Files.delete(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

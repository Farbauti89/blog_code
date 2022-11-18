package dev.jschmitz.mockftpserver.shop;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public String place(@RequestBody PlaceOrderRequest placeOrderRequest) throws IOException {
        return orderService.place(placeOrderRequest.customerId(), placeOrderRequest.itemId()).toString();
    }
}

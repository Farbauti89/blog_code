package dev.jschmitz.mockftpserver.shop;

import java.util.UUID;

record PlaceOrderRequest(UUID customerId, UUID itemId) {

}

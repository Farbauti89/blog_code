package dev.jschmitz.mockftpserver.shop;

import java.util.UUID;

record Order(UUID id, UUID customerId, UUID itemId) {

}

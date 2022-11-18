package dev.jschmitz.mockftpserver.processing;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "orders")
class Order {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private UUID customerId;
    private UUID itemId;

    protected Order() {
    }

    public Order(UUID id, UUID customerId, UUID itemId) {
        this.id = id;
        this.customerId = customerId;
        this.itemId = itemId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getItemId() {
        return itemId;
    }
}

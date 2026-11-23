package org.example.entites;

import java.time.Instant;
import java.util.Date;

public class Adjust {
    private String id ;
    private Instant createAt;
    private String fromStockLocationId;
    private String toStockLocationId;
    private String fromOrganizationId;
    private String toOrganizationId;
    private String creatorId;
    private String receiverId;
    public Adjust(String id, Instant createAt) {
        this.id = id;
        this.createAt = createAt;
    }

    public Adjust(String id, Instant createAt, String fromStockLocationId, String toStockLocationId, String fromOrganizationId, String toOrganizationId, String creatorId, String receiverId) {
        this.id = id;
        this.createAt = createAt;
        this.fromStockLocationId = fromStockLocationId;
        this.toStockLocationId = toStockLocationId;
        this.fromOrganizationId = fromOrganizationId;
        this.toOrganizationId = toOrganizationId;
        this.creatorId = creatorId;
        this.receiverId = receiverId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public String getFromStockLocationId() {
        return fromStockLocationId;
    }

    public void setFromStockLocationId(String fromStockLocationId) {
        this.fromStockLocationId = fromStockLocationId;
    }

    public String getToStockLocationId() {
        return toStockLocationId;
    }

    public void setToStockLocationId(String toStockLocationId) {
        this.toStockLocationId = toStockLocationId;
    }

    public String getFromOrganizationId() {
        return fromOrganizationId;
    }

    public void setFromOrganizationId(String fromOrganizationId) {
        this.fromOrganizationId = fromOrganizationId;
    }

    public String getToOrganizationId() {
        return toOrganizationId;
    }

    public void setToOrganizationId(String toOrganizationId) {
        this.toOrganizationId = toOrganizationId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}

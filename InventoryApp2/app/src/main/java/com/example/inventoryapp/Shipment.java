package com.example.inventoryapp;

public class Shipment {
    private String orderId;
    private String productName;
    private String status;
    private int quantity;

    public Shipment(String orderId, String productName, String status, int quantity) {
        this.orderId     = orderId;
        this.productName = productName;
        this.status      = status;
        this.quantity    = quantity;
    }

    public String getOrderId()     { return orderId; }
    public String getProductName() { return productName; }
    public String getStatus()      { return status; }
    public int    getQuantity()    { return quantity; }
}

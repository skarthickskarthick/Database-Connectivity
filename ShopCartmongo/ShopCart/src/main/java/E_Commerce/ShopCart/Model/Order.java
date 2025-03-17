package E_Commerce.ShopCart.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "order")
public class Order {
    @Id
    private String dbID;

    @Indexed(unique = true)
    private long orderID;

    private String customerName;
    private String customerAddress;

    @JsonProperty("products")  // ✅ FIXED: lowercase JSON property
    private List<String> products;

    private double orderTotal;
    private String paymentDetail;
    private boolean paymentStatus;
    private String shipmentStatus;

    // ✅ Constructor (Fixed field name)
    public Order(String customerName, String customerAddress, List<String> products, String paymentDetail, double orderTotal, boolean paymentStatus, String shipmentStatus) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.products = products;
        this.paymentDetail = paymentDetail;
        this.orderTotal = orderTotal;
        this.paymentStatus = paymentStatus;
        this.shipmentStatus = shipmentStatus;
    }

    public Order() {}

    // ✅ Getters & Setters (Fixed field name)
    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(String paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    // ✅ toString() (Fixed field name)
    @Override
    public String toString() {
        return "Order [orderID=" + orderID + ", customerName=" + customerName + ", customerAddress=" + customerAddress +
                ", products=" + products + ", orderTotal=" + orderTotal + ", paymentDetail=" + paymentDetail +
                ", paymentStatus=" + paymentStatus + ", shipmentStatus=" + shipmentStatus + "]";
    }
}

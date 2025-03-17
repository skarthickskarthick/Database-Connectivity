package E_Commerce.ShopCart.Repository;

import E_Commerce.ShopCart.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Order_Repository extends JpaRepository<Order, Long> {

    long count();

    Order findByOrderID(long orderID);

    boolean existsByOrderID(long orderID);

    List<Order> findByCustomerName(String customerName);

    List<Order> findByPaymentStatus(Boolean paymentStatus);

    List<Order> findByShipmentStatus(String shipmentStatus);

    List<Order> findByCustomerNameAndPaymentStatusAndShipmentStatus(String customerName, boolean paymentStatus, String shipmentStatus);

    // ✅ Find orders by Customer ID range (Using JPQL)
    @Query("SELECT o FROM Order o WHERE o.customerID BETWEEN :minID AND :maxID")
    List<Order> findOrdersByCustomerIDRange(@Param("minID") Long minID, @Param("maxID") Long maxID);
    Optional<Order> findByCustomerID(Long customerID); // ✅ Fetch order


}


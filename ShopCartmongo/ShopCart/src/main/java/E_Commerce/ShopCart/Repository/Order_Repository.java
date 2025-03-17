package E_Commerce.ShopCart.Repository;

import E_Commerce.ShopCart.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Map;

public interface Order_Repository extends MongoRepository<Order,Long> {
    long count();
    Order findByOrderID(long orderID);
    boolean existsByOrderID(long orderID);
    List<Order> findByCustomerName(String customerName);
    List<Order> findByPaymentStatus(Boolean paymentStatus);
    List<Order> findByShipmentStatus(String shipmentStatus);
    List<Order> findByCustomerNameAndPaymentStatusAndShipmentStatus(String customerName, boolean paymentStatus, String shipmentStatus);
    @Query(value = "{ 'orderID' : { $gte: ?0, $lte: ?1 } }", fields = "{ _id: 0 }")

    Map<String, Object> findOrdersByCustomerIDRange(Long minID, Long maxID, String fields);

}

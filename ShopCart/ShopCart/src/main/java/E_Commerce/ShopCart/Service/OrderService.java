package E_Commerce.ShopCart.Service;

import E_Commerce.ShopCart.Model.Order;
import E_Commerce.ShopCart.Repository.Order_Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Order_Repository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;  // ✅ Replaces MongoTemplate

    public OrderService(Order_Repository orderRepository, EntityManager entityManager) {
        this.orderRepository = orderRepository;
        this.entityManager = entityManager;
    }

    public List<Map<String, Object>> getOrdersByRangeAndFields(Long minID, Long maxID, List<String> fields) {
        // ✅ Ensure at least one field is selected
        if (fields.isEmpty()) {
            throw new IllegalArgumentException("At least one field must be specified");
        }

        // ✅ Build dynamic SQL query
        String fieldSelection = String.join(", ", fields);
        String sql = "SELECT " + fieldSelection + " FROM orders WHERE orderID BETWEEN :minID AND :maxID";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("minID", minID);
        query.setParameter("maxID", maxID);

        List<?> results = query.getResultList(); // ✅ Result could be List<Object[]> or List<Object>

        List<Map<String, Object>> finalResult = new ArrayList<>();

        for (Object row : results) {
            Map<String, Object> rowMap = new HashMap<>();

            if (fields.size() == 1) {
                // ✅ Only one field selected, result is List<Object>
                rowMap.put(fields.get(0), row);
            } else {
                // ✅ Multiple fields selected, result is List<Object[]>
                Object[] rowArray = (Object[]) row;
                for (int i = 0; i < fields.size(); i++) {
                    rowMap.put(fields.get(i), rowArray[i]);
                }
            }

            finalResult.add(rowMap);
        }

        return finalResult;
    }



    // ✅ Fetch orders by a specific field
    public List<Map<String, Object>> getOrdersByField(String fieldName) {
        String sql = "SELECT " + fieldName + " FROM orders";
        Query query = entityManager.createNativeQuery(sql);

        List<Object> results = query.getResultList(); // ✅ Expecting a single column, so Object

        return results.stream()
                .map(obj -> {
                    Map<String, Object> row = new java.util.HashMap<>();
                    row.put(fieldName, obj); // ✅ No array access needed
                    return row;
                })
                .collect(Collectors.toList());
    }

}

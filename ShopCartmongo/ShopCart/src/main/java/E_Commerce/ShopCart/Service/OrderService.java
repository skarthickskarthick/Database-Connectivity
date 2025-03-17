package E_Commerce.ShopCart.Service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final MongoTemplate mongoTemplate;

    public OrderService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Map<String, Object>> getOrdersByRangeAndFields(Long minID, Long maxID, List<String> fields) {
        Query query = new Query();

        // ✅ Add range condition
        query.addCriteria(Criteria.where("orderID").gte(minID).lte(maxID));

        // ✅ Add dynamic projection fields
        Field projection = query.fields();
        fields.forEach(projection::include);

        // ✅ Explicitly define the return type to List<Map<String, Object>>
        return mongoTemplate.find(query, Map.class, "order")
                .stream()
                .map(doc -> (Map<String, Object>) doc) // Explicit casting to Map<String, Object>
                .collect(Collectors.toList());
    }
    public List<Map<String, Object>> getOrdersByField(String fieldName) {
        Query query = new Query();
        query.fields().include(fieldName); // Only include the requested field

        // Fetch all documents with only the requested field
        return mongoTemplate.find(query, Map.class, "order")
                .stream()
                .map(doc -> (Map<String, Object>) doc)
                .collect(Collectors.toList());
    }
}

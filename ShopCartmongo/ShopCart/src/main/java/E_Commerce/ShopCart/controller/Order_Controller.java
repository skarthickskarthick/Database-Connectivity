package E_Commerce.ShopCart.controller;


import E_Commerce.ShopCart.DTO.OrderDTO;
import E_Commerce.ShopCart.Model.Order;
import E_Commerce.ShopCart.Repository.Order_Repository;
import E_Commerce.ShopCart.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/orders")
public class Order_Controller {
    @Autowired
    private Order_Repository orderRepository;
    private final OrderService orderService;
    public Order_Controller(OrderService orderService) {
        this.orderService = orderService;
    }

    private static final Logger logger = LoggerFactory.getLogger(Order_Controller.class);
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders()
    {
        List<Order> orders= orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Order> getOrdersBYID(@PathVariable("id") long orderID)
    {
        Order order=orderRepository.findByOrderID(orderID);//good practise but donot use optional in the entity class
        if(order!=null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        else {

            //ResponseEntity.status(HttpStatus.NOT_FOUND).body("wrong id");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findbypaymentstatus/{paymentStatus}")
    public ResponseEntity<List<Order>> findbyPaymentstatus(@PathVariable("paymentStatus") boolean status)
    {
        List<Order> orders=orderRepository.findByPaymentStatus(status);
        return orders.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.ok(orders);
    }

    @GetMapping("/findbycriteria")
    public ResponseEntity<List<Order>> getOrdersByCriteria(@RequestBody OrderDTO orderdto) {
        List<Order> orders;

        if (orderdto.getCustomerName() != null && orderdto.getPaymentStatus() != null && orderdto.getShipmentStatus() != null) {
            orders = orderRepository.findByCustomerNameAndPaymentStatusAndShipmentStatus(
                    orderdto.getCustomerName(), orderdto.getPaymentStatus(), orderdto.getShipmentStatus());
        } else if (orderdto.getCustomerName() != null) {
            orders = orderRepository.findByCustomerName(orderdto.getCustomerName());
        } else if (orderdto.getPaymentStatus() != null) {
            orders = orderRepository.findByPaymentStatus(orderdto.getPaymentStatus());
        } else if (orderdto.getShipmentStatus() != null) {
            orders = orderRepository.findByShipmentStatus(orderdto.getShipmentStatus());
        } else {
            orders = orderRepository.findAll(); // Return all orders if no filters are provided
        }

        return orders.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(orders);
    }

    @GetMapping("/range/{minID}/{maxID}/fields/{fields}")
    public ResponseEntity<Object> getOrdersByRangeAndFields(
            @PathVariable Long minID,
            @PathVariable Long maxID,
            @PathVariable String fields) {

        // ✅ Convert comma-separated fields into a List
        List<String> fieldList = Arrays.asList(fields.split(","));

        // ✅ Fetch results from service
        List<Map<String, Object>> orders = orderService.getOrdersByRangeAndFields(minID, maxID, fieldList);

        if (orders.isEmpty()) {
            return ResponseEntity.status(404).body("No orders found for customer IDs between " + minID + " and " + maxID);
        }

        return ResponseEntity.ok(orders);
    }
    @GetMapping("/field/{fieldName}")
    public ResponseEntity<List<Map<String, Object>>> getOrdersByField(@PathVariable String fieldName) {
        List<Map<String, Object>> result = orderService.getOrdersByField(fieldName);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) throws Exception {

        if(!orderRepository.existsByOrderID(order.getOrderID()))
        {
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(order);
            Order order1 = objectMapper.readValue(jsonString, Order.class);

            System.out.println("Json to Object"+order1.toString());
            System.out.println("Object to Json"+jsonString);
            Order savedOrder = orderRepository.save(order);
           // System.out.println(savedOrder.toString());
            logger.info("Order created successfully: {} | HTTP Status: {}", savedOrder, HttpStatus.CREATED);

            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        }
        logger.warn("Failed to create order: Order ID already exists | HTTP Status: {}", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable("id") long orderID, @RequestBody Order updatedOrder) {
       Order existingOrder = orderRepository.findByOrderID(orderID);

        if (existingOrder!=null) {

            Order order = existingOrder;
            order.setOrderID(updatedOrder.getOrderID());
            order.setCustomerName(updatedOrder.getCustomerName());
            order.setCustomerAddress(updatedOrder.getCustomerAddress());
            order.setProducts(updatedOrder.getProducts());
            order.setOrderTotal(updatedOrder.getOrderTotal());
            order.setPaymentDetail(updatedOrder.getPaymentDetail());
            order.setPaymentStatus(updatedOrder.isPaymentStatus());
            order.setShipmentStatus(updatedOrder.getShipmentStatus());

            orderRepository.save(order);
            logger.info("Order upodated successfully: {} | HTTP Status: {}", order, HttpStatus.CREATED);
            return ResponseEntity.ok("Order updated successfully.");
        } else {
            logger.warn("Failed to update order: Order ID already exists | HTTP Status: {}", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
    }
    @GetMapping("/count")
    public ResponseEntity<Long> documentcount()
    {

        return ResponseEntity.ok(orderRepository.count());
    }



    @DeleteMapping("/remove/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") long orderID)
    {
       Order order=orderRepository.findByOrderID(orderID);
        if(order!=null)
        {
            orderRepository.deleteById(orderID);
            logger.info("Order deleted successfully: {} | HTTP Status: {}", order, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else
        {
            logger.warn("Failed to create order: Order ID already exists | HTTP Status: {}", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }




}

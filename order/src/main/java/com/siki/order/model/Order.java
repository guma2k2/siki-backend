package com.siki.order.model;
import com.siki.order.model.OrderDetail;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private String receiverPhoneNumber;
    private String receiverAddress;
    private String receiverName;
    private String note;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private String userId;
    private List<OrderDetail> orderDetails = new ArrayList<>();
}

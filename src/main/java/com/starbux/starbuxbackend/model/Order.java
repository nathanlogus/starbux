package com.starbux.starbuxbackend.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long id;
    private User user;
    private Date orderDate;
    private String status;
    private BigDecimal total;
}

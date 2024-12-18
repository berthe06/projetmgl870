package com.projetmgl870.microservices.order.dto;

import io.swagger.v3.oas.annotations.info.Contact;

import java.math.BigDecimal;

public record OrderRequest ( Long Id,
         String orderNumber,
         String skuCode,
         BigDecimal price,
         Integer quantity

                          // String email
        ){

        public record UserDetails (String firstName, String lastName){}

}

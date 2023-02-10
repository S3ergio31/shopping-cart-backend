package com.shoppingcart.backend.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String name;
    private String password;
}

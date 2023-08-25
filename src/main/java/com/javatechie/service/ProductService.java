package com.javatechie.service;

import com.javatechie.dto.ProductDTO;
import com.javatechie.entity.UserInfo;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();
    ProductDTO getProduct(int id);
    String addUser(UserInfo userInfo);
}
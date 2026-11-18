package com.luis.springcloud.msvcitems.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luis.springcloud.msvcitems.items.models.ProductDTO;

@FeignClient (name = "msvc-products/api/products") 
public interface ProductFeignClient {

    @GetMapping
    List<ProductDTO> findAll();

    @GetMapping( "/{id}" )
    ProductDTO details( @PathVariable Long id );
}

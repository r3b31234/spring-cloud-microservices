package com.luis.springcloud.msvcitems.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.luis.springcloud.msvcitems.items.clients.ProductFeignClient;
import com.luis.springcloud.msvcitems.items.models.Item;
import com.luis.springcloud.msvcitems.items.models.ProductDTO;

import feign.FeignException;

@Service 
public class ItemServiceFeign implements ItemService {

    private final ProductFeignClient productFeignClient;

    ItemServiceFeign(ProductFeignClient productFeignClient) {
        this.productFeignClient = productFeignClient;
    }

    @Override
    public List<Item> findAll() {
        return productFeignClient.findAll()
            .stream()
            .map(product -> new Item(product, new Random().nextInt(11) + 1))
        .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            
            ProductDTO product = productFeignClient.details(id);
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));

        } catch (FeignException e) {
            return Optional.empty();
        }        
    }
    
}

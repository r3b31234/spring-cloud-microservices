package com.luis.springcloud.msvcitems.items.services;

import java.util.List;
import java.util.Optional;

import com.luis.springcloud.msvcitems.items.models.Item;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Long id);
    
}

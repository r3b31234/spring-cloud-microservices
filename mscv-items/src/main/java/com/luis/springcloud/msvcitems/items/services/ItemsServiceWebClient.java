package com.luis.springcloud.msvcitems.items.services;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.luis.springcloud.msvcitems.items.models.Item;
import com.luis.springcloud.msvcitems.items.models.ProductDTO;

//@Primary // Anotación que indica que esta clase es la implementación principal de la interfaz ItemService, y se utilizará por defecto cuando se inyecte un objeto de tipo ItemService en otra clase.
@Service 
public class ItemsServiceWebClient implements ItemService{

    private final WebClient.Builder client;
    
    public ItemsServiceWebClient(WebClient.Builder client) {
        this.client = client;
    }   
    
    @Override
    public List<Item> findAll() {
        return this.client.build()
            .get() 
            .accept(MediaType.APPLICATION_JSON)             // Aceptamos el tipo de respuesta que esperamos
            .retrieve()                                     // Realizamos la petición y obtenemos la respuesta
            .bodyToFlux(ProductDTO.class)                         // Convertimos la respuesta en un flujo de objetos de tipo Item
            .map(product -> new Item(product, new Random().nextInt(11) + 1))
            .collectList()                                  // Convertimos el flujo en una lista de objetos de tipo Item
            .block();                                       // Bloqueamos el flujo y esperamos a que se complete la operación para obtener la lista de objetos de tipo Item
    }

    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Long> params = new HashMap<>();                      // Creamos un mapa para almacenar los parámetros de la URI

        params.put("id", id);                                       // Agregamos el parámetro "id" al mapa con su valor correspondiente
        try{
            return Optional.of(this.client.build()                      // Construimos el cliente WebClient
            .get().uri("/{id}", params)
            .accept(MediaType.APPLICATION_JSON)                         // Aceptamos el tipo de respuesta que esperamos
            .retrieve()                                                 // Realizamos la petición y obtenemos la respuesta
            .bodyToMono(ProductDTO.class)                                     // Convertimos la respuesta en un objeto de tipo Item
            .map(product -> new Item(product, new Random().nextInt(11) + 1))
            .block());  
        }catch(WebClientResponseException e){
            return Optional.empty();                                    // Si ocurre una excepción al realizar la petición, retornamos un Optional vacío
        }
    }

}

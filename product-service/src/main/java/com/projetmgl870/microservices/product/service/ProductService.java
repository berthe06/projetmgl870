package com.projetmgl870.microservices.product.service;

import com.projetmgl870.microservices.product.dto.ProductRequest;
import com.projetmgl870.microservices.product.dto.ProductResponse;
import com.projetmgl870.microservices.product.model.Product;
import com.projetmgl870.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Crée un nouveau produit et retourne une réponse contenant les détails du produit.
     * @param productRequest la requête contenant les données du produit.
     * @return une réponse avec les informations du produit créé.
     */
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("Received request to create a product with name: {}", productRequest.name());
        try {
            Product product = Product.builder()
                    .name(productRequest.name())
                    .description(productRequest.description())
                    .price(productRequest.price())
                    .build();

            product = productRepository.save(product);
            log.info("Product created successfully: [ID: {}, Name: {}]", product.getId(), product.getName());

            return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
        } catch (Exception e) {
            log.error("Failed to create product with name: {}", productRequest.name(), e);
            throw new RuntimeException("Error while creating product", e); // Rééventuellement, une exception métier peut être utilisée.
        }
    }

    /**
     * Récupère tous les produits disponibles dans le système.
     * @return une liste des réponses des produits.
     */
    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products from the database");
        try {
            List<ProductResponse> products = productRepository.findAll()
                    .stream()
                    .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                    .toList();

            log.info("Successfully fetched {} products", products.size());
            return products;
        } catch (Exception e) {
            log.error("Failed to fetch products from the database", e);
            throw new RuntimeException("Error while fetching products", e); // Rééventuellement, une exception métier peut être utilisée.
        }
    }
}

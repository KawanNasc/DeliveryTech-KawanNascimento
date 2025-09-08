package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;

import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Cadastrar novo produto
    public Product register(Product product, Long restaurant_id) {
        Restaurant restaurant = restaurantRepository.findById(restaurant_id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restaurant_id));

        validateDataProduct(product);

        product.setRestaurant(restaurant);
        product.setAvailable(true);

        return productRepository.save(product);
    }

    // Buscar p/ ID
    @Transactional(readOnly = true)
    public Optional<Product> findPerId(Long id) {
        return productRepository.findById(id);
    }

    // Listar produtos p/ restaurante
    @Transactional(readOnly = true)
    public List<Product> listPerRestaurant(Long restaurant_id, boolean available) {
        return productRepository.findByRestaurantIdAndAvailableTrue(restaurant_id);
    }

    // Buscar p/ categoria
    @Transactional(readOnly = true)
    public List<Product> findPerCategory(String category) {
        return productRepository.findByCategoryAndAvailableTrue(category);
    }

    // Atualizar produto
    public Product update(Long id, Product updatedProduct) {
        Product product = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        validateDataProduct(updatedProduct);

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        return productRepository.save(product);
    }

    // Alterar disponibilidade
    public void changeAvailability(Long id, boolean available) {
        Product product = findPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        product.setAvailable(available);
        productRepository.save(product);
    }

    // Buscar p/ faixa de preço
    @Transactional(readOnly = true)
    public List<Product> findByPriceRange(BigDecimal priceMin, BigDecimal priceMax) {
        return productRepository.findByPriceBetweenAndAvailableTrue(priceMin, priceMax);
    }

    private void validateDataProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço devce ser maior que zero");
        }
    }
}

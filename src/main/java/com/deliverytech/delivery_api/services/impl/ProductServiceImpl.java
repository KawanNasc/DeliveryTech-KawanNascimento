package com.deliverytech.delivery_api.services.impl;

import com.deliverytech.delivery_api.data.request.ProductDTORequest;
import com.deliverytech.delivery_api.data.response.ProductDTOResponse;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.exception.EntityNotFoundException;
import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.services.interfaces.ProductServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductServiceInterface {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductDTOResponse registerProduct(ProductDTORequest dto) {
        // Validate if restaurant exists and is active
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        if (!restaurant.isActive()) {
            throw new BusinessException("Não é possível cadastrar produtos para restaurantes inativos");
        }

        // Check if product with same name already exists in this restaurant
        if (productRepository.existsByNameAndRestaurantId(dto.getName(), dto.getRestaurantId())) {
            throw new BusinessException("Já existe um produto com este nome neste restaurante");
        }

        // Create and save product
        Product product = modelMapper.map(dto, Product.class);
        product.setRestaurant(restaurant);
        product.setAvailable(true); // New products are available by default

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTOResponse findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        return modelMapper.map(product, ProductDTOResponse.class);
    }

    @Override
    @Transactional
    public ProductDTOResponse updateProduct(Long id, ProductDTORequest dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        // Validate if restaurant exists (if being changed)
        if (!product.getRestaurant().getId().equals(dto.getRestaurantId())) {
            Restaurant newRestaurant = restaurantRepository.findById(dto.getRestaurantId())
                    .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

            if (!newRestaurant.isActive()) {
                throw new BusinessException("Não é possível mover produto para restaurante inativo");
            }
            product.setRestaurant(newRestaurant);
        }

        // Check if name already exists in the restaurant (excluding current product)
        if (productRepository.existsByNameAndRestaurantIdAndIdNot(dto.getName(), dto.getRestaurantId(), id)) {
            throw new BusinessException("Já existe outro produto com este nome neste restaurante");
        }

        // Update product fields
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());

        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductDTOResponse.class);
    }

    @Override
    @Transactional
    public void removeProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        // Check if product has associated requests (business rule)
        if (productRepository.hasAssociatedRequests(id)) {
            throw new BusinessException("Não é possível remover produto que possui pedidos associados");
        }

        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductDTOResponse changeAvailability(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        // Toggle availability
        product.setAvailable(!product.isAvailable());

        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductDTOResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTOResponse> findProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTOResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTOResponse> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndAvailableTrue(name);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTOResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTOResponse> findProductsByRestaurant(Long restaurantId, Boolean available) {
        // Validate restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new EntityNotFoundException("Restaurante não encontrado");
        }

        List<Product> products;

        if (available != null) {
            products = productRepository.findByRestaurantIdAndAvailableTrue(restaurantId);
        } else {
            products = productRepository.findByRestaurantIdAndAvailableTrue(restaurantId);
        }

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTOResponse.class))
                .collect(Collectors.toList());
    }
}
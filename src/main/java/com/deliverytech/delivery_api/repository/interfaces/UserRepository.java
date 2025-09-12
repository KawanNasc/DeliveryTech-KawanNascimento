package com.deliverytech.delivery_api.repository.interfaces;

import com.deliverytech.delivery_api.model.User;
import com.deliverytech.delivery_api.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndActive(String email, Boolean active);

    long countByRole(Role role);
}
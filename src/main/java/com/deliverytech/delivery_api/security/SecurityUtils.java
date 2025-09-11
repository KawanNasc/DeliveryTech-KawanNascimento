package com.deliverytech.delivery_api.security;

import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) { return (User) authentication.getPrincipal(); }
        throw new RuntimeException("User not authenticated");
    }

    public static Long getCurrentUserId() { return getCurrentUser().getId(); }
    public static String getCurrentUserEmail() { return getCurrentUser().getEmail(); }
    public static String getCurrentUserRole() { return getCurrentUser().getRole().name(); }

    public static Restaurant getCurrentRestaurantId() {
        User user = getCurrentUser();
        return user.getRestaurant();
    }

    public static boolean hasRole(String role) {
        try {
            User user = getCurrentUser();
            return user.getRole().name().equals(role);
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isAdmin() { return hasRole("ADMIN"); }
    public static boolean isClient() { return hasRole("CLIENT"); }
    public static boolean isRestaurant() { return hasRole("RESTAURANT"); }
    public static boolean isDeliveryPerson() { return hasRole("DELIVERY_PERSON"); }
}
package vn.nun.services.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.Cart;
import vn.nun.models.CartItem;
import vn.nun.models.Product;
import vn.nun.repository.CartItemRepository;
import vn.nun.services.CartItemService;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public CartItem findById(Integer id) {
        return cartItemRepository.findById(id).get();
    }

    @Override
    public boolean create(CartItem cartItem) {
        try {
            cartItemRepository.save(cartItem);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(CartItem cartItem) {
        try {
            cartItemRepository.save(cartItem);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            cartItemRepository.delete(findById(id));
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existProduct(Cart cart, Product product) {
        return cartItemRepository.existsByCartAndProduct(cart, product);
    }

    @Override
    public CartItem findByProduct(Cart cart, Product product) {
        return cartItemRepository.getCartItemByCartAndProduct(cart, product);
    }
}

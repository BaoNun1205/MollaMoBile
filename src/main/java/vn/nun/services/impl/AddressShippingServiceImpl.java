package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.nun.models.AddressShipping;
import vn.nun.models.CustomUserDetail;
import vn.nun.models.User;
import vn.nun.repository.AddressShippingRepository;
import vn.nun.services.AddressShippingService;
import vn.nun.services.UserService;

@Service
public class AddressShippingServiceImpl implements AddressShippingService {
    @Autowired
    private AddressShippingRepository addressShippingRepository;
    @Autowired
    private UserService userService;
    @Override
    public AddressShipping save(AddressShipping addressShipping) {
        return addressShippingRepository.save(addressShipping);
    }

    @Override
    public AddressShipping findById(Integer id) {
        return addressShippingRepository.findById(id).get();
    }

    @Override
    public AddressShipping findByUser(User user) {
        return addressShippingRepository.findByUser(user);
    }

    @Override
    public AddressShipping getAddressShippingForCurrentUser() {
        User user = userService.currentUser();
        return findByUser(user);
    }
}

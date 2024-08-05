package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.nun.models.*;
import vn.nun.services.*;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private WishListService wishListService;
	@Autowired
	private AddressShippingService addressShippingService;
	@Autowired
	private CartService cartService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@GetMapping("/login")
	public String login() {
		return ("user/login");
	}

	@GetMapping("/register")
	public String register(){
		return "user/register";
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("username") String username,
			@RequestParam("pass") String password,
			@RequestParam("re_pass") String confirmPassword,
			@RequestParam("agree-term") boolean agreeTerm
	) {
		try {
			User user = User.builder()
					.fullName(name)
					.email(email)
					.userName(username)
					.passWord(password)
					.enabled(true)
					.build();

			User newUser = userService.save(user);

			//Xet Role cho user
			UserRole userRole = UserRole.builder()
					.role(roleService.findByName("USER"))
					.user(newUser)
					.build();

			userRoleService.save(userRole);

			//Tao danh sach yeu thich cho user
			WishList newWishlist = new WishList();
			newWishlist.setUser(user);
			wishListService.create(newWishlist);

			//Tao gio hang cho user
			Cart newCart = new Cart();
			newCart.setUser(user);
			cartService.create(newCart);

			//Tao dia chi giao hang cho user
			AddressShipping newAddressShipping = new AddressShipping();
			newAddressShipping.setUser(user);
			addressShippingService.save(newAddressShipping);

			// Trả về phản hồi thành công
			return ResponseEntity.noContent().build();
		} catch (Exception e){
			e.printStackTrace();
			// Trả về phản hồi lỗi
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
		}
	}
}

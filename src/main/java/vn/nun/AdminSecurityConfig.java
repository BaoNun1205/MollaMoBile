package vn.nun;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {
//	@Autowired
//	private CustomUserDetailService customUserDetailService;

	@Bean
	@Order(2)
	SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.anyRequest().permitAll()
				)
				.formLogin(login -> login
						.loginPage("/logon")
						.loginProcessingUrl("/logon")
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/admin", true)
				)
				.logout(logout -> logout
						.logoutUrl("/admin-logout")
						.logoutSuccessUrl("/logon")
				);
		return http.build();
	}

	@Bean
	@Order(1)
	SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/add-cart", "/favorite-list").authenticated()
						.anyRequest().permitAll()
				)
				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/", true)
				)
				.logout(logout -> logout
						.logoutUrl("/user-logout")
						.logoutSuccessUrl("/")
				);
		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web)->web.debug(true).ignoring().requestMatchers("/static/**", "/fe/**", "/assets/**", "/uploads/**");
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

package vn.nun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.yaml.snakeyaml.tokens.FlowEntryToken;

import vn.nun.models.CustomUserDetail;
import vn.nun.services.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//	@Autowired
//	private CustomUserDetailService customUserDetailService;

	@Bean
	SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable()).authorizeHttpRequests((auth) -> auth
				.requestMatchers("admin/**").permitAll()
//				.requestMatchers("admin/**").hasAuthority("ADMIN")
				.anyRequest().permitAll())
				.formLogin(login->login.loginPage("/logon").loginProcessingUrl("/logon")
				.usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/admin", true)).logout(logout->logout.logoutUrl("/admin-logout").logoutSuccessUrl("/logon"));

		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web)->web.debug(true).ignoring().requestMatchers("/static/**", "/fe/**", "/assets/**", "/uploads/**", "/css/**", "/plugins/**", "/images/**");
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

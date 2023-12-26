package org.mql.accountservice.configs;

import java.util.List;
import java.util.Vector;

import org.mql.accountservice.entities.Account;
import org.mql.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.User;


@EnableWebSecurity
public class SecurityConfig {
	
		private AccountService accountService;

		private final PasswordEncoder encoder;

		@Autowired
		public SecurityConfig(PasswordEncoder encoder, AccountService accountService) {
			this.encoder = encoder;
			this.accountService = accountService;
		}
	 	@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 	

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http
	        .csrf((c) -> c.disable())
	        .authorizeHttpRequests((authorize) -> {
	            authorize.requestMatchers("/login").permitAll()
						.anyRequest().authenticated();
	        })
            .logout((l) -> l.logoutUrl("/logout")
					.logoutSuccessUrl("/v2/api-docs")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID"));


	        return http.build();
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {

			return new InMemoryUserDetailsManager(getEmployees());
	    }
	    
	    
	    private List<UserDetails> getEmployees() {
	    	List<UserDetails> accountsAsUser = new Vector<UserDetails>();
	    	List<Account> accounts = accountService.getAccounts();
	    	for(Account a : accounts) {
	    		UserDetails usr = User.builder()
		                .username(a.getEmail())
		                .password(encoder.encode(a.getPassword()))
		                .roles("ADMIN")
		                .build();
				accountsAsUser.add(usr);
	    	}
	        return accountsAsUser;
	    }

}

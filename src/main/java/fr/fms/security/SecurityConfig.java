package fr.fms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	public String encodePassword(String password) {
		PasswordEncoder pe = passwordEncoder();
		return pe.encode(password);
	}
	
	@Override  
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select name as principal, password as credentials, active from User where username=?")
		.authoritiesByUsernameQuery("select name as principal, role as role from User where username=?")
		.rolePrefix("ROLE_")
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		   http.formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/index").failureUrl("/login?error=true").permitAll();
		   http.authorizeRequests()
//			.antMatchers("/myOrder","/commande","/delete","/save","/edit","/article").hasRole("ADMIN")
//			.antMatchers("/myOrder","/commande").hasRole("USER")
			.anyRequest().permitAll();
	   
//		http.exceptionHandling().accessDeniedPage("/403");
		
		
	}
	
}


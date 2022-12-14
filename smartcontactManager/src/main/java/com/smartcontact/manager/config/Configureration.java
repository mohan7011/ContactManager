package com.smartcontact.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class Configureration extends WebSecurityConfigurerAdapter{
	@Bean
	public UserDetailsService getUserDetailsService()
	{
		return new  UserDetailsServiceImpl();
	} 
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{return  new  BCryptPasswordEncoder();
		
		
	}
	
	public DaoAuthenticationProvider authenticationProvider()
	 {
		 
		 DaoAuthenticationProvider aaoAuthenticationProvider= new DaoAuthenticationProvider();
		 aaoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		 aaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		 return aaoAuthenticationProvider;
	 }
	//configure method++++++++++++++++++++++++
	 @Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(authenticationProvider());
		}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll().and().formLogin().loginPage("/signin")
		.loginProcessingUrl("/dologin").defaultSuccessUrl("/user/index")
		.and().csrf().disable();
	}
	 

}

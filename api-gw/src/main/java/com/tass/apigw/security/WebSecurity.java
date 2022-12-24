package com.tass.apigw.security;


import com.tass.common.redis.repository.UserLoginRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserLoginRepository userLoginRepository;

    public WebSecurity(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public void configure(
        org.springframework.security.config.annotation.web.builders.WebSecurity web)
        throws Exception {
        web.ignoring().antMatchers("/noauth/**" , "/user/register" , "/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        HttpSecurity httpSercurity = http.headers().disable()
            .cors()
            .and()
            .requestCache().disable()
            .csrf().disable().authorizeRequests().and();

        BasicAuthenticationFilter filter = new Oauth2AuthorizationFilter(authenticationManager() , userLoginRepository);
        httpSercurity.addFilterBefore(filter, BasicAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling();

        http.authorizeRequests().anyRequest().authenticated();
    }

}

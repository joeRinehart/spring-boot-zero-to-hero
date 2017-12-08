package com.thirdstart.spring.zerotohero

import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(ManagementServerProperties.SessionCreationPolicy.NEVER)
        ;
    }
}
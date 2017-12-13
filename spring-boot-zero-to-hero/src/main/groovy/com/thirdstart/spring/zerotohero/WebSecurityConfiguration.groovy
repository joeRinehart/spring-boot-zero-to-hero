package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.filters.JwtAuthorizationFilter
import com.thirdstart.spring.zerotohero.util.jwt.JwtParser
import com.thirdstart.spring.zerotohero.util.rsa.RsaKeyParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService

    public WebSecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        If you'd like to default to allowing requests from anyone and requiring
        login per-url/controller/method/mapping, change the
        .anyRequest().authenticated() to .anyRequest().permitAll()
        */
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers("/info/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(jwtAuthorizationFilter())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final source = new UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    @Value('${jwt.publicKey}')
    String jwtPublicKey

    @Value('${jwt.headerName}')
    String jwtHeaderName

    @Bean
    public jwtParser() {
        return new JwtParser(key: RsaKeyParser.toPublicKey(jwtPublicKey))
    }

    @Bean
    public jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(
                authenticationManager(),
                jwtParser(),
                jwtHeaderName
        )
    }

}

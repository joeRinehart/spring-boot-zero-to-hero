package com.thirdstart.spring.zerotohero.filters

import com.thirdstart.spring.zerotohero.util.jwt.RuntimeJwtHelper
import groovy.util.logging.Log4j
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Log4j
/**
 * Uses incoming JWT tokens to authenticate a user. Based on example at
 * https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
 */
class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    RuntimeJwtHelper runtimeJwtHelper

    JwtAuthorizationFilter( AuthenticationManager authenticationManager, RuntimeJwtHelper runtimeJwtHelper ) {
        super(authenticationManager)
        this.runtimeJwtHelper = runtimeJwtHelper
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        String header = request.getHeader('Authorization')

        log.info("Authorization header: ${header}")

        UsernamePasswordAuthenticationToken authenticationToken

        if ( header ) {
            Jws<Claims> jwt = runtimeJwtHelper.parseToken(header)

            if ( jwt ) {
                String username = jwt.body.sub
                List<GrantedAuthority> grantedAuthorities = jwt.body.authorities?.collect{ String authorityName ->
                    return new SimpleGrantedAuthority( authorityName )
                }
                authenticationToken = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities)
                log.info("Setting security context authentication to ${username}")
            }
        }

        SecurityContextHolder.context.authentication = authenticationToken

        chain.doFilter( request, response )
    }


}

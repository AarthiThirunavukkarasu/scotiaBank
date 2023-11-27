package com.scotiaBank.ScotiaBank.Filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.scotiaBank.ScotiaBank.Service.impl.CustomUserDetailsServiceImpl;
import com.scotiaBank.ScotiaBank.config.SeedDataConfig;
import com.scotiaBank.ScotiaBank.security.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;


    @Autowired
    private JwtProvider jwtProvider;
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	String authHeader = request.getHeader("AuthorizationHeader");
    	String jwt;
    	String userName;
        try {
             jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
            	
                String username = jwtProvider.extractUsername(jwt);
                LOG.debug("JwtAuthenticationFilter  for user "+username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtProvider.validateToken(jwt,userDetails)) {
                	LOG.debug("Debug Log: "+userDetails);
                	SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                    LOG.info("JwtAuthenticationFilter:: JwtAuthentication is completed for user "+username+ " has roles "+userDetails.getAuthorities());
                    //ADD audit log
                }


            }
        } catch (Exception ex) {
           
        	LOG.error("JwtAuthenticationFilter :: doFilterInternal Caught Exception "+ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
    	String output = "";
    	LOG.debug("in getJwtFromRequest");
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        	LOG.debug("request sent out from getJwtFromRequest");
            return bearerToken.substring(7);
        }
        return output;
    }
}


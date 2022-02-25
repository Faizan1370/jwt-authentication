package com.jwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.helper.JwtUtil;
import com.jwt.services.CustomUserDetailService;

@Component
public class JwtAucthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestHeaderToken = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if (requestHeaderToken != null && requestHeaderToken.startsWith("Bearer")) {
            jwtToken = requestHeaderToken.substring(7);

            try {
                // username = Jwts.parser().setSigningKey(JwtUtil.SECRET_KEY).parseClaimsJws(requestHeaderToken.replace("Bearer", "")).getBody().getSubject();

                username = jwtUtil.extractUsername(jwtToken);
                System.out.println("username *****************" + username);
            } catch (final Exception e) {
                e.printStackTrace();
                throw new UsernameNotFoundException("Username not found +" + username);
            }
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Token is not validated");
            }

        }
        filterChain.doFilter(request, response);
    }

}

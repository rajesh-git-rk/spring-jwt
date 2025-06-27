package com.security.spring_security_jwt.Security;

import com.security.spring_security_jwt.service.CustomUserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetail customUserDetailService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetail customUserDetail){
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetail;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token !=null && token.startsWith("Bearer")){
            token = token.substring(7);
            String userName = jwtUtil.extractUserName(token);
            if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetail = customUserDetailService.loadUserByUsername(userName);

                if(jwtUtil.isTokenValid(token)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }
        filterChain.doFilter(request, response);
    }
}

package com.example.demo.jwt.helper;


import com.example.demo.services.impl.customStudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtBeforeFilter extends OncePerRequestFilter {
    @Autowired
    private customStudentDetailsService studentDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtRequestToken = request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;
        if(jwtRequestToken!=null && jwtRequestToken.startsWith("Bearer ")){
            jwtToken=jwtRequestToken.substring(7);
            try{
                username=jwtUtils.getUsernameFromToken(jwtToken);
            }catch(Exception e){
                e.printStackTrace();
            }
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=this.studentDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                System.out.println("Token is not Valid !!!!");
            }
        }else {
            System.out.println("Token is not Formatted !!!!");
        }
  filterChain.doFilter(request,response);
    }
}
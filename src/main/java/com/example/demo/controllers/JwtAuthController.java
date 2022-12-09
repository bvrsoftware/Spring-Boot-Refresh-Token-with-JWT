package com.example.demo.controllers;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.Student;
import com.example.demo.exceptions.TokenRefreshException;
import com.example.demo.jwt.helper.JwtUtils;
import com.example.demo.payloads.JwtRequest;
import com.example.demo.payloads.JwtResponse;
import com.example.demo.payloads.TokenRefreshRequest;
import com.example.demo.payloads.TokenRefreshResponse;
import com.example.demo.repository.studentRepository;
import com.example.demo.services.impl.RefreshTokenService;
import com.example.demo.services.impl.customStudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class JwtAuthController {
    @Autowired
    private customStudentDetailsService studentDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private studentRepository studentRepository;
    @PostMapping(value = "/generate/token")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (BadCredentialsException e){
            System.out.println("Bad credentials !!");
            throw new BadCredentialsException("Bad credentials !!");
        }catch (Exception e){
            e.printStackTrace();
        }
        UserDetails userDetails=this.studentDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token=this.jwtUtils.generateToken(userDetails);
        List<String> roles=userDetails.getAuthorities().stream().map(e->e.getAuthority()).collect(Collectors.toList());
        RefreshToken refreshToken=this.refreshTokenService.createRefreshToken(this.studentRepository.findByEmail(jwtRequest.getUsername()).get().getId());
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setUsername(jwtRequest.getUsername());
        jwtResponse.setId(refreshToken.getId());
        jwtResponse.setToken(token);
        jwtResponse.setRoles(roles);
        jwtResponse.setRefreshToken(refreshToken.getToken());
        jwtResponse.setEmail(jwtRequest.getUsername());
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.CREATED);
    }
    @PostMapping(value = "/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getStudent)
                .map(std->{
                  String token=this.jwtUtils.generateTokenFromUsername(std.getUsername());
                  return ResponseEntity.ok(new TokenRefreshResponse(token,requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

}

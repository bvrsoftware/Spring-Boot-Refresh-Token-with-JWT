package com.example.demo.services.impl;

import com.example.demo.entity.RefreshToken;
import com.example.demo.exceptions.TokenRefreshException;
import com.example.demo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${demo.app.jwtRefreshExpirationMs}")
    private  Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private customStudentDetailsService studentDetailsService;

    public Optional<RefreshToken> findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token);
    }
    public RefreshToken createRefreshToken(Integer stdId){
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setStudent(this.studentDetailsService.getById(stdId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken=this.refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            this.refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),"Refresh token was expired. Please make a new sign in request");
        }
        return  token;
    }
    public int deleteByUserId(Integer stdId) {
        return  this.refreshTokenRepository.deleteByStudent(this.studentDetailsService.getById(stdId));
    }
}

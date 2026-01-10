package com.auth.controller;

import com.auth.dto.AuthUserDto;
import com.auth.service.AuthUserService;
import com.auth.service.JwtService;
import com.auth.service.GoogleTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class AuthUserController {

    private final AuthUserService authUserService;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final JwtService jwtService;

    public AuthUserController(AuthUserService authUserService, GoogleTokenVerifier googleTokenVerifier, JwtService jwtService) {
        this.authUserService = authUserService;
        this.googleTokenVerifier = googleTokenVerifier;
        this.jwtService = jwtService;
    }

    @PostMapping("/google")
    public ResponseEntity<AuthUserDto> googleLogin(@RequestBody Map<String, String> body) {
        String credential = body.get("credential");
        if (credential == null) return ResponseEntity.badRequest().build();

        try {
            Payload p = googleTokenVerifier.verify(credential);

            AuthUserDto dto = new AuthUserDto();
            dto.googleSub = p.getSubject();
            dto.email = p.getEmail();
            dto.displayName = (String) p.get("name");

            AuthUserDto mapped = authUserService.upsert(dto);
            mapped.token = jwtService.issueToken(mapped.ownerUserId, mapped.googleSub);
            System.out.println(mapped.googleSub);
            return ResponseEntity.ok(mapped);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }

}

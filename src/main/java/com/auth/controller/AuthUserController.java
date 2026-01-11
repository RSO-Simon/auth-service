package com.auth.controller;

import com.auth.dto.AuthUserDto;
import com.auth.service.AuthUserService;
import com.auth.service.JwtService;
import com.auth.service.GoogleTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(
        name = "Authentication",
        description = "Authentication endpoints using Google Sign-In and internal JWT issuance"
)
@RestController
@RequestMapping
public class AuthUserController {

    private final AuthUserService authUserService;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final JwtService jwtService;

    public AuthUserController(
            AuthUserService authUserService,
            GoogleTokenVerifier googleTokenVerifier,
            JwtService jwtService
    ) {
        this.authUserService = authUserService;
        this.googleTokenVerifier = googleTokenVerifier;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Authenticate using Google Sign-In",
            description = "Accepts a Google-issued ID token (credential) and exchanges it for an internally "
                    + "signed JWT access token. The returned token must be used for all authenticated API requests."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful, JWT issued",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthUserDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Missing Google credential in request body"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid or expired Google ID token"
            )
    })
    @PostMapping("/google")
    public ResponseEntity<AuthUserDto> googleLogin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request containing a Google ID token obtained via Google Sign-In",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    example = "{ \"credential\": \"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...\" }"
                            )
                    )
            )
            @RequestBody Map<String, String> body
    ) {
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

            return ResponseEntity.ok(mapped);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}
package com.auth.dto;
import io.swagger.v3.oas.annotations.media.Schema;

public class AuthUserDto {
    @Schema(
            description = "Internal identifier of the authenticated user",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public Long ownerUserId;

    @Schema(
            description = "Google subject identifier (sub claim) of the authenticated user",
            example = "109876543210987654321",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public String googleSub;

    @Schema(
            description = "Email address obtained from Google account",
            example = "user@example.com",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public String email;

    @Schema(
            description = "Display name of the authenticated user",
            example = "Captain Stormeye",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public String displayName;

    @Schema(
            description = "Internally issued JWT access token used for authenticating API requests",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public String token;

    public AuthUserDto() {}

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getGoogleSub() {
        return googleSub;
    }

    public void setGoogleSub(String googleSub) {
        this.googleSub = googleSub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

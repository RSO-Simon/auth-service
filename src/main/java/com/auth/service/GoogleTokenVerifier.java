package com.auth.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleTokenVerifier {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier(@Value("${google.client-id}") String clientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(
                Utils.getDefaultTransport(),
                Utils.getDefaultJsonFactory()
        )
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public GoogleIdToken.Payload verify(String token) throws Exception {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            throw new IllegalArgumentException("Invalid Google ID token");
        }
        return idToken.getPayload();
    }
}

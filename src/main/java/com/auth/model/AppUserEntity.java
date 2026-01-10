package com.auth.model;
import jakarta.persistence.*;

@Entity
@Table(
        name = "app_user",
        uniqueConstraints = @UniqueConstraint(name = "uk_app_user_google_sub", columnNames = "google_sub")
)
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "google_sub", nullable = false, updatable = false, length = 64)
    private String googleSub;

    @Column(name = "email")
    private String email;

    @Column(name = "display_name")
    private String displayName;

    protected AppUserEntity() { }

    public AppUserEntity(String googleSub, String email, String displayName) {
        this.googleSub = googleSub;
        this.email = email;
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public String getGoogleSub() {
        return googleSub;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
package com.donats.backend.user;

import com.donats.backend.donation.DonationEntity;
import com.donats.backend.fundraiser.FundraiserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FundraiserEntity> fundraisers;

    @OneToMany(mappedBy = "user")
    private List<DonationEntity> donations;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<FundraiserEntity> getFundraisers() {
        return fundraisers;
    }

    public void setFundraisers(List<FundraiserEntity> fundraiser) {
        this.fundraisers = fundraiser;
    }

    public List<DonationEntity> getDonations() {
        return donations;
    }

    public void setDonations(List<DonationEntity> donations) {
        this.donations = donations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

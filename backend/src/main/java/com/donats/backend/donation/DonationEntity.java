package com.donats.backend.donation;

import com.donats.backend.entities.UserEntity;
import com.donats.backend.fundraiser.FundraiserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "message")
    private String message;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundraiser_id", nullable = false)
    private FundraiserEntity fundraiser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DonationStatusEnum status;

    @Column(name = "order_id", unique = true)
    private String orderId;

    public DonationEntity() {
    }

    public DonationEntity(Long id, String name, LocalDateTime createdAt, String message, BigDecimal amount, FundraiserEntity fundraiser, UserEntity user, DonationStatusEnum status, String orderId) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.message = message;
        this.amount = amount;
        this.fundraiser = fundraiser;
        this.user = user;
        this.status = status;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FundraiserEntity getFundraiser() {
        return fundraiser;
    }

    public void setFundraiser(FundraiserEntity fundraiserEntity) {
        this.fundraiser = fundraiserEntity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    public DonationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DonationStatusEnum status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

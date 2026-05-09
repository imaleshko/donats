package com.donats.backend.update;

import com.donats.backend.fundraiser.FundraiserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fundraiser_updates")
public class FundraiserUpdateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundraiser_id", nullable = false)
    private FundraiserEntity fundraiser;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "title", nullable = false)
    private String title;

    public FundraiserUpdateEntity() {
    }

    public FundraiserUpdateEntity(Long id, LocalDateTime createdAt, FundraiserEntity fundraiser, String title, String message) {
        this.id = id;
        this.createdAt = createdAt;
        this.fundraiser = fundraiser;
        this.title = title;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FundraiserEntity getFundraiser() {
        return fundraiser;
    }

    public void setFundraiser(FundraiserEntity fundraiserEntity) {
        this.fundraiser = fundraiserEntity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

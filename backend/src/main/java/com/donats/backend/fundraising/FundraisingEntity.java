package com.donats.backend.fundraising;

import com.donats.backend.donation.DonationEntity;
import com.donats.backend.entities.UserEntity;
import com.donats.backend.update.FundraisingUpdateEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "fundraisings", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "slug"})})
public class FundraisingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "goal")
    private BigDecimal goal;

    @Column(name = "startedAt")
    @CreationTimestamp
    private LocalDateTime startedAt;

    @Column(name = "endedAt")
    private LocalDateTime endedAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "fundraising_images", joinColumns = @JoinColumn(name = "fundraising_id"))
    @Column(name = "imageUrls", nullable = false)
    private List<String> imageUrls;

    @Enumerated(EnumType.STRING)
    private FundraisingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "fundraising", cascade = CascadeType.ALL)
    private List<DonationEntity> donations;

    @OneToMany(mappedBy = "fundraising", cascade = CascadeType.ALL)
    private List<FundraisingUpdateEntity> updates;

    public FundraisingEntity() {
    }

    public FundraisingEntity(Long id, String title, String slug, String description, BigDecimal balance, BigDecimal goal, LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime updatedAt, List<String> imageUrls, FundraisingStatus status, UserEntity userEntity, List<DonationEntity> donations, List<FundraisingUpdateEntity> updates) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.balance = balance;
        this.goal = goal;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.updatedAt = updatedAt;
        this.imageUrls = imageUrls;
        this.status = status;
        this.user = userEntity;
        this.donations = donations;
        this.updates = updates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> images) {
        this.imageUrls = images;
    }

    public FundraisingStatus getStatus() {
        return status;
    }

    public void setStatus(FundraisingStatus status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    public List<DonationEntity> getDonations() {
        return donations;
    }

    public void setDonations(List<DonationEntity> donations) {
        this.donations = donations;
    }

    public List<FundraisingUpdateEntity> getUpdates() {
        return updates;
    }

    public void setUpdates(List<FundraisingUpdateEntity> updates) {
        this.updates = updates;
    }
}

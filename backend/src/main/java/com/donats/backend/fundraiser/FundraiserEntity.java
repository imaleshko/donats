package com.donats.backend.fundraiser;

import com.donats.backend.donation.DonationEntity;
import com.donats.backend.fundraiser.update.UpdateEntity;
import com.donats.backend.user.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "fundraisers", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "slug"})})
public class FundraiserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "goal")
    private BigDecimal goal;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ElementCollection
    @CollectionTable(name = "fundraiser_images", joinColumns = @JoinColumn(name = "fundraiser_id"))
    @Column(name = "image_url", nullable = false)
    private List<String> imageUrls;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FundraiserStatus status;

    @Column(name = "started_at")
    @CreationTimestamp
    private LocalDateTime startedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "fundraiser")
    private List<DonationEntity> donations;

    @OneToMany(mappedBy = "fundraiser")
    private List<UpdateEntity> updates;

    public FundraiserEntity() {
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

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public FundraiserStatus getStatus() {
        return status;
    }

    public void setStatus(FundraiserStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<DonationEntity> getDonations() {
        return donations;
    }

    public void setDonations(List<DonationEntity> donations) {
        this.donations = donations;
    }

    public List<UpdateEntity> getUpdates() {
        return updates;
    }

    public void setUpdates(List<UpdateEntity> updates) {
        this.updates = updates;
    }
}

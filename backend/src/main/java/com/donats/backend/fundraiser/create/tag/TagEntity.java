package com.donats.backend.fundraiser.create.tag;

import com.donats.backend.fundraiser.FundraiserEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tags")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<FundraiserEntity> fundraisers;

    public TagEntity() {
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

    public Set<FundraiserEntity> getFundraisers() {
        return fundraisers;
    }

    public void setFundraisers(Set<FundraiserEntity> fundraisers) {
        this.fundraisers = fundraisers;
    }
}

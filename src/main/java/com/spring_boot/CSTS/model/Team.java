package com.spring_boot.CSTS.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table(name = "teams")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") //bidirectional mapping
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
//    @JsonManagedReference
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package com.example.excel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Candidate {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String primarySkill;
    private String secondarySkill;
    private double rate;
    private int experience;
     @OneToOne(mappedBy = "candidate")
     @JsonBackReference
    private Requirement requirement;

    public Candidate() {
    }

    public Candidate(Long id, String name, String email, String primarySkill, String secondarySkill, double rate,
            int experience, Requirement requirement) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.primarySkill = primarySkill;
        this.secondarySkill = secondarySkill;
        this.rate = rate;
        this.experience = experience;
        this.requirement = requirement;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimarySkill() {
        return primarySkill;
    }

    public void setPrimarySkill(String primarySkill) {
        this.primarySkill = primarySkill;
    }

    public String getSecondarySkill() {
        return secondarySkill;
    }

    public void setSecondarySkill(String secondarySkill) {
        this.secondarySkill = secondarySkill;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

}

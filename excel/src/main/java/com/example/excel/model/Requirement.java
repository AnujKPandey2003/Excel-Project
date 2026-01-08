package com.example.excel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Requirement {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String primarySkill;
    private String secondarySkill;
    private String location;
    private String subLocation;
    private double billrate;
    private String status;
    private String comments;
    private String assigned;
      /* OWNER of user relationship */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    /* OWNER of candidate relationship */
    @OneToOne
    @JoinColumn(name = "candidate_id")
    @JsonManagedReference
    private Candidate candidate;

    public Requirement() {
    }

    public Requirement(Long id, String name, String description, String primarySkill, String secondarySkill,
            String location, String subLocation, double billrate, String status, String comments, String assigned,
            User user, Candidate candidate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.primarySkill = primarySkill;
        this.secondarySkill = secondarySkill;
        this.location = location;
        this.subLocation = subLocation;
        this.billrate = billrate;
        this.status = status;
        this.comments = comments;
        this.assigned = assigned;
        this.user = user;
        this.candidate = candidate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public double getBillrate() {
        return billrate;
    }

    public void setBillrate(double billrate) {
        this.billrate = billrate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

}

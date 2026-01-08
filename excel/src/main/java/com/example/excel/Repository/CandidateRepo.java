package com.example.excel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.excel.model.Candidate;

public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    List<Candidate> findByPrimarySkill(String primarySkill);
    List<Candidate> findBySecondarySkill(String secondarySkill);
    Boolean existsByEmail(String email);
    

}

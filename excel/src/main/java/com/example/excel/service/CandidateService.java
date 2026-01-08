package com.example.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.excel.Repository.CandidateRepo;
import com.example.excel.model.Candidate;

@Service
public class CandidateService {

    private final CandidateRepo candidateRepo;

    @Autowired
    public CandidateService(CandidateRepo candidateRepo) {
        this.candidateRepo = candidateRepo;
    }

    // 🔹 READ: cache list
    @Cacheable("candidates")
    public List<Candidate> getAllCandidates() {
        System.out.println("📦 Fetching candidates from DB");
        return candidateRepo.findAll();
    }

    // 🔹 WRITE: evict entire list
    @CacheEvict(value = "candidates", allEntries = true)
    public Candidate addCandidate(Candidate candidate) {
        return candidateRepo.save(candidate);
    }

    // 🔹 WRITE: evict entire list
    @CacheEvict(value = "candidates", allEntries = true)
    public Candidate updateCandidate(Long id, Candidate candidate) {
        Candidate existing = candidateRepo.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(candidate.getName());
        existing.setEmail(candidate.getEmail());
        existing.setPrimarySkill(candidate.getPrimarySkill());
        existing.setSecondarySkill(candidate.getSecondarySkill());
        existing.setExperience(candidate.getExperience());
        existing.setRate(candidate.getRate());

        return candidateRepo.save(existing);
    }

    // 🔹 WRITE: evict entire list
    @CacheEvict(value = "candidates", allEntries = true)
    public boolean deleteCandidate(Long id) {
        if (!candidateRepo.existsById(id)) return false;
        candidateRepo.deleteById(id);
        return true;
    }
}

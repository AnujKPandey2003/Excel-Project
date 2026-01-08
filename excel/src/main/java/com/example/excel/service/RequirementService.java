package com.example.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.example.excel.Repository.CandidateRepo;
import com.example.excel.Repository.RequirementRepo;
import com.example.excel.Repository.UserRepo;
import com.example.excel.model.Candidate;
import com.example.excel.model.Requirement;
import com.example.excel.model.User;

@Service
public class RequirementService {

    private final RequirementRepo requirementRepo;
    private final CandidateRepo candidateRepo;
    private final UserRepo userRepo;

    @Autowired
    public RequirementService(
            RequirementRepo requirementRepo,
            CandidateRepo candidateRepo,
            UserRepo userRepo) {
        this.requirementRepo = requirementRepo;
        this.candidateRepo = candidateRepo;
        this.userRepo = userRepo;
    }

    

    @Cacheable(value = "requirementsByUser", key = "#userId")
    public List<Requirement> getRequirementsByUser(Long userId) {
        return requirementRepo.findByUser_Id(userId);
    }

    @Cacheable(value = "requirementsByUserAndStatus", key = "#userId + '-' + #status")
    public List<Requirement> getRequirementsByUserAndStatus(Long userId, String status) {
        return requirementRepo.findByUserIdAndStatus(userId, status);
    }

    @Cacheable("allRequirements")
    public List<Requirement> getAllRequirements() {
        return requirementRepo.findAll();
    }



    @Caching(evict = {
        @CacheEvict(value = "allRequirements", allEntries = true),
        @CacheEvict(value = "requirementsByUser", allEntries = true),
        @CacheEvict(value = "requirementsByUserAndStatus", allEntries = true)
    })
    public Requirement addRequirement(Long userId, Requirement requirement) {

        User user = userRepo.findById(userId).orElse(null);
        if (user == null) return null;

        requirement.setUser(user);
        requirement.setStatus("OPEN");
        requirement.setCandidate(null);
        requirement.setAssigned(null);

        return requirementRepo.save(requirement);
    }

    @Caching(evict = {
        @CacheEvict(value = "allRequirements", allEntries = true),
        @CacheEvict(value = "requirementsByUser", allEntries = true),
        @CacheEvict(value = "requirementsByUserAndStatus", allEntries = true)
    })
    public boolean assignCandidate(Long requirementId, Long candidateId) {

        Requirement req = requirementRepo.findById(requirementId).orElse(null);
        Candidate cand = candidateRepo.findById(candidateId).orElse(null);

        if (req == null || cand == null || "CLOSED".equalsIgnoreCase(req.getStatus())) {
            return false;
        }

        req.setCandidate(cand);
        req.setAssigned(cand.getName());
        req.setPrimarySkill(cand.getPrimarySkill());
        req.setSecondarySkill(cand.getSecondarySkill());
        req.setBillrate(cand.getRate());

        requirementRepo.save(req);
        return true;
    }

    @Caching(evict = {
        @CacheEvict(value = "allRequirements", allEntries = true),
        @CacheEvict(value = "requirementsByUser", allEntries = true),
        @CacheEvict(value = "requirementsByUserAndStatus", allEntries = true)
    })
    public Requirement updateRequirement(Long id, Requirement incoming) {

        Requirement existing = requirementRepo.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(incoming.getName());
        existing.setDescription(incoming.getDescription());
        existing.setLocation(incoming.getLocation());
        existing.setSubLocation(incoming.getSubLocation());
        existing.setComments(incoming.getComments());

        if ("CLOSED".equalsIgnoreCase(incoming.getStatus())) {
            existing.setStatus("CLOSED");
            existing.setCandidate(null);
            existing.setAssigned(null);
            existing.setPrimarySkill(null);
            existing.setSecondarySkill(null);
            existing.setBillrate(0);
        } else {
            existing.setStatus("OPEN");
        }

        return requirementRepo.save(existing);
    }

    @Caching(evict = {
        @CacheEvict(value = "allRequirements", allEntries = true),
        @CacheEvict(value = "requirementsByUser", allEntries = true),
        @CacheEvict(value = "requirementsByUserAndStatus", allEntries = true)
    })
    public boolean deleteRequirement(Long id) {
        if (!requirementRepo.existsById(id)) return false;
        requirementRepo.deleteById(id);
        return true;
    }
}

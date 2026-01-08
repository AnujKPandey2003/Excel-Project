package com.example.excel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.excel.model.Requirement;

@Repository
public interface RequirementRepo extends JpaRepository<Requirement, Long> {
    
    List<Requirement> findByPrimarySkill(String primarySkill);

    List<Requirement> findBySecondarySkill(String secondarySkill);
    List<Requirement> findByLocation(String location);
    List<Requirement> findByStatus(String status);
    
    List<Requirement> findByUser_Id(Long userId);

    List<Requirement> findByUserIdAndStatus(Long userId, String status);
}

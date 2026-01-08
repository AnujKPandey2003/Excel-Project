package com.example.excel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.excel.model.Requirement;
import com.example.excel.service.RequirementService;

@RestController
@RequestMapping("/api/requirements")
public class RequirementController {

    private final RequirementService requirementService;

    @Autowired
    public RequirementController(RequirementService requirementService) {
        this.requirementService = requirementService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Requirement>> getAllRequirements() {
        List<Requirement> list = requirementService.getAllRequirements();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }
    /* Manager-based filtering */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Requirement>> getByUser(@PathVariable Long userId) {
        List<Requirement> list = requirementService.getRequirementsByUser(userId);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @PostMapping("/add/user/{userId}")
    public ResponseEntity<Requirement> addRequirement(
            @PathVariable Long userId,
            @RequestBody Requirement requirement) {

        Requirement saved = requirementService.addRequirement(userId, requirement);
        return saved == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(saved);
    }

    @PutMapping("/assign/{reqId}/candidate/{candId}")
    public ResponseEntity<String> assignCandidate(
            @PathVariable Long reqId,
            @PathVariable Long candId) {

        return requirementService.assignCandidate(reqId, candId)
                ? ResponseEntity.ok("Candidate assigned")
                : ResponseEntity.badRequest().body("Assignment failed");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Requirement> update(
            @PathVariable Long id,
            @RequestBody Requirement req) {

        Requirement updated = requirementService.updateRequirement(id, req);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRequirement(@PathVariable Long id) {
        boolean isDeleted = requirementService.deleteRequirement(id);
        if (isDeleted) {
            return ResponseEntity.ok("Requirement deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

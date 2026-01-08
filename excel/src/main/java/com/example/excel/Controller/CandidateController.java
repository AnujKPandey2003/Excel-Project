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

import com.example.excel.model.Candidate;
import com.example.excel.service.CandidateService;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/all")
    
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> canidates = candidateService.getAllCandidates();
        if (canidates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(canidates);
    }

    @PostMapping("/add")
    public ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
        Candidate addedCandidate = candidateService.addCandidate(candidate);
        if (addedCandidate != null) {
            return ResponseEntity.status(200).body(addedCandidate);
        }
        return ResponseEntity.noContent().build();

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id,@RequestBody Candidate candidate) {
        Candidate updatedCandidate = candidateService.updateCandidate(id, candidate);
        if (updatedCandidate != null) {
            return ResponseEntity.ok(updatedCandidate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id){
        boolean isDeleted = candidateService.deleteCandidate(id);
        if(isDeleted){
            return ResponseEntity.ok("Candidate deleted successfully.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

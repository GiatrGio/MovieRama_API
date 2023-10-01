package com.example.movierama_api.controllers;

import com.example.movierama_api.models.Vote;
import com.example.movierama_api.services.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping()
    public ResponseEntity<String> updateVote(@RequestBody Vote vote) {
        if (voteService.userVotesOwnMovie(vote)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users cannot vote their own movies");
        }

        try {
            voteService.updateVote(vote);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vote successfully updated");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vote update failed");
        }
    }

    @DeleteMapping("/{voteId}")
    public ResponseEntity<String> deleteVote(@PathVariable Long voteId) {
        try {
            voteService.deleteVote(voteId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vote successfully deleted");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vote deletion failed");
        }
    }
}

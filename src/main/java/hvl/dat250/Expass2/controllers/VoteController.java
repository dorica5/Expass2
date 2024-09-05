package hvl.dat250.Expass2.controllers;

import hvl.dat250.Expass2.DomainManager;
import hvl.dat250.Expass2.ResourceNotFoundException;
import hvl.dat250.Expass2.domain.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls/{pollId}/options/{optionId}/votes")

public class VoteController {
    private final DomainManager manager;

    public VoteController(@Autowired DomainManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getVotes(@PathVariable String pollId, @PathVariable String optionId) {
        try {
            List<Vote> votes = manager.getVotes(pollId, optionId);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@PathVariable String pollId, @RequestParam String userId, @RequestParam String optionId) {
        try {
            Vote vote = manager.createVote(userId, pollId, optionId);
            return ResponseEntity.status(HttpStatus.CREATED).body(vote);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping("/{voteId}")
    public ResponseEntity<Void> deleteVote(@PathVariable String pollId, @RequestParam String optionId, @PathVariable String voteId) {
        try {
            manager.deleteVote(pollId, optionId, voteId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
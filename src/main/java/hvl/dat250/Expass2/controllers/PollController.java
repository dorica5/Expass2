package hvl.dat250.Expass2.controllers;

import hvl.dat250.Expass2.DomainManager;
import hvl.dat250.Expass2.ResourceNotFoundException;
import hvl.dat250.Expass2.domain.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls")
public class PollController {
    private final DomainManager manager;

    public PollController(@Autowired DomainManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getPolls() {
        List<Poll> polls = manager.getPolls();
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable String id) {
        try {
            Poll poll = manager.getPoll(id);
            return ResponseEntity.ok(poll);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping

    public ResponseEntity<Poll> createPoll(@RequestParam String userId, @RequestParam String question) {
        try {
            Poll poll = manager.createPoll(userId, question);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(poll);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poll> updatePoll(@PathVariable String id, @RequestParam String question) {
        try {
            Poll updatedPoll = manager.updatePoll(id, question);
            return ResponseEntity.ok(updatedPoll);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable String id) {
        try {
            manager.deletePoll(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
package hvl.dat250.Expass2.controllers;

import hvl.dat250.Expass2.DomainManager;
import hvl.dat250.Expass2.ResourceNotFoundException;
import hvl.dat250.Expass2.domain.VoteOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/polls/{pollId}/options")
public class VoteOptionController {
    private final DomainManager manager;

    public VoteOptionController(@Autowired DomainManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public ResponseEntity<List<VoteOption>> getVoteOptions(@PathVariable String pollId) {
        try {
            List<VoteOption> options = manager.getOptions(pollId);
            return ResponseEntity.ok(options);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{optionId}")
    public ResponseEntity<VoteOption> getVoteOption(@PathVariable String pollId, @PathVariable String optionId) {
        try {
            VoteOption option = manager.getOption(pollId, optionId);
            return ResponseEntity.ok(option);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<VoteOption> createVoteOption(@PathVariable String pollId, @RequestParam String caption) {
        try {
            VoteOption option = manager.createOption(pollId, caption);
            return ResponseEntity.status(HttpStatus.CREATED).body(option);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{optionId}")
    public ResponseEntity<VoteOption> updateVoteOption(@PathVariable String pollId, @PathVariable String optionId, @RequestParam String caption) {
        try {
            VoteOption updatedOption = manager.updateOption(pollId, optionId, caption);
            return ResponseEntity.ok(updatedOption);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteVoteOption(@PathVariable String pollId, @PathVariable String optionId) {
        try {
            manager.deleteOption(pollId, optionId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
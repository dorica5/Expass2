package hvl.dat250.Expass2;

import hvl.dat250.Expass2.domain.Poll;
import hvl.dat250.Expass2.domain.User;
import hvl.dat250.Expass2.domain.Vote;
import hvl.dat250.Expass2.domain.VoteOption;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DomainManager {
    private final PollManager repo;

    public DomainManager(PollManager repo) {
        this.repo = repo;
    }

    // User operations
    public User getUser(String id) {
        User user = repo.getUser(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    public List<User> getUsers() {
        return new ArrayList<>(repo.getUsers().values());
    }

    public User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return repo.createUser(user);
    }

    public User updateUser(String id, String username, String email) {
        User user = getUser(id);
        user.setUsername(username);
        user.setEmail(email);
        return repo.updateUser(id, user);
    }

    public void deleteUser(String id) {
        if (repo.deleteUser(id) == null) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    // Poll operations
    public Poll getPoll(String id) {
        Poll poll = repo.getPoll(id);
        if (poll == null) {
            throw new ResourceNotFoundException("Poll not found");
        }
        return poll;
    }

    public List<Poll> getPolls() {
        return new ArrayList<>(repo.getPolls().values());
    }

    public Poll createPoll(String userId, String question) {
        User user = getUser(userId);
        Poll poll = new Poll();
        poll.setQuestion(question);
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(86400)); // Valid for 24 hours
        return repo.createPoll(user, poll);
    }

    public Poll updatePoll(String id, String question) {
        Poll poll = getPoll(id);
        poll.setQuestion(question);
        return repo.updatePoll(id, poll);
    }

    public void deletePoll(String id) {
        if (repo.deletePoll(id) == null) {
            throw new ResourceNotFoundException("Poll not found");
        }
    }

    // VoteOption operations
    public VoteOption createOption(String pollId, String caption) {
        Poll poll = getPoll(pollId);
        VoteOption option = new VoteOption();
        option.setCaption(caption);
        return repo.addOptionToPoll(pollId, option);
    }

    public VoteOption getOption(String pollId, String optionId) {
        Poll poll = getPoll(pollId);
        VoteOption option = poll.getOptions().get(optionId);
        if (option == null) {
            throw new ResourceNotFoundException("Option not found");
        }
        return option;
    }

    public List<VoteOption> getOptions(String pollId) {
        Poll poll = getPoll(pollId);
        return new ArrayList<>(poll.getOptions().values());
    }

    public VoteOption updateOption(String pollId, String optionId, String newCaption) {
        Poll poll = getPoll(pollId);
        VoteOption option = getOption(pollId, optionId);
        VoteOption updatedOption = repo.updateOption(pollId, optionId, newCaption);
        if (updatedOption == null) {
            throw new ResourceNotFoundException("Failed to update option");
        }
        return updatedOption;
    }

    public void deleteOption(String pollId, String optionId) {
        Poll poll = getPoll(pollId);
        VoteOption option = getOption(pollId, optionId);
        boolean deleted = repo.deleteOption(pollId, optionId);
        if (!deleted) {
            throw new ResourceNotFoundException("Failed to delete option");
        }
    }

    public List<Vote> getVotes(String pollId, String optionId) {
        VoteOption option = getOption(pollId, optionId);
        return option.getVotes();
    }

    // Vote operations
    public Vote createVote(String userId, String pollId, String optionId) {
        User user = getUser(userId);
        Poll poll = getPoll(pollId);
        VoteOption option = getOption(pollId, optionId);

        Instant now = Instant.now();
        if (now.isBefore(poll.getPublishedAt()) || now.isAfter(poll.getValidUntil())) {
            throw new IllegalStateException("Poll is not active. Voting is only allowed between "
                    + poll.getPublishedAt() + " and " + poll.getValidUntil());
        }

        Vote vote = repo.createVote(userId, pollId, optionId);
        if (vote == null) {
            throw new ResourceNotFoundException("Failed to create vote. User, poll, or option not found.");
        }
        return vote;
    }

    public void deleteVote(String pollId, String optionId, String voteId) {
        Poll poll = getPoll(pollId);
        VoteOption option = getOption(pollId, optionId);
        boolean deleted = repo.deleteVote(pollId, optionId, voteId);
        if (!deleted) {
            throw new ResourceNotFoundException("Failed to delete vote");
        }
    }
}


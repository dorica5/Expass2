package hvl.dat250.Expass2;

import hvl.dat250.Expass2.domain.Poll;
import hvl.dat250.Expass2.domain.User;
import hvl.dat250.Expass2.domain.Vote;
import hvl.dat250.Expass2.domain.VoteOption;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PollManager {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, Poll> polls = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();
    private final AtomicLong presentationCounter = new AtomicLong();
    public PollManager() {}

    private String generateId() {
        return String.valueOf(idCounter.incrementAndGet());
    }

    // User operations
    public User getUser(String id) {
        return users.get(id);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public User createUser(User user) {
        String id = generateId();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public User updateUser(String id, User updatedUser) {
        if (users.containsKey(id)) {
            updatedUser.setId(id);
            return users.put(id, updatedUser);
        }
        return null;
    }

    public User deleteUser(String id) {
        return users.remove(id);
    }
    // User verification method
    public User verifyUser(String username, String email) throws ResourceNotFoundException {

        for (User user : users.values()) {
            if (user.getEmail().equals(email) && user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new ResourceNotFoundException("User not found");
    }



    // Poll operations
    public Poll getPoll(String id) {
        return polls.get(id);
    }

    public Map<String, Poll> getPolls() {
        return polls;
    }

    public List<Poll> getPollsForFrontend() {
        List<Poll> pollsForFrontend = new ArrayList<>();

        for (Poll poll : polls.values()) {
            Poll pollCopy = new Poll();
            pollCopy.setId(poll.getId());
            pollCopy.setQuestion(poll.getQuestion());
            pollCopy.setPublishedAt(poll.getPublishedAt());
            pollCopy.setValidUntil(poll.getValidUntil());


            List<VoteOption> optionsList = new ArrayList<>(poll.getOptions().values());
            pollCopy.setOptionsList(optionsList);

            pollsForFrontend.add(pollCopy);
        }

        return pollsForFrontend;
    }


    public Poll createPoll(User user, Poll poll) {

        if (user == null) {
            System.out.println("We are here");
            throw new ResourceNotFoundException("User not found");
        }
        String id = generateId();
        poll.setId(id);
        polls.put(id, poll);
        return poll;
    }

    public Poll updatePoll(String id, Poll updatedPoll) {
        if (polls.containsKey(id)) {
            updatedPoll.setId(id);
            return polls.put(id, updatedPoll);
        }
        return null;
    }

    public Poll deletePoll(String id) {
        return polls.remove(id);
    }

    // VoteOption operations
    public VoteOption addOptionToPoll(String pollId, VoteOption option) {
        Poll poll = polls.get(pollId);
        if (poll == null) {
            return null;
        }
        String optionId = generateId();
        option.setId(optionId);
        option.setPresentationOrder((presentationCounter.incrementAndGet()));
        poll.getOptions().put(optionId, option);
        return option;
    }
    public VoteOption updateOption(String pollId, String optionId, String newCaption) {
        Poll poll = polls.get(pollId);
        if (poll == null) {
            return null;
        }
        VoteOption option = poll.getOptions().get(optionId);
        if (option == null) {
            return null;
        }
        option.setCaption(newCaption);
        return option;
    }

    public boolean deleteOption(String pollId, String optionId) {
        Poll poll = polls.get(pollId);
        if (poll == null) {
            return false;
        }
        VoteOption removedOption = poll.getOptions().remove(optionId);
        return removedOption != null;
    }

    // Vote operations
    public Vote createVote(String userId, String pollId, String optionId) {
        User user = users.get(userId);
        Poll poll = polls.get(pollId);
        if (user == null || poll == null) {
            return null;
        }

        for (VoteOption option : poll.getOptions().values()) {
            option.getVotes().removeIf(vote -> vote.getUser().getId().equals(userId));
        }

        VoteOption option = poll.getOptions().get(optionId);
        if (option == null) {
            return null;
        }

        Vote vote = new Vote();
        vote.setId(generateId());
        vote.setVoteOption(option);
        vote.setUser(user);
        option.addVote(vote);
        return vote;
    }

    public boolean deleteVote(String pollId, String optionId, String voteId) {
        Poll poll = polls.get(pollId);
        if (poll == null) {
            return false;
        }
        VoteOption option = poll.getOptions().get(optionId);
        if (option == null) {
            return false;
        }
        return option.getVotes().removeIf(vote -> vote.getId().equals(voteId));
    }
}
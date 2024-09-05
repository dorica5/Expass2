package hvl.dat250.Expass2;

import hvl.dat250.Expass2.domain.Poll;
import hvl.dat250.Expass2.domain.User;
import hvl.dat250.Expass2.domain.Vote;
import hvl.dat250.Expass2.domain.VoteOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollAppIntegrationTest {

    @LocalServerPort
    private int port;

    RestClient customClient;

    @BeforeEach
    public void setup() {
        customClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void testPollAppScenario() {
        // 1. Create a new user
        User user1 = createUser("user1", "user1@example.com");
        assertNotNull(user1.getId());

        // 2. List all users
        User[] users = getUsers();
        assertEquals(1, users.length);
        assertEquals(user1.getId(), users[0].getId());

        // 3. Create another user
        User user2 = createUser("user2", "user2@example.com");
        assertNotNull(user2.getId());

        // 4. List all users again
        users = getUsers();
        assertEquals(2, users.length);

        // 5. User 1 creates a new poll
        Poll poll = createPoll(user1.getId(), "What's your favorite color?");
        assertNotNull(poll.getId());

        // Add options to the poll
        VoteOption option1 = addOptionToPoll(poll.getId(), "Red");
        VoteOption option2 = addOptionToPoll(poll.getId(), "Blue");
        VoteOption option3 = addOptionToPoll(poll.getId(), "Green");

        // 6. List polls
        Poll[] polls = getPolls();
        assertEquals(1, polls.length);
        assertEquals(poll.getId(), polls[0].getId());

        // 7. User 2 votes on the poll
        Vote vote1 = createVote(poll.getId(), option1.getId(), user2.getId());
        assertNotNull(vote1);

        // 8. User 2 changes his vote
        Vote vote2 = createVote(poll.getId(), option2.getId(), user2.getId());
        assertNotNull(vote2);

        // 9. List votes
        Vote[] votes = getVotes(poll.getId(), option2.getId());
        assertEquals(1, votes.length);
        assertEquals(user2.getId(), votes[0].getUser().getId());

        // 10. Delete the poll
        deletePoll(poll.getId());

        // 11. List votes (should throw an exception)
        assertThrows(Exception.class, () -> getVotes(poll.getId(), option2.getId()));
    }

    private User createUser(String username, String email) {
        return customClient.post()
                .uri("/users?username={username}&email={email}", username, email)
                .retrieve()
                .body(User.class);
    }

    private User[] getUsers() {
        return customClient.get()
                .uri("/users")
                .retrieve()
                .body(User[].class);
    }

    private Poll createPoll(String userId, String question) {
        return customClient.post()
                .uri("/polls?userId={userId}&question={question}", userId, question)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Poll.class);
    }

    private VoteOption addOptionToPoll(String pollId, String caption) {
        return customClient.post()
                .uri("/polls/{pollId}/options?caption={caption}", pollId, caption)
                .retrieve()
                .body(VoteOption.class);
    }

    private Poll[] getPolls() {
        return customClient.get()
                .uri("/polls")
                .retrieve()
                .body(Poll[].class);
    }

    private Vote createVote(String pollId, String optionId, String userId) {
        return customClient.post()
                .uri("/polls/{pollId}/options/{optionId}/votes?userId={userId}", pollId, optionId, userId)
                .retrieve()
                .body(Vote.class);
    }

    private Vote[] getVotes(String pollId, String optionId) {
        return customClient.get()
                .uri("/polls/{pollId}/options/{optionId}/votes", pollId, optionId)
                .retrieve()
                .body(Vote[].class);
    }

    private void deletePoll(String pollId) {
        customClient.delete()
                .uri("/polls/{pollId}", pollId)
                .retrieve()
                .toBodilessEntity();
    }

    @Test
    public void contextLoads() {
        assertNotNull(customClient);
    }
}
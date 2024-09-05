package hvl.dat250.Expass2.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.Instant;
import java.util.Calendar;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Vote {
    private Instant publishedAt;
    private String id;
    private User user;
    private VoteOption voteOption;

    public Vote() {
        publishedAt = Instant.now();
        id = "";
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}

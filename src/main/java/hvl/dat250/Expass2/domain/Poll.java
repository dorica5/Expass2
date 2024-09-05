package hvl.dat250.Expass2.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.Time;
import java.time.Instant;
import java.util.*;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Poll {
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private String id;
    @JsonManagedReference
    private final Map<String, VoteOption> options;

    public Poll() {
        question = "";
        publishedAt = Calendar.getInstance().toInstant();
        validUntil = Calendar.getInstance().toInstant();
        id = "";
        options = new HashMap<>();

    }

    public String getQuestion() {
        return question;
    }

    public String getId() {
        return id;
    }

    public Map<String, VoteOption> getOptions() {
        return options;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }
}

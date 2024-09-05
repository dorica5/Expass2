package hvl.dat250.Expass2.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Poll {
    @JsonProperty("question")
    private String question;

    @JsonProperty("publishedAt")
    private Instant publishedAt;

    @JsonProperty("validUntil")
    private Instant validUntil;

    @JsonProperty("id")
    private String id;

    @JsonManagedReference
    @JsonProperty("options")
    private Map<String, VoteOption> options;

    public Poll() {
        this.question = "";
        this.publishedAt = Instant.now();
        this.validUntil = Instant.now().plusSeconds(86400); // 24 hours from now
        this.id = UUID.randomUUID().toString();
        this.options = new HashMap<>();
    }

    // Getters and setters for all fields

    public String getQuestion() {
        return question;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, VoteOption> getOptions() {
        return options;
    }

}
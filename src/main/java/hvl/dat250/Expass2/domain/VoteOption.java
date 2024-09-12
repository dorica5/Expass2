package hvl.dat250.Expass2.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;

public class VoteOption {

    private String caption;
    private long presentationOrder;
    private String id;
    @JsonManagedReference
    private List<Vote> votes;


    public VoteOption() {
        this.caption = "";
        this.presentationOrder = 0;
        this.id = "";
        this.votes = new ArrayList<>();
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public long getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(long presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Vote addVote(Vote vote) {
        votes.add(vote);
        return vote;
    }
}

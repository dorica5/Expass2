package hvl.dat250.Expass2.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class VoteOption {
    private String caption;
    private int presentationOrder = 0;
    private String id;
    private final List<Vote> votes;

    public VoteOption() {
        caption = "";
        setPresentationOrder(getPresentationOrder()+1);
        id = "";
        votes = new ArrayList<>();
    }

    public List<Vote> getVotes() {
        return votes;
    }
    public Vote addVote(Vote vote) {
        votes.add(vote);
        return vote;
    }


    public String getCaption() {
        return caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }
}

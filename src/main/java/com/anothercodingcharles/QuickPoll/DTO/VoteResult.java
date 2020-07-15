package com.anothercodingcharles.QuickPoll.DTO;


import java.util.Collection;
import java.util.List;

public class VoteResult {

    private int totalVotes;
    private Collection<OptionCount> results;

    public VoteResult() {

    }
    public VoteResult(int total_votes, List<OptionCount> optionCountList) {
        this.totalVotes = total_votes;
        this.results = optionCountList;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int total_votes) {
        this.totalVotes = total_votes;
    }

    public Collection<OptionCount> getResults() {
        return results;
    }

    public void setResults(Collection<OptionCount> optionCountList) {
        this.results = optionCountList;
    }

    @Override
    public String toString() {
        return "VoteResult{" +
                "total_votes=" + totalVotes +
                ", optionCountList=" + results +
                '}';
    }
}

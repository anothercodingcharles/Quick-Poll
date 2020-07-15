package com.anothercodingcharles.QuickPoll.Services;

import com.anothercodingcharles.QuickPoll.Domain.Vote;
import com.anothercodingcharles.QuickPoll.Repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    public Iterable<Vote> getVotes(Long pollId) {
        return voteRepository.findByPoll(pollId);
    }

}

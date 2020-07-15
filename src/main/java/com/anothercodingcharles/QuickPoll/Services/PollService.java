package com.anothercodingcharles.QuickPoll.Services;

import com.anothercodingcharles.QuickPoll.Domain.Poll;
import com.anothercodingcharles.QuickPoll.Exception.ResourceNotFoundException;
import com.anothercodingcharles.QuickPoll.Repository.PollRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollService {

    private PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }


    public Page<Poll> getAllPolls(Pageable pageable) {
        return pollRepository.findAll(pageable);
    }

    public Poll getPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (!poll.isPresent())
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        return poll.get();
    }


    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public void deletePoll(Long pollId) {
        pollRepository.deleteById(pollId);
    }


}

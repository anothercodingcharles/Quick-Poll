package com.anothercodingcharles.QuickPoll.RestController;


import com.anothercodingcharles.QuickPoll.Domain.Option;
import com.anothercodingcharles.QuickPoll.Domain.Poll;
import com.anothercodingcharles.QuickPoll.Domain.Vote;
import com.anothercodingcharles.QuickPoll.Exception.ResourceNotFoundException;
import com.anothercodingcharles.QuickPoll.Services.PollService;
import com.anothercodingcharles.QuickPoll.Services.VoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
public class VoteController {

    private final VoteService voteService;
    private final PollService pollService;

    public VoteController(VoteService voteService, PollService pollService) {
        this.voteService = voteService;
        this.pollService = pollService;
    }

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.POST)
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @Valid @RequestBody Vote vote) {


        Poll poll = pollService.getPoll(pollId);
        Set<Long> validIds = new HashSet<>();

        for (Option option : poll.getOptions()) {
            validIds.add(option.getId());
        }

        if (!validIds.contains(vote.getOption().getId()))
            throw new ResourceNotFoundException("Poll with option id " + vote.getId() + " not found");

        voteService.save(vote);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newVoteURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri();
        responseHeaders.setLocation(newVoteURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.GET)
    public Iterable<Vote> getVotes(@PathVariable Long pollId) {
        pollService.getPoll(pollId);
        return voteService.getVotes(pollId);
    }


}

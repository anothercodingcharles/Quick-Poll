package com.anothercodingcharles.QuickPoll.RestController;

import com.anothercodingcharles.QuickPoll.Domain.Poll;
import com.anothercodingcharles.QuickPoll.Services.PollService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @RequestMapping(value = "/polls", method = RequestMethod.GET)
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {
        Page<Poll> allPolls = pollService.getAllPolls(pageable);
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value = "/polls", method = RequestMethod.POST)
    public ResponseEntity<Poll> createPoll(@RequestBody @Valid Poll poll) {

        pollService.createPoll(poll);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(poll.getId()).toUri();
        responseHeaders.setLocation(newPollURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        Poll poll = pollService.getPoll(pollId);
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }


    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@RequestBody @Valid Poll poll, @PathVariable Long pollId) {

        pollService.getPoll(pollId);
        pollService.createPoll(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {

        pollService.getPoll(pollId);
        pollService.deletePoll(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



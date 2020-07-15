package com.anothercodingcharles.QuickPoll.RestController;


import com.anothercodingcharles.QuickPoll.DTO.OptionCount;
import com.anothercodingcharles.QuickPoll.DTO.VoteResult;
import com.anothercodingcharles.QuickPoll.Domain.Poll;
import com.anothercodingcharles.QuickPoll.Domain.Vote;
import com.anothercodingcharles.QuickPoll.Exception.ResourceNotFoundException;
import com.anothercodingcharles.QuickPoll.Repository.PollRepository;
import com.anothercodingcharles.QuickPoll.Repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ComputeResultController {


    private final VoteRepository voteRepository;
    private final PollRepository pollRepository;

    public ComputeResultController(VoteRepository voteRepository,PollRepository pollRepository) {
        this.voteRepository = voteRepository;
        this.pollRepository = pollRepository;

    }


    private void  verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(!poll.isPresent())
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
    }

    @RequestMapping(value = "/computeResult", method = RequestMethod.GET)
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {


        verifyPoll(pollId);
        VoteResult voteResult = new VoteResult();
        Iterable<Vote> votes =  voteRepository.findByPoll(pollId);

        Map<Long, OptionCount> map = new HashMap<>();
        int totalVotes = 0;
        for(Vote vote : votes){
            totalVotes++;
            if(!map.containsKey(vote.getOption().getId())){
                OptionCount optionCount =  new OptionCount();
                optionCount.setOptionId(vote.getOption().getId());
                map.put(vote.getOption().getId(),optionCount);
            }

            OptionCount optionCount = map.get(vote.getOption().getId());
            optionCount.setCount(optionCount.getCount()+1);
        }
        //logic compute result
        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(map.values());
        return new ResponseEntity<>(voteResult, HttpStatus.OK);
    }



}
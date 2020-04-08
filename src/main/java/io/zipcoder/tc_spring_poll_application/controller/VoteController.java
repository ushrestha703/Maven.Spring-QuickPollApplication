package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.Repository.VoteRepository;
import io.zipcoder.tc_spring_poll_application.domain.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class VoteController {
    private VoteRepository voteRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.POST)
    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote
            vote) {
        vote = voteRepository.save(vote);
        // Set the headers for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

        @RequestMapping(value="/polls/votes", method=RequestMethod.GET)
    @GetMapping("/polls/votes")
    public Iterable<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

       @RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.GET)
    @GetMapping("/polls/{pollId}/votes")
    public Iterable<Vote> getVote(@PathVariable Long pollId) {
        return voteRepository.findVotesByPoll(pollId);
    }

//    @RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.GET)
//    public Iterable<Vote> getVote(@PathVariable Long pollId) {
//        return voteRepository.findById(pollId);
//    }
}

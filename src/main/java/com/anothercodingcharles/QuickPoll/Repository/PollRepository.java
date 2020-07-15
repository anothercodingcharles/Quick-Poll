package com.anothercodingcharles.QuickPoll.Repository;


import com.anothercodingcharles.QuickPoll.Domain.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
        Page<Poll> findAll(Pageable page);
}

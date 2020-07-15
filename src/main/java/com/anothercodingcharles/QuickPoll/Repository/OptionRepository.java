package com.anothercodingcharles.QuickPoll.Repository;

import com.anothercodingcharles.QuickPoll.Domain.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository<Option, Long> {

}


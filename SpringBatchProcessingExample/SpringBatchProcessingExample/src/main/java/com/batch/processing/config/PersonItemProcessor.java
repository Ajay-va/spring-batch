package com.batch.processing.config;

import com.batch.processing.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger LOGGER= LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception {


        final String firstName=person.firstName().toUpperCase();
        final String lastName=person.lastName().toUpperCase();

        final Person transformedPerson=new Person(firstName,lastName);

        LOGGER.info("(converting ("+person+") into ("+transformedPerson+"))  ");

        return transformedPerson;
    }
}

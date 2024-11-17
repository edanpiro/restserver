package com.idat.restserver.repository;

import com.idat.restserver.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);
}

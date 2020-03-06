package com.zmijewski.adam.addressbook.repository;

import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByLastnameAndUser(String lastname, User user);
    void deleteByIdAndUser(Long id, User user);
    List<Person> findAllByUser(User user);
    Optional<Person> findByIdAndUser(Long id, User user);
}

package com.zmijewski.adam.addressbook.repository;

import com.zmijewski.adam.addressbook.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByLastname(String lastname);
    void deleteById(Long id);
    @Query("update Person p set p.firstname=:firstname where p=:person")
    @Modifying
    void updateFirstname(@Param("firstname") String firstname, @Param("person") Person person);
    @Query("update Person p set p.lastname=:lastname where p=:person")
    @Modifying
    void updateLastname(@Param("lastname") String lastname, @Param("person") Person person);
}

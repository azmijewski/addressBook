package com.zmijewski.adam.addressbook.repository;

import com.zmijewski.adam.addressbook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("update Contact c set c.mobileNumber = :mobileNumber where c = :contact")
    @Modifying
    void updateMobileNumber(@Param("mobileNumber") String mobileNumber, @Param("contact") Contact contact);
    @Query("update Contact c set c.email = :email where c = :contact")
    @Modifying
    void updateEmail(@Param("email") String email, @Param("contact") Contact contact);
}

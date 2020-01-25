package com.zmijewski.adam.addressbook.repository;

import com.zmijewski.adam.addressbook.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("UPDATE Address c set c.street=:street where c=:address")
    @Modifying
    void updateStreet(@Param("street") String street, @Param("address") Address address);
    @Query("UPDATE Address c set c.houseNumber=:houseNumber where c=:address")
    @Modifying
    void updateHouseNumber(@Param("houseNumber") String houseNumber, @Param("address") Address address);
    @Query("UPDATE Address c set c.city=:city where c=:address")
    @Modifying
    void updateCity(@Param("city") String city, @Param("address") Address address);
}

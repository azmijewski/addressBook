package com.zmijewski.adam.addressbook.util;

import com.zmijewski.adam.addressbook.dto.AddressDto;
import com.zmijewski.adam.addressbook.model.Address;


import java.util.Optional;

public class AddressToDtoConverter implements AbstractConventerToDto<Address, AddressDto> {
    @Override
    public  AddressDto convert(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(Optional.ofNullable(address.getStreet()));
        addressDto.setHouseNumber(Optional.ofNullable(address.getHouseNumber()));
        addressDto.setCity(Optional.of(address.getCity()));
        return addressDto;
    }
}

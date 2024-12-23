package com.address.service.service;

import com.address.service.dto.AddressDto;
import com.address.service.entity.Address;
import com.address.service.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository repository;
    @InjectMocks
    private AddressServiceImpl service;
    private Address address;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        address = new Address(1,"guntur","guntur",
                "AP",556677,1234);

        addressDto = new AddressDto(1,"guntur","guntur",
                "AP",556677,1234);
    }

    @Test
    void addAddress() {
        when(repository.save(any(Address.class))).thenReturn(address);
        Address saveAddress = service.addAddress(addressDto);

        assertNotNull(saveAddress);
        assertEquals(addressDto.getAddressId(),saveAddress.getAddressId());
        assertEquals(addressDto.getCityName(),saveAddress.getCityName());
        assertEquals(addressDto.getDistrict(),saveAddress.getDistrict());
        assertEquals(addressDto.getZipcode(),saveAddress.getZipcode());
        assertEquals(addressDto.getState(),saveAddress.getState());
        assertEquals(addressDto.getEmpId(),saveAddress.getEmpId());
        verify(repository,times(1)).save(any(Address.class));
    }

    @Test
    void getAddressByEmpId() {
        when(repository.findByEmpId(anyInt())).thenReturn(Optional.ofNullable(address));
        Address fetchAddress = service.getAddressByEmpId(anyInt());

        assertNotNull(fetchAddress);
        assertEquals(addressDto.getAddressId(),fetchAddress.getAddressId());
        assertEquals(addressDto.getCityName(),fetchAddress.getCityName());
        assertEquals(addressDto.getDistrict(),fetchAddress.getDistrict());
        assertEquals(addressDto.getZipcode(),fetchAddress.getZipcode());
        assertEquals(addressDto.getState(),fetchAddress.getState());
        assertEquals(addressDto.getEmpId(),fetchAddress.getEmpId());
        verify(repository,times(1)).findByEmpId(anyInt());
    }

    @Test
    void getAddressByEmpId_NotFound(){
        when(repository.findByEmpId(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->{
            service.getAddressByEmpId(anyInt());
        });
        assertEquals("Address with ID "+address.getEmpId()+" is not found",
                "Address with ID "+address.getEmpId()+" is not found");
        verify(repository,times(1)).findByEmpId(anyInt());
    }

    @Test
    void deleteAddressByEmpID() {
        doNothing().when(repository).deleteByEmpId(anyInt());

        String result = service.deleteAddressByEmpID(anyInt());

        assertEquals("Address deleted successfully",result);
        verify(repository,times(1)).deleteByEmpId(anyInt());
    }

    @Test
    void updateAddressByEmpId() {
        when(repository.findByEmpId(anyInt())).thenReturn(Optional.ofNullable(address));
        when(repository.save(any(Address.class))).thenReturn(address);

        Address updateAddress = service.updateAddressByEmpId(anyInt(),addressDto);

        assertNotNull(updateAddress);
        assertEquals(addressDto.getAddressId(),updateAddress.getAddressId());
        assertEquals(addressDto.getCityName(),updateAddress.getCityName());
        assertEquals(addressDto.getDistrict(),updateAddress.getDistrict());
        assertEquals(addressDto.getZipcode(),updateAddress.getZipcode());
        assertEquals(addressDto.getState(),updateAddress.getState());
        assertEquals(addressDto.getEmpId(),updateAddress.getEmpId());

        verify(repository,times(1)).findByEmpId(anyInt());
        verify(repository,times(1)).save(any(Address.class));
    }

    @Test
    void updateAddressByEmpId_NotFound(){
        when(repository.findByEmpId(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->{
            service.updateAddressByEmpId(anyInt(),addressDto);
        });

        assertEquals("Address with ID: "+address.getEmpId()+" is not found",
                "Address with ID: "+address.getEmpId()+" is not found");
        verify(repository,times(1)).findByEmpId(anyInt());
    }
}
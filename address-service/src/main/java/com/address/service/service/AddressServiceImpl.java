package com.address.service.service;

import com.address.service.dto.AddressDto;
import com.address.service.entity.Address;
import com.address.service.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;

    @Override
    public Address addAddress(AddressDto addressDto){
        try{
            Address address = new Address();
            address.setAddressId(address.getAddressId());
            address.setCityName(addressDto.getCityName());
            address.setDistrict(addressDto.getDistrict());
            address.setState(addressDto.getState());
            address.setZipcode(addressDto.getZipcode());
            address.setEmpId(addressDto.getEmpId());

            log.info("Address added successfully: {}",addressDto);
            return repository.save(address);
        }
        catch (Exception e){
            log.error("Failed to add address",e.getMessage());
            throw new RuntimeException("Failed to add address", e);
        }
    }

    @Override
    public Address getAddressByEmpId(int empId){
        Optional<Address> addressOptional = repository.findByEmpId(empId);
        try{
            if (addressOptional.isPresent()){
                Address address = addressOptional.get();
                log.info("Address with ID {} is fetched successful", empId);
                return address;
            }
            else {
                log.warn("Address with ID {} is not found", empId);
                throw new RuntimeException("Address with ID "+empId+" is not found");
            }
        }
        catch (Exception e){
            log.error("Error while fetching data",e.getMessage());
            throw new RuntimeException("Error while fetching data",e);
        }
    }

    @Override
    public String deleteAddressByEmpID(int empId){
        try {
            repository.deleteByEmpId(empId);
            log.info("Deleted address records for employee ID: {}", empId);
            return "Address deleted successfully";
        } catch (Exception e) {
            log.error("Error deleting address records for employee ID: {}", empId, e.getMessage());
            return "Error deleting address records";
        }
    }

    @Override
    public Address updateAddressByEmpId(int empId, AddressDto addressDto) {
        Optional<Address> optionalAddress = repository.findByEmpId(empId);
        try {
            if (optionalAddress.isPresent()){
                Address existingAddress = optionalAddress.get();

                existingAddress.setAddressId(addressDto.getAddressId());
                existingAddress.setCityName(addressDto.getCityName());
                existingAddress.setDistrict(addressDto.getDistrict());
                existingAddress.setState(addressDto.getState());
                existingAddress.setZipcode(addressDto.getZipcode());
                existingAddress.setState(addressDto.getState());

                log.info("Address with ID {} is updated {}", empId,addressDto);

                return repository.save(existingAddress);
            }
            else {
                log.warn("Address with ID {} is not found to update", empId);
                throw new RuntimeException("Address with ID: "+empId+" is not found");
            }
        }
        catch (Exception e){
            log.error("Error occurred while updating address");
            throw new RuntimeException("Error occurred while updating address");
        }
    }
}

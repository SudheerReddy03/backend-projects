package com.address.service.repository;

import com.address.service.entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {

    Optional<Address> findByEmpId(int empId);

    @Transactional
    void deleteByEmpId(int empId);
}

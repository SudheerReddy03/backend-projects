package com.address.service.repository;

import com.address.service.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address(1,"guntur","guntur",
                "AP",556677,1234);
        repository.save(address);
    }

    @Test
    void findByEmpId() {
        Optional<Address> address = repository.findByEmpId(1234);
        assertThat(address).isPresent();
        assertThat(address.get().getEmpId()).isEqualTo(1234);
    }

    @Test
    void deleteByEmpId() {
        repository.deleteByEmpId(1234);
        Optional<Address> address = repository.findByEmpId(1234);
        assertThat(address).isNotPresent();
    }
}
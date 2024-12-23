package com.address.service.service;

import com.address.service.dto.AddressDto;
import com.address.service.entity.Address;

public interface AddressService {

    public abstract Address addAddress(AddressDto address);

    public abstract Address getAddressByEmpId(int empId);

    public abstract String deleteAddressByEmpID(int empId);

    public abstract Address updateAddressByEmpId(int empId,AddressDto addressDto);
}

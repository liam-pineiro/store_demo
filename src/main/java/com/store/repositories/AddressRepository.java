package com.store.repositories;

import org.springframework.data.repository.CrudRepository;

import com.store.entities.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
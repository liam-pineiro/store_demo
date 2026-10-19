package com.store.repositories;

import org.springframework.data.repository.CrudRepository;

import com.store.entities.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
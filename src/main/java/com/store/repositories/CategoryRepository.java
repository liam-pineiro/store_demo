package com.store.repositories;

import org.springframework.data.repository.CrudRepository;

import com.store.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}
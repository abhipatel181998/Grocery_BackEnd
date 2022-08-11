package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}

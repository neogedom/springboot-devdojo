package com.viniciusgomes.repository;

import com.viniciusgomes.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}

package dev.calculator.repository;

import dev.calculator.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    Page<User> findByUsernameContaining(String username, Pageable pageable);

}

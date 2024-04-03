package com.hatim.qp.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hatim.qp.db.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserId(long id);

    Optional<User> findByUserName(String userName);

    @SuppressWarnings("unchecked")
    User save(User user);

    void deleteByUserId(long id);
}

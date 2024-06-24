package com.spring.management.repository.user;


import com.spring.management.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    /**
     * find User by login name
     * @param loginName
     * @return User
     */
    Optional<User> findByLoginName(String loginName);

    /**
     * find User by email
     * @param email
     * @return User
     */
    Optional<User> findByEmail(String email);

    /**
     * find User By LoginName Or Email
     * @param loginName
     * @param email
     * @return
     */
    Optional<User> findByLoginNameOrEmail(String loginName, String email);
}

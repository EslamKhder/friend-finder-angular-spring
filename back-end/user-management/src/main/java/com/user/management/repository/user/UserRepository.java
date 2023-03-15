package com.user.management.repository.user;


import com.user.management.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findByLoginName(String loginName);
    User findByEmail(String email);
}

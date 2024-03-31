package com.stefanydev.paymentsystempix.repository;

import com.stefanydev.paymentsystempix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
    User findByVerificationCode (String verificationCode);

}

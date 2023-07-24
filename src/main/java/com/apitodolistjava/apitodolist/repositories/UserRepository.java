package com.apitodolistjava.apitodolist.repositories;


import com.apitodolistjava.apitodolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Boolean existsByEmailUser(String emailUser);

    Optional<User> findByEmailUser(String emailUser);

}

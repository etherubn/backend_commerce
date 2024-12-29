package com.catdog.comerce.repository;

import com.catdog.comerce.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends RepoGeneric<User,Long> {
   boolean existsByDni(String dni);
   Optional<User> findByDni(String dni);
   Optional<User> findByEmail(String email);
   Optional<User> findByUsername(String username);
   Optional<User> findOneByUsername(String email);
   boolean existsByUsername(String username);
   boolean existsByEmail(String email);

   //Find all users different as admin
   @Query("select u from User u where u.username != 'admin'")
   List<User> findAllUsers();
}

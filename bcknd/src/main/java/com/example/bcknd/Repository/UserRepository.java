package com.example.bcknd.Repository;

import com.example.bcknd.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
interface extending another interface,
*
* */
public interface UserRepository extends JpaRepository<User,String> {
}

package com.example.ateeb;

import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends CrudRepository<UserData, Integer> {

    UserData findByName(String name);

}
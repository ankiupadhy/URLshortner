package com.urlshortner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.urlshortner.model.UserForUrlShortner;

public interface UserRepository extends CrudRepository<UserForUrlShortner,String> {
	Optional<UserForUrlShortner> findByUserId(String userId);
	List<UserForUrlShortner> findAll();
	Optional<UserForUrlShortner> findByUsername(String username);
}

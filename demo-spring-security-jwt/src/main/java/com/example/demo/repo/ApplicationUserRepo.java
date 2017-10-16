package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ApplicationUser;

@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Long>{

	ApplicationUser findByUsername(String username);
}

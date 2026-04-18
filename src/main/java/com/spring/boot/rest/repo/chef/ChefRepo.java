package com.spring.boot.rest.repo.chef;

import com.spring.boot.rest.model.chef.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefRepo extends JpaRepository<Chef,Long> {

    List<Chef> findAllByOrderByIdAsc();
}

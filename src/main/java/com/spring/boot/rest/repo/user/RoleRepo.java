package com.spring.boot.rest.repo.user;
import com.spring.boot.rest.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Role findByRoleName(String name);
}

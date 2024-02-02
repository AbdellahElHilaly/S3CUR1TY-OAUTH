package com.youcode.s3cur1ty.app.core.database.repository;

import com.youcode.s3cur1ty.app.core.database.model.entity.Role;
import com.youcode.s3cur1ty.app.core.database.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> , JpaSpecificationExecutor<User> {


    Role findByName(String roleUser);
}
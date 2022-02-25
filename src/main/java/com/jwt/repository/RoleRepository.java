package com.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import com.jwt.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

}

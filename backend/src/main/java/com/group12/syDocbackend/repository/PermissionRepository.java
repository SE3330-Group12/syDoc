package com.group12.syDocbackend.repository;

import com.group12.syDocbackend.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    List<Permission> findPermissionsByDocid(int docid);
}

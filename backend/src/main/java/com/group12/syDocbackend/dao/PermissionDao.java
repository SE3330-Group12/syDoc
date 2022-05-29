package com.group12.syDocbackend.dao;

import com.group12.syDocbackend.entity.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> allPermission();
    List<Permission> findByDoc(int DocId);
}

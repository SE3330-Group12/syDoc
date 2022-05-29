package com.group12.syDocbackend.daoimpl;

import com.group12.syDocbackend.dao.PermissionDao;
import com.group12.syDocbackend.entity.Permission;
import com.group12.syDocbackend.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PermissionDaoimpl implements PermissionDao {
    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public List<Permission> allPermission(){
        return permissionRepository.findAll();
    }

    @Override
    public List<Permission> findByDoc(int DocId){
        return permissionRepository.findPermissionsByDocid(DocId);
    }
}

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
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> allPermission(){
        return permissionRepository.findAll();
    }

    @Override
    public List<Permission> findByDoc(int DocId){
        return permissionRepository.findPermissionsByDocid(DocId);
    }

    @Override
    public int getPermission(int docId,int userId){
        List<Permission> temp =  permissionRepository.findPermissionsByDocidAndUserid(docId,userId);
        if(temp.isEmpty())return -1;
        return temp.get(0).getUserPower();
    }

    @Override
    public boolean modifyPermission(int docId,int userId){
        List<Permission> temp = permissionRepository.findPermissionsByDocidAndUserid(docId,userId);
        if(temp.isEmpty())return false;
        Permission toBeModify = temp.get(0);
        int oldPower = toBeModify.getUserPower();
        toBeModify.setUserPower(1-oldPower);
        permissionRepository.save(toBeModify);
        return true;
    }
}

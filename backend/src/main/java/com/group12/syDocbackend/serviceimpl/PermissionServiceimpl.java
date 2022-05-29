package com.group12.syDocbackend.serviceimpl;

import com.group12.syDocbackend.dao.PermissionDao;
import com.group12.syDocbackend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceimpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public boolean modifyPermission(int docId,int userId){
        return permissionDao.modifyPermission(docId,userId);
    }
}

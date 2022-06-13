package com.group12.syDocbackend.daoimpl;

import com.group12.syDocbackend.dao.PermissionDao;
import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.entity.Permission;
import com.group12.syDocbackend.repository.AccountRepository;
import com.group12.syDocbackend.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.AnnotatedArrayType;
import java.util.List;

@Repository
public class PermissionDaoimpl implements PermissionDao {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private AccountRepository accountRepository;

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
    public boolean modifyPermission(int docId,int userName){
        List<Permission> temp = permissionRepository.findPermissionsByDocidAndUserid(docId,userName);
        if(temp.isEmpty())return false;
        Permission toBeModify = temp.get(0);
        int oldPower = toBeModify.getUserPower();
        int newPower;
        if(oldPower==1)newPower = 2;
        else newPower = 1;
        toBeModify.setUserPower(newPower);
        permissionRepository.save(toBeModify);
        return true;
    }

    @Override
    public boolean deletePartner(int documentId, int userId){
        Account toBeDelte = accountRepository.getById(userId);
        List<Document> projects = toBeDelte.getProjects();
        int size = projects.size();
        for(int i=0;i<size;i++){
            if(projects.get(i).getDocumentId()==documentId){
                projects.remove(i);
                toBeDelte.setProjects(projects);
                accountRepository.save(toBeDelte);
                List<Permission> powerTemp = permissionRepository.findPermissionsByDocidAndUserid(documentId,userId);
                permissionRepository.delete(powerTemp.get(0));
                permissionRepository.flush();
                return true;
            }
        }
        return false;
    }
}

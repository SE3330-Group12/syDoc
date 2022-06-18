package com.group12.syDocbackend.serviceimpl;

import com.group12.syDocbackend.dao.AccountDao;
import com.group12.syDocbackend.dao.DocumentDao;
import com.group12.syDocbackend.dao.PermissionDao;
import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.entity.Permission;
import com.group12.syDocbackend.service.DocumentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Document addDocument(int userId,String docName,String type){

        return accountDao.addDocument(userId,docName,type);
    }

    @Override
    public List<Map> getDocList(int userId){
        return accountDao.getDocList(userId);
    }

    @Override
    public boolean deleteDocument(int docId,int userId){
        return accountDao.deleteDocument(docId,userId);
    }


    public class Result{
        String name;
        String power;
        int userId;

        public Result(String name,int power,int id){
            this.name = name;
            this.userId = id;
            if(power==0) this.power="Creator";
            else if(power ==1) this.power = "Editor";
            else this.power = "Reader";
        }
        public Result(){}

        public String  getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    @Override
    public List<Result> getUserPower(int docId){
        List<Permission> findByDoc = permissionDao.findByDoc(docId);
        int size = findByDoc.size();
        List<Result> retList = new ArrayList<>();
        for(int i=0;i<size;i++){
            int userId = findByDoc.get(i).getUserid();
            Result temp = new Result(accountDao.getNameById(userId),findByDoc.get(i).getUserPower(),userId);
            retList.add(temp);
        }
        return retList;
    }

    @Override
    public int getPermission(int docId,int userId){
        return permissionDao.getPermission(docId,userId);
    }
}

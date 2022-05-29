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
    AccountDao accountDao;
    @Autowired
    DocumentDao documentDao;
    @Autowired
    PermissionDao permissionDao;

    @Override
    public Document addDocument(int userId,String docName,String type){

        return accountDao.addDocument(userId,docName,type);
    }

    @Override
    public List<Map> getDocList(int userId){
        return accountDao.getDocList(userId);
    }

    @Override
    public boolean deleteDocument(int docId){
        return accountDao.deleteDocument(docId);
    }


    public class Result{
        String name;
        int power;

        public Result(String name,int power){
            this.name = name;
            this.power = power;
        }
        public Result(){}

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    @Override
    public List<Result> getUserPower(int docId){
        List<Permission> findByDoc = permissionDao.findByDoc(docId);
        int size = findByDoc.size();
        List<Result> retList = new ArrayList<>();
        for(int i=0;i<size;i++){
            int userId = findByDoc.get(i).getUserid();
            Result temp = new Result(accountDao.getNameById(userId),findByDoc.get(i).getUserPower());
            retList.add(temp);
        }
        return retList;
    }
}

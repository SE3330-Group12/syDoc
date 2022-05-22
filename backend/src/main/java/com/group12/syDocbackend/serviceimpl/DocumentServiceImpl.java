package com.group12.syDocbackend.serviceimpl;

import com.group12.syDocbackend.dao.AccountDao;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    AccountDao accountDao;

    @Override
    public Document addDocument(int userId,String docName,int type){

        return accountDao.addDocument(userId,docName,type);
    }

    @Override
    public boolean deleteDocument(int docId){
        return accountDao.deleteDocument(docId);
    }
}

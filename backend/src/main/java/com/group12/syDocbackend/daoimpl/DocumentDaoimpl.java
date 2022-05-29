package com.group12.syDocbackend.daoimpl;

import com.group12.syDocbackend.dao.DocumentDao;
import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.repository.DocumentRepository;
import com.group12.syDocbackend.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentDaoimpl implements DocumentDao {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    PermissionRepository permissionRepository;


    @Override
    public List<Account> getUsers(int docId){
        return documentRepository.getById(docId).getAccounts();
    }
}

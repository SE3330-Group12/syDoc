package com.group12.syDocbackend.dao;

import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import org.springframework.http.RequestEntity;

import java.util.List;
import java.util.Map;

public interface AccountDao {
    void addAccount(String name,String password,String email);
    void modifyAccount(int id,String newPWD);
    Account invite(int userId, int documentId);
    boolean checkAccNameDup(String name);
    Account checkAccount(String name,String password);
    Document addDocument(int userId,String docName);
    boolean modifyDocName(int docId,String newname);
    List<Map> getDocList(int userId);
    boolean deleteDocument(int docId);
}

package com.group12.syDocbackend.daoimpl;

import com.group12.syDocbackend.dao.AccountDao;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.entity.Permission;
import com.group12.syDocbackend.repository.AccountRepository;
import com.group12.syDocbackend.repository.DocumentRepository;
import com.group12.syDocbackend.repository.PermissionRepository;
import net.bytebuddy.pool.TypePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import com.group12.syDocbackend.entity.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.sql.Types.NULL;

@Repository
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Account getAccount(int accountId){
        return accountRepository.getById(accountId);
    }

    @Override
    public void addAccount(String name,String password,String email){
        Account a = new Account();
        a.setName(name);
        a.setPassword(password);
        a.setEmail(email);
        accountRepository.save(a);
    }

    @Override
    public boolean checkAccNameDup(String name){
        if(accountRepository.checkAccDup(name).isEmpty())return true;
        return false;
    }

    @Override
    public Account checkAccount(String name,String password){

        return accountRepository.checkAccount(name,password);
    }

    @Override
    public void modifyAccount(int id,String newPWD) {
        Account temp = accountRepository.getById(id);
        temp.setPassword(newPWD);
        accountRepository.flush();
    }

    @Override
    public Account invite(int userId, int documentId,int userPower){
        Account acc = accountRepository.findById(userId).orElse(null);
        Document doc = documentRepository.findById(documentId).orElse(null);
        if(acc==null||doc==null) return null;
        Permission temp = new Permission();
        temp.setDocid(documentId);
        temp.setUserid(userId);
        temp.setUserPower(userPower);
        permissionRepository.save(temp);
        List<Document> newProjects = acc.getProjects();
        newProjects.add(doc);
        acc.setProjects(newProjects);
        accountRepository.flush();
        documentRepository.flush();
        return acc;
    }

    @Override
    public boolean deletePartner(int docId,int userId){
        //TODO
        return true;
    }

    @Override
    public Document addDocument(int userId,String docName,String type){
        Account toBeAdded = accountRepository.getById(userId);

        // 检查该用户文档列表中有无重复名字的文档
        if(toBeAdded.findDupDoc(docName,type))return null;

        //新建文档对象
        Document temp = new Document();
        temp.setDocumentName(docName);
        temp.setType(type);
        temp.setAuthor(toBeAdded.getName());
        List<Document> oldDocList = toBeAdded.getProjects();
        oldDocList.add(temp);
        toBeAdded.setProjects(oldDocList);
        accountRepository.flush();

        //储存创建者权限
        List<Document> newDocList = toBeAdded.getProjects();
        int size = newDocList.size();
        int docId = newDocList.get(size-1).getDocumentId();
        Permission per = new Permission();
        per.setUserid(userId);
        per.setDocid(docId);
        per.setUserPower(0);
        permissionRepository.save(per);
        return temp;
    }

    @Override
    public List<Map> getDocList(int userId){
        return accountRepository.getDocList(userId);
    }

    @Override
    public boolean modifyDocName(int docId,String newName){
        Document temp = documentRepository.getById(docId);
        if(temp.getDocumentId() == NULL)return false;
        temp.setDocumentName(newName);
        documentRepository.flush();
        return true;
    }

    @Override
    public boolean deleteDocument(int docId){
        documentRepository.deleteById(docId);
        return true;
    }

    @Override
    public String getNameById(int userId){
        return accountRepository.getById(userId).getName();
    }
}

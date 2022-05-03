package com.group12.syDocbackend.daoimpl;

import com.group12.syDocbackend.dao.AccountDao;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.repository.AccountRepository;
import com.group12.syDocbackend.repository.DocumentRepository;
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

    @Override
    public void addAccount(String name,String password,String email){
        Account a = new Account();
        a.setName(name);
        a.setPassword(password);
        a.setEmail(email);
        accountRepository.save(a);
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
    public Account invite(int userId, int documentId){
        Account acc = accountRepository.findById(userId).orElse(null);
        if(acc==null) return null;
        List<Document> newProjects = acc.getProjects();
        Document doc = documentRepository.getById(documentId);
        newProjects.add(doc);
        acc.setProjects(newProjects);
        accountRepository.flush();
        documentRepository.flush();
        return acc;
    }

    @Override
    public boolean checkAccNameDup(String name){
        if(accountRepository.checkAccDup(name).isEmpty())return true;
        return false;
    }

    @Override
    public Document addDocument(int userId,String docName){
        //TODO
        // 检查该用户文档列表中有无重复名字的文档

        //新建文档对象
        Document temp = new Document();
        temp.setDocumentName(docName);
//        documentRepository.save(temp);
        //将文档对象加入user的List中
        Account toBeAdded = accountRepository.getById(userId);
        List<Document> oldDocList = toBeAdded.getProjects();
        oldDocList.add(temp);
        toBeAdded.setProjects(oldDocList);
        accountRepository.flush();
//        documentRepository.flush();
        //将user加入Document的List中(这一方在多对多关系中放弃维护故无需更新)
//        List<Account> accList = temp.getAccounts();
//        accList.add(toBeAdded);
//        temp.setAccounts(accList);
//        //保存
//        documentRepository.flush();
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
//        List<Integer> userlist = accountRepository.getAccByDoc(docId);
        documentRepository.deleteById(docId);
        return true;
    }
}

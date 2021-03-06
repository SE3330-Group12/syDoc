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

import javax.print.Doc;
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
        if(doc.getAuthor().equals(acc.getName()))return null;
        if(!permissionRepository.findPermissionsByDocidAndUserid(documentId,userId).isEmpty())return null;
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

        // ?????????????????????????????????????????????????????????
        if(toBeAdded.findDupDoc(docName,type))return null;

        //??????????????????
        Document temp = new Document();
        temp.setDocumentName(docName);
        temp.setType(type);
        temp.setAuthor(toBeAdded.getName());
        List<Document> oldDocList = toBeAdded.getProjects();
        oldDocList.add(temp);
        toBeAdded.setProjects(oldDocList);
        accountRepository.flush();

        //?????????????????????
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
    public boolean deleteDocument(int docId,int userId){
        Document doc = documentRepository.getById(docId);
        List<Account> toBeDelete= doc.getAccounts();
        String author = doc.getAuthor();
        Account deletePerson = accountRepository.getById(userId);
        if(author.equals(deletePerson.getName())){
            int accountNumber = toBeDelete.size();
            for(int k=0;k<accountNumber;k++){
                Account currentAccount = toBeDelete.get(k);
                List<Document> projects = currentAccount.getProjects();
                int size = projects.size();
                for(int i=0;i<size;i++){
                    if(projects.get(i).getDocumentId()==docId){
                        projects.remove(i);
                        currentAccount.setProjects(projects);
                        accountRepository.save(currentAccount);
                        List<Permission> temp = permissionRepository.findPermissionsByDocid(docId);
                        int permissionSize = temp.size();
                        for(int j=0;j<permissionSize;j++){
                            permissionRepository.delete(temp.get(j));
                        }
                        permissionRepository.flush();
                    }
                }
            }
            documentRepository.deleteById(docId);
            return true;
        }
        else
        {
            List<Document> proj = deletePerson.getProjects();
            int projectSize = proj.size();
            for(int i=0;i<projectSize;i++){
                if(proj.get(i).getDocumentId()==docId){
                    proj.remove(i);
                    deletePerson.setProjects(proj);
                    accountRepository.save(deletePerson);
                    List<Permission> temp = permissionRepository.findPermissionsByDocidAndUserid(docId,userId);
                    permissionRepository.delete(temp.get(0));
                    permissionRepository.flush();
                    return true;

                }
            }
            return false;
        }
    }

    @Override
    public String getNameById(int userId){
        return accountRepository.getById(userId).getName();
    }
}

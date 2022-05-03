package com.group12.syDocbackend.serviceimpl;

import com.group12.syDocbackend.dao.AccountDao;
import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account checkAccount (String username,String password)
    {
        Account temp = accountDao.checkAccount(username,password);
        return temp;
    }

    @Override
    public boolean addAccount(String username, String password,String email){
        accountDao.addAccount(username,password,email);
        return true;
    }

    @Override
    public List<Map> getDocList(int userId){
        return accountDao.getDocList(userId);
    }

    @Override
    public Account invite(int userId,int docId)
    {
        return accountDao.invite(userId,docId);
    }

    @Override
    public boolean checkDup(String username){
        return accountDao.checkAccNameDup(username);
    }
}

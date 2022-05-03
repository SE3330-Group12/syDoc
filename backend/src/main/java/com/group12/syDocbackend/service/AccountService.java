package com.group12.syDocbackend.service;

import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface AccountService {
    Account checkAccount(String username,String password);
    boolean addAccount(String username, String password,String email);
    Account invite(int userId,int docId);
    boolean checkDup(String username);
    List<Map> getDocList(int userId);
}

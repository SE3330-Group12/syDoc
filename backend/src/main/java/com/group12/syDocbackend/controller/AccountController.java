package com.group12.syDocbackend.controller;

import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.service.AccountService;
import com.group12.syDocbackend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private DocumentService documentService;

    @RequestMapping  ("/getAccount")
    public Account getAccount(@RequestParam("accountID") int accountId){
        System.out.println("getAccount--"+accountId);
        return accountService.getAccount(accountId);
    }

    @RequestMapping  ("/checkAccount")
    public Account checkAccount(@RequestParam("name") String username, @RequestParam("password") String password){
        System.out.println("chackAccount!");
        return accountService.checkAccount(username, password);
    }

    @RequestMapping("/checkDup")
    public boolean checkDup(@RequestParam("name") String username){
        System.out.println("chackDup!");
        if(accountService.checkDup(username))
            return false;
        else return true;
    }

    @RequestMapping("/addAccount")
    public boolean addAccount(@RequestParam("name") String username, @RequestParam("password") String password,@RequestParam("email") String email){
        System.out.println("addAccount!");
        return accountService.addAccount(username,password,email);
    }

    @RequestMapping("/getDocList")
    public List<Map> getDocList(@RequestParam("userId")int userId){
        System.out.println("getDocList!");
        return documentService.getDocList(userId);
    }

    @RequestMapping("/invite")
    public Account invite(@RequestParam("userId")int userId, @RequestParam("docId")int docId,@RequestParam("userPower")int userPower){
        System.out.println("invited!");
        return accountService.invite(userId,docId,userPower);
    }

    @RequestMapping("/addDocument")
    public Document addDocument( @RequestParam("userId")int userId,@RequestParam("docName")String docName,@RequestParam("type")String doctype){
        System.out.println("addDocument!");
        return documentService.addDocument(userId,docName,doctype);
    }

    @RequestMapping("/deleteDocument")
    public boolean deleteDocument(@RequestParam("docId")int docId){
        System.out.println("deleteDoc!");
        documentService.deleteDocument(docId);
        return true;
    }

}

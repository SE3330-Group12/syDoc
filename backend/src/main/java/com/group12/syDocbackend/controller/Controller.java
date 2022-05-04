package com.group12.syDocbackend.controller;

import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.service.AccountService;
import com.group12.syDocbackend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class Controller {
    @Autowired
    private AccountService accountService;

    @Autowired
    private DocumentService documentService;

    @RequestMapping  ("/checkAccount")
    public Account checkAccount(@RequestParam("name") String username, @RequestParam("password") String password){
        System.out.println("chackAccount!");
        return accountService.checkAccount(username, password);
    }

    @RequestMapping("/checkDup")
    public boolean checkDup(@RequestParam("name") String username){
        System.out.println("chackDup!");
        if(accountService.checkDup(username))
            return true;
        else return false;
    }

    @RequestMapping("/addAccount")
    public boolean addAccount(@RequestParam("name") String username, @RequestParam("password") String password,@RequestParam("email") String email){
        System.out.println("addAccount!");
        return accountService.addAccount(username,password,email);
    }

    @RequestMapping("/getDocList")
    public List<Map> getDocList(@RequestParam("userId")String userId){
        System.out.println("getDocList!");
        int id=Integer.valueOf(userId);
        return accountService.getDocList(id);
    }

    @RequestMapping("/invite")
    public Account invite(@RequestParam("userId")String userId, @RequestParam("docId")String docId){
        System.out.println("invited!");
        int id=Integer.valueOf(userId);
        int did = Integer.valueOf(docId);
        return accountService.invite(id,did);
    }

    @RequestMapping("/addDocument")
    public Document addDocument( @RequestParam("userId")String userId,@RequestParam("docName")String docName){
        System.out.println("addDocument!");
        int id=Integer.valueOf(userId);
        return documentService.addDocument(id,docName);
    }

    @RequestMapping("/deleteDocument")
    public boolean deleteDocument(@RequestParam("docId")String docId){
        System.out.println("deleteDoc!");
        int id=Integer.valueOf(docId);
        return true;
    }

}
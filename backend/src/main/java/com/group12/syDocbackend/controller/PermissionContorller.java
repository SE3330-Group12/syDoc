package com.group12.syDocbackend.controller;

import com.group12.syDocbackend.dao.PermissionDao;
import com.group12.syDocbackend.service.AccountService;
import com.group12.syDocbackend.service.DocumentService;
import com.group12.syDocbackend.service.PermissionService;
import com.group12.syDocbackend.serviceimpl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class PermissionContorller {
    @Autowired
    private AccountService accountService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/getUsers")
    public List<DocumentServiceImpl.Result> getUserPower(@RequestParam("documentId") int docId){
        return documentService.getUserPower(docId);
    }
    @RequestMapping("/getPermission")
    public int getPermission(@RequestParam("documentId")int docId,@RequestParam("userId") int userId){
        System.out.println("getPermission--"+docId+" "+userId);
        return documentService.getPermission(docId,userId);
    }
}

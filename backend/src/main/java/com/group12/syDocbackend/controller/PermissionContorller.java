package com.group12.syDocbackend.controller;

import com.group12.syDocbackend.service.AccountService;
import com.group12.syDocbackend.service.DocumentService;
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

    @RequestMapping("/getUsers")
    public List<DocumentServiceImpl.Result> getUserPower(@RequestParam("documentId") int docId){
        return documentService.getUserPower(docId);
    }
}

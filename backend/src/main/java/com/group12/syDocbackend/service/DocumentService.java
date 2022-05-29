package com.group12.syDocbackend.service;

import com.group12.syDocbackend.entity.Document;
import com.group12.syDocbackend.serviceimpl.DocumentServiceImpl;

import java.util.List;
import java.util.Map;


public interface DocumentService {
    Document addDocument(int userId,String docName,String type);
    boolean deleteDocument(int docId);
    List<DocumentServiceImpl.Result> getUserPower(int docId);
    List<Map> getDocList(int userId);
}

package com.group12.syDocbackend.service;

import com.group12.syDocbackend.entity.Document;


public interface DocumentService {
    Document addDocument(int userId,String docName);
    boolean deleteDocument(int docId);
}

package com.group12.syDocbackend.service;

public interface PermissionService {
    boolean modifyPermission(int docId,int userName);
    boolean deletePartner(int documentId, int userId);
    boolean getPermissionByName(int docId,String userName);
}

package com.group12.syDocbackend.dao;

import com.group12.syDocbackend.entity.Account;

import java.util.List;

public interface DocumentDao {
    List<Account> getUsers(int docId);

}

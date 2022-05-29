package com.group12.syDocbackend.repository;

import com.group12.syDocbackend.entity.Account;
import com.group12.syDocbackend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    @Query(value = "from Account where name=:name and password=:password")
    Account checkAccount(@Param("name") String username, @Param("password") String password);

    @Modifying
    @Query("update Account u set u.password = :password where u.name = :name")
    void modifyPWD(@Param("name") String name, @Param("password") String password);

    //TODO
//    @Modifying
//    @Query("update Account u set u. = :usreId where u.accountId = :userId")
//    void invite(@Param("userId") int userId, @Param("DocId") int docId);

    @Query(value = "from Account where name=:name")
    List<Account> checkAccDup(@Param("name") String accName);

    @Query(nativeQuery = true, value = "select b.id,b.name,b.type,b.author from account_document as a,documents as b where a.user_id=:userId and a.document_id=b.id")
    List<Map> getDocList(@RequestParam("userId") int userId);

}

package com.group12.syDocbackend.repository;

import com.group12.syDocbackend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Integer> {
    @Query(value = "select documentId from Document where documentName=:docName")
    int checkDocDup(@Param("docName") String docName);

}

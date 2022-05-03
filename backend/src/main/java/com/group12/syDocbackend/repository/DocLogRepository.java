package com.group12.syDocbackend.repository;

import com.group12.syDocbackend.entity.DocLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocLogRepository extends MongoRepository<DocLog,String> {
    public List<DocLog> findByDocId(String docId);

}

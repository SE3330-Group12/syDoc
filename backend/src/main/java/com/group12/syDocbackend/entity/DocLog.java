package com.group12.syDocbackend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "syDoc")
public class DocLog {

    @Id
    private String id;
    private String docId;
    private String logEntry;
    public DocLog(String docId,String logEntry){
        this.docId=docId;
        this.logEntry=logEntry;
    }
    public String getLogEntry(){
        return  this.logEntry;
    }
}

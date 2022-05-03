package com.group12.syDocbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import com.group12.syDocbackend.entity.Account;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="documents")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "documentId")
public class Document {
    private int documentId;
    private String documentName;
    private List<Account> accounts = new ArrayList<>();

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public Integer getDocumentId() {
        return documentId;
    }
    private void setDocumentId(Integer id) {this.documentId = id;}

    @Basic
    @Column(name = "name")
    public String getDocumentName(){return documentName;}
    public void setDocumentName(String docName){this.documentName = docName;}

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "projects",cascade = CascadeType.MERGE)
    public List<Account> getAccounts(){return accounts;}
    public void setAccounts(List<Account> accounts){this.accounts = accounts;}
}

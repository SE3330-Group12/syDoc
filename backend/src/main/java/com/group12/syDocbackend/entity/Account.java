package com.group12.syDocbackend.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import com.group12.syDocbackend.entity.Document;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="accounts")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountId")
public class Account {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int accountId;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="ACCOUNT_DOCUMENT",joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    public List<Document> projects = new ArrayList<>();

    public Account(){}

    public boolean findDupDoc(String docname,String type){
        int size = projects.size();
        for(int i=0;i<size;i++){
            Document temp = projects.get(i);
            if(temp.getDocumentName().equals(docname) && temp.getType().equals(type))return true;
        }
        return false;
    }

}

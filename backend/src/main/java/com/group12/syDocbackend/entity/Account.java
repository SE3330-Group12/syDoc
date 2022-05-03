package com.group12.syDocbackend.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import com.group12.syDocbackend.entity.Document;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accounts")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountId")
public class Account {
    private int accountId;
    private String name;
    private String password;
    private String email;
    public List<Document> projects = new ArrayList<>();

    public Account(){}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer id) {this.accountId = id;}

    @Basic
    @Column(name="password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {this.password = password;}

    @Basic
    @Column(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}

    @Basic
    @Column(name="email")
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="ACCOUNT_DOCUMENT",joinColumns = @JoinColumn(name = "USER_ID"),
    inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    public List<Document> getProjects(){return projects;}
    public void setProjects(List<Document> projects){this.projects = projects;}
}

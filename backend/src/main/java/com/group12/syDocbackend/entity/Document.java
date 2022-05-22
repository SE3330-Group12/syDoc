package com.group12.syDocbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.group12.syDocbackend.entity.Account;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="documents")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "documentId")
public class Document {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int documentId;

    @Column(name = "name")
    private String documentName;

    @Column(name = "type")
    private int type;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "projects",cascade = CascadeType.MERGE)
    private List<Account> accounts = new ArrayList<>();
}

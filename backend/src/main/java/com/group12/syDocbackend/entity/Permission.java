package com.group12.syDocbackend.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "permissions", schema = "demo", catalog = "")
public class Permission {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "userid")
    private int userid;

    @Basic
    @Column(name = "docid")
    private int docid;

    @Basic
    @Column(name = "permission")
    private int userPower;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return userid == that.userid && docid == that.docid && userPower == that.userPower;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, docid, userPower);
    }
}

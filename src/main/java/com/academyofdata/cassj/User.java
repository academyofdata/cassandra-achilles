package com.academyofdata.cassj;

import com.datastax.driver.core.ConsistencyLevel;
import info.archinnov.achilles.annotations.*;


@Table(table="users")
@Consistency(read=ConsistencyLevel.ONE, write=ConsistencyLevel.QUORUM, serial = ConsistencyLevel.SERIAL)

public class User
{
    @PartitionKey
    private Long uid;

    @Column
    private String gender;

    @ClusteringColumn
    @Column("age")
    private Integer age;

    @Column
    private String ocupation;

    @Column
    private String zip;

    public User(){}

    public User(Long u, String g, Integer a,String o,String z){
        this.age = a;
        this.ocupation = o;
        this.gender = g;
        this.zip = z;
        this.uid = u;
    }

    public Long getUid() {
        return uid;
    }

    public Integer getAge() {
        return age;
    }

    public String getOcupation() {
        return ocupation;
    }

    public String getGender() {
        return gender;
    }

    public String getZip() {
        return zip;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
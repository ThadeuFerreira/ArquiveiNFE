package com.example.arquiveiNFE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

@Entity
@Table(name = "arquivei_nfe")
@ApiModel(description = "This is the object returned by Arquivei API")
public class ArquiveiNFE {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty(value = "access_key")
    @NaturalId
    private String accessKey;

    @Column(length = 80000)
    private String xml;

    public ArquiveiNFE(){}

    public ArquiveiNFE(String accessKey, String xml) {
        this.accessKey = accessKey;
        this.xml = xml;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}

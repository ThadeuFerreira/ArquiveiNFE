package com.example.arquiveiNFE.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "local_nfe" )
@ApiModel(description = "Contains the Access Key and total value of a NFE.")
public class LocalNFE {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty(value = "access_key")
    @NaturalId
    private String accessKey;

    private BigDecimal nfe_total_value;

    public LocalNFE(){}

    public LocalNFE(String accessKey, BigDecimal nfe_total_value) {
        this.accessKey = accessKey;
        this.nfe_total_value = nfe_total_value;
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

    public BigDecimal getNfe_total_value() {
        return nfe_total_value;
    }

    public void setNfe_total_value(BigDecimal nfe_total_value) {
        this.nfe_total_value = nfe_total_value;
    }
}

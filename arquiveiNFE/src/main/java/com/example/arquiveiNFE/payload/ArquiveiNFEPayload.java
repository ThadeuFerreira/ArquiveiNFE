package com.example.arquiveiNFE.payload;

import com.example.arquiveiNFE.model.ArquiveiNFE;


import java.util.List;

public class ArquiveiNFEPayload {
    private Status status;

    private List<ArquiveiNFE> data;

    private Integer count;
    private String signature;


    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ArquiveiNFE> getData() {
        return data;
    }

    public void setData(List<ArquiveiNFE> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

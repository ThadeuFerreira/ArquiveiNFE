package com.example.arquieiNFE.controller;


import com.example.arquieiNFE.NFE;
import com.example.arquieiNFE.NfeRepository;
import io.swagger.annotations.Api;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value="Endpoint for Nota Fiscal Eletronica")
@RequestMapping("/api/v1")
public class NfeController {

    @Autowired
    private NfeRepository nfeRepository;

    @GetMapping("/nfe")
    public List<NFE> getEmployeeById() throws IOException {

        List<NFE> cache = nfeRepository.findAll();
        if(!cache.isEmpty()){
            System.out.println("Using Cache");
            return  cache;
        }
        URL url = new URL("https://sandbox-api.arquivei.com.br/v1/nfe/received");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("x-api-id", "f96ae22f7c5d74fa4d78e764563d52811570588e");
        con.setRequestProperty("x-api-key", "cc79ee9464257c9e1901703e04ac9f86b0f387c2");
        String header = con.getHeaderField("x-api-id");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        JSONObject obj = new JSONObject(content.toString());
        JSONArray data = obj.getJSONArray("data");

        List<NFE> retList = new ArrayList<>();
        for ( Object o: data ) {
            if(o instanceof JSONObject){
                String accessKey = ((JSONObject) o).getString("access_key");
                String xml = ((JSONObject) o).getString("xml");
                NFE nfe = new NFE(accessKey,xml);
                retList.add(nfe);
                nfeRepository.save(nfe);
            }
        }

        return retList;
    }
}

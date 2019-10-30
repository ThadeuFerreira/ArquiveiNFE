package com.example.arquiveiNFE;

import com.example.arquiveiNFE.model.ArquiveiNFE;
import com.example.arquiveiNFE.model.LocalNFE;
import com.example.arquiveiNFE.payload.ArquiveiNFEPayload;
import com.example.arquiveiNFE.repository.ArquiveiNfeRepository;
import com.example.arquiveiNFE.repository.LocalNfeRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArquiveiService {

    @Autowired
    private ArquiveiNfeRepository arquiveiNfeRepository;

    @Autowired
    private LocalNfeRepository localNfeRepository;
    /*
     * Saves NFEs in local database format
     * @param cache - list of ArquiveiNFE
     * @param localNfeRepository - JPA repository of NFE saved in local format
     * */
    public void saveInLocalDB(List<ArquiveiNFE> cache) throws IOException {
        for ( ArquiveiNFE  arquiveiNFE: cache
        ) {
            byte[] byteArray = Base64.decodeBase64(arquiveiNFE.getXml().getBytes());


            String xml = new String(byteArray);
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                builder = factory.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(xml));
                Document doc = builder.parse(is);
                String vNF_s = doc.getElementsByTagName("vNF").item(0).getTextContent();
                BigDecimal vNF = new BigDecimal(vNF_s);
                String accessKey = arquiveiNFE.getAccessKey();
                if( localNfeRepository.findByAccessKey(accessKey) == null) {
                    LocalNFE localNFE = new LocalNFE(accessKey, vNF);


                    localNfeRepository.save(localNFE);
                }
            } catch (ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * Hits Arquivei.com NFE endpoint and stores the
     * @param arquiveiNfeRepository - JPA repository of Arquivei NFE
     * @param localNfeRepository - JPA repository of local NFE format
     * */
    public void getNfes() throws IOException {
        String url = "https://sandbox-api.arquivei.com.br/v1/nfe/received";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("x-api-id", "f96ae22f7c5d74fa4d78e764563d52811570588e");
        headers.set("x-api-key", "cc79ee9464257c9e1901703e04ac9f86b0f387c2");
        List<ArquiveiNFE> arquiveiNFE = new ArrayList<>();
        try {
            HttpEntity request = new HttpEntity(headers);

            // use `exchange` method for HTTP call
            ResponseEntity<ArquiveiNFEPayload> response =
                    restTemplate.exchange(url, HttpMethod.GET, request, ArquiveiNFEPayload.class);
            arquiveiNFE = Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        List<ArquiveiNFE> retList = new ArrayList<>();
        for ( ArquiveiNFE o: arquiveiNFE ) {

            String accessKey = o.getAccessKey();
            String xml = o.getXml();
            ArquiveiNFE arquiveiNfe = new ArquiveiNFE(accessKey,xml);
            retList.add(arquiveiNfe);
            arquiveiNfeRepository.save(arquiveiNfe);

        }
        saveInLocalDB(retList);
    }

}

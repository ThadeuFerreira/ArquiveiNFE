package com.example.arquiveiNFE.util;

import com.example.arquiveiNFE.model.ArquiveiNFE;
import com.example.arquiveiNFE.model.LocalNFE;
import com.example.arquiveiNFE.repository.ArquiveiNfeRepository;
import com.example.arquiveiNFE.repository.LocalNfeRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

public class ArquiveiUtil {

    /*
    * Saves NFEs in local database format
    * @param cache - list of ArquiveiNFE
    * @param localNfeRepository - JPA repository of NFE saved in local format
    * */
    public static void saveInLocalDB(List<ArquiveiNFE> cache, LocalNfeRepository localNfeRepository) throws IOException {
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
    public static void getNfes(ArquiveiNfeRepository arquiveiNfeRepository, LocalNfeRepository localNfeRepository) throws IOException {
        URL url = new URL("https://sandbox-api.arquivei.com.br/v1/nfe/received");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("x-api-id", "f96ae22f7c5d74fa4d78e764563d52811570588e");
        con.setRequestProperty("x-api-key", "cc79ee9464257c9e1901703e04ac9f86b0f387c2");


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        JSONObject obj = new JSONObject(content.toString());
        JSONArray data = obj.getJSONArray("data");

        List<ArquiveiNFE> retList = new ArrayList<>();
        for ( Object o: data ) {
            if(o instanceof JSONObject){
                String accessKey = ((JSONObject) o).getString("access_key");
                String xml = ((JSONObject) o).getString("xml");
                ArquiveiNFE arquiveiNfe = new ArquiveiNFE(accessKey,xml);
                retList.add(arquiveiNfe);
                arquiveiNfeRepository.save(arquiveiNfe);
            }
        }
        saveInLocalDB(retList,localNfeRepository);
    }
}

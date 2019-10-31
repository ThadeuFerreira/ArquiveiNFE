package com.example.arquiveiNFE;

import com.example.arquiveiNFE.model.ArquiveiNFE;
import com.example.arquiveiNFE.model.LocalNFE;
import com.example.arquiveiNFE.repository.ArquiveiNfeRepository;
import com.example.arquiveiNFE.repository.LocalNfeRepository;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
public class ArquiveiServiceTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LocalNfeRepository localNfeRepository;
    @Autowired
    private ArquiveiNfeRepository arquiveiNfeRepository;

    private List<LocalNFE> localNFES = new ArrayList<>();
    private List<ArquiveiNFE> arquiveiNFES = new ArrayList<>();

    @Before
    public void setUp() {
        String accessKey[] = {"1", "2", "3", "4", "5"};
        List<String> nfeValues = new ArrayList<>();
        int i = 1;
        for(String aK : accessKey) {
            BigDecimal value = new BigDecimal(i*100);
            nfeValues.add("<vNF>" + value.toString() + "</vNF>");
            LocalNFE localNFE = new LocalNFE(aK, value);
            localNFES.add(localNFE);
            testEntityManager.persist(localNFE);
            i++;
        }

        nfeValues = nfeValues
                .stream()
                .map(s -> Base64.getEncoder()
                                .encodeToString(s.getBytes())).collect(Collectors.toList());
        i = 1;
        for(String aK : accessKey) {
            ArquiveiNFE arquiveiNFE = new ArquiveiNFE(aK, nfeValues.get(i -1));
            arquiveiNFES.add(arquiveiNFE);
            testEntityManager.persist(arquiveiNFE);
            i++;
        }
    }

    @Test
    public void whenCashHasData_saveInLocalDB() {
        try {
            ArquiveiService arquiveiService = new ArquiveiService(arquiveiNfeRepository, localNfeRepository);

            arquiveiService.saveInLocalDB(arquiveiNFES);
            List<LocalNFE> _localNFES = localNfeRepository.findAll();

            //then
            assertThat(_localNFES).hasSize(5);

            //when
            List<ArquiveiNFE> _arquiveiNFES = arquiveiNfeRepository.findAll();

            //then
            assertThat(_arquiveiNFES).hasSize(5);
        }
        catch (IOException e){
            assertThat(false);
        }
        assertThat(true);
    }

    @Test
    public void getNfes() {
        ArquiveiService arquiveiService = new ArquiveiService(arquiveiNfeRepository, localNfeRepository);

        try {

            arquiveiService.getNfes("https://sandbox-api.arquivei.com.br/v1/nfe/received");

            arquiveiService.saveInLocalDB(arquiveiNFES);
            List<LocalNFE> _localNFES = localNfeRepository.findAll();

            //then
            assertThat(_localNFES).hasSize(88);
            //when
            List<ArquiveiNFE> _arquiveiNFES = arquiveiNfeRepository.findAll();

            //then
            assertThat(_arquiveiNFES).hasSize(88);
        } catch (IOException e) {
            assertThat(false);
        }


    }
}
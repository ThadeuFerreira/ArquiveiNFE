package com.example.arquiveiNFE.repository;

import com.example.arquiveiNFE.model.ArquiveiNFE;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArquiveiNfeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ArquiveiNfeRepository arquiveiNfeRepository;

    @Before
    public void setUp(){
        String accessKey[] = {"1", "2", "3", "4", "5"};
        int i = 1;
        for(String aK : accessKey) {
            ArquiveiNFE arquiveiNFE = new ArquiveiNFE(aK, "a1s2d3f4" + i);
            testEntityManager.persist(arquiveiNFE);
        }
    }

    @Test
    public void whenFindAll_thenReturnAllLocalNfeList() {
        //when
        List<ArquiveiNFE> arquiveiNFES = arquiveiNfeRepository.findAll();

        //then
        assertThat(arquiveiNFES).hasSize(5);
    }

}
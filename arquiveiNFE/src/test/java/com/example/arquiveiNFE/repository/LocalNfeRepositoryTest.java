package com.example.arquiveiNFE.repository;

import com.example.arquiveiNFE.model.LocalNFE;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class LocalNfeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LocalNfeRepository localNfeRepository;

    @Before
    public void setUp(){
        String accessKey[] = {"1", "2", "3", "4", "5"};
        int i = 1;
        for(String aK : accessKey) {
            BigDecimal value = new BigDecimal(i*100);
            LocalNFE localNFE = new LocalNFE(aK, value);
            testEntityManager.persist(localNFE);
        }
    }

    @Test
    public void whenFindAll_thenReturnAllLocalNfeList() {
        //when
        List<LocalNFE> localNFES = localNfeRepository.findAll();

        //then
        assertThat(localNFES).hasSize(5);
    }

    @Test
    public void whenFindByAccessKey_thenReturnNfe(){
        Optional<LocalNFE> optionalLocalNFE = localNfeRepository.findByAccessKey("1");

        if(optionalLocalNFE.isPresent()) {
            BigDecimal value = optionalLocalNFE.get().getNfe_total_value();
            assertThat(value.equals(new BigDecimal(100)));
        }
        else{
            assert false;
        }
    }

    @Test
    public void whenFindByAccessKey_thenReturnNothing(){
        Optional<LocalNFE> optionalLocalNFE = localNfeRepository.findByAccessKey("6");
        assertThat(!optionalLocalNFE.isPresent());
    }
}
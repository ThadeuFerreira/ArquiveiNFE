package com.example.arquiveiNFE.repository;

import com.example.arquiveiNFE.model.LocalNFE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@EnableJpaRepositories
public interface LocalNfeRepository extends JpaRepository<LocalNFE, Long> {

    Optional<LocalNFE> findByAccessKey(String accessKey);
}
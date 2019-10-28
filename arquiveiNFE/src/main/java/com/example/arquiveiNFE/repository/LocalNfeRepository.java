package com.example.arquiveiNFE.repository;

import com.example.arquiveiNFE.model.LocalNFE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalNfeRepository extends JpaRepository<LocalNFE, Long> {

    LocalNFE findByAccessKey(String accessKey);
}
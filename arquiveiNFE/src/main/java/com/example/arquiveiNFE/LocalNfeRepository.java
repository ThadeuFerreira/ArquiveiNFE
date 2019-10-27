package com.example.arquiveiNFE;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalNfeRepository extends JpaRepository<LocalNFE, Long> {

    LocalNFE findByAccessKey(String accessKey);
}
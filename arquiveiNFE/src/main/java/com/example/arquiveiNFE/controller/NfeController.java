package com.example.arquiveiNFE.controller;


import com.example.arquiveiNFE.ArquiveiService;
import com.example.arquiveiNFE.model.ArquiveiNFE;
import com.example.arquiveiNFE.repository.ArquiveiNfeRepository;
import com.example.arquiveiNFE.model.LocalNFE;
import com.example.arquiveiNFE.repository.LocalNfeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Api(value="Endpoint for Nota Fiscal Eletronica")
@RequestMapping("/api/v1")
public class NfeController {

    @Autowired
    private ArquiveiNfeRepository arquiveiNfeRepository;

    @Autowired
    private LocalNfeRepository localNfeRepository;

    @Autowired
    private ArquiveiService arquiveiService;

    @GetMapping("/update")
    @ApiOperation(value = "Updates local database with data from Arquivei API")
    public ResponseEntity updateAllNFE() throws IOException {

        // Check if the data is load in local database
        List<ArquiveiNFE> cache = arquiveiNfeRepository.findAll();
        if(!cache.isEmpty()){
            arquiveiService.saveInLocalDB(cache);
            return new ResponseEntity(HttpStatus.OK);
        }
        // Go to Arquivei endpoint and load NFE
        arquiveiService.getNfes();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/values")
    @ApiOperation(value = "Return all pairs of access-keys and total NFE values")
    public @ResponseBody
    List<LocalNFE> findAllLocalNFE(){
        return localNfeRepository.findAll();
    }

    @GetMapping("/value/{accessKey}")
    @ApiOperation(value = "Return total value for a NFE with given access_key")
    public @ResponseBody BigDecimal findValueByAccessKey(@PathVariable String accessKey){
        LocalNFE localNFE = localNfeRepository.findByAccessKey(accessKey);
        return localNFE.getNfe_total_value();
    }

    @GetMapping("/nfe")
    @ApiIgnore
    // Endpoint to check if NFE where loaded
    public @ResponseBody List<ArquiveiNFE> findaAllNFE(){
        return arquiveiNfeRepository.findAll();
    }

}

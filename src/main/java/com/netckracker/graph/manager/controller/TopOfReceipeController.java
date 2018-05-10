/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ReceipeInformationDto;
import com.netckracker.graph.manager.service.TopOfReceipesService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author eliza
 */
@RestController
public class TopOfReceipeController {
    @Autowired
    private TopOfReceipesService topService;
    
    @RequestMapping(value = "/topofreceipes/gettop", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public  ResponseEntity<?> getReceipesTop( @RequestParam (required=false) Integer page, @RequestParam (required=false) Integer size , 
            @RequestParam (value="userId", required=false) String userId, @RequestParam (required=false) String catalogId){        
        if (size==null&&page==null)
        {
            page=0;
            size=5;
        }
        List<ReceipeInformationDto> receipes=new ArrayList<>();
        if (userId==null&&catalogId==null)
        {            
            receipes=topService.getTopOfReceipes(page, size);
        }
        if(userId!=null&&catalogId==null)
        {
            receipes=topService.getTopReceipesOfUser(page, size, userId);
        }
        if(userId!=null&&catalogId!=null)
        {
            receipes=topService.getTopOfReceipesByCatalogAndUser(page, size, userId, catalogId);
        }
        if(userId==null&&catalogId!=null)
        {
            receipes=topService.getTopOfReceipesByCatalog(page, size, catalogId);
        }
        if (receipes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{           
            return new ResponseEntity<>(receipes, HttpStatus.OK);
        }
    }     
}

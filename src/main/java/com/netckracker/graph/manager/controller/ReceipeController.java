/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ReceipeInformationDto;
import com.netckracker.graph.manager.service.ReceipeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author eliza
 */
@RestController
public class ReceipeController {
    @Autowired
    private ReceipeService receipeService;
    
    @RequestMapping(value = "/receipe/addreceipe", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<ReceipeDto> addReceipe(@RequestParam String name, @RequestParam String description, 
            @RequestParam String catalogId, @RequestParam String userId, @RequestParam boolean isPublic ){
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, isPublic);
     return new ResponseEntity<>(receipe, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/receipe/getreceipeinfo/{receipeId}", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<ReceipeInformationDto> getReceipeInfo(@PathVariable   String receipeId){
        ReceipeInformationDto receipe=receipeService.getReceipeInformation(receipeId);
        if (receipe==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(receipe, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/receipe/setcompleted/{receipeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> setReceipeCompleted(@PathVariable   String receipeId){
        if (receipeService.isReceipeExcist(receipeId)==true)
        {
            receipeService.setCompleted(receipeId);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);        
    }
    
    @RequestMapping(value = "/receipe/delete/{receipeId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReceipe(@PathVariable ("receipeId") String receipeId, @RequestParam String userId) throws InterruptedException{
       if (receipeService.isReceipeExcist(receipeId)==true)
        {
            receipeService.deleteReceipe(receipeId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
       else return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
    }
    
    @RequestMapping(value = "/receipe/createversion/{receipeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> createReceipeVersion (@PathVariable   String receipeId, @RequestParam String userId){
        if (receipeService.isReceipeExcist(receipeId)==true)
        {
            receipeService.createReceipeVersion(receipeId, userId);
            return new ResponseEntity<>( HttpStatus.OK);
        }
       else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    @RequestMapping(value = "/receipe/addreceiperesources/{receipeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> addReceipeResource(@PathVariable ("receipeId") String receipeId, 
            @RequestParam("userId")  String userId, @RequestParam ("resourceId") String resourceId, @RequestParam ("resourceNumber") double resourceNumber){
        if (receipeService.isReceipeExcist(receipeId)==true)
        {
            String receipeResource=receipeService.addReceipeResources(receipeId, userId, resourceId, resourceNumber);
            if (receipeResource!=null)
            {
                return new ResponseEntity<>(receipeResource, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);            
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);        
    }
    @RequestMapping(value = "/receipe/getpublicandcompleted", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getPublicAndCompletesReceipes( @RequestParam int page, @RequestParam  int size){
        if (size==0&&page==0)
        {
            page=0;
            size=6;
        }
       List<ReceipeDto> receipes=receipeService.getPublicCompletedReceipes(page, size);
       if (receipes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{            
            return new ResponseEntity<>(receipes, HttpStatus.OK);
        } 
    }
    
    @RequestMapping(value = "/receipe/getbycatalog/{catalogId}", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getPublicAndCompletesReceipesByCatalogId(@PathVariable  String catalogId,
            @RequestParam( "page" ) int page, @RequestParam( "size" ) int size){
        if (size==0&&page==0)
        {
            page=0;
            size=6;
        }
       List<ReceipeDto> receipes=receipeService.getReceipesByCatalog(catalogId, page, size);
       if (receipes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{            
            return new ResponseEntity<>(receipes, HttpStatus.OK);
        } 
    }
  
}

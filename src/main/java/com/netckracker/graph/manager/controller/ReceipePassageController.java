/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author eliza
 */
@RestController
public class ReceipePassageController {
    @RequestMapping(value = "/receipepassage/makereceipe", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> makeReceipe(@RequestParam String receipeId, @RequestParam String userId, 
            @RequestBody List<String> users){
     //    String resourceId=resourceService.createResource(name, userId, measuring, ingredientOrResource, pictureId);
     return new ResponseEntity<>( HttpStatus.OK);
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.TagsDto;
import com.netckracker.graph.manager.service.ReceipeService;
import com.netckracker.graph.manager.service.TagService;
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
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private ReceipeService receipeService;
     
     @RequestMapping(value = "/receipe/getbytag/{tagName}",method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public  ResponseEntity<?> getReceipesByTag(@PathVariable  String  tagName, 
            @RequestParam (required=false) Integer page, @RequestParam (required=false) Integer size){
        
       if (size==null&&page==null)
        {
            page=0;
            size=5;
        }
        
        List<ReceipeDto> receipes=tagService.findByTag(tagName, page, size);
        if (receipes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{           
            return new ResponseEntity<>(receipes, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/receipe/addtag/{receipeId}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addTagToReceipe(@PathVariable  String receipeId,@RequestParam  String tagName){
        if (receipeService.isReceipeExcist(receipeId)==true)
        {
            tagService.addTag(receipeId, tagName);       
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/tag/getbyletters/{letters}",method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public  ResponseEntity<?> getTagsByLetters(@PathVariable  String  letters, @RequestParam(required=false, value="page" ) Integer page,
           @RequestParam(required=false, value="size" ) Integer size  ){
        
        if (size==null&&page==null)
        {
            page=0;
            size=6;
        }
        
        List<TagsDto> tags=tagService.findByFirstLetters(letters, page, size);
        if (tags.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{           
            return new ResponseEntity<>(tags, HttpStatus.OK);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.modelDto.InviteInformationDto;
import com.netckracker.graph.manager.modelDto.UserStepDto;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ReceipePassageService;
import com.netckracker.graph.manager.service.ReceipeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    @Autowired
    private ReceipePassageService passageService;
    @Autowired
    private ReceipeService receipeService;
    @Autowired
    private NodeService nodeService;
    
    @RequestMapping(value = "/receipepassage/makereceipe", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> makeReceipe(@RequestParam String sessionId,@RequestParam String receipeId, @RequestParam String userId,
            @RequestBody List<String> userIds)
    {
        if (receipeService.isReceipeExcist(receipeId)==true)
        {
            passageService.createUserSteps(sessionId, receipeId, userId, userIds);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);     
    }
    @RequestMapping(value = "/receipepassage/checkinvite", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> userStart(@RequestParam String userId){
        
           /* List<UserStepDto> userStep=passageService.getFirstStep(userId);
            if (userStep.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);    
            }
            else return new ResponseEntity<>(userStep, HttpStatus.OK);  */
           List<InviteInformationDto> invites=passageService.checkInvite(userId);
           if (invites.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);    
            }
           else return new ResponseEntity<>(invites, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/receipepassage/getnextstep", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getNextStep (@RequestParam String sessionId, @RequestParam String userId, @RequestParam(required=false) String perviousNodeId)
    {
        UserStepDto userStep=passageService.getNextStep(sessionId,  userId, perviousNodeId);
        if (userStep!=null)
        {
            if (nodeService.isLastNode(userStep.getNodeId())==true)
            {
                HttpHeaders headers = new HttpHeaders();
                headers.add("isLastNode", "true");
                return new ResponseEntity<>(userStep,headers, HttpStatus.OK);
            }
            else
            {
                HttpHeaders headers = new HttpHeaders();
                headers.add("isLastNode", "false");
                return new ResponseEntity<>(userStep,headers, HttpStatus.OK);
            }
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);         
    }
    
    @RequestMapping(value = "/receipepassage/completereceipe", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> completeReceipe(@RequestParam String sessionId,@RequestParam String receipeId, @RequestParam String userId)
    {
        if (receipeService.isReceipeExcist(receipeId)==true)
        {
            passageService.completeReceipe(sessionId, userId);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);     
    }
    
    @RequestMapping(value = "/receipepassage/getnotcompletedstep", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getNotCompletedStep (@RequestParam String sessionId, @RequestParam String userId)
    {
        UserStepDto userStep=passageService.getNotCompletedStep(sessionId, userId);
        if (userStep!=null)
        {
            if (nodeService.isLastNode(userStep.getNodeId())==true)
            {
                HttpHeaders headers = new HttpHeaders();
                headers.add("isLastNode", "true");
                return new ResponseEntity<>(userStep,headers, HttpStatus.OK);
            }
            else
            {
                HttpHeaders headers = new HttpHeaders();
                headers.add("isLastNode", "false");
                return new ResponseEntity<>(userStep,headers, HttpStatus.OK);
            }
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);         
    }
    
    /*    @RequestMapping(value = "/receipepassage/setstarted", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> setStepStarted (@RequestParam String sessionId, @RequestParam String userId,@RequestParam String nodeId )
    {
        passageService.setStepStarted(sessionId, userId, nodeId);
        return new ResponseEntity<>(HttpStatus.OK);    
    }
    */
    
    
    @RequestMapping(value = "/receipepassage/getpassinggraph", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getPassingGraph (@RequestParam String sessionId)
    {
        Map<String, Boolean> nodes=passageService.getAllGraph( sessionId);        
        if (nodes!=null)
            {            
                return new ResponseEntity<>(nodes, HttpStatus.OK);
            }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);         
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.UserStep;
import com.netckracker.graph.manager.modelDto.InviteInformationDto;
import com.netckracker.graph.manager.modelDto.NodeDto;
import com.netckracker.graph.manager.modelDto.UserStepDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eliza
 */
public interface ReceipePassageService {
    public void createUserSteps(String sessionId,String receipeId,String ownerUserId, List<String> userIds);
    public List<InviteInformationDto> checkInvite(String userId);
    public List<UserStepDto> getFirstStep(String userId);
    public UserStepDto getNextStep(String sessionId, String userId, String perviousNodeId);
    //public UserStepDto getFreeStep (String sessionId, String receipeId, String userId);
    public void completeReceipe(String sessionId, String userId);
    public Map<String, Boolean> getAllGraph(String sessionId);
    public UserStep getNotCompletedStep(String sessionId, String userId);
    public void setStepStarted(String sessionId, String userId, String nodeId);
    
}

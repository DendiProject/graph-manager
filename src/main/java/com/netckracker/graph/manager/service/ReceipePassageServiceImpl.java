/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.model.InvitedUsers;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.ReceipeVersion;
import com.netckracker.graph.manager.model.Sessions;
import com.netckracker.graph.manager.model.UserStep;
import com.netckracker.graph.manager.modelDto.InviteInformationDto;
import com.netckracker.graph.manager.modelDto.UserStepDto;
import com.netckracker.graph.manager.repository.InviteUsersRepository;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ReceipeVersionRepository;
import com.netckracker.graph.manager.repository.SessionsRepository;
import com.netckracker.graph.manager.repository.UserStepRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Boolean;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author eliza
 */
@Service
public class ReceipePassageServiceImpl implements ReceipePassageService{
    @Autowired
    private ReceipeVersionRepository versionRepository;
    @Autowired
    private ReceipeRepository receipeRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private Convertor convertor;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private UserStepRepository userStepRepository;
    @Autowired
    private InviteUsersRepository usersRepository;
    @Autowired
    private SessionsRepository sessionsRepository;

    @Override
    @Transactional
    public List<UserStepDto> getFirstStep( String userId) {
        boolean findedStep=false;
        List<InvitedUsers> users=usersRepository.findByUserId(userId);
        List<UserStepDto> stepsDto=new ArrayList<>();
        for (int j=0; j<users.size();j++)
        {
            findedStep=false;
            Sessions session=users.get(j).getSession();
            List<UserStep> stepps=userStepRepository.findBySession(session);
            ReceipeVersion version=stepps.get(0).getVersion();            
            List<Node> firstNodes=nodeRepository.findFirst(version.getVersionId());
            for (int i=0;i<firstNodes.size();i++)
            {
            /*    UserStep startedUserStep=getNotCompletedStep(session.getSessionId(),userId);
                if (startedUserStep!=null)
                {                   
                    stepsDto.add(convertor.convertNodeToUserStepDto(startedUserStep.getNode(),startedUserStep.isIsStarted(), session.getInviterId()));  
                    findedStep=true;
                    break;                    
                }*/
                UserStep userStep=userStepRepository.findByNodeAndSessionAndIsCompletedAndIsStarted(firstNodes.get(i), session, false, false);                
                if (userStep!=null)
                {                    
                    //userStep.setIsStarted(true);
                    userStep.setUserId(userId);
                    userStepRepository.saveAndFlush(userStep);
                    stepsDto.add(convertor.convertNodeToUserStepDto(userStep.getNode(),userStep.isIsStarted(), session.getInviterId()));  
                    findedStep=true;
                    break;
                }
            }
            if (findedStep==false)
            {
                stepsDto.add(convertor.convertNodeToUserStepDto(nodeService.getDefaultNode(), false, session.getInviterId()));
            }                                   
        }
        return stepsDto;
    }

    @Override
    @Transactional
    public UserStepDto getNextStep(String sessionId,String userId, String perviousNodeId) {
        Sessions session=sessionsRepository.findBySessionId(sessionId);
        if (session!=null)
        {
            if (perviousNodeId==null)
            {
               Node node=getFreeStep(session, userId);
               if (node!=null)
               {
                   return convertor.convertNodeToUserStepDto(node, true, session.getInviterId());
               }
            }
            else 
            {
                Node perviousNode=nodeRepository.findByNodeId(perviousNodeId);
                if ( perviousNode!=null)
                {
                    UserStep userStep=userStepRepository.findByNodeAndSession(perviousNode, session);                                 
                    userStep.setIsCompleted(true);                    
                    userStepRepository.saveAndFlush(userStep);
                    
                    List<Node> nextNodes=nodeRepository.findNextNode(perviousNode.getNodeId());    
                    for (int i=0; i<nextNodes.size();i++)
                    {
                        if (checkPerviousNodes(nextNodes.get(i), session)==true)
                        {
                            UserStep nextStep=userStepRepository.findByNodeAndSessionAndIsCompletedAndIsStarted(nextNodes.get(i), session, false, false);
                            if (nextStep!=null)                           
                            {                                
                                nextStep.setIsStarted(true);
                                nextStep.setUserId(userId);
                                UserStep savedStep=userStepRepository.save(nextStep);                                                            
                                return convertor.convertNodeToUserStepDto(savedStep.getNode(), true, session.getInviterId());  
                            }
                        }
                    }  
                    Node node=getFreeStep(session, userId);
                    if (node!=null)
                    {
                        return convertor.convertNodeToUserStepDto(node, true, session.getInviterId());
                    }                      
                }
                Node node=getFreeStep(session, userId);
                if (node!=null)
                {
                    return convertor.convertNodeToUserStepDto(node, true, session.getInviterId());
                } 
            }   
            Node defaultNode=nodeService.getDefaultNode();
            return convertor.convertNodeToUserStepDto(defaultNode, true, session.getInviterId());
        }
        else return null;        
    }  
    

    @Override
    @Transactional
    public void createUserSteps(String sessionId, String receipeId, String ownerUserId, List<String> userIds) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version=versionRepository.findByReceipeAndUserId(receipe, ownerUserId);
        if (version==null)
        {
            version=versionRepository.findByReceipeAndIsMainVersion(receipe, true);
        }
        List<Node> nodes=nodeRepository.findByVersion(version);
        Sessions session=new Sessions();
        session.setSessionId(sessionId);
        session.setInviterId(ownerUserId);
        session.setReceipeId(receipeId);
        Sessions saved=sessionsRepository.saveAndFlush(session);
        for (int i=0; i<nodes.size();i++)
        {
            UserStep userStep=new UserStep();
            userStep.setNode(nodes.get(i));
            userStep.setVersion(version);
            userStep.setIsCompleted(false);
            userStep.setIsStarted(false);
            userStep.setSession(saved);
            userStepRepository.save(userStep);
        }
        for (int i=0; i<userIds.size(); i++)
        {
            InvitedUsers user=new InvitedUsers();
            user.setUserId(userIds.get(i));
            user.setSession(session);
            usersRepository.save(user);
        }
    }

    public boolean checkPerviousNodes(Node currentNode,Sessions session)
    {        
        List<Node> nodes=nodeRepository.getPerviousNodes(currentNode.getNodeId());
        List<UserStep> steps=userStepRepository.findBySession(session);
        for (int i=0; i<nodes.size(); i++)
        {
            for (int j=0; j<steps.size();j++)
            {
                if (nodes.get(i).getNodeId().equals(steps.get(j).getNode().getNodeId()))
                {
                    if (steps.get(j).isIsCompleted()==false)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Transactional
    public Node getFreeStep(Sessions session, String userId) {
       List<UserStep> steps=userStepRepository.findBySessionAndIsCompletedAndIsStarted(session, false, false);
       for (int i=0; i<steps.size();i++)
       {           
               if (checkPerviousNodes(steps.get(i).getNode(), session)==true)
               {
                   Node node=steps.get(i).getNode();
                   UserStep step=steps.get(i);
                   step.setIsStarted(true);
                   step.setUserId(userId);
                   userStepRepository.save(step);
                   return node;                   
               }           
       }
       return null;
    } 

    @Override
    @Transactional
    public void completeReceipe(String sessionId, String userId) {
        Sessions session=sessionsRepository.findBySessionId(sessionId);
        if (session!=null)
        {
            List<UserStep> steps=userStepRepository.findBySession(session);
            for (int i=0; i<steps.size();i++)
            {
                userStepRepository.delete(steps.get(i));
            }
            List<InvitedUsers> users=usersRepository.findBySession(session);
            for (int i=0; i<users.size();i++)
            {
                usersRepository.delete(users.get(i));
            }
            sessionsRepository.delete(session);
        }
        
    } 

    @Override
    public Map<String, Boolean> getAllGraph(String sessionId) {
        Map <String,Boolean> nodes=new HashMap<>();
        Sessions session=sessionsRepository.findBySessionId(sessionId);
        if (session!=null)
        {
            List<UserStep> steps=userStepRepository.findBySession(session);
            for (int i=0; i<steps.size();i++)
            {
                nodes.put(steps.get(i).getNode().getNodeId(), steps.get(i).isIsCompleted());
            }
            return nodes;
        }
        else return null;       
    }
    
    public boolean checkUser(Sessions session, String userId)
    {        
        List<InvitedUsers> users=usersRepository.findByUserIdAndSession(userId, session);
        if (users.isEmpty())
        {
            return false;
        }
        else return true;
    }

    @Override
    public UserStepDto getNotCompletedStep(String sessionId, String userId) {
        Sessions session=sessionsRepository.findBySessionId(sessionId);
        if (session!=null)
        {
            UserStep userStep=userStepRepository.findByUserIdAndSessionAndIsCompletedAndIsStarted(userId, session, false, true);
            if (userStep!=null)
            {
                return convertor.convertNodeToUserStepDto(userStep.getNode(),userStep.isIsStarted(), session.getInviterId());
            }
        }
        return null;
        //return convertor.convertNodeToUserStepDto(nodeService.getDefaultNode());
    }

    @Override
    @Transactional
    public void setStepStarted(String sessionId, String userId, String nodeId) {
        Sessions session=sessionsRepository.findBySessionId(sessionId);
        if (session!=null)
        {
            UserStep userStep=userStepRepository.findByUserIdAndSessionAndIsCompletedAndIsStarted(userId, session, false, false);
                if (userStep!=null)
                {
                    userStep.setIsStarted(true);
                    userStepRepository.save(userStep);
                }
        }
    }

    @Override
    public List<InviteInformationDto>  checkInvite(String userId) {
        List<InvitedUsers> users=usersRepository.findByUserId(userId);
        List<InviteInformationDto> inviters=new ArrayList<>();
        for (int j=0; j<users.size();j++)
        {
          InviteInformationDto invite=new InviteInformationDto();
          Receipe receipe=receipeRepository.findByReceipeId(users.get(j).getSession().getReceipeId());
          if (receipe!=null)
          {
              invite.setReceipeInformation(convertor.convertReceipeToReceipeInformationDto(receipe));
          }
          invite.setSessionId(users.get(j).getSession().getSessionId());
          invite.setInviterId(users.get(j).getSession().getInviterId());
          inviters.add(invite);          
        }
        return inviters;
    }
}

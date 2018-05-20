/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.modelDto;

/**
 *
 * @author eliza
 */
public class InviteInformationDto {
    private ReceipeInformationDto receipeInformation;
    private String sessionId;
    private String inviterId;

    public ReceipeInformationDto getReceipeInformation() {
        return receipeInformation;
    }

    public void setReceipeInformation(ReceipeInformationDto receipeInformation) {
        this.receipeInformation = receipeInformation;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }
    
    
}

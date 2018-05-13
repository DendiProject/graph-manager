/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.model.ReceipeVersion;
import java.util.List;

/**
 *
 * @author eliza
 */
public class NodeState {
    private List<Node> nodes;
    private Node currentNode;
    private Node nextNode;
    List<NodeResources> currentInputResources;
    List<NodeResources> nextOutputResources;
    private boolean isContainsOnlyInputResources;
    private Node node;
    private ReceipeVersion receipeVersion;

    public ReceipeVersion getReceipeVersion() {
        return receipeVersion;
    }

    public void setReceipeVersion(ReceipeVersion receipeVersion) {
        this.receipeVersion = receipeVersion;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public List<NodeResources> getCurrentInputResources() {
        return currentInputResources;
    }

    public void setCurrentInputResources(List<NodeResources> currentInputResources) {
        this.currentInputResources = currentInputResources;
    }

    public List<NodeResources> getNextOutputResources() {
        return nextOutputResources;
    }

    public void setNextOutputResources(List<NodeResources> nextOutputResources) {
        this.nextOutputResources = nextOutputResources;
    }

    public boolean isIsContainsOnlyInputResources() {
        return isContainsOnlyInputResources;
    }

    public void setIsContainsOnlyInputResources(boolean isContainsOnlyInputResources) {
        this.isContainsOnlyInputResources = isContainsOnlyInputResources;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.iterator;

import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.ReceipeVersion;
import com.netckracker.graph.manager.repository.EdgesRepository;
import com.netckracker.graph.manager.repository.NodeRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author eliza
 */
@Component
public class NodeGraph {
    private List<Node> nodeList;
    private Map<Node, Boolean> usedNodes;
    private Queue queue; //очередь для добавления вершин при обходе в ширину
    @Autowired
    private EdgesRepository edgesRepository;
    @Autowired
    private NodeRepository nodeRepository;
    public NodeGraph() {
        usedNodes=new HashMap<>();
        queue=new LinkedList<>();
        nodeList=new ArrayList<>();
    }

    private void setAllNodesUnused(ReceipeVersion version)
    {
        System.out.println(version.getReceipe().getName());
        List<Node> nodes=nodeRepository.findByVersion(version);
        for (int i=0; i<nodes.size(); i++)
        {
            usedNodes.put(nodes.get(i), Boolean.FALSE);
        }
    }
 
    public List<Node> getNodeList (ReceipeVersion version)
    {
        setAllNodesUnused(version);
        Node lastNode=nodeRepository.findByLastNode(version.getVersionId());
        if (Objects.equals(usedNodes.get(lastNode), Boolean.TRUE))
        {
            
        }
        else {
            nodeList.add(lastNode);
            queue.add(lastNode);
            usedNodes.put(lastNode, Boolean.TRUE);
            while (!queue.isEmpty()) {
                Node currentNode=(Node) queue.poll();
                List<Edges> edges=edgesRepository.findByEndNode(currentNode);
                for (int i=0; i<edges.size(); i++)
                {
                    if (Objects.equals(usedNodes.get(edges.get(i).getStartNode()), Boolean.TRUE))
                    {
                        continue;
                    }
                    nodeList.add(edges.get(i).getStartNode());
                    queue.add(edges.get(i).getStartNode());
                    usedNodes.put(edges.get(i).getStartNode(), Boolean.TRUE);
                }
            }
        }
        return nodeList;
    }
    
    public void reset()
    {
        queue.clear();
        nodeList.clear();
        usedNodes.clear();
    }
    
}


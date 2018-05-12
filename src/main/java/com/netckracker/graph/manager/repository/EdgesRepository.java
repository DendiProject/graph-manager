/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.model.Node;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eliza
 */
@Repository
public interface EdgesRepository extends JpaRepository <Edges, String>  {
    Edges findByStartNodeAndEndNode(Node startNode, Node endNode);
    List<Edges> findByEndNode(Node endNode);
    @Query (value="SELECT DISTINCT e.* FROM EDGES e WHERE e.START_NODE_ID IN "
            + "(SELECT n.NODE_ID FROM NODE n WHERE n.version_id=:versionId) OR"
            + "e.END_NODE_ID IN"
            + "(SELECT n1.NODE_ID FROM NODE n1 WHERE n1.version_id=:versionId)",
            nativeQuery = true)
    List<Edges> findAllEdges(@Param ("versionId") String versionId);
    
    List <Edges> findByStartNodeOrEndNode(Node node1, Node node2);
  
}

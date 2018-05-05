/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.repository;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.ReceipeVersion;
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
public interface NodeRepository extends JpaRepository <Node, String> {
    Node findByNodeId(String nodeId);
    List<Node> findByVersion(ReceipeVersion version);
    
    @Query(value="SELECT n.* FROM NODE n JOIN EDGES e ON n.node_id=e.end_node_id "
            + "WHERE n.version_id=:versionId and e.end_node_id not in"
            + "(SELECT e1.start_node_id  FROM EDGES e1 JOIN NODE n1 ON e1.start_node_id=n1.node_id WHERE n1.version_id=:versionId)", 
            nativeQuery = true)
    Node findByLastNode(@Param("versionId") String versionId);

}

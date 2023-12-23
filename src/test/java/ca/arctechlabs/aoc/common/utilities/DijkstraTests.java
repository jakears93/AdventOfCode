package ca.arctechlabs.aoc.common.utilities;

import ca.arctechlabs.aoc.common.models.DijkstraNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DijkstraTests {
    @Test
    public void testDijkstraDistanceList_UsingValue(){
        DijkstraNode<String> a = new DijkstraNode<>("A");
        DijkstraNode<String> b = new DijkstraNode<>("B");
        DijkstraNode<String> c = new DijkstraNode<>("C");
        DijkstraNode<String> d = new DijkstraNode<>("D");
        DijkstraNode<String> e = new DijkstraNode<>("E");
        
        a.addNeighbour(b, 6);
        a.addNeighbour(d, 1);

        b.addNeighbour(a, 6);
        b.addNeighbour(c, 5);
        b.addNeighbour(d, 2);
        b.addNeighbour(e, 2);

        c.addNeighbour(b, 5);
        c.addNeighbour(e, 5);
        
        d.addNeighbour(a, 1);
        d.addNeighbour(b, 2);
        d.addNeighbour(e, 1);

        e.addNeighbour(b, 2);
        e.addNeighbour(c, 5);
        e.addNeighbour(d, 1);

        List<DijkstraNode<String>> result = Utils.dijkstraDistancesFromStart(a.getValue(), List.of(a,b,c,d,e));
        for(DijkstraNode<String> node : result){
            if(node.getValue().equals("A")){
                assertEquals(0, node.getDistance());
                assertNull(node.getPreviousVertex());
            }
            else if(node.getValue().equals("B")){
                assertEquals(3, node.getDistance());
                assertEquals(d, node.getPreviousVertex());
            }
            else if(node.getValue().equals("C")){
                assertEquals(7, node.getDistance());
                assertEquals(e, node.getPreviousVertex());
            }
            else if(node.getValue().equals("D")){
                assertEquals(1, node.getDistance());
                assertEquals(a, node.getPreviousVertex());
            }
            else if(node.getValue().equals("E")){
                assertEquals(2, node.getDistance());
                assertEquals(d, node.getPreviousVertex());
            }
        }
    }

    @Test
    public void testDijkstraDistanceList_UsingNode(){
        DijkstraNode<String> a = new DijkstraNode<>("A");
        DijkstraNode<String> b = new DijkstraNode<>("B");
        DijkstraNode<String> c = new DijkstraNode<>("C");
        DijkstraNode<String> d = new DijkstraNode<>("D");
        DijkstraNode<String> e = new DijkstraNode<>("E");

        a.addNeighbour(b, 6);
        a.addNeighbour(d, 1);

        b.addNeighbour(a, 6);
        b.addNeighbour(c, 5);
        b.addNeighbour(d, 2);
        b.addNeighbour(e, 2);

        c.addNeighbour(b, 5);
        c.addNeighbour(e, 5);

        d.addNeighbour(a, 1);
        d.addNeighbour(b, 2);
        d.addNeighbour(e, 1);

        e.addNeighbour(b, 2);
        e.addNeighbour(c, 5);
        e.addNeighbour(d, 1);

        List<DijkstraNode<String>> result = Utils.dijkstraDistancesFromStart(a, List.of(a,b,c,d,e));
        for(DijkstraNode<String> node : result){
            if(node.getValue().equals("A")){
                assertEquals(0, node.getDistance());
                assertNull(node.getPreviousVertex());
            }
            else if(node.getValue().equals("B")){
                assertEquals(3, node.getDistance());
                assertEquals(d, node.getPreviousVertex());
            }
            else if(node.getValue().equals("C")){
                assertEquals(7, node.getDistance());
                assertEquals(e, node.getPreviousVertex());
            }
            else if(node.getValue().equals("D")){
                assertEquals(1, node.getDistance());
                assertEquals(a, node.getPreviousVertex());
            }
            else if(node.getValue().equals("E")){
                assertEquals(2, node.getDistance());
                assertEquals(d, node.getPreviousVertex());
            }
        }
    }
}

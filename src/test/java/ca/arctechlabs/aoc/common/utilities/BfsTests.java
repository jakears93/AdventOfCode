package ca.arctechlabs.aoc.common.utilities;

import ca.arctechlabs.aoc.common.models.Coordinates;
import ca.arctechlabs.aoc.common.models.GraphNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BfsTests {

    @Test
    public void testBFS_validWithBranches(){
        List<GraphNode<Coordinates>> graph = mockGraph_14();
        GraphNode<Coordinates> start = graph.get(0);
        GraphNode<Coordinates> goal = graph.get(graph.size()-1);

        Integer result = PathFinder.bfs(start, goal, graph);
        assertEquals(14, result);
    }

    @Test
    public void testBFS_validNoBranches(){
        List<GraphNode<Coordinates>> graph = mockGraph_16();
        GraphNode<Coordinates> start = graph.get(0);
        GraphNode<Coordinates> goal = graph.get(graph.size()-1);

        Integer result = PathFinder.bfs(start, goal, graph);
        assertEquals(16, result);
    }

    @Test
    public void testBFS_NoPath(){
        List<GraphNode<Coordinates>> graph = mockGraph_null();
        GraphNode<Coordinates> start = graph.get(0);
        GraphNode<Coordinates> goal = graph.get(graph.size()-1);

        Integer result = PathFinder.bfs(start, goal, graph);
        assertNull(result);
    }

    private List<GraphNode<Coordinates>> mockGraph_14(){
        List<GraphNode<Coordinates>> graph = new ArrayList<>();
        String graphString = """
                S.....####
                ##.#######
                ##.....###
                #..#....##
                ##.####.##
                ...####..E""";
        String[] lines = graphString.split("\n");
        for(int y=0; y<lines.length; y++){
            String line = lines[y];
            for(int x=0; x< line.length(); x++){
                Character c = line.charAt(x);
                if(!c.equals('#')){
                    Coordinates coordinates = new Coordinates(x,y);
                    GraphNode<Coordinates> current = getCurrent(coordinates);
                    graph.add(current);
                }
            }
        }
        return graph;
    }

    private List<GraphNode<Coordinates>> mockGraph_16(){
        List<GraphNode<Coordinates>> graph = new ArrayList<>();
        String graphString = """
                S.........
                #########.
                #########.
                #########.
                #########.
                #########.
                #########.
                #########E""";
        String[] lines = graphString.split("\n");
        for(int y=0; y<lines.length; y++){
            String line = lines[y];
            for(int x=0; x< line.length(); x++){
                Character c = line.charAt(x);
                if(!c.equals('#')){
                    Coordinates coordinates = new Coordinates(x,y);
                    GraphNode<Coordinates> current = getCurrent(coordinates);
                    graph.add(current);
                }
            }
        }
        return graph;
    }

    private List<GraphNode<Coordinates>> mockGraph_null(){
        List<GraphNode<Coordinates>> graph = new ArrayList<>();
        String graphString = """
                S.....####
                ##########
                ##.....###
                #..#....##
                ##.####.##
                ...####..E""";
        String[] lines = graphString.split("\n");
        for(int y=0; y<lines.length; y++){
            String line = lines[y];
            for(int x=0; x< line.length(); x++){
                Character c = line.charAt(x);
                if(!c.equals('#')){
                    Coordinates coordinates = new Coordinates(x,y);
                    GraphNode<Coordinates> current = getCurrent(coordinates);
                    graph.add(current);
                }
            }
        }
        return graph;
    }

    private GraphNode<Coordinates> getCurrent(Coordinates coordinates) {
        GraphNode<Coordinates> current = new GraphNode<>(coordinates);
        current.setIsAPossibleNeighbour((node -> {
            //x+1, y
            if (coordinates.getX() - node.getValue().getX() == -1 && node.getValue().getY() == coordinates.getY()) return true;
                //x-1, y
            else if (coordinates.getX() - node.getValue().getX() == 1 && node.getValue().getY() == coordinates.getY()) return true;
                //x, y+1
            else if (coordinates.getX() == node.getValue().getX() && node.getValue().getY() - coordinates.getY() == -1) return true;
                //x, y-1
            else return coordinates.getX() == node.getValue().getX() && node.getValue().getY() - coordinates.getY() == 1;
        }));
        return current;
    }
}
package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        String input = "[(I,2),(A,5),(E,4),(F,1),(T,2),(S,3)]";

        Graph graph = new SingleGraph("Circular Directed Graph");

        graph.setAttribute("ui.stylesheet", "node { text-mode: normal; text-background-mode: plain; text-alignment: at-right; text-offset: 5px, 0px; }");

        Pattern pattern = Pattern.compile("\\((\\w),(\\d+)\\)");

        List<String> vertices = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String v = matcher.group(1);
            int x = Integer.parseInt(matcher.group(2));
            vertices.add(v);
            values.add(x);
            Node node = graph.addNode(v);
            node.setAttribute("ui.label", v);
        }

        for (int i = 0; i < vertices.size(); i++) {
            String v = vertices.get(i);
            int x = values.get(i);

            int leftIndex = (i - x + vertices.size()) % vertices.size();
            int rightIndex = (i + x) % vertices.size();

            String leftVertex = vertices.get(leftIndex);
            String rightVertex = vertices.get(rightIndex);

            String edgeIdLeft = "Edge_" + v + "_" + leftVertex;
            String edgeIdRight = "Edge_" + v + "_" + rightVertex;

            if (graph.getEdge(edgeIdLeft) == null) {
                graph.addEdge(edgeIdLeft, v, leftVertex, true);
            }

            if (graph.getEdge(edgeIdRight) == null) {
                graph.addEdge(edgeIdRight, v, rightVertex, true);
            }
        }

        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }
}

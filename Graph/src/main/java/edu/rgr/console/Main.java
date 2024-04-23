package edu.rgr.console;

import edu.rgr.graph.*;
import edu.rgr.task.TaskA;
import edu.rgr.task.TaskB;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Engine;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Rasterizer;


import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

public class Main {
    public static void main(String[] args) throws IOException {
       testB1();
    }

    private static void generateImage(SimpleGraph<String> graph, String name) throws IOException {
        guru.nidi.graphviz.model.Graph g = graph(); // Initialize DOT graph

        if (graph.getEdgeForm() == Graph.EdgeForm.Undirected) g = g.strict(); //  Do not draw arrows
        else g = g.directed(); // Draw all arrows
        // Iterate through all graph vertices
        Iterator<Vertex<String>> vi = graph.iteratorOfGraphVertices();
        while (vi.hasNext()) {
            Vertex<String> v = vi.next();
             // g = g.with(node(v.getName())); // Draw vertex even it doesnt have any edges
            // Iterate through all vertex edges
            Iterator<Edge<String>> ei = graph.iteratorOfVertexEdges(v);
            while (ei.hasNext()) {
                Edge<String> e = ei.next();
                if (e != null) {
                    g = g.with(node(Integer.toString(e.getStart().getIndex())).link(node(Integer.toString(e.getEnd().getIndex()))));
                }
            }
        }
        // Render result
        Graphviz.fromGraph(g).render(Format.SVG).toFile(new File("images/" + name +".svg"));
    }

    private static void generateImageWithPeriphery(SimpleGraph<String> graph, String name, HashSet<Vertex<String>> per) throws IOException {
        guru.nidi.graphviz.model.Graph g = graph(); // Initialize DOT graph

        if (graph.getEdgeForm() == Graph.EdgeForm.Undirected) g = g.strict(); //  Do not draw arrows
        else g = g.directed(); // Draw all arrows
        // Iterate through all graph vertices
        Iterator<Vertex<String>> vi = graph.iteratorOfGraphVertices();
        while (vi.hasNext()) {
            Vertex<String> v = vi.next();
            // g = g.with(node(v.getName())); // Draw vertex even it doesnt have any edges
            // Iterate through all vertex edges
            Iterator<Edge<String>> ei = graph.iteratorOfVertexEdges(v);
            while (ei.hasNext()) {
                Edge<String> e = ei.next();
                if (e != null) {
                    g = g.with(node(Integer.toString(e.getStart().getIndex())).link(node(Integer.toString(e.getEnd().getIndex()))));
                    if (per.contains(e.getStart())) {
                        g = g.with(node(Integer.toString(e.getStart().getIndex())).with(Color.RED));
                    }
                    if (per.contains(e.getEnd())) {
                        g = g.with(node(Integer.toString(e.getEnd().getIndex())).with(Color.RED));
                    }
                }
            }
        }
        // Render result
        Graphviz.fromGraph(g).engine(Engine.FDP).render(Format.SVG).toFile(new File("images/" + name +".svg"));
    }

    private static void testA1() throws IOException {
        SimpleGraph<String> graph = new SimpleGraph<>();
        graph.makeVertex("Zero");
        graph.makeVertex("One");
        graph.makeVertex("Two");
        graph.makeVertex("Three");
        graph.makeVertex("Four");
        graph.makeVertex("Five");
        graph.makeVertex("Six");
        graph.makeVertex("Seven");
        graph.makeVertex("Eight");
        graph.makeVertex("Nine");
        graph.makeVertex("Ten");
        graph.makeVertex("Eleven");
        graph.makeVertex("Twelve");
        graph.makeEdge(graph.getVertex(0), graph.getVertex(1));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(2));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(3));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(5));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(6));
        graph.makeEdge(graph.getVertex(2), graph.getVertex(3));
        graph.makeEdge(graph.getVertex(3), graph.getVertex(4));
        graph.makeEdge(graph.getVertex(3), graph.getVertex(5));
        graph.makeEdge(graph.getVertex(4), graph.getVertex(9));
        graph.makeEdge(graph.getVertex(6), graph.getVertex(4));
        graph.makeEdge(graph.getVertex(6), graph.getVertex(9));
        graph.makeEdge(graph.getVertex(7), graph.getVertex(6));
        graph.makeEdge(graph.getVertex(8), graph.getVertex(7));
        graph.makeEdge(graph.getVertex(9), graph.getVertex(10));
        graph.makeEdge(graph.getVertex(9), graph.getVertex(11));
        graph.makeEdge(graph.getVertex(9), graph.getVertex(12));
        graph.makeEdge(graph.getVertex(11), graph.getVertex(12));

        generateImage(graph, "unsorted");
        TaskA<String> taskA = new TaskA<>(graph);
        generateImage(taskA.result(), "sorted");
    }

    private static void testB1() throws IOException {
        SimpleGraph<String> graph = new SimpleGraph<>(Graph.EdgeForm.Undirected, DualGraph.GraphForm.List);
        graph.makeVertex("Zero");
        graph.makeVertex("One");
        graph.makeVertex("Two");
        graph.makeVertex("Three");
        graph.makeVertex("Four");
        graph.makeVertex("Five");
        graph.makeVertex("Six");
        graph.makeVertex("Seven");
        graph.makeVertex("Eight");
        graph.makeVertex("Nine");
        graph.makeVertex("Ten");
        graph.makeVertex("Eleven");
        graph.makeVertex("Twelve");
        graph.makeEdge(graph.getVertex(0), graph.getVertex(1));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(2));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(3));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(5));
        graph.makeEdge(graph.getVertex(0), graph.getVertex(6));
        graph.makeEdge(graph.getVertex(2), graph.getVertex(3));
        graph.makeEdge(graph.getVertex(3), graph.getVertex(4));
        graph.makeEdge(graph.getVertex(3), graph.getVertex(5));
        graph.makeEdge(graph.getVertex(4), graph.getVertex(9));
        graph.makeEdge(graph.getVertex(6), graph.getVertex(4));
        graph.makeEdge(graph.getVertex(6), graph.getVertex(9));
        graph.makeEdge(graph.getVertex(7), graph.getVertex(6));
        graph.makeEdge(graph.getVertex(8), graph.getVertex(7));
        graph.makeEdge(graph.getVertex(9), graph.getVertex(10));
        graph.makeEdge(graph.getVertex(9), graph.getVertex(11));
        graph.makeEdge(graph.getVertex(9), graph.getVertex(12));
        graph.makeEdge(graph.getVertex(11), graph.getVertex(12));

        TaskB<String> taskB = new TaskB<>(graph);
        HashSet<Vertex<String>> res = taskB.result();
        generateImageWithPeriphery(graph, "test", res);
    }
}

package edu.rgr.console;

import edu.rgr.graph.*;
import edu.rgr.task.TaskA;
import edu.rgr.task.TaskB;
import edu.rgr.visualizer.Visualizer;
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
        testA1();
        testB1();
    }

    private static void testA1() throws IOException {
        SimpleGraph<String> graph = createGraph(Graph.EdgeForm.Directed, DualGraph.GraphForm.Matrix);
        Visualizer.builder().format(Format.SVG).engine(Engine.DOT).filename("testA1pre").build().render(graph);
        TaskA<String> taskA = new TaskA<>(graph);
        Visualizer.builder().format(Format.SVG).engine(Engine.DOT).filename("testA1post").build().render(graph);
    }

    private static void testB1() throws IOException {
        SimpleGraph<String> graph = createGraph(Graph.EdgeForm.Undirected, DualGraph.GraphForm.Matrix);
        TaskB<String> taskB = new TaskB<>(graph);
        HashSet<Vertex<String>> res = taskB.result();
        Visualizer.builder().periphery(res).format(Format.SVG).engine(Engine.DOT).filename("testB1").build().render(graph);
    }

    private static SimpleGraph<String> createGraph(Graph.EdgeForm ef, DualGraph.GraphForm gf) {
        SimpleGraph<String> graph = new SimpleGraph<>(ef, gf);
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
        return graph;
    }
}

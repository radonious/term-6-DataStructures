package edu.rgr.visualizer;

import edu.rgr.graph.Graph;

import java.io.IOException;

public interface Visualizable {
    <V> void render(Graph<V> graph) throws IOException;
}

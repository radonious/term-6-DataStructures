package edu.rgr.visualizer;

import edu.rgr.graph.Graph;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface GraphVisualizer {
    <V> File toFile(Graph<V> graph) throws IOException;
    <V> BufferedImage toImage(Graph<V> graph) throws IOException;
    <V> String toStr(Graph<V> graph) throws IOException;
}

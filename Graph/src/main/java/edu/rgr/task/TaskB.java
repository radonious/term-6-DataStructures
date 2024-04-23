package edu.rgr.task;

import edu.rgr.graph.Edge;
import edu.rgr.graph.SimpleGraph;
import edu.rgr.graph.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/*
* 9. определение периферии взвешенного орграфа на основе алгоритма Флойда
*/

public class TaskB<V> implements WeightedTask<V> {
    SimpleGraph<V> graph;

    double[][] adjacency;
    double[][] control;

    HashSet<Vertex<V>> result;

    public TaskB(SimpleGraph<V> graph) {
        this.graph = graph;
        solve();
    }

    @Override
    public void solve() {
        result = new HashSet<>();
        adjacency = getAdjacencyMatrix(graph);
        control = new double[adjacency.length][adjacency.length];
        floydAlgorithm(); // Fill adjacency matrix with minimum sums of maximum paths (eccentricity)
        double diameter = findDiameter(); // Graph diameter equals maximum of it's eccentricity
        findResult(diameter);
    }

    private void findResult(double diameter) {
        for (int i = 0; i < adjacency.length - 1; i++) {
            for (int j = i + 1; j < adjacency.length; j++) {
                if (adjacency[i][j] == diameter) {
                    result.add(graph.getVertex(i));
                }
            }
        }
    }

    private double findDiameter() {
        double diameter = 0;
        for (double[] str : adjacency) {
            for (double num : str) {
                if (num > diameter && num != Double.POSITIVE_INFINITY) {
                    diameter = num;
                }
            }
        }
        return diameter;
    }

    private void floydAlgorithm() {
        // K - Increasing path length
        for (int k = 0; k < adjacency.length; k++) {
            // I, J - Indexes for all possible vertex pairs
            for (int i = 0; i < adjacency.length; i++) {
                for (int j = 0; j < adjacency.length; j++) {
                    double len = adjacency[i][k] + adjacency[k][j];
                    // If new path is shorter, then store it and control transship vertex
                    if (len < adjacency[i][j]) {
                        adjacency[i][j] = len;
                        control[i][j] = k;
                    }
                }
            }
        }
    }

    private double[][] getAdjacencyMatrix(SimpleGraph<V> graph) {
        double[][] res = new double[graph.sizeVertex()][graph.sizeVertex()];
        for (int i = 0; i < graph.sizeVertex(); i++) {
            Vertex<V> v = graph.getVertex(i);
            Iterator<Edge<V>> vei = graph.iteratorOfVertexEdges(v);
            // Weight value infinite if there is no pure edge between vertices
            for (int n = 0; n < graph.sizeVertex(); n++) res[i][n] = Double.POSITIVE_INFINITY;
            while (vei.hasNext()) {
                Edge<V> e = vei.next();
                // Floyd's algorithm doesn't use graph loops
                if (i != e.getEnd().getIndex()) {
                    // If edge has no weight then we will assume that it's weight is 1
                    int weight = e.getWeight() == null ? 1 : e.getWeight();
                    res[i][e.getEnd().getIndex()] = weight;
                }
            }
        }
        return res;
    }

    @Override
    public void set(SimpleGraph<V> graph) {
        this.graph = graph;
    }

    @Override
    public void restart() {
        solve();
    }

    public HashSet<Vertex<V>> result() {
        return result;
    }
}

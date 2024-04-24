package edu.rgr.graph;

import java.util.Iterator;
import java.util.List;

public interface Graph<V> {
    int sizeVertex();

    int sizeEdge();

    double calcK();

    EdgeForm getEdgeForm();

    Vertex<V> makeVertex();

    Vertex<V> makeVertex(V data);

    Vertex<V> getVertex(int index);

    void deleteVertex(Vertex<V> v);

    Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd);

    Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd, Integer weight);

    Edge<V> getEdge(Vertex<V> vStart, Vertex<V> vEnd);

    List<Edge<V>> getEdges(Vertex<V> v);

    void deleteEdge(Vertex<V> vStart, Vertex<V> vEnd);

    Iterator<Vertex<V>> iteratorOfGraphVertices();

    Iterator<Edge<V>> iteratorOfGraphEdges();

    Iterator<Edge<V>> iteratorOfVertexEdges(Vertex<V> src);

    void clear();

    enum EdgeForm {
        Directed,
        Undirected,
    }
}

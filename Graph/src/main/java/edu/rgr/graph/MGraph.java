package edu.rgr.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MGraph<V> implements Graph<V> {

    private final ArrayList<Vertex<V>> vertices;
    private final ArrayList<ArrayList<Edge<V>>> edges;

    private int countVertex;
    private int countEdge;

    private EdgeForm edgeForm;

    MGraph() {
        countVertex = 0;
        countEdge = 0;
        edgeForm = EdgeForm.Undirected;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    MGraph(EdgeForm ef) {
        this();
        edgeForm = ef;
    }

    @Override
    public int sizeVertex() {
        return countVertex;
    }

    @Override
    public int sizeEdge() {
        return countEdge;
    }

    @Override
    public double calcK() {
        int n = sizeEdge();
        return (double) n / (n * (n - 1) / 2.0);
    }

    @Override
    public EdgeForm getEdgeForm() {
        return edgeForm;
    }

    @Override
    public Vertex<V> makeVertex() {
        return makeVertex(null);
    }

    @Override
    public Vertex<V> makeVertex(V data) {
        Vertex<V> vertex = new Vertex<>(countVertex++, data);
        vertices.add(vertex);
        edges.add(new ArrayList<>(countVertex));
        for (int i = 0; i < countVertex - 1; i++) {
            edges.get(vertex.getIndex()).add(null);
        }
        for (ArrayList<Edge<V>> adj : edges) {
            adj.add(null);
        }
        return vertex;
    }

    @Override
    public Vertex<V> getVertex(int index) {
        return vertices.get(index);
    }

    @Override
    public void deleteVertex(Vertex<V> v) {
        vertices.remove(v);
        edges.remove(v.getIndex());
        for (ArrayList<Edge<V>> adj : edges) {
            adj.remove(v.getIndex());
        }
    }

    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        return makeEdge(vStart, vEnd, null);
    }

    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd, Integer weight) {
        if (edges.get(vStart.getIndex()).get(vEnd.getIndex()) == null) ++countEdge;
        Edge<V> edge = new Edge<>(vStart, vEnd, weight);
        edges.get(vStart.getIndex()).set(vEnd.getIndex(), edge);
        if (edgeForm == EdgeForm.Undirected && vStart.getIndex() != vEnd.getIndex()) edges.get(vEnd.getIndex()).set(vStart.getIndex(), new Edge<>(vEnd, vStart, weight, Edge.Type.COPY));
        return edge;
    }

    @Override
    public Edge<V> getEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        return edges.get(vStart.getIndex()).get(vEnd.getIndex());
    }

    @Override
    public List<Edge<V>> getEdges(Vertex<V> v) {
        return edges.get(v.getIndex());
    }

    @Override
    public void deleteEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        Edge<V> edge = edges.get(vStart.getIndex()).get(vEnd.getIndex());
        if (edge != null) {
            --countEdge;
            edges.get(vStart.getIndex()).set(vEnd.getIndex(), null);
            if (edgeForm == EdgeForm.Undirected) {
                edges.get(vEnd.getIndex()).set(vStart.getIndex(), null);
            }
        }
    }

    @Override
    public void clear() {
        countVertex = 0;
        countEdge = 0;
        vertices.clear();
        edges.clear();
    }

    @Override
    public Iterator<Vertex<V>> iteratorOfGraphVertices() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < countVertex;
            }

            @Override
            public Vertex<V> next() {
                return getVertex(index++);
            }
        };
    }

    @Override
    public Iterator<Edge<V>> iteratorOfGraphEdges() {
        return new Iterator<>() {
            int countFound = 0;
            int vertexIndex = 0, edgeIndex = 0;

            @Override
            public boolean hasNext() {
                return countFound < countEdge;
            }

            @Override
            public Edge<V> next() {
                boolean found = false;
                while (!found) {
                    if (edgeIndex < edges.get(vertexIndex).size() && edges.get(vertexIndex).get(edgeIndex) != null && edges.get(vertexIndex).get(edgeIndex).getType() != Edge.Type.COPY) {
                        found = true;
                    } else if (edgeIndex < edges.get(vertexIndex).size()) {
                        ++edgeIndex;
                    } else {
                        edgeIndex = 0;
                        ++vertexIndex;
                    }
                }
                ++countFound;
                return edges.get(vertexIndex).get(edgeIndex++);
            }
        };
    }

    @Override
    public Iterator<Edge<V>> iteratorOfVertexEdges(Vertex<V> src) {
        return new Iterator<>() {
            final List<Edge<V>> list = getEdges(src);
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public Edge<V> next() {
                return list.get(index++);
            }
        };
    }
}

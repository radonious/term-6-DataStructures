package edu.rgr.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MGraph<V> implements Graph<V> {

    private final ArrayList<Vertex<V>> vertices;
    private final ArrayList<ArrayList<Edge<V>>> edges;

    private int id = 0;

    private int countEdge;

    private EdgeForm edgeForm;

    MGraph() {
        countEdge = 0;
        edgeForm = EdgeForm.Undirected;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    MGraph(EdgeForm ef) {
        this();
        edgeForm = ef;
    }

    private int currentIndex(Vertex<V> v) {
        return vertices.indexOf(v);
    }

    @Override
    public int sizeVertex() {
        return vertices.size();
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
        Vertex<V> vertex = new Vertex<>(id++, data);
        vertices.add(vertex);
        edges.add(new ArrayList<>(sizeVertex()));
        for (int i = 0; i < sizeVertex() - 1; i++) {
            edges.get(currentIndex(vertex)).add(null);
        }
        for (ArrayList<Edge<V>> adj : edges) {
            adj.add(null);
        }
        return vertex;
    }

    @Override
    public Vertex<V> getVertex(int index) {
        for (Vertex<V> vertex : vertices) {
            if (vertex.getIndex() == index) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public void deleteVertex(Vertex<V> v) {
        if (v == null || !vertices.contains(v)) return;
        countEdge -= (int) edges.get(currentIndex(v)).stream().filter(e -> e != null && Edge.Type.MAIN.equals(e.getType())).count(); // Main edges
        edges.remove(currentIndex(v)); // Horizontal delete of edges
        for (ArrayList<Edge<V>> adj : edges) {
            // Null all edges linked with deleted vertex
            for (int j = 0; j < adj.size(); ++j) {
                if (adj.get(j) != null && adj.get(j).getEnd() == v) {
                    if (adj.get(j).getType() == Edge.Type.MAIN) --countEdge; // Count only main edges
                    adj.set(j, null);
                }
            }
            // Vertical delete of edges
            adj.remove(currentIndex(v));
        }
        vertices.remove(v);
    }



    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        return makeEdge(vStart, vEnd, null);
    }

    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd, Integer weight) {
        // Pointless to create edge with nulls or not existing vertices
        if (vStart == null || vEnd == null) return null;
        else if (!vertices.contains(vStart) || !vertices.contains(vEnd)) return null;

        if (edges.get(vStart.getIndex()).get(vEnd.getIndex()) == null) ++countEdge; // Increment count if edge don't exist (update != add new)
        Edge<V> edge = new Edge<>(vStart, vEnd, weight, Edge.Type.MAIN); // Create new main edge
        edges.get(vStart.getIndex()).set(vEnd.getIndex(), edge); // Set main edge
        // Add reversed edge copy if graph undirected and it's not loop
        if (edgeForm == EdgeForm.Undirected && vStart.getIndex() != vEnd.getIndex()) {
            edges.get(vEnd.getIndex()).set(vStart.getIndex(), new Edge<>(vEnd, vStart, weight, Edge.Type.COPY));
        }

        return edge;
    }

    @Override
    public Edge<V> getEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        if (vertices.contains(vStart) && vertices.contains(vEnd)) {
            return edges.get(vStart.getIndex()).get(vEnd.getIndex());
        } else {
            return null;
        }
    }

    @Override
    public List<Edge<V>> getEdges(Vertex<V> v) {
        if (v == null || !vertices.contains(v)) return null;
        return edges.get(vertices.indexOf(v));
    }

    @Override
    public void deleteEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        if (vStart == null || vEnd == null) return;
        else if (!vertices.contains(vStart) || !vertices.contains(vEnd)) return;

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
        id = 0;
        countEdge = 0;
        vertices.clear();
        edges.clear();
    }

    @Override
    public Iterator<Vertex<V>> iteratorOfGraphVertices() {
        return new Iterator<>() {
            int current = 0;

            @Override
            public boolean hasNext() {
                return current < sizeVertex();
            }

            @Override
            public Vertex<V> next() {
                return vertices.get(current++);
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

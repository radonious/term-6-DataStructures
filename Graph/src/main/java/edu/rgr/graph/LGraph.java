package edu.rgr.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LGraph<V> implements Graph<V> {

    private final HashMap<Vertex<V>, LinkedList<Edge<V>>> graph;

    private int countVertex;
    private int countEdge;

    private EdgeForm edgeForm;

    LGraph() {
        countVertex = 0;
        countEdge = 0;
        edgeForm = EdgeForm.Undirected;
        graph = new HashMap<>();
    }

    LGraph(EdgeForm ef) {
        this();
        edgeForm = ef;
    }

    LGraph(MGraph donor) {
        this();
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
        graph.put(vertex, new LinkedList<>());
        return vertex;
    }

    @Override
    public Vertex<V> getVertex(int index) {
        for (Vertex<V> vertex : graph.keySet()) {
            if (vertex.getIndex() == index) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public void deleteVertex(Vertex<V> v) {
        graph.remove(v);
    }

    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        return makeEdge(vStart, vEnd, null);
    }

    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd, Integer weight) {
        Edge<V> edge = new Edge<>(vStart, vEnd, weight);
        boolean foundSameEdge = false;
        for (int i = 0; i < graph.get(vStart).size(); ++i) {
            if (graph.get(vStart).get(i).getEnd().getIndex() == vEnd.getIndex()) {
                graph.get(vStart).set(i, edge);
                foundSameEdge = true;
                break;
            }
        }
        if (!foundSameEdge) {
            ++countEdge;
            graph.get(vStart).add(edge);
            if (edgeForm == EdgeForm.Undirected && vStart.getIndex() != vEnd.getIndex()) {
                graph.get(vEnd).add(new Edge<>(vEnd, vStart, weight, Edge.Type.COPY));
            }
        } else if (edgeForm == EdgeForm.Undirected) {
            for (int i = 0; i < graph.get(vEnd).size(); ++i) {
                if (graph.get(vEnd).get(i).getStart() == vStart) {
                    graph.get(vEnd).set(i, new Edge<>(vEnd, vStart, weight));
                    break;
                }
            }
        }

        return edge;
    }

    @Override
    public Edge<V> getEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        for (Edge<V> edge : graph.get(vStart)) {
            if (edge.getEnd() == vEnd) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public List<Edge<V>> getEdges(Vertex<V> v) {
        return graph.get(v);
    }

    @Override
    public void deleteEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        Edge<V> tmp = null;
        for (Edge<V> edge : graph.get(vStart)) {
            if (edge.getEnd() == vEnd) {
                tmp = edge;
                break;
            }
        }
        if (tmp != null) {
            --countEdge;
            graph.get(vStart).remove(tmp);
            if (edgeForm == EdgeForm.Undirected) {
                graph.get(vEnd).remove(tmp);
            }
        }
    }

    @Override
    public void clear() {
        countVertex = 0;
        countEdge = 0;
        graph.clear();
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
                    if (edgeIndex < graph.get(getVertex(vertexIndex)).size() && graph.get(getVertex(vertexIndex)).get(edgeIndex).getType() != Edge.Type.COPY) {
                        found = true;
                    } else if (edgeIndex < graph.get(getVertex(vertexIndex)).size()) {
                        ++edgeIndex;
                    } else {
                        edgeIndex = 0;
                        do {
                            ++vertexIndex;
                        } while (graph.get(getVertex(vertexIndex)).isEmpty());
                    }
                }
                ++countFound;
                return graph.get(getVertex(vertexIndex)).get(edgeIndex++);
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

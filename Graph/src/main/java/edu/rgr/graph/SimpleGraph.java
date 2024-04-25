package edu.rgr.graph;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SimpleGraph<V> implements DualGraph<V> {

    private GraphForm graphForm;

    private Graph<V> graph;

    public SimpleGraph() {
        graphForm = GraphForm.Matrix;
        initGraph(EdgeForm.Directed, graphForm);
    }

    public SimpleGraph(EdgeForm ef, GraphForm gf) {
        graphForm = gf;
        initGraph(ef, graphForm);
    }

    public SimpleGraph(int cv, EdgeForm ef, GraphForm gf) {
        graphForm = gf;
        initGraph(ef, graphForm);
        for (int i = 0; i < cv; ++i) {
            graph.makeVertex();
        }
    }

    public SimpleGraph(int cv, int ce, EdgeForm ef, GraphForm gf) {
        graphForm = gf;
        initGraph(ef, graphForm);
        for (int i = 0; i < cv; ++i) {
            graph.makeVertex();
        }
        for (int i = 0; i < ce; ++i) {
            makeRandomEdge();
        }
    }

    public SimpleGraph(SimpleGraph<V> donor) {
        graphForm = donor.graphForm;
        graph = donor.graph;
    }

    private void initGraph(EdgeForm ef, GraphForm gf) {
        if (gf == GraphForm.List) {
            graph = new LGraph<>(ef);
        } else {
            graph = new MGraph<>(ef);
        }
    }

    private void makeRandomEdge() {
        Random r = new Random();
        graph.makeEdge(graph.getVertex(r.nextInt(graph.sizeVertex())), graph.getVertex(r.nextInt(graph.sizeVertex())), r.nextInt(graph.sizeVertex()));
    }

    @Override
    public GraphForm getGraphForm() {
        return graphForm;
    }

    @Override
    public EdgeForm getEdgeForm() {
        return graph.getEdgeForm();
    }

    @Override
    public void toListGraph() {
        if (graphForm == GraphForm.List) return;
        Graph<V> newGraph = new LGraph<>(graph.getEdgeForm());
        copyGraph(newGraph);
        graphForm = GraphForm.List;
        graph = newGraph;
    }

    @Override
    public void toMatrixGraph() {
        if (graphForm == GraphForm.Matrix) return;
        Graph<V> newGraph = new MGraph<>(graph.getEdgeForm());
        copyGraph(newGraph);
        graphForm = GraphForm.Matrix;
        graph = newGraph;
    }

    private void copyGraph(Graph<V> newGraph) {
        Iterator<Vertex<V>> vi = iteratorOfGraphVertices();
        while (vi.hasNext()) {
            newGraph.makeVertex(vi.next().getData());
        }
        Iterator<Edge<V>> ei = iteratorOfGraphEdges();
        while (ei.hasNext()) {
            Edge<V> e = ei.next();
            newGraph.makeEdge(newGraph.getVertex(e.getStart().getIndex()), newGraph.getVertex(e.getEnd().getIndex()), e.getWeight());
        }
    }

    @Override
    public int sizeVertex() {
        return graph.sizeVertex();
    }

    @Override
    public int sizeEdge() {
        return graph.sizeEdge();
    }

    @Override
    public double calcK() {
        return graph.calcK();
    }

    @Override
    public Vertex<V> makeVertex() {
        return graph.makeVertex();
    }

    @Override
    public Vertex<V> makeVertex(V data) {
        return graph.makeVertex(data);
    }

    @Override
    public Vertex<V> getVertex(int index) {
        return graph.getVertex(index);
    }

    @Override
    public void deleteVertex(Vertex<V> v) {
        graph.deleteVertex(v);
    }

    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        return graph.makeEdge(vStart, vEnd);
    }

    @Override
    public Edge<V> makeEdge(Vertex<V> vStart, Vertex<V> vEnd, Integer weight) {
        return graph.makeEdge(vStart, vEnd, weight);
    }

    @Override
    public Edge<V> getEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        return graph.getEdge(vStart, vEnd);
    }

    @Override
    public List<Edge<V>> getEdges(Vertex<V> v) {
        return graph.getEdges(v);
    }

    @Override
    public void deleteEdge(Vertex<V> vStart, Vertex<V> vEnd) {
        graph.deleteEdge(vStart, vEnd);
    }

    @Override
    public Iterator<Vertex<V>> iteratorOfGraphVertices() {
        return graph.iteratorOfGraphVertices();
    }

    @Override
    public Iterator<Edge<V>> iteratorOfGraphEdges() {
        return graph.iteratorOfGraphEdges();
    }

    @Override
    public Iterator<Edge<V>> iteratorOfVertexEdges(Vertex<V> src) {
        return graph.iteratorOfVertexEdges(src);
    }

    @Override
    public void clear() {
        graph.clear();
    }

    @Override
    public void print() {
        Iterator<Vertex<V>> vi = iteratorOfGraphVertices();
        while (vi.hasNext()) {
            Vertex<V> v = vi.next();
            System.out.print(v + " : ");
            Iterator<Edge<V>> ei = iteratorOfVertexEdges(v);
            while (ei.hasNext()) {
                Edge<V> e = ei.next();
                if (e != null) System.out.print(e + " ");
            }
            System.out.print('\n');
        }
    }
}

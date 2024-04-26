package edu.rgr.task;

import edu.rgr.graph.Edge;
import edu.rgr.graph.Graph;
import edu.rgr.graph.SimpleGraph;
import edu.rgr.graph.Vertex;

import java.util.ArrayDeque;
import java.util.Iterator;

/*
* 5. Определение последовательности вершин в результате прямой топологической
* сортировки ациклического орграфа DAG (сортировка - на основе очереди истоков)
*/

public class TaskA<V> {
    SimpleGraph<V> graph;
    ArrayDeque<Vertex<V>> stack;
    boolean[] visited;

    public TaskA(SimpleGraph<V> graph) {
        this.graph = graph;
        solve();
    }

    public void solve() {
        if (graph.getEdgeForm() == Graph.EdgeForm.Undirected) {
            System.err.println("Graph must be directed.");
            return;
        }

        stack = new ArrayDeque<>();
        visited = new boolean[graph.sizeVertex()];

        Iterator<Vertex<V>> vi = graph.iteratorOfGraphVertices();
        while (vi.hasNext()) {
            Vertex<V> vertex = vi.next();
            BFS(vertex);
        }

        int newIndex = 0;
        System.out.println("TOPOLOGICAL ORDER:");
        while (!stack.isEmpty()) {
            Vertex<V> vertex = stack.pop();
            System.out.println( "Index: " + newIndex + " - Vertex: [" + vertex.getIndex() + "]");
            vertex.setIndex(newIndex);
            ++newIndex;
        }

    }

    private void BFS(Vertex<V> vertex) {
        boolean toStack = !visited[vertex.getIndex()];
        visited[vertex.getIndex()] = true;
        Iterator<Edge<V>> vei = graph.iteratorOfVertexEdges(vertex);
        while (vei.hasNext()) {
            Edge<V> edge = vei.next();
            if (edge != null ) {
                Vertex<V> child = edge.getEnd();
                if (!visited[child.getIndex()]) {
                    BFS(child);
                }
            }

        }
        if (toStack) stack.push(vertex);
    }

    public void set(SimpleGraph<V> graph) {
        this.graph = graph;
    }

    public void restart() {
        solve();
    }

    public SimpleGraph<V> result() {
        return graph;
    }
}

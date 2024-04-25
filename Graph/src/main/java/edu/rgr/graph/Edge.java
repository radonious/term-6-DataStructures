package edu.rgr.graph;

public class Edge<V> {
    private final Vertex<V> start;
    private final Vertex<V> end;
    private final Integer weight;
    private final Type type;

    Edge(Vertex<V> vStart, Vertex<V> vEnd) {
        start = vStart;
        end = vEnd;
        this.weight = null;
        type = Type.MAIN;
    }

    Edge(Vertex<V> vStart, Vertex<V> vEnd, Type type) {
        start = vStart;
        end = vEnd;
        this.weight = null;
        this.type = type;
    }

    Edge(Vertex<V> vStart, Vertex<V> vEnd, Integer weight) {
        start = vStart;
        end = vEnd;
        this.weight = weight;
        type = Type.MAIN;
    }

    Edge(Vertex<V> vStart, Vertex<V> vEnd, Integer weight, Type type) {
        start = vStart;
        end = vEnd;
        this.weight = weight;
        this.type = type;
    }

    public Vertex<V> getStart() {
        return start;
    }

    public Vertex<V> getEnd() {
        return end;
    }

    public Integer getWeight() {
        return weight;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ", " + weight +", " + type +")";
    }

    public enum Type {
        COPY,
        MAIN
    }
}

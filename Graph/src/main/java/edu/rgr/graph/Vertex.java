package edu.rgr.graph;

public class Vertex<V> {
    private int index;
    private String name;
    private V data;

    public Vertex(int index, String name, V data) {
        this.index = index;
        this.name = name;
        this.data = data;
    }

    public Vertex(int index) {
        this(index, String.valueOf(index), null);
    }

    public Vertex(int index, V data) {
        this(index, String.valueOf(index), data);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "[" + index + "]";
    }
}

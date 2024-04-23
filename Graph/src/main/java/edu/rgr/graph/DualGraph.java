package edu.rgr.graph;

import java.util.Iterator;

public interface DualGraph<V> extends Graph<V>{
    GraphForm getGraphForm();

    void toListGraph();

    void toMatrixGraph();

    void print();

    enum GraphForm {
        List,
        Matrix,
    }
}

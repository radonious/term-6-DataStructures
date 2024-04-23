package edu.rgr.task;


import edu.rgr.graph.SimpleGraph;

public interface WeightedTask<V> {
    void solve();

    void set(SimpleGraph<V> graph);

    void restart();
}

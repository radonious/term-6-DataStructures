package edu.rgr.task;

import edu.rgr.graph.SimpleGraph;

public interface UnweightedTask<V> {
    void solve();

    void set(SimpleGraph<V> graph);

    void restart();
}

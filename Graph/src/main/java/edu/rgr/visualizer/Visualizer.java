package edu.rgr.visualizer;

import edu.rgr.graph.SimpleGraph;
import guru.nidi.graphviz.engine.Engine;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.model.Graph;


public class Visualizer implements Visualizable {
    Graph graph;
    Format renderer;
    Engine engine;
    String filename;
    //HashSet<Vertex<>>

    private Visualizer() {
        // Private constructor
    }

    @Override
    public void visualize(SimpleGraph) {

    }

    @Override
    public void render(String filename) {

    }

    public static Builder builder() {
        return new Visualizer().new Builder();
    }

    public class Builder {
        private Builder() {
            // Private constructor
        };

        public Builder format(Format value) {
            renderer = value;
            return this;
        }

        public Builder engine(Engine value) {
            engine = value;
            return this;
        }

        public Builder filename(String value) {
            filename = value;
            return this;
        }
    }
}

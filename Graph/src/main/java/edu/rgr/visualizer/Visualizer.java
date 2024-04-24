package edu.rgr.visualizer;

import edu.rgr.graph.Edge;
import edu.rgr.graph.Graph;
import edu.rgr.graph.Vertex;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Engine;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

public class Visualizer implements Visualizable {
    boolean drawUnlinked;
    Format format = Format.SVG;
    Engine engine = Engine.DOT;
    String filename = "result";
    HashSet<Vertex<?>> periphery = null;

    private Visualizer() {
        // Private constructor
    }

    @Override
    public <V> void render(Graph<V> graph) throws IOException {
        guru.nidi.graphviz.model.Graph g = graph(); // Initialize DOT graph
        if (graph.getEdgeForm() == Graph.EdgeForm.Undirected) g = g.strict(); //  Do not draw arrows
        else g = g.directed(); // Draw all arrows
        Iterator<Vertex<V>> vi = graph.iteratorOfGraphVertices(); // Iterate through all graph vertices
        while (vi.hasNext()) {
            Vertex<V> v = vi.next();
            if (drawUnlinked) g = g.with(node(v.getName())); // Draw vertex even it doesnt have any edges
            Iterator<Edge<V>> ei = graph.iteratorOfVertexEdges(v);  // Iterate through all vertex edges
            while (ei.hasNext()) {
                Edge<V> e = ei.next();
                if (e != null) {
                    g = g.with(node(Integer.toString(e.getStart().getIndex())).link(node(Integer.toString(e.getEnd().getIndex()))));
                    if (periphery != null && periphery.contains(e.getStart())) {
                        g = g.with(node(Integer.toString(e.getStart().getIndex())).with(Color.RED, Style.BOLD));
                    }
                    if (periphery != null && periphery.contains(e.getEnd())) {
                        g = g.with(node(Integer.toString(e.getEnd().getIndex())).with(Color.RED, Style.BOLD));
                    }
                }
            }
        }
        // Render result
        Graphviz.fromGraph(g).engine(engine).render(format).toFile(new File("images/" + filename +".svg"));
    }

    public static Builder builder() {
        return new Visualizer().new Builder();
    }

    public class Builder {
        private Builder() {
            // Private constructor
        };

        public Visualizer build() {
            return Visualizer.this;
        }

        public Builder drawUnlinked(boolean value) {
            drawUnlinked = value;
            return this;
        }

        public Builder format(Format value) {
            format = value;
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

        public <V> Builder periphery(HashSet<Vertex<V>> value) {
            periphery = new HashSet<>(value);
            return this;
        }
    }
}

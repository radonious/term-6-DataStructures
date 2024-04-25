package edu.rgr.app;

import edu.rgr.graph.DualGraph;
import edu.rgr.graph.Graph;
import edu.rgr.graph.SimpleGraph;
import edu.rgr.task.TaskA;
import edu.rgr.task.TaskB;
import edu.rgr.visualizer.Visualizer;
import guru.nidi.graphviz.engine.Engine;
import guru.nidi.graphviz.engine.Format;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Optional;

public class Controller {

    SimpleGraph<String> graph;

    TaskA<String> taskA;
    TaskB<String> taskB;

    @FXML
    private ImageView imageView;

    @FXML
    private CheckBox vSaveWithDataCheckBox;

    @FXML
    private CheckBox oDrawUnlinked;

    @FXML
    private ComboBox<String> oEngineChoiceBox;

    @FXML
    private CheckBox eSaveWithWeight;

    @FXML
    void initialize() throws IOException {
        graph = new SimpleGraph<>(Graph.EdgeForm.Undirected, DualGraph.GraphForm.Matrix);
        taskA = new TaskA<>(graph);
        taskB = new TaskB<>(graph);
        oEngineChoiceBox.getItems().addAll("FDP","DOT", "CIRCO","NEATO","OSAGE","TWOPI");
        oEngineChoiceBox.setValue("FDP");
        oEngineChoiceBox.valueProperty().addListener((observableValue, s, t1) -> {
            try {
                updateImage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        updateImage();
    }

    private void updateImage() throws IOException {
        BufferedImage image = Visualizer.builder()
                .engine(Engine.valueOf(oEngineChoiceBox.getValue()))
                .format(Format.SVG)
                .drawUnlinked(oDrawUnlinked.isSelected())
                .width(525)
                .height(573)
                .build().toImage(graph);
        imageView.setImage(getImage(image));
    }

    private Image getImage(BufferedImage img){
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        newImg.createGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

        int[] type_int_agrb = ((DataBufferInt) newImg.getRaster().getDataBuffer()).getData();
        IntBuffer buffer = IntBuffer.wrap(type_int_agrb);
        
        PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(newImg.getWidth(), newImg.getHeight(), buffer, pixelFormat);
        return new WritableImage(pixelBuffer);
    }

    @FXML
    void vAddBtnClicked(MouseEvent event) throws IOException {
        if (vSaveWithDataCheckBox.isSelected()) {
            String data = dialogString();
            graph.makeVertex(data);
            System.out.println("[VERTEX] [CREATE] Index: " + (graph.sizeVertex() - 1) + " Data:  " + data);
        } else {
            graph.makeVertex();
            System.out.println("[VERTEX] [CREATE] Index: " + (graph.sizeVertex() - 1));
        }
        updateImage();
    }

    @FXML
    void vCountBtnClicked(MouseEvent event) {
        popUp("Vertex count", String.valueOf(graph.sizeVertex()));
    }

    @FXML
    void vDataBtnClicked(MouseEvent event) {
        Integer index = dialogIndex();
        if (index != null && index < graph.sizeVertex()) {
            popUp("Vertex data", String.valueOf(graph.getVertex(index).getData()));
        }
    }

    @FXML
    void vRemoveBtnClicked(MouseEvent event) throws IOException {
        Integer index = dialogIndex();
        if (index != null && graph.getVertex(index) != null) {
            graph.deleteVertex(graph.getVertex(index));
            System.out.println("[VERTEX] [REMOVE] Index: " + index);
            updateImage();
        }
    }

    @FXML
    void eAddBtnClicked(MouseEvent event) throws IOException {
        Integer s = dialogIndex();
        Integer e = dialogIndex();
        Integer w = null;
        if (eSaveWithWeight.isSelected()) w = dialogIndex();
        if (s == null || e == null) return;
        else if (graph.getVertex(s) == null || graph.getVertex(e) == null) return;
        System.out.println("[EDGE] [CREATE] Start: " + s + " End: " + e + " Weight: " + w);
        graph.makeEdge(graph.getVertex(s), graph.getVertex(e), w);
        updateImage();
    }

    @FXML
    void eCountBtnClicked(MouseEvent event) {
        popUp("Edge count", String.valueOf(graph.sizeEdge()));
    }

    @FXML
    void eWeightBtnClicked(MouseEvent event) {
        Integer s = dialogIndex();
        Integer e = dialogIndex();
        if (s == null || e == null) return;
        else if (graph.getVertex(s) == null || graph.getVertex(e) == null) return;
        popUp("Edge count", String.valueOf(graph.getEdge(graph.getVertex(s), graph.getVertex(e)).getWeight()));
    }

    @FXML
    void eRemoveBtnClicked(MouseEvent event) throws IOException {
        Integer s = dialogIndex();
        Integer e = dialogIndex();
        if (s == null || e == null) return;
        else if (graph.getVertex(s) == null || graph.getVertex(e) == null) return;
        System.out.println("[EDGE] [REMOVE] Start: " + s + " End: " + e);
        graph.deleteEdge(graph.getVertex(s), graph.getVertex(e));
        updateImage();
    }

    @FXML
    void oClearBtnClicked(MouseEvent event) throws IOException {
        graph.clear();
        updateImage();
    }

    @FXML
    void oFormBtnClicked(MouseEvent event) {
        popUp("Graph form", graph.getGraphForm().toString() + "\n" + graph.getEdgeForm());
    }

    @FXML
    void oKBtnClicked(MouseEvent event) {
        popUp("Graph form", String.valueOf(graph.calcK()));
    }

    @FXML
    void oLGraphBtnClicked(MouseEvent event) {
        graph.toListGraph();
    }

    @FXML
    void oMGraphBtnClicked(MouseEvent event) {
        graph.toMatrixGraph();
    }

    @FXML
    void oPrintBtnClicked(MouseEvent event) {
        System.out.println("[GRAPH][PRINT INFO]");
        graph.print();
    }

    @FXML
    void oToDirBtnClicked(MouseEvent event) throws IOException {
        graph = new SimpleGraph<>(Graph.EdgeForm.Directed, graph.getGraphForm());
        updateImage();
    }

    @FXML
    void oToUndirBtnClicked(MouseEvent event) throws IOException {
        graph = new SimpleGraph<>(Graph.EdgeForm.Undirected, graph.getGraphForm());
        updateImage();
    }

    @FXML
    void update(MouseEvent event) throws IOException {
        updateImage();
    }

    @FXML
    void taskAClicked(MouseEvent event) throws IOException {
        taskA.set(graph);
        taskA.restart();
        updateImage();
    }
    @FXML
    void taskBClicked(MouseEvent event) throws IOException {
        taskB.set(graph);
        taskB.restart();
        BufferedImage image = Visualizer.builder()
                .engine(Engine.valueOf(oEngineChoiceBox.getValue()))
                .format(Format.SVG)
                .drawUnlinked(oDrawUnlinked.isSelected())
                .width(525)
                .height(573)
                .periphery( taskB.result())
                .build().toImage(graph);
        imageView.setImage(getImage(image));
    }


    private void popUp(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private Integer dialogIndex() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Input Reader");
        dialog.setHeaderText("Please enter index:");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 20));

        TextField key = new TextField();
        key.setPromptText("Index");
        gridPane.add(key, 0, 0);

        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(key::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                int dkey = 0;
                try {
                    dkey = Integer.parseInt(key.getText());
                } catch (NumberFormatException ex) {
                    System.out.println("Exception: Invalid Double Number Format - '" + key.getText() + "'");
                }
                return dkey;
            }
            return null;
        });

        Optional<Integer> res = dialog.showAndWait();

        return res.orElse(null);
    }

    private String dialogString() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Input Reader");
        dialog.setHeaderText("Please enter data:");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 20));

        TextField key = new TextField();
        key.setPromptText("Data");
        gridPane.add(key, 0, 0);

        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(key::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return key.getText();
            }
            return null;
        });

        Optional<String> res = dialog.showAndWait();

        return res.orElse(null);
    }

}
package model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ScalePane extends AnchorPane {
    private Pane itemPane = new Pane();
    private double scale = 0;
    private double scaleAmount = 0.1;
    private double xOffset, yOffset;
    private final double fieldSize;

    public ScalePane(int cardSize) {
        fieldSize = cardSize * 13;
        createField();
    }

    private void createField() {
        itemPane.setPrefSize(fieldSize, fieldSize);
        setTopAnchor(this, (double) 0);
        setBottomAnchor(this, (double) 0);
        setLeftAnchor(this, (double) 0);
        setRightAnchor(this, (double) 0);
        getChildren().add(itemPane);
        mouseDragged();
        scaling();
    }

    public void centerItems(double width, double height) {
        itemPane.setLayoutX(width / 2 - fieldSize / 2 + 25); // + 48
        itemPane.setLayoutY(height / 2 - fieldSize / 2 + 35); // + 58
    }

    private void mouseDragged() {
        setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                xOffset = event.getX();
                yOffset = event.getY();
            }
        });

        setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                itemPane.setLayoutX(itemPane.getLayoutX() + event.getX() - xOffset);
                xOffset = event.getX();
                itemPane.setLayoutY(itemPane.getLayoutY() + event.getY() - yOffset);
                yOffset = event.getY();
            }
        });
    }

    private void scaling() {
        setOnScroll(event -> {
            scale = event.getDeltaY() > 0 ? scale + scaleAmount : scale - scaleAmount;
            scale = scale > 100 ? 100 : scale;
            itemPane.setScaleX(Math.exp(scale));
            itemPane.setScaleY(Math.exp(scale));
        });
    }

    public ObservableList<Node> getItems() {
        return itemPane.getChildren();
    }
}

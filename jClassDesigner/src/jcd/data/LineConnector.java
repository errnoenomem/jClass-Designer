/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Billy
 */
public class LineConnector extends Line {

    private Polygon triangle;

    public LineConnector() {
        this.setStrokeWidth(2);
    }

    public Polygon createTriangle() {
        triangle = new Polygon();
        triangle.getPoints().setAll(0d, 20d, 30d, 20d, 15d, 10d);
        triangle.setFill(Color.WHITE);
        triangle.setStroke(Color.BLACK);
        return triangle;
    }

    public Polygon getTriangle() {
        return triangle;
    }

    public void setStartProperties(DoubleProperty xStartProp, DoubleProperty yStartProp) {
        this.startXProperty().bind(xStartProp);
        this.startYProperty().bind(yStartProp);
    }

    public void setTranslate(double x, double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void setEndProperties(DoubleProperty endXProp, DoubleProperty endYProp) {
        this.endXProperty().bind(endXProp);
        this.endYProperty().bind(endYProp);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Billy
 */
public class Diagram extends VBox {

    static final String CLASS_FONT = "edit_font";
    static final String CLASS_VUML = "edit_uml_vbox";
    static final String VM_BOXES = "edit_vm_box";

    ArrayList<UMLVariables> variables;
    ArrayList<UMLMethods> methods;
    Text className;
    String packageName;
    Object parent;
    Diagram test;

    double height;
    double width;

    double x;
    double y;

    double initX;
    double initY;

    DropShadow borderGlow;

    String Type;

    Circle centerPoint;
    Circle northPoint;
    Circle westPoint;
    Circle eastPoint;
    Circle southPoint;

    DoubleProperty startX;
    DoubleProperty startY;
    DoubleProperty startNX;
    DoubleProperty startNY;
    DoubleProperty startSX;
    DoubleProperty startSY;
    DoubleProperty startWX;
    DoubleProperty startWY;
    DoubleProperty startEX;
    DoubleProperty startEY;

    Line lineConnector;

    Polygon triangle;

    private Point2D north;
    private Point2D east;
    private Point2D south;
    private Point2D west;
    private Point2D center;

    public Diagram() {
        Type = "diagram";

        x = 450;
        y = 200;

        packageName = "defaultPackage";
        parent = "defaultParent";

        variables = new ArrayList();
        methods = new ArrayList();

        borderGlow = new DropShadow();
        borderGlow.setColor(Color.GOLD);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        borderGlow.setWidth(70);
        borderGlow.setHeight(70);

        this.setTranslateX(x);
        this.setTranslateY(y);

        this.setMinSize(150, 150);

    }

    public ArrayList<Point2D> getAnchorPoints() {
        ArrayList list = new ArrayList();
        north = new Point2D(northPoint.getCenterX(), northPoint.getCenterY());
        west = new Point2D(westPoint.getCenterX(), westPoint.getCenterY());
        south = new Point2D(southPoint.getCenterX(), southPoint.getCenterY());
        east = new Point2D(eastPoint.getCenterX(), eastPoint.getCenterY());
        list.add(north);
        list.add(south);
        list.add(east);
        list.add(west);
        return list;
    }

    public Circle findClosestNodes(Diagram diagram) {
        center = new Point2D(centerPoint.getCenterX(), centerPoint.getCenterY());
        Point2D[] closest = new Point2D[1];
        double shortestDistance = Double.MAX_VALUE;
        for (Point2D point : diagram.getAnchorPoints()) {
            double distance = center.distance(point);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closest[0] = point;
            }
        }
        if (diagram.northPoint.getCenterX() == closest[0].getX() && diagram.northPoint.getCenterY() == closest[0].getY()) {
            return diagram.getNorthPoint();
        }
        if (diagram.westPoint.getCenterX() == closest[0].getX() && diagram.westPoint.getCenterY() == closest[0].getY()) {
            return diagram.getWestPoint();
        }
        if (diagram.eastPoint.getCenterX() == closest[0].getX() && diagram.eastPoint.getCenterY() == closest[0].getY()) {
            return diagram.getEastPoint();
        }
        if (diagram.southPoint.getCenterX() == closest[0].getX() && diagram.southPoint.getCenterY() == closest[0].getY()) {
            return diagram.getSouthPoint();
        } else {
            return centerPoint;
        }
    }

    public void setObjectType(String text) {
        Type = text;
    }

    public void setTest(Diagram testObj) {
        test = testObj;
    }

    public Diagram getTest() {
        return test;
    }

    public void createAllPoints(Pane DesignRenderer) {
        centerPoint = new Circle();
        centerPoint.setRadius(1);
        centerPoint.setFill(Color.BLACK);
        centerPoint.setTranslateX(this.getTranslateX());
        centerPoint.setTranslateY(this.getTranslateY());
        startX = new SimpleDoubleProperty(this.getLayoutX() + (0.5 * this.getMinWidth()));
        startY = new SimpleDoubleProperty(this.getLayoutY() + (0.5 * this.getMinHeight()));
        centerPoint.centerXProperty().bind(startX);
        centerPoint.centerYProperty().bind(startY);
        centerPoint.setVisible(false);
        DesignRenderer.getChildren().add(centerPoint);

        northPoint = new Circle();
        northPoint.setRadius(1);
        northPoint.setFill(Color.BLACK);
        northPoint.setTranslateX(this.getTranslateX());
        northPoint.setTranslateY(this.getTranslateY());
        startNX = new SimpleDoubleProperty(this.getLayoutX() + (0.5 * this.getMinWidth()));
        startNY = new SimpleDoubleProperty(this.getLayoutY());
        northPoint.centerXProperty().bind(startNX);
        northPoint.centerYProperty().bind(startNY);
        northPoint.setVisible(false);
        DesignRenderer.getChildren().add(northPoint);

        southPoint = new Circle();
        southPoint.setRadius(1);
        southPoint.setFill(Color.BLACK);
        southPoint.setTranslateX(this.getTranslateX());
        southPoint.setTranslateY(this.getTranslateY());
        startSX = new SimpleDoubleProperty(this.getLayoutX() + (0.5 * this.getMinWidth()));
        startSY = new SimpleDoubleProperty(this.getLayoutY() + this.getMinHeight());
        southPoint.centerXProperty().bind(startSX);
        southPoint.centerYProperty().bind(startSY);
        southPoint.setVisible(false);
        DesignRenderer.getChildren().add(southPoint);

        westPoint = new Circle();
        westPoint.setRadius(1);
        westPoint.setFill(Color.BLACK);
        westPoint.setTranslateX(this.getTranslateX());
        westPoint.setTranslateY(this.getTranslateY());
        startWX = new SimpleDoubleProperty(this.getLayoutX());
        startWY = new SimpleDoubleProperty(this.getLayoutY() + (0.5 * this.getMinHeight()));
        westPoint.centerXProperty().bind(startWX);
        westPoint.centerYProperty().bind(startWY);
        westPoint.setVisible(false);
        DesignRenderer.getChildren().add(westPoint);

        eastPoint = new Circle();
        eastPoint.setRadius(1);
        eastPoint.setFill(Color.BLACK);
        eastPoint.setTranslateX(this.getTranslateX());
        eastPoint.setTranslateY(this.getTranslateY());
        startEX = new SimpleDoubleProperty(this.getLayoutX() + this.getMinWidth());
        startEY = new SimpleDoubleProperty(this.getLayoutY() + (0.5 * this.getMinHeight()));
        eastPoint.centerXProperty().bind(startEX);
        eastPoint.centerYProperty().bind(startEY);
        eastPoint.setVisible(false);
        DesignRenderer.getChildren().add(eastPoint);

        northPoint.toBack();
        westPoint.toBack();
        eastPoint.toBack();
        southPoint.toBack();
        centerPoint.toBack();
    }

    public Circle getCenterPoint() {
        return centerPoint;
    }

    public Circle getNorthPoint() {
        return northPoint;
    }

    public Circle getWestPoint() {
        return westPoint;
    }

    public Circle getEastPoint() {
        return eastPoint;
    }

    public Circle getSouthPoint() {
        return southPoint;
    }

    public void setLineConnector(Line line) {
        lineConnector = line;
    }

    public Line getLineConnector() {
        return lineConnector;
    }

    public void setTriangle(Polygon t) {
        triangle = t;
    }

    public Polygon getTriangle() {
        return triangle;
    }

    public void setClassName(String text) {
        className.setText(text);
    }

    public void removePoints(Pane DesignRenderer) {
        DesignRenderer.getChildren().removeAll(northPoint, westPoint, eastPoint, southPoint, centerPoint);
    }

    public void setParents(Object theParent) {
        parent = theParent;
        //parents.add(theParent);
    }

    public void setPackageName(String data) {
        packageName = data;
    }

    public void setX(double xValue) {
        x = xValue;
        this.setLayoutX(x);
    }

    public void setY(double yValue) {
        y = yValue;
        this.setLayoutY(y);
    }

    public void setHEIGHT(double h) {
        this.setMinHeight(h);
    }

    public void setWIDTH(double w) {
        this.setMinWidth(w);
    }

    public String getClassName() {
        return className.getText();
    }

    public Object getParents() {
        return parent;
    }

    public String getParentName() {
        return (String) parent;
    }

    public String getPackageName() {
        return packageName;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public ArrayList<UMLMethods> getMethods() {
        return methods;
    }

    public ArrayList<UMLVariables> getVariables() {
        return variables;
    }

    public void addVariables(UMLVariables variable) {

    }

    public void updateVariables() {

    }

    public void addMethods(UMLMethods method) {

    }

    public void updateMethods() {

    }

    public void printClassData() {

    }

    public void setHighlight() {
        this.setEffect(borderGlow);
    }

    public void removeHighlight() {
        this.setEffect(null);
    }

    public String getObjectType() {
        return Type;
    }

    public void move(Pane DesignRenderer, CheckBox Snap) {
        this.setOnMousePressed(event -> {
            if (DesignRenderer.getCursor() == Cursor.CROSSHAIR) {
                initX = this.getLayoutX() - event.getSceneX();
                initY = this.getLayoutY() - event.getSceneY();
            }
        });
        this.setOnMouseDragged(event -> {
            if (DesignRenderer.getCursor() == Cursor.CROSSHAIR) {
                this.setLayoutX((event.getSceneX() + initX));
                this.setLayoutY((event.getSceneY() + initY));
                this.setX(this.getLayoutX());
                this.setY(this.getLayoutY());
            }
            if (Snap.isSelected()) {
                int testx = (int) (this.getLayoutX() / 50);
                int testy = (int) (this.getLayoutY() / 50);
                this.setLayoutX((testx * 50));
                this.setLayoutY((testy * 50));
                this.setX(this.getLayoutX());
                this.setY(this.getLayoutY());
            }
            startX = new SimpleDoubleProperty(this.getLayoutX() + (0.5 * this.getMinWidth()));
            startY = new SimpleDoubleProperty(this.getLayoutY() + (0.5 * this.getMinHeight()));
            centerPoint.centerXProperty().bind(startX);
            centerPoint.centerYProperty().bind(startY);

            startNX = new SimpleDoubleProperty(this.getLayoutX() + (0.5 * this.getMinWidth()));
            startNY = new SimpleDoubleProperty(this.getLayoutY());
            northPoint.centerXProperty().bind(startNX);
            northPoint.centerYProperty().bind(startNY);

            startSX = new SimpleDoubleProperty(this.getLayoutX() + (0.5 * this.getMinWidth()));
            startSY = new SimpleDoubleProperty(this.getLayoutY() + this.getMinHeight());
            southPoint.centerXProperty().bind(startSX);
            southPoint.centerYProperty().bind(startSY);

            startWX = new SimpleDoubleProperty(this.getLayoutX());
            startWY = new SimpleDoubleProperty(this.getLayoutY() + (0.5 * this.getMinHeight()));
            westPoint.centerXProperty().bind(startWX);
            westPoint.centerYProperty().bind(startWY);

            startEX = new SimpleDoubleProperty(this.getLayoutX() + this.getMinWidth());
            startEY = new SimpleDoubleProperty(this.getLayoutY() + (0.5 * this.getMinHeight()));
            eastPoint.centerXProperty().bind(startEX);
            eastPoint.centerYProperty().bind(startEY);

            if (test != null && !this.getParentName().equals(this.getClassName()) && !this.getParentName().equals("defaultParent")) {
                if (getTriangle().isVisible() == false) {
                    getTriangle().setVisible(true);
                }
                this.getLineConnector().endXProperty().bind(this.findClosestNodes(this.getTest()).centerXProperty());
                this.getLineConnector().endYProperty().bind(this.findClosestNodes(this.getTest()).centerYProperty());

                triangle.layoutXProperty().bind(this.findClosestNodes(this.getTest()).centerXProperty());
                triangle.layoutYProperty().bind(this.findClosestNodes(this.getTest()).centerYProperty());

                if (this.findClosestNodes(this.getTest()) == this.getTest().getNorthPoint()) {
                    triangle.getTransforms().clear();
                    triangle.setTranslateX(this.getTest().getNorthPoint().getTranslateX() + 15);
                    triangle.setTranslateY(this.getTest().getNorthPoint().getTranslateY() + 10);
                    triangle.getTransforms().add(new Rotate(180));
                } else if (this.findClosestNodes(this.getTest()) == this.getTest().getSouthPoint()) {
                    triangle.getTransforms().clear();
                    triangle.setTranslateX(this.getTest().getSouthPoint().getTranslateX() - 15);
                    triangle.setTranslateY(this.getTest().getSouthPoint().getTranslateY() - 10);
                } else if (this.findClosestNodes(this.getTest()) == this.getTest().getWestPoint()) {
                    triangle.getTransforms().clear();
                    triangle.setTranslateX(this.getTest().getWestPoint().getTranslateX() + 10);
                    triangle.setTranslateY(this.getTest().getWestPoint().getTranslateY() - 15);
                    triangle.getTransforms().add(new Rotate(90));
                } else if (this.findClosestNodes(this.getTest()) == this.getTest().getEastPoint()) {
                    triangle.getTransforms().clear();
                    triangle.setTranslateX(this.getTest().getEastPoint().getTranslateX() - 10);
                    triangle.setTranslateY(this.getTest().getEastPoint().getTranslateY() + 15);
                    triangle.getTransforms().add(new Rotate(270));
                }
            }
        });
    }
}

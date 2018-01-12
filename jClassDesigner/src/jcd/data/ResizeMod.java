package jcd.data;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Billy
 */
public class ResizeMod {

    private static final int MARGIN = 4;

    private final Diagram diagram;

    private final Pane designRenderer;

    private final Button resizeButton;

    private double x;
    private double y;

    private boolean initMinHeight;
    private boolean initMinWidth;

    private boolean dragging;

    double initX;
    double initY;

    public ResizeMod(Diagram aDiagram, Pane dR, Button rB) {
        diagram = aDiagram;
        designRenderer = dR;
        resizeButton = rB;
        initMinHeight = false;
        initMinWidth = false;
    }

    public static void enableResize(Diagram diagram, Pane DesignRenderer, Button ResizeButton) {
        final ResizeMod resizer = new ResizeMod(diagram, DesignRenderer, ResizeButton);
        diagram.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mousePressed(event);
            }
        });
        diagram.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseDragged(event);
            }
        });
        diagram.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseOver(event);
            }
        });
        diagram.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseReleased(event);
            }
        });
    }

    protected void mouseReleased(MouseEvent event) {
        if (resizeButton.isDisable()) {
            dragging = false;
            designRenderer.setCursor(Cursor.DEFAULT);
        }
    }

    protected void mouseOver(MouseEvent event) {
        if (resizeButton.isDisable()) {
            if (inZoneX(event)) {
                designRenderer.setCursor(Cursor.E_RESIZE);
            }
            if (inZoneY(event)) {
                designRenderer.setCursor(Cursor.S_RESIZE);
            }
            if (inZoneX(event) && inZoneY(event)) {
                designRenderer.setCursor(Cursor.SE_RESIZE);
            }
        }
    }

    protected boolean inZoneX(MouseEvent event) {
        if (resizeButton.isDisable()) {
            return event.getX() > (diagram.getWidth() - MARGIN);
        }
        return false;
    }

    protected boolean inZoneY(MouseEvent event) {
        if (resizeButton.isDisable()) {
            return event.getY() > (diagram.getHeight() - MARGIN);
        }
        return false;
    }

    protected void mouseDragged(MouseEvent event) {
        if (resizeButton.isDisable() && diagram.getEffect() != null) {
            if (initMinWidth && designRenderer.getCursor() == Cursor.E_RESIZE) {
                if (!dragging) {
                    return;
                }
                double mouseX = event.getX();
                double newWidth = diagram.getMinWidth() + (mouseX - x);
                diagram.setMinWidth(newWidth);
                x = mouseX;
                if (designRenderer.getCursor() != Cursor.E_RESIZE || designRenderer.getCursor() != Cursor.S_RESIZE) {
//                    diagram.getCenterPoint().setTranslateX(diagram.getTranslateX() + (0.5 * diagram.getMinWidth()));
//                    diagram.getNorthPoint().setTranslateX(diagram.getTranslateX() + (0.5 * diagram.getMinWidth()));
//                    diagram.getWestPoint().setTranslateX(diagram.getTranslateX());
//                    diagram.getSouthPoint().setTranslateX(diagram.getTranslateX() + (0.5 * diagram.getMinWidth()));
//                    diagram.getEastPoint().setTranslateX(diagram.getTranslateX() + diagram.getMinWidth());
                }
            }
            if (initMinHeight && designRenderer.getCursor() == Cursor.S_RESIZE) {
                if (!dragging) {
                    return;
                }
                double mouseY = event.getY();
                double newHeight = diagram.getMinHeight() + (mouseY - y);
                diagram.setMinHeight(newHeight);
                y = mouseY;
                if (designRenderer.getCursor() != Cursor.E_RESIZE || designRenderer.getCursor() != Cursor.S_RESIZE) {
//                    diagram.getCenterPoint().setTranslateY(diagram.getTranslateY() + (0.5 * diagram.getMinHeight()));
//                    diagram.getNorthPoint().setTranslateY(diagram.getTranslateY());
//                    diagram.getWestPoint().setTranslateY(diagram.getTranslateY() + (0.5 * diagram.getMinHeight()));
//                    diagram.getSouthPoint().setTranslateY(diagram.getTranslateY() + diagram.getMinHeight());
//                    diagram.getEastPoint().setTranslateY(diagram.getTranslateY() + (0.5 * diagram.getMinHeight()));
                }
            }
            if (designRenderer.getCursor() == Cursor.SE_RESIZE) {
                if (!dragging) {
                    return;
                }
                double mouseX = event.getX();
                double newWidth = diagram.getMinWidth() + (mouseX - x);
                diagram.setMinWidth(newWidth);
                x = mouseX;
                double mouseY = event.getY();
                double newHeight = diagram.getMinHeight() + (mouseY - y);
                diagram.setMinHeight(newHeight);
                y = mouseY;
                if (designRenderer.getCursor() != Cursor.SE_RESIZE) {
                    diagram.getCenterPoint().setTranslateX(diagram.getTranslateX() + (0.5 * diagram.getMinWidth()));
                    diagram.getNorthPoint().setTranslateX(diagram.getTranslateX() + (0.5 * diagram.getMinWidth()));
                    diagram.getWestPoint().setTranslateX(diagram.getTranslateX());
                    diagram.getSouthPoint().setTranslateX(diagram.getTranslateX() + (0.5 * diagram.getMinWidth()));
                    diagram.getEastPoint().setTranslateX(diagram.getTranslateX() + diagram.getMinWidth());
                    diagram.getCenterPoint().setTranslateY(diagram.getTranslateY() + (0.5 * diagram.getMinHeight()));
                    diagram.getNorthPoint().setTranslateY(diagram.getTranslateY());
                    diagram.getWestPoint().setTranslateY(diagram.getTranslateY() + (0.5 * diagram.getMinHeight()));
                    diagram.getSouthPoint().setTranslateY(diagram.getTranslateY() + diagram.getMinHeight());
                    diagram.getEastPoint().setTranslateY(diagram.getTranslateY() + (0.5 * diagram.getMinHeight()));
                }
            }

        }
    }

    protected void mousePressed(MouseEvent event) {
        if (resizeButton.isDisable()) {
            if (!inZoneX(event) && !inZoneY(event)) {
                return;
            }
            if (inZoneX(event)) {
                dragging = true;
                if (!initMinWidth) {
                    diagram.setMinWidth(diagram.getWidth());
                    initMinWidth = true;
                }
                x = event.getX();
            }
            if (inZoneY(event)) {
                dragging = true;
                if (!initMinHeight) {
                    diagram.setMinHeight(diagram.getHeight());
                    initMinHeight = true;
                }
                y = event.getY();
            }
        }
    }
}

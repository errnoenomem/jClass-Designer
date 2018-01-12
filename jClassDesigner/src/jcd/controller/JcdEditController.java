package jcd.controller;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import jcd.data.DataManager;
import jcd.data.ClassDiagram;
import jcd.data.Diagram;
import jcd.data.InterfaceDiagram;
import jcd.data.LineConnector;
import jcd.data.MethodInput;
import jcd.data.ResizeMod;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import jcd.data.VariableInput;
import saf.AppTemplate;

/**
 *
 * @author Billy
 */
public class JcdEditController {
    
    ArrayList lineArray;
    
    DataManager dataManager;
    AppTemplate app;
    
    double initX;
    double initY;
    
    ArrayList<Diagram> diagramArray;
    ArrayList<Diagram> loadedArray;
    ArrayList<Diagram> resizedArray;
    
    Diagram selected;
    Diagram selected2;
    Diagram loadedObject;
    
    ClassDiagram CLASS;
    
    InterfaceDiagram INTERFACE;
    
    Diagram test;
    LineConnector line;
    
    VariableInput vDialog;
    MethodInput mDialog;
    ArrayList vData;
    ArrayList mData;
    UMLVariables variable;
    UMLMethods method;
    
    ResizeMod resizer;
    
    Boolean dragging;
    double x;
    double y;
    
    int magnifier;

    //GridPane diagram;
    static final String CLASS_FONT = "edit_font";
    static final String CLASS_VUML = "edit_uml_vbox";
    static final String CLASS_HUML = "edit_uml_hbox";
    
    public JcdEditController(AppTemplate initApp) throws Exception {
        magnifier = 0;
        app = initApp;
        dataManager = (DataManager) app.getDataComponent();
        diagramArray = new ArrayList();
        loadedArray = new ArrayList();
        vData = new ArrayList();
        mData = new ArrayList();
        lineArray = new ArrayList();
        resizedArray = new ArrayList();
    }
    
    public void handleSelect() {
        dataManager.getDesignRenderer().setCursor(Cursor.CROSSHAIR);
        selected.setOnMouseClicked(event -> {
            if (dataManager.getDesignRenderer().getCursor() == Cursor.CROSSHAIR && dataManager.getSelectButton().isDisable()) {
                selected = (Diagram) event.getSource();
                if (selected2 != null) {
                    selected2.setEffect(null);
                }
                selected.setHighlight();
                selected.move(dataManager.getDesignRenderer(), dataManager.getSnap());
                selected2 = selected;
                updateComponentToolbar();
                updateVariables();
                updateMethods();
                dataManager.getRemoveButton().setDisable(false);
            }
        });
    }
    
    public void handleResize() {
        if (dataManager.getResizeButton().isDisable()) {
            ResizeMod.enableResize(selected, dataManager.getDesignRenderer(), dataManager.getResizeButton());
        }
    }
    
    public void handleAddClass() {
        dataManager.getDesignRenderer().setCursor(Cursor.DEFAULT);
        
        CLASS = new ClassDiagram();
        selected = CLASS;
        
        selected.setHighlight();
        if (selected2 != null) {
            selected2.setEffect(null);
        }
        selected2 = selected;
        
        dataManager.getDesignRenderer().getChildren().add(selected);
        selected.createAllPoints(dataManager.getDesignRenderer());
        
        selected.move(dataManager.getDesignRenderer(), dataManager.getSnap());
        
        updateComponentToolbar();
        updateVariables();
        updateMethods();
        Object storage = selected.getParents();
        dataManager.setToList(selected);
        if (selected.getParents() == null) {
            selected.setParents(storage);
            dataManager.getParentComboBox().getSelectionModel().select(selected.getParents());
        }
    }
    
    public void handleAddInterface() {
        dataManager.getDesignRenderer().setCursor(Cursor.DEFAULT);
        
        INTERFACE = new InterfaceDiagram();
        selected = INTERFACE;
        
        selected.setHighlight();
        if (selected2 != null) {
            selected2.setEffect(null);
        }
        selected2 = selected;
        
        dataManager.getDesignRenderer().getChildren().add(INTERFACE);
        selected.createAllPoints(dataManager.getDesignRenderer());
        
        selected.move(dataManager.getDesignRenderer(), dataManager.getSnap());
        
        updateComponentToolbar();
        Object storage = selected.getParents();
        dataManager.setToList(selected);
        if (selected.getParents() == null) {
            selected.setParents(storage);
            dataManager.getParentComboBox().getSelectionModel().select(selected.getParents());
        }
    }
    
    public void handleRemove() {
        dataManager.removeFromList(selected);
        selected.removePoints(dataManager.getDesignRenderer());
        dataManager.getDesignRenderer().getChildren().remove(selected.getTriangle());
        dataManager.getDesignRenderer().getChildren().remove(selected.getLineConnector());
        dataManager.getDesignRenderer().setCursor(Cursor.DEFAULT);
        dataManager.getSelectButton().setDisable(false);
        dataManager.reloadWorkspaceHelper();
    }
    
    public void handleUndo(Button button) {
        System.out.println("undo test");
    }
    
    public void handleRedo(Button button) {
        System.out.println("redo test");
    }
    
    public void handleZoomIn(Button button) {
        if (magnifier < 3) {
            Scale scale = new Scale(0, 0);
            scale.setX(1.33333);
            scale.setY(1.33333);
            dataManager.getDesignRenderer().getTransforms().add(scale);
            magnifier++;
        }
    }
    
    public void handleZoomOut(Button button) {
        if (magnifier > -3) {
            Scale scale = new Scale(0, 0);
            scale.setX(.75);
            scale.setY(.75);
            dataManager.getDesignRenderer().getTransforms().add(scale);
            magnifier--;
        }
    }
    
    public void handleGrid(CheckBox check) {
        Line line;
        if (check.isSelected()) {
            for (int i = 50; i < 2200; i += 50) {
                line = new Line();
                line.setTranslateX(i);
                line.setEndY(2200);
                dataManager.getDesignRenderer().getChildren().add(line);
                lineArray.add(line);
                line.toBack();
                line = new Line();
                line.setTranslateY(i);
                line.setEndX(2200);
                dataManager.getDesignRenderer().getChildren().add(line);
                lineArray.add(line);
                line.toBack();
            }
        } else {
            for (Object node : lineArray) {
                line = (Line) node;
                dataManager.getDesignRenderer().getChildren().remove(line);
            }
            lineArray.clear();
        }
    }
    
    public void handleClassText() {
        for (int i = 0; i < dataManager.returnList().size(); i++) {
            if (dataManager.getListIndex(i).getParents() == selected.getClassName()) {
                dataManager.getListIndex(i).setParents((Object) dataManager.getClassText().getText());
            }
        }
        selected.setClassName(dataManager.getClassText().getText());
        Object storage = selected.getParents();
        dataManager.updateParentComboBox();
        if (selected.getParents() == null) {
            selected.setParents(storage);
            dataManager.getParentComboBox().getSelectionModel().select(selected.getParents());
        }
    }
    
    public void handlePackageText() {
        selected.setPackageName(dataManager.getPackageText().getText());
    }
    
    public void handleParentSelection() {
        selected.setParents(dataManager.getParentComboBox().getSelectionModel().getSelectedItem());
        Object storage = selected.getParentName();
        if (selected.getParentName() == null) {
            selected.setParents("defaultParent");
        }
        if (selected.getParentName().equals(selected.getClassName())) {
            if (selected.getLineConnector() != null) {
                dataManager.getDesignRenderer().getChildren().remove(selected.getLineConnector());
            }
            if (selected.getTriangle() != null) {
                dataManager.getDesignRenderer().getChildren().remove(selected.getTriangle());
            }
            selected.setLineConnector(null);
            selected.setTriangle(null);
        }
        for (Object node : dataManager.returnList()) {
            Diagram diagram = (Diagram) node;
            if (diagram.getClassName().equals(selected.getParentName()) && !selected.getParentName().equals(selected.getClassName())) {
                selected.setTest(diagram);
                if (selected.getLineConnector() == null && !selected.getParentName().equals(selected.getClassName())) {
                    line = new LineConnector();
                    line.setStartProperties(selected.getCenterPoint().centerXProperty(), selected.getCenterPoint().centerYProperty());
                    line.setTranslate(selected.getCenterPoint().getTranslateX(), selected.getCenterPoint().getTranslateY());
                    line.setEndProperties(selected.findClosestNodes(diagram).centerXProperty(), selected.findClosestNodes(diagram).centerYProperty());
                    selected.setTriangle(line.createTriangle());
                    selected.setLineConnector(line);
                    dataManager.getDesignRenderer().getChildren().add(line);
                    dataManager.getDesignRenderer().getChildren().add(selected.getTriangle());
                    selected.getTriangle().toFront();
                    selected.getTriangle().setVisible(false);
                    line.toBack();
                }
            }
        }
        selected.setParents(storage);
    }
    
    public void handleAddVariable() {
        vDialog = new VariableInput();
        Optional<ButtonType> result = vDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            vData.add(vDialog);
            dataManager.getColVName().setCellValueFactory(new PropertyValueFactory<>("name"));
            dataManager.getColVType().setCellValueFactory(new PropertyValueFactory<>("type"));
            dataManager.getColVStatic().setCellValueFactory(new PropertyValueFactory<>("stat"));
            dataManager.getColVAccess().setCellValueFactory(new PropertyValueFactory<>("access"));
            dataManager.getVariablesTable().setItems(FXCollections.observableList(vData));
            variable = new UMLVariables();
            variable.setVariable(vDialog.getName(), vDialog.getType(), vDialog.getAccess(), vDialog.getStat());
            selected.addVariables(variable);
            updateVariables();
        }
    }
    
    public void handleRemoveVariable() {
        int i = dataManager.getVariablesTable().getSelectionModel().getSelectedIndex();
        dataManager.getVariablesTable().getItems().remove(dataManager.getVariablesTable().getSelectionModel().getSelectedItem());
        selected.getVariables().remove(i);
        selected.updateVariables();
    }
    
    public void handleAddMethod() {
        mDialog = new MethodInput();
        Optional<ButtonType> result = mDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            mData.add(mDialog);
            dataManager.getColM_Access().setCellValueFactory(new PropertyValueFactory<>("access"));
            dataManager.getColM_Static().setCellValueFactory(new PropertyValueFactory<>("stat"));
            dataManager.getColM_Abstract().setCellValueFactory(new PropertyValueFactory<>("abs"));
            dataManager.getColM_ReturnType().setCellValueFactory(new PropertyValueFactory<>("returnType"));
            dataManager.getColM_Name().setCellValueFactory(new PropertyValueFactory<>("name"));
            dataManager.getColM_arg1().setCellValueFactory(new PropertyValueFactory<>("arg1"));
            dataManager.getColM_arg2().setCellValueFactory(new PropertyValueFactory<>("arg2"));
            dataManager.getColM_arg3().setCellValueFactory(new PropertyValueFactory<>("arg3"));
            dataManager.getColM_arg4().setCellValueFactory(new PropertyValueFactory<>("arg4"));
            dataManager.getMethodsTable().setItems(FXCollections.observableList(mData));
            method = new UMLMethods();
            method.loadMethod(mDialog.getName(), mDialog.getReturnType(), mDialog.getAccess(), mDialog.getStat(), mDialog.getAbs());
            method.loadParameters(mDialog.getArg1(), mDialog.getArg2(), mDialog.getArg3(), mDialog.getArg4());
            method.loadParameterArrayList();
            selected.addMethods(method);
            updateMethods();
        }
    }
    
    public void handleRemoveMethod() {
        int i = dataManager.getMethodsTable().getSelectionModel().getSelectedIndex();
        dataManager.getMethodsTable().getItems().remove(dataManager.getMethodsTable().getSelectionModel().getSelectedItem());
        selected.getMethods().remove(i);
        selected.updateMethods();
    }
    
    public void loadDesignRenderer() {
        loadedArray.clear();
        dataManager.getSelectButton().setDisable(true);
        for (Object node : dataManager.returnList()) {
            Diagram loadedObj;
            Diagram typeVerifier = (Diagram) node;
            if (typeVerifier.getObjectType().equals("class")) {
                loadedObj = (ClassDiagram) node;
            } else {
                loadedObj = (InterfaceDiagram) node;
            }
            handleAddLoaded(loadedObj);
            handleSelectLoaded();
        }
        dataManager.clearList();
        for (Object node : loadedArray) {
            Diagram loadedArrayObj = (Diagram) node;
            Object storage = selected.getParents();
            dataManager.setToList(loadedArrayObj);
            if (selected.getParents() == null) {
                selected.setParents(storage);
                dataManager.getParentComboBox().getSelectionModel().select(selected.getParents());
            }
        }
        for (Object node : dataManager.returnList()) {
            Diagram diagram = (Diagram) node;
            selected = diagram;
            Object storage = selected.getParents();
            updateLoadedParentLines();
            selected.setParents(storage);
        }
    }
    
    public void handleAddLoaded(Diagram loadedObj) {
        selected = loadedObj;
        selected.setHighlight();
        if (selected2 != null) {
            selected2.setEffect(null);
        }
        selected2 = selected;
        dataManager.getDesignRenderer().getChildren().add(selected);
        selected.createAllPoints(dataManager.getDesignRenderer());
        
        selected.move(dataManager.getDesignRenderer(), dataManager.getSnap());
        updateComponentToolbar();
        updateVariables();
        updateMethods();
        loadedArray.add(selected);
    }
    
    public void handleSelectLoaded() {
        dataManager.getDesignRenderer().setCursor(Cursor.CROSSHAIR);
        dataManager.getSelectButton().setDisable(true);
        selected.setOnMouseClicked(event -> {
            if (dataManager.getDesignRenderer().getCursor() == Cursor.CROSSHAIR && dataManager.getSelectButton().isDisable()) {
                selected = (Diagram) event.getSource();
                if (selected2 != null) {
                    selected2.setEffect(null);
                }
                selected.setHighlight();
                selected.move(dataManager.getDesignRenderer(), dataManager.getSnap());
                selected2 = selected;
                updateComponentToolbar();
                updateVariables();
                updateMethods();
                dataManager.getRemoveButton().setDisable(false);
            }
        });
    }
    
    public void updateComponentToolbar() {
        dataManager.getClassText().setText(selected.getClassName());
        dataManager.getPackageText().setText(selected.getPackageName());
        dataManager.getParentComboBox().getSelectionModel().select(selected.getParents());
    }
    
    public void updateVariables() {
        vData.clear();
        for (Object node : selected.getVariables()) {
            UMLVariables variable = (UMLVariables) node;
            vData.add(variable);
        }
        
        dataManager.getColVName().setCellValueFactory(new PropertyValueFactory<>("variableName"));
        dataManager.getColVType().setCellValueFactory(new PropertyValueFactory<>("variableType"));
        dataManager.getColVStatic().setCellValueFactory(new PropertyValueFactory<>("variableStatic"));
        dataManager.getColVAccess().setCellValueFactory(new PropertyValueFactory<>("variableAccess"));
        dataManager.getVariablesTable().setItems(FXCollections.observableList(vData));
    }
    
    public void updateMethods() {
        mData.clear();
        for (Object node : selected.getMethods()) {
            UMLMethods method = (UMLMethods) node;
            mData.add(method);
        }
        
        dataManager.getColM_Access().setCellValueFactory(new PropertyValueFactory<>("methodAccess"));
        dataManager.getColM_Static().setCellValueFactory(new PropertyValueFactory<>("methodStatic"));
        dataManager.getColM_Abstract().setCellValueFactory(new PropertyValueFactory<>("methodAbstract"));
        dataManager.getColM_ReturnType().setCellValueFactory(new PropertyValueFactory<>("methodReturn"));
        dataManager.getColM_Name().setCellValueFactory(new PropertyValueFactory<>("methodName"));
        dataManager.getColM_arg1().setCellValueFactory(new PropertyValueFactory<>("arg1"));
        dataManager.getColM_arg2().setCellValueFactory(new PropertyValueFactory<>("arg2"));
        dataManager.getColM_arg3().setCellValueFactory(new PropertyValueFactory<>("arg3"));
        dataManager.getColM_arg4().setCellValueFactory(new PropertyValueFactory<>("arg4"));
        dataManager.getMethodsTable().setItems(FXCollections.observableList(mData));
    }
    
    public void updateLoadedParentLines() {
        for (Object node : dataManager.returnList()) {
            Diagram diagram = (Diagram) node;
            if (selected.getParentName().equals(diagram.getClassName())) {
                selected.setTest(diagram);
                line = new LineConnector();
                line.setStartProperties(selected.getCenterPoint().centerXProperty(), selected.getCenterPoint().centerYProperty());
                line.setTranslate(selected.getCenterPoint().getTranslateX(), selected.getCenterPoint().getTranslateY());
                selected.setLineConnector(line);
                line.setEndProperties(selected.findClosestNodes(diagram).centerXProperty(), selected.findClosestNodes(diagram).centerYProperty());
                selected.setTriangle(line.createTriangle());
                selected.setLineConnector(line);
                dataManager.getDesignRenderer().getChildren().add(line);
                dataManager.getDesignRenderer().getChildren().add(selected.getTriangle());
                selected.getTriangle().setVisible(false);
                selected.getTriangle().toFront();
                line.toBack();
            }
        }
    }
}

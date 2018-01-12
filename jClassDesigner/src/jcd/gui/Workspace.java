package jcd.gui;

import java.io.IOException;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jcd.controller.JcdEditController;
import jcd.data.DataManager;
import saf.ui.AppYesNoCancelDialogSingleton;
import saf.ui.AppMessageDialogSingleton;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;

public class Workspace extends AppWorkspaceComponent {

    static final String CLASS_RIGHT_PANE = "right_pane";
    static final String CLASS_CENTER_PANE = "center_pane";
    static final String CLASS_CLASS_FONT_SIZE = "class_font_size";
    static final String CLASS_FONT_SIZE = "font_size";
    static final String CLASS_EDIT_ROWS = "edit_rows";
    static final String CLASS_EDIT_PLUSMINUS = "edit_plusminus";
    static final String CLASS_DESIGN = "edit_renderer";
    static final String CLASS_SCROLL = "edit_scroll";

    AppTemplate app;
    AppGUI gui;
    DataManager dataManager;
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;

    JcdEditController jcdEditController;

    int num;

    //BUTTONS
    Button select;
    Button resize;
    Button addClass;
    Button addInterface;
    Button remove;
    Button undo;
    Button redo;
    Button zoomIn;
    Button zoomOut;
    CheckBox grid;
    CheckBox snap;

    //scrollpane
    ScrollPane centerPane;
    Pane designRenderer;
    //rightpane
    VBox rightPane;
    //first row in right pane
    HBox classRow;
    Label classLabel;
    TextField classText;
    //second row in rightpane
    HBox packageRow;
    Label packageLabel;
    TextField packageText;
    //third row in rightpane
    HBox potentialParentsRow;
    HBox parentsRow;
    Label potentialParentsLabel;
    Label parentsLabel;
    ComboBox parentListCombo;
    ComboBox parentsCombo;
    //fourth for in rightpane
    HBox variablesBox;
    Label variablesLabel;
    Button plus;
    Button minus;
    //5th
    ScrollPane varScroll;
    //6th
    HBox methodsBox;
    Label methodsLabel;
    Button plus2;
    Button minus2;
    //7th
    ScrollPane methodScroll;

    TableView variablesTable;
    TableColumn colV_Name;
    TableColumn colV_Type;
    TableColumn colV_Static;
    TableColumn colV_Access;
    TableView methodsTable;
    TableColumn colM_Name;
    TableColumn colM_ReturnType;
    TableColumn colM_Static;
    TableColumn colM_Abstract;
    TableColumn colM_Access;
    TableColumn colM_arg1;
    TableColumn colM_arg2;
    TableColumn colM_arg3;
    TableColumn colM_arg4;

    public Workspace(AppTemplate initApp) throws IOException, Exception {
        app = initApp;
        gui = app.getGUI();
        dataManager = (DataManager) app.getDataComponent();
        layoutGUI();
        setUpHandlers();
        num = 0;
    }

    private void layoutGUI() {
        Group dRGroup = new Group();
        dRGroup.setAutoSizeChildren(true);
        designRenderer = new Pane();
        designRenderer.setMinSize(2200, 2000);
        designRenderer.setMaxSize(2200, 2000);
        dRGroup.getChildren().add(designRenderer);
        centerPane = new ScrollPane(dRGroup);
        rightPane = new VBox();
        //row1
        classRow = new HBox();
        classLabel = new Label("Class Name:     ");
        classText = new TextField();
        classText.setMaxWidth(150);
        classRow.getChildren().add(classLabel);
        classRow.getChildren().add(classText);
        rightPane.getChildren().add(classRow);
        //row2
        packageRow = new HBox();
        packageLabel = new Label("Package:                    ");
        packageText = new TextField();
        packageText.setMaxWidth(150);
        packageRow.getChildren().add(packageLabel);
        packageRow.getChildren().add(packageText);
        rightPane.getChildren().add(packageRow);
        //row3
        potentialParentsRow = new HBox();
        potentialParentsLabel = new Label("Potential Parents:     ");
        parentListCombo = new ComboBox();
        parentListCombo.setMinWidth(150);
        parentListCombo.setMaxWidth(150);
        potentialParentsRow.getChildren().add(potentialParentsLabel);
        potentialParentsRow.getChildren().add(parentListCombo);
        rightPane.getChildren().add(potentialParentsRow);

//        parentsRow = new HBox();
//        parentsLabel = new Label("Parents:                      ");
//        parentsCombo = new ComboBox();
//        parentsRow.getChildren().add(parentsLabel);
//        parentsRow.getChildren().add(parentsCombo);
//        rightPane.getChildren().add(parentsRow);
        //row4
        variablesBox = new HBox();
        variablesLabel = new Label("Variables:");
        plus = new Button("", new ImageView("File:C:./images/plus.png"));
        minus = new Button("", new ImageView("File:C:./images/minus.png"));
        variablesBox.getChildren().add(variablesLabel);
        variablesBox.getChildren().add(plus);
        variablesBox.getChildren().add(minus);
        rightPane.getChildren().add(variablesBox);
        //row5
        variablesTable = new TableView();
        variablesTable.setEditable(true);
        colV_Name = new TableColumn("Name");
        colV_Type = new TableColumn("Type");
        colV_Static = new TableColumn("Static");
        colV_Access = new TableColumn("Access");
        variablesTable.getColumns().addAll(colV_Access, colV_Type, colV_Static, colV_Name);
        varScroll = new ScrollPane(variablesTable);
        varScroll.setHbarPolicy(ALWAYS);
        varScroll.setVbarPolicy(ALWAYS);
        rightPane.getChildren().add(varScroll);
        //row6
        methodsBox = new HBox();
        methodsLabel = new Label("Methods:");
        plus2 = new Button("", new ImageView("File:C:./images/plus.png"));
        minus2 = new Button("", new ImageView("File:C:./images/minus.png"));
        methodsBox.getChildren().add(methodsLabel);
        methodsBox.getChildren().add(plus2);
        methodsBox.getChildren().add(minus2);
        rightPane.getChildren().add(methodsBox);
        //row7
        methodsTable = new TableView();
        colM_Name = new TableColumn("Name");
        colM_ReturnType = new TableColumn("Type");
        colM_Static = new TableColumn("Static");
        colM_Abstract = new TableColumn("Abstract");
        colM_Access = new TableColumn("Access");
        colM_arg1 = new TableColumn("Arg1");
        colM_arg2 = new TableColumn("Arg2");
        colM_arg3 = new TableColumn("Arg3");
        colM_arg4 = new TableColumn("Arg4");
        methodsTable.getColumns().addAll(colM_Access, colM_Static, colM_Abstract, colM_ReturnType, colM_Name, colM_arg1, colM_arg2, colM_arg3, colM_arg4);
        methodScroll = new ScrollPane(methodsTable);
        methodScroll.setHbarPolicy(ALWAYS);
        methodScroll.setVbarPolicy(ALWAYS);
        rightPane.getChildren().add(methodScroll);
        rightPane.setMaxWidth(350);
        centerPane.setFitToHeight(true);
        centerPane.setFitToWidth(true);
        workspace = new BorderPane();
        ((BorderPane) workspace).setCenter(centerPane);
        ((BorderPane) workspace).setRight(rightPane);
    }

    private void setUpHandlers() throws Exception {
        jcdEditController = new JcdEditController(app);

        select = gui.getSelect();
        resize = gui.getResize();
        addClass = gui.getAddClass();
        addInterface = gui.getAddInterface();
        remove = gui.getRemove();
        undo = gui.getUndo();
        redo = gui.getRedo();
        zoomIn = gui.getZoomIn();
        zoomOut = gui.getZoomOut();
        grid = gui.getGrid();
        snap = gui.getSnap();

        //the handlers
        select.setOnAction(event -> {
            select.setDisable(true);
            resize.setDisable(false);
            jcdEditController.handleSelect();
        });
        resize.setOnAction(event -> {
            resize.setDisable(true);
            jcdEditController.handleResize();
            select.setDisable(false);
        });
        addClass.setOnAction(event -> {
            remove.setDisable(false);
            jcdEditController.handleAddClass();
            select.setDisable(true);
            jcdEditController.handleSelect();
            plus.setDisable(false);
            plus2.setDisable(false);
            resize.setDisable(false);
        });
        addInterface.setOnAction(event -> {
            remove.setDisable(false);
            jcdEditController.handleAddInterface();
            select.setDisable(true);
            jcdEditController.handleSelect();
            plus.setDisable(false);
            plus2.setDisable(false);
            resize.setDisable(false);
        });
        remove.setOnAction(event -> {
            jcdEditController.handleRemove();
        });
        undo.setOnAction(event -> {
            jcdEditController.handleUndo(undo);
        });
        redo.setOnAction(event -> {
            jcdEditController.handleRedo(redo);
        });
        zoomIn.setOnAction(event -> {
            jcdEditController.handleZoomIn(zoomIn);
        });
        zoomOut.setOnAction(event -> {
            jcdEditController.handleZoomOut(zoomOut);
        });
        grid.setOnAction(event -> {
            jcdEditController.handleGrid(grid);
        });
        classText.setOnAction(event -> {
            jcdEditController.handleClassText();
        });
        packageText.setOnAction(event -> {
            jcdEditController.handlePackageText();
        });
        parentListCombo.setOnAction(event -> {
            jcdEditController.handleParentSelection();
        });
        plus.setOnAction(event -> {
            jcdEditController.handleAddVariable();
        });
        minus.setOnAction(event -> {
            jcdEditController.handleRemoveVariable();
        });
        plus2.setOnAction(event -> {
            jcdEditController.handleAddMethod();
        });
        minus2.setOnAction(event -> {
            jcdEditController.handleRemoveMethod();
        });
    }

    public Pane returnDesignRenderer() {
        return designRenderer;
    }

    public ComboBox returnParentCombo() {
        return parentListCombo;
    }

    public Button returnSelectButton() {
        return select;
    }

    public Button returnRemoveButton() {
        return remove;
    }

    public Button returnResizeButton() {
        return resize;
    }

    public TextField returnClassText() {
        return classText;
    }

    public TextField returnPackageText() {
        return packageText;
    }

    public CheckBox returnSnap() {
        return snap;
    }

    public TableView returnVariablesTable() {
        return variablesTable;
    }

    public TableColumn returnColVName() {
        return colV_Name;
    }

    public TableColumn returnColVType() {
        return colV_Type;
    }

    public TableColumn returnColVStatic() {
        return colV_Static;
    }

    public TableColumn returnColVAccess() {
        return colV_Access;
    }

    public TableView returnMethodsTable() {
        return methodsTable;
    }

    public TableColumn returnColM_Name() {
        return colM_Name;
    }

    public TableColumn returnColM_ReturnType() {
        return colM_ReturnType;
    }

    public TableColumn returnColM_Static() {
        return colM_Static;
    }

    public TableColumn returnColM_Abstract() {
        return colM_Abstract;
    }

    public TableColumn returnColM_Access() {
        return colM_Access;
    }

    public TableColumn returnColM_arg1() {
        return colM_arg1;
    }

    public TableColumn returnColM_arg2() {
        return colM_arg2;
    }

    public TableColumn returnColM_arg3() {
        return colM_arg3;
    }

    public TableColumn returnColM_arg4() {
        return colM_arg4;
    }

    //return TableColumn return
    public void loadExtension() {
        jcdEditController.loadDesignRenderer();
        num = 1;
    }

    @Override
    public void initStyle() {
        centerPane.getStyleClass().add(CLASS_CENTER_PANE);
        rightPane.getStyleClass().add(CLASS_RIGHT_PANE);
        classLabel.getStyleClass().add(CLASS_CLASS_FONT_SIZE);
        packageLabel.getStyleClass().add(CLASS_FONT_SIZE);
        potentialParentsLabel.getStyleClass().add(CLASS_FONT_SIZE);
        //parentsLabel.getStyleClass().add(CLASS_FONT_SIZE);
        variablesLabel.getStyleClass().add(CLASS_FONT_SIZE);
        methodsLabel.getStyleClass().add(CLASS_FONT_SIZE);
        classRow.getStyleClass().add(CLASS_EDIT_ROWS);
        packageRow.getStyleClass().add(CLASS_EDIT_ROWS);
        potentialParentsRow.getStyleClass().add(CLASS_EDIT_ROWS);
        //parentsRow.getStyleClass().add(CLASS_EDIT_ROWS);
        variablesBox.getStyleClass().add(CLASS_EDIT_ROWS);
        methodsBox.getStyleClass().add(CLASS_EDIT_ROWS);
        plus.getStyleClass().add(CLASS_EDIT_PLUSMINUS);
        minus.getStyleClass().add(CLASS_EDIT_PLUSMINUS);
        plus2.getStyleClass().add(CLASS_EDIT_PLUSMINUS);
        minus2.getStyleClass().add(CLASS_EDIT_PLUSMINUS);
        designRenderer.getStyleClass().add(CLASS_DESIGN);
    }

    @Override
    public void reloadWorkspace() {
        if (designRenderer.getChildren().isEmpty()) {
            select.setDisable(true);
        }
        resize.setDisable(false);
        addClass.setDisable(false);
        addInterface.setDisable(false);
        if (designRenderer.getChildren().isEmpty()) {
            remove.setDisable(true);
        } else {
            remove.setDisable(false);
        }
        if (num == 0) {
            remove.setDisable(true);
        } else if (num == 1) {
            remove.setDisable(false);
        }
        undo.setDisable(false);
        redo.setDisable(false);
        zoomIn.setDisable(false);
        zoomOut.setDisable(false);
        grid.setDisable(false);
        snap.setDisable(false);
        grid.setSelected(false);
        snap.setSelected(false);

        plus.setDisable(false);
        minus.setDisable(false);

        plus2.setDisable(false);
        minus2.setDisable(false);

        if (designRenderer.getChildren().isEmpty()) {
            resize.setDisable(true);
        } else {
            resize.setDisable(false);
        }
        num = 0;
    }

}

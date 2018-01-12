package saf.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import saf.controller.AppFileController;
import saf.AppTemplate;
import static saf.settings.AppPropertyType.*;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;
import saf.components.AppStyleArbiter;

/**
 * This class provides the basic user interface for this application, including
 * all the file controls, but not including the workspace, which would be
 * customly provided for each app.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class AppGUI implements AppStyleArbiter {

    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    protected AppFileController fileController;

    // THIS IS THE APPLICATION WINDOW
    protected Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    protected Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION AppGUI
    protected BorderPane appPane;

    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    protected HBox fileToolbarPane;
    protected HBox leftToolbarPane;
    protected HBox middleToolbarPane;
    protected HBox rightToolbarPane;
    //fileToolbarPane
    protected Button newButton;
    protected Button loadButton;
    protected Button saveButton;
    protected Button saveAsButton;
    protected VBox exportBox;
    protected Button exportPhotoButton;
    protected Button exportCodeButton;
    protected Button exitButton;
    //topToolbarPane
    protected Button selectButton;
    protected Button resizeButton;
    protected Button addClassButton;
    protected Button addInterfaceButton;
    protected Button removeButton;
    protected Button undoButton;
    protected Button redoButton;
    //rightToolbarPane
    protected Button zoomInButton;
    protected Button zoomOutButton;
    protected VBox checkVBox;
    protected CheckBox Grid;
    protected CheckBox Snap;
    protected Label gridLabel;
    protected Label snapLabel;
    protected VBox labelVBox;

    // HERE ARE OUR DIALOGS
    protected AppYesNoCancelDialogSingleton yesNoCancelDialog;

    protected String appTitle;

    /**
     * This constructor initializes the file toolbar for use.
     *
     * @param initPrimaryStage The window for this application.
     *
     * @param initAppTitle The title of this application, which will appear in
     * the window bar.
     *
     * @param app The app within this gui is used.
     */
    public AppGUI(Stage initPrimaryStage,
            String initAppTitle,
            AppTemplate app) {
        // SAVE THESE FOR LATER
        primaryStage = initPrimaryStage;
        appTitle = initAppTitle;

        // INIT THE TOOLBAR
        initFileToolbar(app);

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow();
    }

    /**
     * Accessor method for getting the application pane, within which all user
     * interface controls are ultimately placed.
     *
     * @return This application GUI's app pane.
     */
    public BorderPane getAppPane() {
        return appPane;
    }

    /**
     * Accessor method for getting this application's primary stage's, scene.
     *
     * @return This application's window's scene.
     */
    public Scene getPrimaryScene() {
        return primaryScene;
    }

    /**
     * Accessor method for getting this application's window, which is the
     * primary stage within which the full GUI will be placed.
     *
     * @return This application's primary stage (i.e. window).
     */
    public Stage getWindow() {
        return primaryStage;
    }

    /**
     * This method is used to activate/deactivate toolbar buttons when they can
     * and cannot be used so as to provide foolproof design.
     *
     * @param saved Describes whether the loaded Page has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        saveButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
        newButton.setDisable(false);
        loadButton.setDisable(false);
        saveButton.setDisable(false);
        saveAsButton.setDisable(false);
        exportPhotoButton.setDisable(false);
        exportCodeButton.setDisable(false);
        exitButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }

    /**
     * *************************************************************************
     */
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR AppGUI */
    /**
     * *************************************************************************
     */
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar(AppTemplate app) {
        fileToolbarPane = new HBox();
        leftToolbarPane = new HBox();
        middleToolbarPane = new HBox();
        rightToolbarPane = new HBox();
        exportBox = new VBox();
        Grid = new CheckBox();
        Snap = new CheckBox();
        checkVBox = new VBox();
        gridLabel = new Label("Grid");
        snapLabel = new Label("Snap");
        labelVBox = new VBox();

        //right toolbar
        newButton = initChildButton(leftToolbarPane, NEW_ICON.toString(), NEW_TOOLTIP.toString(), false);
        loadButton = initChildButton(leftToolbarPane, LOAD_ICON.toString(), LOAD_TOOLTIP.toString(), false);
        saveButton = initChildButton(leftToolbarPane, SAVE_ICON.toString(), SAVE_TOOLTIP.toString(), false);
        saveAsButton = initChildButton(leftToolbarPane, SAVEAS_ICON.toString(), SAVEAS_TOOLTIP.toString(), false);
        exportPhotoButton = initChildButton(exportBox, PHOTO_ICON.toString(), EXPORTPHOTO_TOOLTIP.toString(), false);
        exportCodeButton = initChildButton(exportBox, CODE_ICON.toString(), EXPORTCODE_TOOLTIP.toString(), false);
        leftToolbarPane.getChildren().add(exportBox);
        exitButton = initChildButton(leftToolbarPane, EXIT_ICON.toString(), EXIT_TOOLTIP.toString(), false);
        //middle toolbar
        selectButton = initChildButton(middleToolbarPane, SELECT_ICON.toString(), SELECT_TOOLTIP.toString(), false);
        resizeButton = initChildButton(middleToolbarPane, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), false);
        addClassButton = initChildButton(middleToolbarPane, ADDCLASS_ICON.toString(), ADDCLASS_TOOLTIP.toString(), false);
        addInterfaceButton = initChildButton(middleToolbarPane, ADDINTERFACE_ICON.toString(), ADDINTERFACE_TOOLTIP.toString(), false);
        removeButton = initChildButton(middleToolbarPane, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), false);
        undoButton = initChildButton(middleToolbarPane, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), false);
        redoButton = initChildButton(middleToolbarPane, REDO_ICON.toString(), REDO_TOOLTIP.toString(), false);
        //right toolbar
        zoomInButton = initChildButton(rightToolbarPane, ZOOMIN_ICON.toString(), ZOOMIN_TOOLTIP.toString(), false);
        zoomOutButton = initChildButton(rightToolbarPane, ZOOMOUT_ICON.toString(), ZOOMOUT_TOOLTIP.toString(), false);
        checkVBox.getChildren().add(Grid);
        checkVBox.getChildren().add(Snap);
        rightToolbarPane.getChildren().add(checkVBox);
        labelVBox.getChildren().add(gridLabel);
        labelVBox.getChildren().add(snapLabel);
        rightToolbarPane.getChildren().add(labelVBox);

        fileController = new AppFileController(app);
        newButton.setOnAction(e -> {
            fileController.handleNewRequest();
        });
        loadButton.setOnAction(e -> {
            fileController.handleLoadRequest();
        });
        saveButton.setOnAction(e -> {
            fileController.handleSaveRequest();
        });
        saveAsButton.setOnAction(e -> {
            fileController.handleSaveAsRequest();
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest();
        });
        exportCodeButton.setOnAction(e -> {
            try {
                fileController.handleCodeRequest();
            } catch (IOException ex) {
                Logger.getLogger(AppGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        exportPhotoButton.setOnAction(e -> {
            try {
                fileController.handlePhotoRequest();
            } catch (IOException ex) {
                Logger.getLogger(AppGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Page IS CREATED OR LOADED
    private void initWindow() {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(appTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        fileToolbarPane.getChildren().add(leftToolbarPane);
        fileToolbarPane.getChildren().add(middleToolbarPane);
        fileToolbarPane.getChildren().add(rightToolbarPane);
        appPane = new BorderPane();
        appPane.setTop(fileToolbarPane);
        primaryScene = new Scene(appPane);

        // SET THE APP ICON
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String appIcon = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO);
        primaryStage.getIcons().add(new Image(appIcon));

        // NOW TIE THE SCENE TO THE WINDOW AND OPEN THE WINDOW
        selectButton.setDisable(true);
        resizeButton.setDisable(true);
        addClassButton.setDisable(true);
        addInterfaceButton.setDisable(true);
        removeButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        zoomInButton.setDisable(true);
        zoomOutButton.setDisable(true);
        Grid.setDisable(true);
        Snap.setDisable(true);

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    /**
     * This is a public helper method for initializing a simple button with an
     * icon and tooltip and placing it into a toolbar.
     *
     * @param toolbar Toolbar pane into which to place this button.
     *
     * @param icon Icon image file name for the button.
     *
     * @param tooltip Tooltip to appear when the user mouses over the button.
     *
     * @param disabled true if the button is to start off disabled, false
     * otherwise.
     *
     * @return A constructed, fully initialized button placed into its
     * appropriate pane container.
     */
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);

        Button button = new Button();
        button.setDisable(disabled);

        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);

        toolbar.getChildren().add(button);

        return button;
    }

    /**
     * This function specifies the CSS style classes for the controls managed by
     * this framework.
     */
    @Override
    public void initStyle() {
        fileToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        leftToolbarPane.getStyleClass().add(CLASS_TOOLBAR_BOX);
        middleToolbarPane.getStyleClass().add(CLASS_TOOLBAR_BOX);
        rightToolbarPane.getStyleClass().add(CLASS_TOOLBAR_BOX);
        exportBox.getStyleClass().add(CLASS_EXPORT_BOX);
        newButton.getStyleClass().add(CLASS_FILE_BUTTON);
        loadButton.getStyleClass().add(CLASS_FILE_BUTTON);
        saveButton.getStyleClass().add(CLASS_FILE_BUTTON);
        exitButton.getStyleClass().add(CLASS_FILE_BUTTON);
        saveAsButton.getStyleClass().add(CLASS_FILE_BUTTON);
        exportPhotoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        exportCodeButton.getStyleClass().add(CLASS_FILE_BUTTON);
        selectButton.getStyleClass().add(CLASS_FILE_BUTTON);
        resizeButton.getStyleClass().add(CLASS_FILE_BUTTON);
        addClassButton.getStyleClass().add(CLASS_FILE_BUTTON);
        addInterfaceButton.getStyleClass().add(CLASS_FILE_BUTTON);
        removeButton.getStyleClass().add(CLASS_FILE_BUTTON);
        undoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        redoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        zoomInButton.getStyleClass().add(CLASS_FILE_BUTTON);
        zoomOutButton.getStyleClass().add(CLASS_FILE_BUTTON);
    }

    public Button getSelect() {
        return selectButton;
    }

    public Button getResize() {
        return resizeButton;
    }

    public Button getAddClass() {
        return addClassButton;
    }

    public Button getAddInterface() {
        return addInterfaceButton;
    }

    public Button getRemove() {
        return removeButton;
    }

    public Button getUndo() {
        return undoButton;
    }

    public Button getRedo() {
        return redoButton;
    }

    public Button getZoomIn() {
        return zoomInButton;
    }

    public Button getZoomOut() {
        return zoomOutButton;
    }

    public CheckBox getGrid() {
        return Grid;
    }

    public CheckBox getSnap() {
        return Snap;
    }
}

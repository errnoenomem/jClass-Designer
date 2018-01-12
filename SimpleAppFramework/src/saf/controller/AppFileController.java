package saf.controller;

import saf.ui.AppYesNoCancelDialogSingleton;
import saf.ui.AppMessageDialogSingleton;
import saf.ui.AppGUI;
import saf.components.AppFileComponent;
import saf.components.AppDataComponent;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import properties_manager.PropertiesManager;
import saf.AppTemplate;
import static saf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static saf.settings.AppPropertyType.LOAD_WORK_TITLE;
import static saf.settings.AppPropertyType.WORK_FILE_EXT;
import static saf.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static saf.settings.AppPropertyType.NEW_COMPLETED_MESSAGE;
import static saf.settings.AppPropertyType.NEW_COMPLETED_TITLE;
import static saf.settings.AppPropertyType.NEW_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.NEW_ERROR_TITLE;
import static saf.settings.AppPropertyType.SAVE_COMPLETED_MESSAGE;
import static saf.settings.AppPropertyType.SAVE_COMPLETED_TITLE;
import static saf.settings.AppPropertyType.SAVE_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.SAVE_ERROR_TITLE;
import static saf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static saf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import static saf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static saf.settings.AppPropertyType.EXPORT_CODE_COMPLETION;
import static saf.settings.AppPropertyType.EXPORT_PHOTO_COMPLETION;
import static saf.settings.AppPropertyType.EXPORT_CODE_MESSAGE;
import static saf.settings.AppPropertyType.EXPORT_PHOTO_MESSAGE;
import static saf.settings.AppStartupConstants.PATH_WORK;

public class AppFileController {

    AppTemplate app;

    boolean saved;

    File currentWorkFile;

    public AppFileController(AppTemplate initApp) {
        saved = true;
        app = initApp;
    }

    public void markAsEdited(AppGUI gui) {
        saved = false;

        gui.updateToolbarControls(saved);
    }

    public void handleNewRequest() {
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            boolean continueToMakeNew = true;
            if (!saved) {
                continueToMakeNew = promptToSave();
            }

            if (continueToMakeNew) {
                app.getDataComponent().reset();

                app.getWorkspaceComponent().reloadWorkspace();

                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                saved = false;
                currentWorkFile = null;

                app.getGUI().updateToolbarControls(saved);
                app.getWorkspaceComponent().reloadWorkspace();

                dialog.show(props.getProperty(NEW_COMPLETED_TITLE), props.getProperty(NEW_COMPLETED_MESSAGE));
            }
        } catch (IOException ioe) {
            dialog.show(props.getProperty(NEW_ERROR_TITLE), props.getProperty(NEW_ERROR_MESSAGE));
        }
    }

    public void handleLoadRequest() {
        try {
            boolean continueToOpen = true;
            if (!saved) {
                continueToOpen = promptToSave();
            }

            if (continueToOpen) {
                promptToOpen();

            }
        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }

    public void handleSaveRequest() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            if (currentWorkFile != null) {
                saveWork(currentWorkFile);
            } else {
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
                fc.getExtensionFilters().addAll(
                        new ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

                File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
                if (selectedFile != null) {
                    saveWork(selectedFile);
                }
            }
        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }

    public void handleSaveAsRequest() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
            fc.getExtensionFilters().addAll(
                    new ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

            File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
            if (selectedFile != null) {
                saveWork(selectedFile);
            }

        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }

    private void saveWork(File selectedFile) throws IOException {
        app.getFileComponent().saveData(app.getDataComponent(), selectedFile.getPath());

        currentWorkFile = selectedFile;
        saved = true;

        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        dialog.show(props.getProperty(SAVE_COMPLETED_TITLE), props.getProperty(SAVE_COMPLETED_MESSAGE));

        app.getGUI().updateToolbarControls(saved);
    }

    public void handleCodeRequest() throws IOException {
        app.getFileComponent().exportData(app.getDataComponent(), "somebs");
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        dialog.show(props.getProperty(EXPORT_CODE_MESSAGE), props.getProperty(EXPORT_CODE_COMPLETION));
    }

    public void handlePhotoRequest() throws IOException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
        app.getFileComponent().exportPhoto(app.getDataComponent(), selectedFile);
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        dialog.show(props.getProperty(EXPORT_PHOTO_MESSAGE), props.getProperty(EXPORT_PHOTO_COMPLETION));
    }

    public void handleExitRequest() {
        try {
            boolean continueToExit = true;
            if (!saved) {
                continueToExit = promptToSave();
            }

            if (continueToExit) {
                System.exit(0);
            }
        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            dialog.show(props.getProperty(SAVE_ERROR_TITLE), props.getProperty(SAVE_ERROR_MESSAGE));
        }
    }

    private boolean promptToSave() throws IOException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(SAVE_UNSAVED_WORK_TITLE), props.getProperty(SAVE_UNSAVED_WORK_MESSAGE));

        String selection = yesNoDialog.getSelection();

        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            AppDataComponent dataManager = app.getDataComponent();

            if (currentWorkFile == null) {
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
                fc.getExtensionFilters().addAll(
                        new ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

                File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
                if (selectedFile != null) {
                    saveWork(selectedFile);
                    saved = true;
                }
            } else {
                saveWork(currentWorkFile);
                saved = true;
            }
        } else if (selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
            return false;
        }
        return true;
    }

    private void promptToOpen() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
        currentWorkFile = selectedFile;
        if (selectedFile != null) {
            try {
                app.getDataComponent().reset();
                AppDataComponent dataManager = app.getDataComponent();
                AppFileComponent fileManager = app.getFileComponent();
                fileManager.loadData(dataManager, selectedFile.getAbsolutePath());

                app.getWorkspaceComponent().reloadWorkspace();
                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }

    public void markFileAsNotSaved() {
        saved = false;
    }

    public boolean isSaved() {
        return saved;
    }
}

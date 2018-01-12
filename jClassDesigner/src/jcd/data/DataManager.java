package jcd.data;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jcd.gui.Workspace;
import saf.components.AppDataComponent;
import saf.AppTemplate;

public class DataManager implements AppDataComponent {

    AppTemplate app;

    ArrayList<Diagram> list;

    public DataManager(AppTemplate initApp) throws Exception {
        app = initApp;
        list = new ArrayList();
    }

    public void setToList(Diagram Class) {
        list.add(Class);
        updateParentComboBox();
    }

    public void removeFromList(Diagram Class) {
        ((Workspace) app.getWorkspaceComponent()).returnDesignRenderer().getChildren().remove(Class);

        list.remove(Class);
        updateParentComboBox();
    }

    public Diagram getListIndex(int num) {
        return (Diagram) list.get(num);
    }

    public ArrayList returnList() {
        return list;
    }

    public void clearList() {
        list.clear();
    }

    public void startLoadSequence() {
        ((Workspace) app.getWorkspaceComponent()).loadExtension();
    }

    public Pane getDesignRenderer() {
        return ((Workspace) app.getWorkspaceComponent()).returnDesignRenderer();
    }

    public CheckBox getSnap() {
        return ((Workspace) app.getWorkspaceComponent()).returnSnap();
    }

    public Button getSelectButton() {
        return ((Workspace) app.getWorkspaceComponent()).returnSelectButton();
    }

    public Button getRemoveButton() {
        return ((Workspace) app.getWorkspaceComponent()).returnRemoveButton();
    }

    public Button getResizeButton() {
        return ((Workspace) app.getWorkspaceComponent()).returnResizeButton();
    }

    public TextField getClassText() {
        return ((Workspace) app.getWorkspaceComponent()).returnClassText();
    }

    public TextField getPackageText() {
        return ((Workspace) app.getWorkspaceComponent()).returnPackageText();
    }

    public ComboBox getParentComboBox() {
        return ((Workspace) app.getWorkspaceComponent()).returnParentCombo();
    }

    public TableView getVariablesTable() {
        return ((Workspace) app.getWorkspaceComponent()).returnVariablesTable();
    }

    public TableColumn getColVName() {
        return ((Workspace) app.getWorkspaceComponent()).returnColVName();
    }

    public TableColumn getColVType() {
        return ((Workspace) app.getWorkspaceComponent()).returnColVType();
    }

    public TableColumn getColVStatic() {
        return ((Workspace) app.getWorkspaceComponent()).returnColVStatic();
    }

    public TableColumn getColVAccess() {
        return ((Workspace) app.getWorkspaceComponent()).returnColVAccess();
    }

    public TableView getMethodsTable() {
        return ((Workspace) app.getWorkspaceComponent()).returnMethodsTable();
    }

    public TableColumn getColM_Name() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_Name();
    }

    public TableColumn getColM_ReturnType() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_ReturnType();
    }

    public TableColumn getColM_Static() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_Static();
    }

    public TableColumn getColM_Abstract() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_Abstract();
    }

    public TableColumn getColM_Access() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_Access();
    }

    public TableColumn getColM_arg1() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_arg1();
    }

    public TableColumn getColM_arg2() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_arg2();
    }

    public TableColumn getColM_arg3() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_arg3();
    }

    public TableColumn getColM_arg4() {
        return ((Workspace) app.getWorkspaceComponent()).returnColM_arg4();
    }

    public void updateParentComboBox() {
        ((Workspace) app.getWorkspaceComponent()).returnParentCombo().getItems().clear();
        for (Object node : list) {
            Diagram diagram = (Diagram) node;
            ((Workspace) app.getWorkspaceComponent()).returnParentCombo().getItems().add(diagram.getClassName());
        }
    }

    @Override
    public void reset() {
        list.clear();
        ((Workspace) app.getWorkspaceComponent()).returnDesignRenderer().getChildren().clear();
    }

    public void printListObjects() {
        int counter = 1;
        for (Object i : list) {
            System.out.println("object " + counter + " i's value = " + i);

            System.out.println("size" + list.size());
            counter++;
        }
    }

    public void printListData() {
        for (Object node : list) {
            ClassDiagram diagram = (ClassDiagram) node;
            diagram.printClassData();
        }
    }

    public void reloadWorkspaceHelper() {
        ((Workspace) app.getWorkspaceComponent()).reloadWorkspace();
    }

}

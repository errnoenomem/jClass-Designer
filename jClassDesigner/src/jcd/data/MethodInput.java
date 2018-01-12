/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Billy
 */
public class MethodInput extends Dialog {

    String name;
    String returnType;
    Boolean stat;
    Boolean abs;
    String access;
    String arg1;
    String arg2;
    String arg3;
    String arg4;
    int count;

    public MethodInput() {
        count = 1;
        this.setTitle("Method Control");
        stat = false;
        abs = false;
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label accessLabel = new Label("Access:");
        Label returnTypeLabel = new Label("Return Type:");
        Label StaticLabel = new Label("Static:");
        Label AbstractLabel = new Label("Abstract");
        Label nameLabel = new Label("Name:");

        ComboBox accessComboBox = new ComboBox();
        accessComboBox.getItems().addAll("public", "private", "protected");
        setAccess("public");
        accessComboBox.getSelectionModel().selectFirst();
        TextField returnTypeTextField = new TextField();
        CheckBox staticCheckBox = new CheckBox();
        staticCheckBox.setSelected(false);
        CheckBox abstractCheckBox = new CheckBox();
        abstractCheckBox.setSelected(false);
        TextField nameTextField = new TextField();

        grid.add(accessLabel, 0, 0);
        grid.add(accessComboBox, 1, 0);
        grid.add(StaticLabel, 0, 1);
        grid.add(staticCheckBox, 1, 1);
        grid.add(AbstractLabel, 0, 2);
        grid.add(abstractCheckBox, 1, 2);
        grid.add(returnTypeLabel, 0, 3);
        grid.add(returnTypeTextField, 1, 3);
        grid.add(nameLabel, 0, 4);
        grid.add(nameTextField, 1, 4);

        Button addArgumentButton = new Button("Add Argument Type");
        addArgumentButton.setMinWidth(100);
        grid.add(addArgumentButton, 1, 5);

        TextField argumentTextField = new TextField();
        grid.add(argumentTextField, 0, 5);

        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);

        accessComboBox.setOnAction(event -> {
            setAccess(accessComboBox.getSelectionModel().getSelectedItem().toString());
        });
        staticCheckBox.setOnAction(event -> {
            setStatic(staticCheckBox);
        });
        abstractCheckBox.setOnAction(event -> {
            setAbstract(abstractCheckBox);
        });
        returnTypeTextField.textProperty().addListener(event -> {
            setReturnType(returnTypeTextField.getText());
        });
        nameTextField.textProperty().addListener(event -> {
            setName(nameTextField.getText());
        });
        addArgumentButton.setOnAction(event -> {
            if (count < 5) {
                if (count == 1) {
                    setArg1(argumentTextField.getText());
                }
                if (count == 2) {
                    setArg2(argumentTextField.getText());
                }
                if (count == 3) {
                    setArg3(argumentTextField.getText());
                }
                if (count == 4) {
                    setArg4(argumentTextField.getText());
                }
                argumentTextField.clear();
                count++;
            }
        });
    }

    public void setAbs(Boolean abs) {
        this.abs = abs;
    }

    public void setArg1(String text) {
        this.arg1 = text;
    }

    public void setArg2(String text) {
        this.arg2 = text;
    }

    public void setArg3(String text) {
        this.arg3 = text;
    }

    public void setArg4(String text) {
        this.arg4 = text;
    }

    public void setAccess(String text) {
        access = text;
    }

    public void setStatic(CheckBox value) {
        if (value.isSelected()) {
            stat = true;
        } else {
            stat = false;
        }
    }

    public void setAbstract(CheckBox value) {
        if (value.isSelected()) {
            abs = true;
        } else {
            abs = false;
        }
    }

    public void setReturnType(String text) {
        returnType = text;
    }

    public void setName(String text) {
        name = text;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public Boolean getStat() {
        return stat;
    }

    public Boolean getAbs() {
        return abs;
    }

    public String getAccess() {
        return access;
    }

    public String getArg1() {
        return arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public String getArg4() {
        return arg4;
    }

}

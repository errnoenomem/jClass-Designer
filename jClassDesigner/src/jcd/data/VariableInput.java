/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.geometry.Insets;
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
public class VariableInput extends Dialog {

    String name;
    String type;
    Boolean stat;
    String access;

    public VariableInput() {
        this.setTitle("Variable Control");
        stat = false;
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label accessLabel = new Label("Access:");
        Label staticLabel = new Label("Static:");
        Label typeLabel = new Label("Type:");
        Label nameLabel = new Label("Name");

        ComboBox accessComboBox = new ComboBox();
        accessComboBox.getItems().addAll("public", "private", "protected");
        accessComboBox.getSelectionModel().selectFirst();
        setAccess("public");
        CheckBox staticCheckBox = new CheckBox();
        staticCheckBox.setSelected(false);
        TextField typeTextField = new TextField();
        TextField nameTextField = new TextField();

        grid.add(accessLabel, 0, 0);
        grid.add(accessComboBox, 1, 0);
        grid.add(staticLabel, 0, 1);
        grid.add(staticCheckBox, 1, 1);
        grid.add(typeLabel, 0, 2);
        grid.add(typeTextField, 1, 2);
        grid.add(nameLabel, 0, 3);
        grid.add(nameTextField, 1, 3);

        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);

        accessComboBox.setOnAction(event -> {
            setAccess(accessComboBox.getSelectionModel().getSelectedItem().toString());
        });
        staticCheckBox.setOnAction(event -> {
            setStatic(staticCheckBox);
        });
        typeTextField.textProperty().addListener(event -> {
            setType(typeTextField.getText());
        });
        nameTextField.textProperty().addListener(event -> {
            setName(nameTextField.getText());
        });
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

    public void setType(String text) {
        type = text;
    }

    public void setName(String text) {
        name = text;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Boolean getStat() {
        return stat;
    }

    public String getAccess() {
        return access;
    }

}

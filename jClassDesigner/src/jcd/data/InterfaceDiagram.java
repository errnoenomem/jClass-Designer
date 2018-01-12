package jcd.data;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Billy
 */
public class InterfaceDiagram extends Diagram {

    VBox variablesBox;
    VBox methodsBox;

    public InterfaceDiagram() {

        Type = "interface";

        VBox classBox = new VBox();
        Label indicator = new Label("<<interface>>");
        className = new Text("defaultInterface");
        classBox.setAlignment(Pos.CENTER);
        classBox.getChildren().add(indicator);
        classBox.getChildren().add(className);
        classBox.getStyleClass().add(VM_BOXES);

        variablesBox = new VBox();
        VBox.setVgrow(variablesBox, Priority.ALWAYS);
        variablesBox.getStyleClass().add(VM_BOXES);

        methodsBox = new VBox();
        VBox.setVgrow(methodsBox, Priority.ALWAYS);
        methodsBox.getStyleClass().add(VM_BOXES);

        this.getChildren().add(classBox);
        this.getChildren().add(variablesBox);
        this.getChildren().add(methodsBox);

        this.getStyleClass().add(VM_BOXES);
    }

    @Override
    public void addVariables(UMLVariables variable) {
        variables.add(variable);
        Label label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(label, Priority.ALWAYS);
        if (variable.getVariableAccess().equals("public") && variable.getVariableAccess() != null) {
            label.setText("+" + variable.getVariableName() + ":" + variable.getVariableType());
            variablesBox.getChildren().add(label);
        } else if (variable.getVariableAccess().equals("private") && variable.getVariableAccess() != null) {
            label.setText("-" + variable.getVariableName() + ":" + variable.getVariableType());
            variablesBox.getChildren().add(label);
        } else if (variable.getVariableAccess().equals("protected") && variable.getVariableAccess() != null) {
            label.setText("#" + variable.getVariableName() + ":" + variable.getVariableType());
            variablesBox.getChildren().add(label);
        } else if (variable.getVariableAccess() != null) {
            label.setText(variable.getVariableName() + ":" + variable.getVariableType());
            variablesBox.getChildren().add(label);
        }
    }

    @Override
    public void updateVariables() {
        variablesBox.getChildren().clear();
        for (Object node : variables) {
            Label label = new Label();
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            VBox.setVgrow(label, Priority.ALWAYS);
            UMLVariables variable = (UMLVariables) node;
            if (variable.getVariableAccess().equals("public") && variable.getVariableAccess() != null) {
                label.setText("+" + variable.getVariableName() + ":" + variable.getVariableType());
                variablesBox.getChildren().add(label);
            } else if (variable.getVariableAccess().equals("private") && variable.getVariableAccess() != null) {
                label.setText("-" + variable.getVariableName() + ":" + variable.getVariableType());
                variablesBox.getChildren().add(label);
            } else if (variable.getVariableAccess().equals("protected") && variable.getVariableAccess() != null) {
                label.setText("#" + variable.getVariableName() + ":" + variable.getVariableType());
                variablesBox.getChildren().add(label);
            } else if (variable.getVariableAccess() != null) {
                label.setText(variable.getVariableName() + ":" + variable.getVariableType());
                variablesBox.getChildren().add(label);
            }
        }
    }

    @Override
    public void addMethods(UMLMethods method) {
        methods.add(method);
        if (method.getMethodAccess().equals("public") && method.getMethodAccess() != null) {
            Label label = new Label();
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            VBox.setVgrow(label, Priority.ALWAYS);
            if (method.getArgs() == 0) {
                label.setText(("+" + method.getMethodName() + "():" + method.getMethodReturn()));
            }
            if (method.getArgs() == 1) {
                label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 2) {
                label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 3) {
                label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 4) {
                label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + ", " + method.getArg4() + "):" + method.getMethodReturn()));
            }
            methodsBox.getChildren().add(label);
        } else if (method.getMethodAccess().equals("private") && method.getMethodAccess() != null) {
            Label label = new Label();
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            VBox.setVgrow(label, Priority.ALWAYS);
            if (method.getArgs() == 0) {
                label.setText(("-" + method.getMethodName() + "():" + method.getMethodReturn()));
            }
            if (method.getArgs() == 1) {
                label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 2) {
                label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 3) {
                label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 4) {
                label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + ", " + method.getArg4() + "):" + method.getMethodReturn()));
            }
            methodsBox.getChildren().add(label);
        } else if (method.getMethodAccess().equals("protected") && method.getMethodAccess() != null) {
            Label label = new Label();
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            VBox.setVgrow(label, Priority.ALWAYS);
            if (method.getArgs() == 0) {
                label.setText(("#" + method.getMethodName() + "():" + method.getMethodReturn()));
            }
            if (method.getArgs() == 1) {
                label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 2) {
                label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 3) {
                label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + "):" + method.getMethodReturn()));
            }
            if (method.getArgs() == 4) {
                label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + ", " + method.getArg4() + "):" + method.getMethodReturn()));
            }
            methodsBox.getChildren().add(label);
        }
    }

    @Override
    public void updateMethods() {
        methodsBox.getChildren().clear();
        for (Object node : methods) {
            UMLMethods method = (UMLMethods) node;
            if (method.getMethodAccess().equals("public") && method.getMethodAccess() != null) {
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                VBox.setVgrow(label, Priority.ALWAYS);
                if (method.getArgs() == 0) {
                    label.setText(("+" + method.getMethodName() + "():" + method.getMethodReturn()));
                }
                if (method.getArgs() == 1) {
                    label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 2) {
                    label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 3) {
                    label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 4) {
                    label.setText(("+" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + ", " + method.getArg4() + "):" + method.getMethodReturn()));
                }
                methodsBox.getChildren().add(label);
            } else if (method.getMethodAccess().equals("private") && method.getMethodAccess() != null) {
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                VBox.setVgrow(label, Priority.ALWAYS);
                if (method.getArgs() == 0) {
                    label.setText(("-" + method.getMethodName() + "():" + method.getMethodReturn()));
                }
                if (method.getArgs() == 1) {
                    label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 2) {
                    label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 3) {
                    label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 4) {
                    label.setText(("-" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + ", " + method.getArg4() + "):" + method.getMethodReturn()));
                }
                methodsBox.getChildren().add(label);
            } else if (method.getMethodAccess().equals("protected") && method.getMethodAccess() != null) {
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                VBox.setVgrow(label, Priority.ALWAYS);
                if (method.getArgs() == 0) {
                    label.setText(("#" + method.getMethodName() + "():" + method.getMethodReturn()));
                }
                if (method.getArgs() == 1) {
                    label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 2) {
                    label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 3) {
                    label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + "):" + method.getMethodReturn()));
                }
                if (method.getArgs() == 4) {
                    label.setText(("#" + method.getMethodName() + "(" + method.getArg1() + ", " + method.getArg2() + ", " + method.getArg3() + ", " + method.getArg4() + "):" + method.getMethodReturn()));
                }
                methodsBox.getChildren().add(label);
            }
        }
    }

    @Override
    public void printClassData() {
        System.out.println("Class Name: " + className.getText());
        System.out.println("Class Variables:");
        for (Object node : variables) {
            UMLVariables variable = (UMLVariables) node;
            variable.printData();
        }
        System.out.println("Class Methods:");
        for (Object node : methods) {
            UMLMethods method = (UMLMethods) node;
            method.printData();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

/**
 *
 * @author Billy
 */
public class UMLVariables {

    String variableName;
    String variableType;
    Boolean variableStatic;
    String variableAccess;

    public UMLVariables() {
        variableName = null;
        variableType = null;
        variableAccess = null;
        variableStatic = null;
    }

    public void setVariable(String name, String type, String access, Boolean Static) {
        variableName = name;
        variableType = type;
        variableAccess = access;
        variableStatic = Static;
    }

    public void setVariableType(String type) {
        variableType = type;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getVariableType() {
        return variableType;
    }

    public String getVariableAccess() {
        return variableAccess;
    }

    public Boolean getVariableStatic() {
        return variableStatic;
    }

    public void printData() {
        System.out.println("[" + variableAccess + " " + variableType + " " + variableName + "]");
    }

    public void printArgumentData() {
        System.out.println("[" + variableType + " " + variableName + "]");
    }
}

package jcd.data;

import java.util.ArrayList;

/**
 *
 * @author Billy
 */
public class UMLMethods {

    UMLVariables parameter;
    ArrayList parameters;
    String methodName;
    String methodReturn;
    Boolean methodStatic;
    Boolean methodAbstract;
    String methodAccess;

    int args;
    String arg1;
    String arg2;
    String arg3;
    String arg4;

    public UMLMethods() {
        args = 0;
        parameters = new ArrayList();
        methodName = null;
        methodReturn = null;
        methodStatic = null;
        methodAbstract = null;
        methodAccess = null;
    }

    public void setArg1(String arg1) {
        args = 1;
        this.arg1 = arg1;
    }

    public void setArg2(String arg2) {
        args = 2;
        this.arg2 = arg2;
    }

    public void setArg3(String arg3) {
        args = 3;
        this.arg3 = arg3;
    }

    public void setArg4(String arg4) {
        args = 4;
        this.arg4 = arg4;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodReturn() {
        return methodReturn;
    }

    public Boolean getMethodStatic() {
        return methodStatic;
    }

    public Boolean getMethodAbstract() {
        return methodAbstract;
    }

    public String getMethodAccess() {
        return methodAccess;
    }

    public int getArgs() {
        return args;
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

    public ArrayList getParameters() {
        return parameters;
    }

    public UMLVariables getParameter(int index) {
        return (UMLVariables) parameters.get(index);
    }

    public void loadMethod(String name, String Return, String access, Boolean Static, Boolean Abstract) {
        methodName = name;
        methodReturn = Return;
        methodStatic = Static;
        methodAbstract = Abstract;
        methodAccess = access;
    }

    public void loadParameters(String a1, String a2, String a3, String a4) {
        arg1 = a1;
        arg2 = a2;
        arg3 = a3;
        arg4 = a4;
    }

    public void loadParameterArrayList() {
        if (arg1 != null) {
            UMLVariables variable = new UMLVariables();
            variable.setVariableType(arg1);
            parameters.add(variable);
            args = 1;
        }
        if (arg2 != null) {
            UMLVariables variable = new UMLVariables();
            variable.setVariableType(arg2);
            parameters.add(variable);
            args = 2;
        }
        if (arg3 != null) {
            UMLVariables variable = new UMLVariables();
            variable.setVariableType(arg3);
            parameters.add(variable);
            args = 3;
        }
        if (arg4 != null) {
            UMLVariables variable = new UMLVariables();
            variable.setVariableType(arg4);
            parameters.add(variable);
            args = 4;
        }
    }

    public void loadActualParameters(String name, String type, String access, Boolean Static) {
        parameter = new UMLVariables();
        parameter.setVariable(name, type, access, Static);
        parameters.add(parameter);
    }

    public void printData() {
        int counter = 1;
        System.out.print("<" + methodAccess + " " + methodReturn + " " + methodName + ">");
        if (parameters.isEmpty() == false) {
            for (Object node : parameters) {
                UMLVariables argument = (UMLVariables) node;
                System.out.print("Argument " + counter + ": ");
                argument.printArgumentData();
                counter++;
            }
        } else if (parameters.isEmpty()) {
            System.out.println("Argument: None");
        }
    }

}

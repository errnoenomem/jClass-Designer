package jcd.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jcd.data.DataManager;
import jcd.data.Diagram;
import jcd.data.ClassDiagram;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import jcd.data.InterfaceDiagram;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 *
 * @author Billy
 */
public class FileManager implements AppFileComponent {

    //DataManager dataManager;
    // FOR JSON LOADING
    static final String DIAGRAM_X = "x_coordinate";
    static final String DIAGRAM_Y = "y_coordinate";
    static final String DIAGRAM_WIDTH = "diagram_width";
    static final String DIAGRAM_HEIGHT = "diagram_height";

    static final String JSON_DIAGRAMS = "diagrams";
    static final String DIAGRAM_TYPE = "diagram_type";
    static final String DIAGRAM_NAME = "diagram_name";
    static final String DIAGRAM_PARENTS = "diagram_parents";
    static final String DIAGRAM_PACKAGE = "diagram_package";

    static final String JSON_VARIABLES_ARRAY = "json_variables_array";
    static final String VARIABLE_NAME = "variable_name";
    static final String VARIABLE_TYPE = "variable_type";
    static final String VARIABLE_ACCESS = "variable_access";
    static final String VARIABLE_STATIC = "variable_static";

    static final String JSON_METHODS_ARRAY = "json_methods_array";
    static final String METHODS_NAME = "method_name";
    static final String METHOD_RETURN = "method_return";
    static final String METHOD_ACCESS = "method_access";
    static final String METHOD_STATIC = "method_static";
    static final String METHOD_ABSTRACT = "method_abstract";

    static final String METHOD_ARGUMENTS = "method_arguments";
    static final String METHOD_ARGUMENT_NAME = "mehtod_argument_name";
    static final String METHOD_ARGUMENT_TYPE = "method_argument_type";

    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager) data;

        String diagramType;
        String diagramName;
        String packageName;
        String parentName;
        double x;
        double y;
        double height;
        double width;
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ArrayList list = dataManager.returnList();

        for (Object node : list) {
            Diagram diagram = (Diagram) node;
            diagramType = diagram.getObjectType();
            diagramName = diagram.getClassName();
            packageName = diagram.getPackageName();
            parentName = diagram.getParentName();
            x = diagram.getX();
            y = diagram.getY();
            height = diagram.getHeight();
            width = diagram.getWidth();

            JsonObject diagramJson = Json.createObjectBuilder()
                    .add(DIAGRAM_TYPE, diagramType)
                    .add(DIAGRAM_NAME, diagramName)
                    .add(DIAGRAM_PACKAGE, packageName)
                    .add(DIAGRAM_PARENTS, parentName)
                    .add(DIAGRAM_X, x)
                    .add(DIAGRAM_Y, y)
                    .add(DIAGRAM_HEIGHT, height)
                    .add(DIAGRAM_WIDTH, width)
                    .add(JSON_VARIABLES_ARRAY, makeVariablesJSON(diagram.getVariables()))
                    .add(JSON_METHODS_ARRAY, makeMethodsJSON(diagram.getMethods()))
                    .build();

            arrayBuilder.add(diagramJson);
        }

        JsonArray diagramsArray = arrayBuilder.build();

        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_DIAGRAMS, diagramsArray)
                .build();

        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();
    }

    public JsonArray makeVariablesJSON(ArrayList<UMLVariables> variables) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String variableName;
        String variableType;
        String variableAccess;
        Boolean variableStatic;
        for (Object node : variables) {
            UMLVariables variable = (UMLVariables) node;
            if (variable.getVariableName() != null) {
                variableName = variable.getVariableName();
            } else {
                variableName = "";
            }
            if (variable.getVariableType() != null) {
                variableType = variable.getVariableType();
            } else {
                variableType = "";
            }
            if (variable.getVariableAccess() != null) {
                variableAccess = variable.getVariableAccess();
            } else {
                variableAccess = "";
            }
            if (variable.getVariableStatic() != null) {
                variableStatic = variable.getVariableStatic();
            } else {
                variableStatic = false;
            }
            JsonObject variableJson = Json.createObjectBuilder()
                    .add(VARIABLE_NAME, variableName)
                    .add(VARIABLE_TYPE, variableType)
                    .add(VARIABLE_ACCESS, variableAccess)
                    .add(VARIABLE_STATIC, variableStatic)
                    .build();
            arrayBuilder.add(variableJson);
        }
        return arrayBuilder.build();
    }

    public JsonArray makeMethodsJSON(ArrayList<UMLMethods> methods) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String methodName;
        String methodReturn;
        String methodAccess;
        Boolean methodStatic;
        Boolean methodAbstract;
        for (Object node : methods) {
            UMLMethods method = (UMLMethods) node;
            if (method.getMethodName() != null) {
                methodName = method.getMethodName();
            } else {
                methodName = "";
            }
            if (method.getMethodReturn() != null) {
                methodReturn = method.getMethodReturn();
            } else {
                methodReturn = "";
            }
            if (method.getMethodAccess() != null) {
                methodAccess = method.getMethodAccess();
            } else {
                methodAccess = "";
            }
            if (method.getMethodStatic() != null) {
                methodStatic = method.getMethodStatic();
            } else {
                methodStatic = false;
            }
            if (method.getMethodAbstract() != null) {
                methodAbstract = method.getMethodAbstract();
            } else {
                methodAbstract = false;
            }
            JsonObject methodJson = Json.createObjectBuilder()
                    .add(METHODS_NAME, methodName)
                    .add(METHOD_RETURN, methodReturn)
                    .add(METHOD_ACCESS, methodAccess)
                    .add(METHOD_STATIC, methodStatic)
                    .add(METHOD_ABSTRACT, methodAbstract)
                    .add(METHOD_ARGUMENTS, makeMethodParametersJSON(method.getParameters()))
                    .build();
            arrayBuilder.add(methodJson);
        }
        return arrayBuilder.build();
    }

    public JsonArray makeMethodParametersJSON(ArrayList arguments) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String argumentName;
        String argumentType;
        for (Object node : arguments) {
            UMLVariables argument = (UMLVariables) node;
            if (argument.getVariableName() != null) {
                argumentName = argument.getVariableName();
            } else {
                argumentName = "";
            }
            if (argument.getVariableType() != null) {
                argumentType = argument.getVariableType();
            } else {
                argumentType = "";
            }
            JsonObject argumentJson = Json.createObjectBuilder()
                    .add(METHOD_ARGUMENT_NAME, argumentName)
                    .add(METHOD_ARGUMENT_TYPE, argumentType)
                    .build();
            arrayBuilder.add(argumentJson);
        }
        return arrayBuilder.build();
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager) data;
        JsonObject json = loadJSONFile(filePath);

        JsonArray jsonDiagramArray = json.getJsonArray(JSON_DIAGRAMS);

        for (int i = 0; i < jsonDiagramArray.size(); i++) {
            Diagram diagram;

            JsonObject jsonDiagram = jsonDiagramArray.getJsonObject(i);

            Diagram typeIdentifier = new Diagram();

            typeIdentifier.setObjectType(jsonDiagram.getString(DIAGRAM_TYPE));

            if (typeIdentifier.getObjectType().equals("class")) {
                diagram = new ClassDiagram();
            } else {
                diagram = new InterfaceDiagram();
            }

            diagram.setClassName(jsonDiagram.getString(DIAGRAM_NAME));
            diagram.setParents(jsonDiagram.getString(DIAGRAM_PARENTS));
            diagram.setPackageName(jsonDiagram.getString(DIAGRAM_PACKAGE));
            diagram.setX(jsonDiagram.getInt(DIAGRAM_X));
            diagram.setY(jsonDiagram.getInt(DIAGRAM_Y));
            diagram.setHEIGHT(jsonDiagram.getInt(DIAGRAM_HEIGHT));
            diagram.setWIDTH(jsonDiagram.getInt(DIAGRAM_WIDTH));

            JsonArray jsonVariablesArray = jsonDiagram.getJsonArray(JSON_VARIABLES_ARRAY);

            JsonArray jsonMethodsArray = jsonDiagram.getJsonArray(JSON_METHODS_ARRAY);

            for (int j = 0; j < jsonVariablesArray.size(); j++) {
                UMLVariables variables = new UMLVariables();
                JsonObject jsonVariable = jsonVariablesArray.getJsonObject(j);
                variables.setVariable(jsonVariable.getString(VARIABLE_NAME), jsonVariable.getString(VARIABLE_TYPE), jsonVariable.getString(VARIABLE_ACCESS), jsonVariable.getBoolean(VARIABLE_STATIC));
                diagram.addVariables(variables);
            }

            for (int k = 0; k < jsonMethodsArray.size(); k++) {
                UMLMethods method = new UMLMethods();
                JsonObject jsonMethod = jsonMethodsArray.getJsonObject(k);
                JsonArray jsonArgumentsArray = jsonMethod.getJsonArray(METHOD_ARGUMENTS);
                method.loadMethod(jsonMethod.getString(METHODS_NAME), jsonMethod.getString(METHOD_RETURN), jsonMethod.getString(METHOD_ACCESS), jsonMethod.getBoolean(METHOD_STATIC), jsonMethod.getBoolean(METHOD_ABSTRACT));
                if (jsonArgumentsArray != null) {
                    for (int l = 0; l < jsonArgumentsArray.size(); l++) {
                        JsonObject jsonMethodArgument = jsonArgumentsArray.getJsonObject(l);
                        method.loadActualParameters(jsonMethodArgument.getString(METHOD_ARGUMENT_NAME), jsonMethodArgument.getString(METHOD_ARGUMENT_TYPE), "", null);

                        if (l == 0) {
                            method.setArg1(jsonMethodArgument.getString(METHOD_ARGUMENT_TYPE));
                        }
                        if (l == 1) {
                            method.setArg2(jsonMethodArgument.getString(METHOD_ARGUMENT_TYPE));
                        }
                        if (l == 2) {
                            method.setArg3(jsonMethodArgument.getString(METHOD_ARGUMENT_TYPE));
                        }
                        if (l == 3) {
                            method.setArg4(jsonMethodArgument.getString(METHOD_ARGUMENT_TYPE));
                        }
                    }
                }

                diagram.addMethods(method);
            }
            dataManager.setToList(diagram);
        }
        dataManager.startLoadSequence();
    }

    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager) data;
        for (Object node : dataManager.returnList()) {
            String type = "dfsfsdd";
            Diagram theData = (Diagram) node;
            for (Object node2 : dataManager.returnList()) {
                Diagram theData2 = (Diagram) node2;
                if (theData.getParentName().equals(theData2.getClassName())) {
                    type = theData2.getObjectType();
                } else {
                    System.out.println("sme ull shti");
                }
            }
            PrintWriter writer = new PrintWriter("./work/" + theData.getClassName() + ".java");
            writer.println("package " + theData.getPackageName() + ";");
            writer.println();
            writer.print("public " + theData.getObjectType() + " " + theData.getClassName() + " ");
            if (type.equals("class") && !theData.getParentName().equals(theData.getClassName())) {
                writer.println("extends " + theData.getParentName() + " {");
            } else if (type.equals("interface") && !theData.getParentName().equals(theData.getClassName())) {
                writer.println("implements " + theData.getParentName() + " {");
            } else {
                writer.println("{");
            }
            writer.println();
            for (Object node2 : theData.getVariables()) {
                UMLVariables variable = (UMLVariables) node2;
                writer.print(variable.getVariableAccess() + " ");
                if (variable.getVariableStatic()) {
                    writer.print("static ");
                }
                writer.println(variable.getVariableType() + " " + variable.getVariableName() + ";");
            }
            writer.println();
            for (Object node3 : theData.getMethods()) {
                UMLMethods method = (UMLMethods) node3;
                if (method.getArgs() == 1) {
                    writer.print(method.getMethodAccess() + " ");
                    if (method.getMethodStatic()) {
                        writer.print("static ");
                    }
                    if (method.getMethodAbstract()) {
                        writer.print("abstract ");
                    }
                    writer.print(method.getMethodReturn() + " " + method.getMethodName() + "(" + method.getArg1() + " arg1)");
                    if (theData.getObjectType().equals("class")) {
                        writer.println(" {");
                        writer.println();
                        writer.println("   }");
                    }
                    if (theData.getObjectType().equals("interface")) {
                        writer.println(";");
                    }
                } else if (method.getArgs() == 2) {
                    writer.print(method.getMethodAccess() + " ");
                    if (method.getMethodStatic()) {
                        writer.print("static ");
                    }
                    if (method.getMethodAbstract()) {
                        writer.print("abstract ");
                    }
                    writer.print(method.getMethodReturn() + " " + method.getMethodName() + "(" + method.getArg1() + " arg1, " + method.getArg2() + " arg2)");
                    if (theData.getObjectType().equals("class")) {
                        writer.println(" {");
                        writer.println();
                        writer.println("   }");
                    }
                    if (theData.getObjectType().equals("interface")) {
                        writer.println(";");
                    }
                } else if (method.getArgs() == 3) {
                    writer.print(method.getMethodAccess() + " ");
                    if (method.getMethodStatic()) {
                        writer.print("static ");
                    }
                    if (method.getMethodAbstract()) {
                        writer.print("abstract ");
                    }
                    writer.print(method.getMethodReturn() + " " + method.getMethodName() + "(" + method.getArg1() + " arg1, " + method.getArg2() + " arg2, " + method.getArg3() + " arg3)");
                    if (theData.getObjectType().equals("class")) {
                        writer.println(" {");
                        writer.println();
                        writer.println("   }");
                    }
                    if (theData.getObjectType().equals("interface")) {
                        writer.println(";");
                    }
                } else if (method.getArgs() == 4) {
                    writer.print(method.getMethodAccess() + " ");
                    if (method.getMethodStatic()) {
                        writer.print("static ");
                    }
                    if (method.getMethodAbstract()) {
                        writer.print("abstract ");
                    }
                    writer.print(method.getMethodReturn() + " " + method.getMethodName() + "(" + method.getArg1() + " arg1, " + method.getArg2() + " arg2, " + method.getArg3() + " arg3, " + method.getArg4() + " arg4)");
                    if (theData.getObjectType().equals("class")) {
                        writer.println(" {");
                        writer.println();
                        writer.println("   }");
                    }
                    if (theData.getObjectType().equals("interface")) {
                        writer.println(";");
                    }
                } else {
                    writer.print(method.getMethodAccess() + " ");
                    if (method.getMethodStatic()) {
                        writer.print("static ");
                    }
                    if (method.getMethodAbstract()) {
                        writer.print("abstract ");
                    }
                    writer.print(method.getMethodReturn() + " " + method.getMethodName() + "()");
                    if (theData.getObjectType().equals("class")) {
                        writer.println(" {");
                        writer.println();
                        writer.println("   }");
                    }
                    if (theData.getObjectType().equals("interface")) {
                        writer.println(";");
                    }
                }
            }
            writer.println("}");
            writer.close();
        }
    }

    @Override
    public void exportPhoto(AppDataComponent data, File filePath) throws IOException {
        DataManager dataManager = (DataManager) data;
        if (filePath != null) {
            WritableImage image = dataManager.getDesignRenderer().snapshot(new SnapshotParameters(), null);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", filePath);
        }
    }
}

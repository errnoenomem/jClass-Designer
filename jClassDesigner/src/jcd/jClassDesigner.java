package jcd;

import java.util.Locale;
import saf.AppTemplate;
import saf.components.AppComponentsBuilder;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;
import saf.components.AppWorkspaceComponent;
import jcd.data.DataManager;
import jcd.file.FileManager;
import jcd.gui.Workspace;

public class jClassDesigner extends AppTemplate {

    @Override
    public AppComponentsBuilder makeAppBuilderHook() {
        return new AppComponentsBuilder() {

            @Override
            public AppDataComponent buildDataComponent() throws Exception {
                return new DataManager(jClassDesigner.this);
            }

            @Override
            public AppFileComponent buildFileComponent() throws Exception {
                return new FileManager();
            }

            @Override
            public AppWorkspaceComponent buildWorkspaceComponent() throws Exception {
                return new Workspace(jClassDesigner.this);
            }
        };
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
}

## Structurizr java client extension to build and merge C4 model

This repository is a convenient extension to an official [Structurizr java client library](https://github.com/structurizr/java) 
for the [Structurizr](https://structurizr.com/) Platform which helps build software architecture diagrams based upon the [C4 model](https://c4model.com/).
Repository extends Structurizr classes by useful convenient API to build model quickly using singleton approach for system/container/component. 
Also this repository helps to merge component models and diagrams from externally published sources. Usually component models are
autogenerated from different business service repositories.
Build produces extended library artifact which should be used by consumers who want to enrich their system's model by component level.

### Create Model and Views (Diagrams)

C4 Architecture model has got a single entry point to the library, it's `com.anmi.c4.EWorkspace` class.

There are **2 Scenarios** how to use this library:
1. We need to add container model/views and levels up
    * use current repository to define corresponded model/views
2. We need to add component model/views and levels down
    * go to a concrete service's git repository and define corresponded model/views there

These 2 scenarios produce models and views from different C4 levels. These C4 models and views are merged by the library before push to Cloud server in order to maintain consistency in a single Cloud space.

#### Scenario 1 steps
1. Create an instance of EWorkspace.kt providing EWorkspaceSpec with modeled Systems and Diagrams.
Model System/Container/Component implementing interfaces SystemModel, ESoftwareSystem, EContainer.

#### Scenario 2 steps
1. Add a test dependency to your service project `com.anmi.architecture:c4-model:1.+`
2. Define model and views(diagrams) in a separate classes, append that definitions to `EWorkspace` state and upload it to the proper Cloud space leveraging `com.anmi.c4.config.Config` enum.
Example in kotlin:
```kotlin
class ComponentModelUploader {
    companion object {
        fun main(args: Array<String>) {
            val workspace = EWorkspace(Config.valueOf(args[0])).workspace
            val model = SomeComponentModel(workspace.model)
            SomeComponentDiagram(workspace).draw()
            workspace.upload(model.targetContainer.name)
        }
    }
}
```

### Open the workspace in Structurizr
Once you've run your program to create and upload the workspace, you can now sign in to your Structurizr account, open the workspace from relevant Dashboard and see uploaded diagrams.
By default, Structurizr does not auto-layout your diagram elements. The diagram layout can be modified by dragging the elements around the diagram canvas in the diagram editor, and the layout saved using the "Save workspace" button. See [Structurizr - Help - Diagram layout](https://structurizr.com/help/diagram-layout) for more information. 

Please do not forget to unlock the "PROD" or "TEST" workspace if you locked it going manually into "Edit" mode. This potentially can break some pipeline.


#### Resources
C4 Cloud Space coordinates and addresses configuration live in `resource` folder per each env: prod = `config.properties`; test = `test-config.properties`; playground = `playground-config.properties`.

Use a `PLAYGORUND` workspace if you just want to play around with C4 and do whatever you want to practice skills in Structurizr tool.

If you just wanna play with C4, you can auto scan your source code repository and upload result to a `PLAYGORUND` space using just a single line library function call as 
`EWorkspace.scanComponentsToPlaygroundFrom("$CONTAINER_NAME", Set.of("$PACKAGE_NAME"));`

#### Pipelines and artifacts
- Each build from `master` branch publish client library jar, updates C4 `PROD` workspace in Cloud space as well as publish zip artifact with model.json and related png diagrams to internal Artifactory via `./gradlew publishArchModelToCloudProd artifactoryPublish`.
- Each build from non `master` branch publish client library jar, updates C4 `TEST` workspace in Cloud space as well as publish zip artifact with model.json and related png diagrams to internal Artifactory.


#### Notes:
1. Be careful with renaming diagram key and name of the elements. If you rename some element you will loose coordinates of that element at diagram layout.
2. Auto scan of components from a code base works only for Java source code compiled with Java 8. 
## C4 Kotlin DSL to model Software Architecture as a code

This repository is a convenient С4 Kotlin DSL over an official [Structurizr java client](https://github.com/structurizr/java)
for a [Structurizr](https://structurizr.com/) cloud service. This extension helps design
software architecture diagrams based upon
the [C4 model](https://c4model.com/). It wraps and abstracts away all the verbosity and complexity of an official
Structurizr java client thus allows to create model and views of a system architecture much faster and conveniently. 
Created here Kotlin DSL leverages
singleton approach for system/container/component elements.
Usually component models are
autogenerated from different business repositories so this library as well helps to merge autogenerated component models and diagrams from externally
published sources to an existing system context and containers modelled with this Kotlin DSL over Structurizr Java client.

## Getting started

### Include the package

#### For Maven

```xml

<project>
    <repository>
        <id>GithubPackages</id>
        <url>"https://maven.pkg.github.com/andrii-minchekov/structurizr-c4model-extension"</url>
    </repository>
    <dependencies>
        <dependency>
            <groupId>com.anmi.architecture</groupId>
            <artifactId>c4-model-extension</artifactId>
            <version>1.0.22</version>
        </dependency>
    </dependencies>
</project>
```

Add GithubPackages maven repository credentials to your settings.xml file

```xml

<servers>
    <server>
        <id>GithubPackages</id>
        <username>andrii-minchekov</username>
        <password>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</password>
    </server>
</servers>
```

#### For Gradle

```groovy
repositories {
    maven {
        url "https://maven.pkg.github.com/andrii-minchekov/structurizr-c4model-extension"
        credentials {
            username "andrii-minchekov"
            password "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"
        }
    }
}
dependencies {
    implementation 'com.anmi.architecture:c4-model-extension:1.0.21'
}
```

### Prerequisites

- JVM version 8 or later. To autogenerate C4 Component Model from the source code the requirement is to compile the source code only with Java 8. If
  Component Model is not autogenerated but created manually with DSL then any JVM version is fine to use.

### Complete Kotlin DSL example

As an example, the following Kotlin code can be used to create a software architecture model and an associated views that describe a user using a
software system with 2 containers and 1 external system dependency, based upon the C4 model.

![Context and Container Diagram](https://github.com/andrii-minchekov/structurizr-c4model-extension/raw/master/src/main/resources/readme/C4-context-container.png)

#### Model definition

```kotlin
class DemoSystemWorkspace(override val cfg: Config) : IWorkspace {
    override val spec = object : IWorkspace.ISpec {
        override val models = listOf(DemoSystemModel())
    }
}
class DemoSystemModel(override val system: ISystem = DEMO_SYSTEM) : SystemModel {
    override val context = emptyArray<ISystemRelationship>()
    override val containers = arrayOf(
        WEB_USER.uses(DEMO_SYSTEM, ORDER_SERVICE, "Uses"),
        ORDER_SERVICE.uses(INVENTORY_SERVICE, "Obtain inventory", REST),
        INVENTORY_SERVICE.uses(THIRD_PARTY_SYSTEM, "Check suppliers", REST)
    )
}
enum class DemoPerson(override val label: String, override val description: String, override val tags: Array<ITag> = emptyArray()) : IPerson {
    WEB_USER("User", "A user")
}
enum class DemoSystem(
    override val location: Location, override val label: String, override val description: String, override val tags: Array<ITag> = emptyArray()
) : ISystem {
    DEMO_SYSTEM(Location.Internal, "Demo System", "Demo System description", arrayOf(ITag.OTHER_SYSTEMS_TAG)),
    THIRD_PARTY_SYSTEM(Location.External, "Third Party System", "Third Party System API"),
}
enum class DemoContainer(
    override val system: ISystem, override val label: String, override val description: String,
    override val technologies: Array<Technology>, override val tags: Array<ITag> = emptyArray()
) : IContainer {
    INVENTORY_SERVICE(DEMO_SYSTEM, "Inventory Service", "Track and manage inventory ", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11)),
    ORDER_SERVICE(DEMO_SYSTEM, "Order Service", "Order Management Service", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11));
}
```

#### Model run and upload to Structurizr cloud service

To run and render this model example above the model needs to be uploaded to Structurizr cloud service as

```kotlin
DemoSystemWorkspace(ConfigInstance.TEST).upload()
```

So whole Flow is as simple as:
- model (describe) Software Architecture with simple Kotlin DSL
- run (instantiate) model elements
- upload to cloud service for
rendering.

#### Merge C4 models

When system context/containers vs container components are modelled in separate repositories there is a need to merge these separately defined Structurizr workspaces. It's possible to do using workspace extension method like `Workspace.upload(containerName: String)` which uses under the hood `Workspace.mergeInto(remoteWorkspace: Workspace, bypassContainer: IContainer)`. In the latter merge method the name of the components container need to be passed to signal merge algorithm to take precedence for components from this workspace over remote Structurizr workspace.

### Open the workspace in Structurizr to render C4 model views (diagrams)

Once model is run and uploaded you can now sign in to your [Structurizr](https://structurizr.com/) cloud account, open a workspace dashboard and see
uploaded diagrams.
By default, current Structurizr client auto-layout all diagrams. The diagram layout can be modified by dragging elements around the diagram
canvas in a diagram editor, then layout needs to be saved using the "Save workspace" button.
See [Structurizr - Help - Diagram layout](https://structurizr.com/help/diagram-layout) for more information.
If workspace is locked by going manually into "Edit" mode then client upload function won't work until workspace is saved and unlocked manually.

#### Structurizr workspace configurations at `resource` folder

C4 cloud service profile and API token configurations live in `resource` folder per each env: prod = `config.properties`; test
= `test-config.properties`.

If you just want to quickly play with C4 and see results, you can auto scan your source code repository and upload result to a `TEST` workspace using
just a single line code as
`ScannedWorkspace.scanComponentsToPlaygroundFrom("$CONTAINER_NAME", Set.of("$PACKAGE_NAME"));`

### Guideline how to design pipeline of a system architecture project repository

#### Gitlab example

After adding this C4 Kotlin DSL library as dependency to your System Architecture project repository and describing your System Architecture with C4 Kotlin
DSL it's very easy to produce all the needed architectural artefacts in pipeline process.

[Example]((https://github.com/andrii-minchekov/structurizr-c4model-extension/.gitlab-ci.yml)) of Gitlab pipeline configuration:

- Each build from `master` branch publish architecture model jar archive, updates C4 `PROD` workspace in Structurizr cloud space as well as publish
  zip artifact with
  model.json and related png diagrams to internal Artifactory via `./gradlew publishArchModelToCloudProd artifactoryPublish`.
- Each build from non `master` branch publish architecture model jar archive, updates C4 `TEST` workspace in Cloud space as well as publish zip
  artifact with
  model.json and related png diagrams to internal Artifactory.

### Notes:

1. Be careful with renaming diagram key and name of the elements. If you rename some element you will lose coordinates of that element at diagram
   layout.
2. Auto scan of components from a source code base works only for Java source code compiled with Java 8.

##### Tags

C4 Kotlin DSL, C4 model Kotlin DSL, Structurizr DSL Kotlin, Structurizr Kotlin DSL, merge C4 models, merge Structuruzr C4 models, merge Structurizr workspaces, merge already defined C4 system context and containers with autoscanned components, merge local and remote Structurizr workspace
package com.structurizr.analysis

import com.structurizr.model.Component
import com.structurizr.util.StringUtils
import org.slf4j.LoggerFactory

fun AbstractComponentFinderStrategy.afterFindComponents(componentsFound: Set<Component>) {
    findSupportingTypes(componentsFound)
    findDependencies()
}

private fun AbstractComponentFinderStrategy.findSupportingTypes(components: Set<Component>) {
    for (component in components) {
        for (codeElement in component.code) {
            val visibility = TypeUtils.getVisibility(this.typeRepository, codeElement.type)
            if (visibility != null) {
                codeElement.visibility = visibility.name
            }
            val category = TypeUtils.getCategory(typeRepository, codeElement.type)
            if (category != null) {
                codeElement.category = category.name
            }
        }
        for (strategy in supportingTypesStrategies) {
            for (type in strategy.findSupportingTypes(component)) {
                if (!isNestedClass(type) && componentFinder.container.getComponentOfType(type.canonicalName) == null) {
                    val codeElement = component.addSupportingType(type.canonicalName)
                    val visibility = TypeUtils.getVisibility(typeRepository, codeElement.type)
                    if (visibility != null) {
                        codeElement.visibility = visibility.name
                    }
                    val category = TypeUtils.getCategory(typeRepository, codeElement.type)
                    if (category != null) {
                        codeElement.category = category.name
                    }
                }
            }
        }
    }
}

private fun isNestedClass(type: Class<*>?): Boolean {
    return type != null && type.name.indexOf('$') > -1
}

private fun AbstractComponentFinderStrategy.findDependencies() {
    for (component in componentFinder.container.components) {
        for (codeElement in component.code) {
            addEfferentDependencies(component, codeElement.type, HashSet())
        }
    }
}

private fun AbstractComponentFinderStrategy.addEfferentDependencies(component: Component, type: String, typesVisited: MutableSet<String>) {
    typesVisited.add(type)
    for (referencedType in typeRepository.findReferencedTypes(type)) {
        try {
            if (!isNestedClass(referencedType)) {
                val referencedTypeName = referencedType.canonicalName
                if (!StringUtils.isNullOrEmpty(referencedTypeName)) {
                    val destinationComponent = componentFinder.container.getComponentOfType(referencedTypeName)
                            ?: componentFinder.container.getComponentWithName(referencedType.simpleName)
                    if (destinationComponent != null) {
                        if (component != destinationComponent) {
                            component.uses(destinationComponent, "")
                        }
                    } else if (!typesVisited.contains(referencedTypeName)) {
                        addEfferentDependencies(component, referencedTypeName, typesVisited)
                    }
                }
            }
        } catch (t: Throwable) {
            LoggerFactory.getLogger(AbstractComponentFinderStrategy::class.java).warn("Could not add efferent dependency", t)
        }
    }
}

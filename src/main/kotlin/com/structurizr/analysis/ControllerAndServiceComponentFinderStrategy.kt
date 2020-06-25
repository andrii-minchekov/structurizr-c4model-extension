package com.structurizr.analysis

import com.structurizr.model.Component
import com.structurizr.model.Container
import java.util.*

class ControllerAndServiceComponentFinderStrategy @JvmOverloads constructor(
        private val duplicateStrategy: DuplicateComponentStrategy = DuplicateComponentStrategy { _, _, _, _, _ -> null },
        supportingTypesStrategies: Array<SupportingTypesStrategy> = arrayOf(),
        private vararg val componentFinderStrategies: AbstractSpringComponentFinderStrategy = arrayOf(SpringRestControllerComponentFinderStrategy(), SpringServiceComponentFinderStrategy())) : AbstractComponentFinderStrategy(*supportingTypesStrategies) {

    override fun beforeFindComponents() {
        super.beforeFindComponents()

        for (componentFinderStrategy in componentFinderStrategies) {
            componentFinderStrategy.setIncludePublicTypesOnly(true)
            componentFinderStrategy.setComponentFinder(getComponentFinder())
            supportingTypesStrategies.forEach { componentFinderStrategy.addSupportingTypesStrategy(it) }
            componentFinderStrategy.duplicateComponentStrategy = duplicateStrategy
            componentFinderStrategy.beforeFindComponents()
        }
    }

    override fun doFindComponents(): Set<Component> {
        val components = HashSet<Component>()

        for (componentFinderStrategy in componentFinderStrategies) {
            components.addAll(componentFinderStrategy.findComponents())
        }
        return components
    }

    override fun addComponent(container: Container, name: String, type: String, description: String, technology: String): Component? {
        return if (container.getComponentWithName(name) == null) {
            container.addComponent(name, type, description, technology)
        } else {
            null
        }
    }
}
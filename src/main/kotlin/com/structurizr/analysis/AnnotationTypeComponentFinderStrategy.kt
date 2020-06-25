package com.structurizr.analysis

import com.google.common.base.Predicate
import com.structurizr.model.Component
import com.structurizr.model.Container
import org.reflections.ReflectionUtils
import org.slf4j.LoggerFactory

class AnnotationTypeComponentFinderStrategy constructor(classAnnotation: Class<out Annotation>) : TypeMatcherComponentFinderStrategy((AnnotationTypeMatcher(classAnnotation, "", ""))) {
    private val log = LoggerFactory.getLogger(AnnotationTypeComponentFinderStrategy::class.java)

    init {
        this.setDuplicateComponentStrategy { _, _, _, _, _ -> null }
        this.addSupportingTypesStrategy(ReferencedTypesSupportingTypesStrategy())
    }

    override fun addComponent(container: Container, name: String, type: String, description: String?, technology: String?): Component? {
        var alignedName = name
        ReflectionUtils.includeObject = false
        val clazz = ReflectionUtils.forName(type)
        val interfaces = ReflectionUtils.getAllSuperTypes(clazz, Predicate { it!!.isInterface && it.simpleName != "Function" })
        if (interfaces.size == 1) {
            alignedName = interfaces.iterator().next().simpleName
        }
        val component = super.addComponent(container, alignedName, type, description, technology)
        if (component != null) {
            componentsFound.add(component)
        }
        return component
    }

    override fun afterFindComponents() {
        afterFindComponents(componentsFound)
    }
}
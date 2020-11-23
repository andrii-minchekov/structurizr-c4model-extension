package com.anmi.c4.model.element

import com.structurizr.model.Model
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getPerson
import com.structurizr.model.getSystem

sealed class ISystemRelationship : (Model) -> Set<SoftwareSystem> {

    class System2System(private val source: ISystem, private val target: ISystem, private val description: String, private val technologies: Array<Technology>) : ISystemRelationship() {
        override fun invoke(model: Model): Set<SoftwareSystem> {
            val sourceSystem: SoftwareSystem = model.getSystem(source)
            val targetSystem = model.getSystem(target)
            sourceSystem.uses(targetSystem, description, Technology.stringify(technologies))
            return setOf(sourceSystem, targetSystem)
        }
    }

    class Person2System(private val source: IPerson, private val target: ISystem, private val description: String, private val technologies: Array<Technology>) : ISystemRelationship() {
        override fun invoke(model: Model): Set<SoftwareSystem> {
            val person = model.getPerson(source)
            val targetSystem = model.getSystem(target)
            person.uses(targetSystem, description, Technology.stringify(technologies))
            return setOf(targetSystem)
        }
    }
}
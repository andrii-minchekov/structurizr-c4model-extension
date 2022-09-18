package com.anmi.c4.model.element

import com.structurizr.model.Container
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.addContainer
import com.structurizr.model.getContainer
import com.structurizr.model.getPerson
import com.structurizr.model.getSystem

sealed class IContainerRelationship : (SoftwareSystem) -> Set<Container> {
    abstract override fun invoke(system: SoftwareSystem): Set<Container>

    class Standalone(private val container: IContainer) : IContainerRelationship() {
        override fun invoke(system: SoftwareSystem): Set<Container> {
            return setOf(system.addContainer(container))
        }
    }

    class Container2Container(private val source: IContainer, private val target: IContainer,
                              private val desc: String, private val technologies: Array<Technology>) : IContainerRelationship() {
        override fun invoke(system: SoftwareSystem): Set<Container> {
            val otherContainer = system.getContainer(target)
            return setOf(
                    system.getContainer(source).apply {
                        uses(otherContainer, desc, Technology.stringify(technologies))
                    },
                    otherContainer
            )
        }
    }

    class Container2System(private val source: IContainer, private val target: ISystem,
                           private val desc: String, private val technologies: Array<Technology>) : IContainerRelationship() {
        override fun invoke(system: SoftwareSystem): Set<Container> {
            return setOf(
                    system.getContainer(source).apply {
                        system.uses(system.model.getSystem(target), desc, Technology.stringify(technologies))
                        this.uses(system.model.getSystem(target), desc, Technology.stringify(technologies))
                    })
        }
    }

    class SystemToContainer(private val source: ISystem, private val targetSystem: ISystem, private val targetContainer: IContainer,
                            private val desc: String, private val technologies: Array<Technology>) : IContainerRelationship() {
        override fun invoke(system: SoftwareSystem): Set<Container> {
            val container = system.model.getSystem(targetSystem).getContainer(targetContainer)
            system.model.getSystem(source).uses(container, desc, Technology.stringify(technologies))
            return setOf(container)
        }
    }

    class Person2Container(private val person: IPerson, private val otherSystem: ISystem, private val otherContainer: IContainer,
                           private val relDescription: String, private val technologies: Array<Technology>) : IContainerRelationship() {
        override fun invoke(system: SoftwareSystem): Set<Container> {
            return system.model.getPerson(person).run {
                val container = system.model.getSystem(otherSystem).getContainer(otherContainer)
                this.uses(system.model.getSystem(otherSystem), relDescription, Technology.stringify(technologies))
                this.uses(container, relDescription, Technology.stringify(technologies))
                setOf(container)
            }
        }
    }
}



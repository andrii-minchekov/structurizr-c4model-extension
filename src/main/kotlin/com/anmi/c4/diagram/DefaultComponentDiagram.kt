package com.anmi.c4.diagram

import com.structurizr.model.Container
import com.structurizr.model.SoftwareSystem

abstract class DefaultComponentDiagram(override val targetSystem: SoftwareSystem, override val targetContainer: Container) : ComponentDiagram
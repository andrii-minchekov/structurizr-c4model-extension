package com.anmi.c4.diagram

import com.anmi.c4.model.element.IContainer
import com.anmi.c4.model.element.ISystem

abstract class DefaultComponentDiagram(
    override val targetSystem: ISystem,
    override val targetContainer: IContainer,
    override val autoLayoutEnabled: Boolean = true
) : ComponentDiagram
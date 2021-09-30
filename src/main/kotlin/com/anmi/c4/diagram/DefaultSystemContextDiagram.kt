package com.anmi.c4.diagram

import com.anmi.c4.model.element.ISystem

abstract class DefaultSystemContextDiagram(override val targetSystem: ISystem, override val autoLayoutEnabled: Boolean = true) : SystemContextDiagram
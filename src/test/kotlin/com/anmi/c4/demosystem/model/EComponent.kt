package com.anmi.c4.demosystem.model

import com.anmi.c4.model.element.IComponent
import com.anmi.c4.model.element.IContainer
import com.anmi.c4.model.element.ITag

enum class EComponent(override val container: IContainer, override val label: String, override val description: String, override val tags: Array<ITag> = emptyArray()) : IComponent {
    ORDER_CONTROLLER(EContainer.ORDER_SERVICE, "Order Controller", "Rest adapter to order domain"),
    ORDER_SERVICE(EContainer.ORDER_SERVICE, "Order Service", "Domain Service for order management")
}
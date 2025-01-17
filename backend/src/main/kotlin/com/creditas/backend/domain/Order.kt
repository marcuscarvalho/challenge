package com.creditas.backend.domain

import com.creditas.backend.domain.exception.EmptyOrderException
import com.creditas.backend.domain.exception.OrderAlreadyPaidException
import com.creditas.backend.domain.exception.ProductAlreadyAddedException
import java.util.*

class Order(val customer: Customer, val address: Address) {
    val items = mutableListOf<OrderItem>()
    var closedAt: Date? = null
        private set
    var payment: Payment? = null
        private set
    val totalAmount
        get() = items.sumByDouble { it.total }

    fun addProduct(product: Product, quantity: Int) {
        var productAlreadyAdded = items.any { it.product == product }
        if (productAlreadyAdded)
            throw ProductAlreadyAddedException("The product have already been added. Change the amount if you want more.")

        items.add(OrderItem(product, quantity))
    }

    fun pay(method: PaymentMethod) {
        if (payment != null)
            throw OrderAlreadyPaidException("The order has already been paid!")

        if (items.count() == 0)
            throw EmptyOrderException("Empty order can not be paid!")

        payment = Payment(this, method)

        close()
    }

    private fun close() {
        closedAt = Date()
    }
}
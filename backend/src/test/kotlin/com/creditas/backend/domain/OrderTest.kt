package com.creditas.backend.domain

import com.creditas.backend.domain.exception.EmptyOrderException
import com.creditas.backend.domain.exception.OrderAlreadyPaidException
import com.creditas.backend.domain.exception.ProductAlreadyAddedException
import kotlin.test.Test

class OrderTest {

    @Test(expected = ProductAlreadyAddedException::class)
    fun testProductAlreadyAdded() {
        val order = Order(Customer("marcus.geekcode@gmail.com"), Address())
        val product = Product("Iron Man", ProductType.DIGITAL, 20.00)
        order.addProduct(product, 1)
        order.addProduct(product, 1)
    }

    @Test(expected = OrderAlreadyPaidException::class)
    fun testOrderHasAlreadyBeenPaid() {
        val order = Order(Customer("marcus.geekcode@gmail.com"), Address())
        val product = Product("Iron Man", ProductType.DIGITAL, 20.00)
        order.addProduct(product, 1)
        order.pay(CreditCard("123"))
        order.pay(CreditCard("123"))
    }

    @Test(expected = EmptyOrderException::class)
    fun testPaymentOfEmptyOrder() {
        val order = Order(Customer("marcus.geekcode@gmail.com"), Address())
        order.pay(CreditCard("123"))
    }

}
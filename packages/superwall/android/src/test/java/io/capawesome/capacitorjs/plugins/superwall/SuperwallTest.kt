package io.capawesome.capacitorjs.plugins.superwall

import io.capawesome.capacitorjs.plugins.superwall.classes.CustomExceptions
import io.capawesome.capacitorjs.plugins.superwall.interfaces.EmptyCallback
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class SuperwallTest {
    @Test
    fun restorePurchasesBeforeConfigureFails() {
        val implementation = Superwall(SuperwallPlugin())
        var completionCalled = false

        implementation.restorePurchases(object : EmptyCallback {
            override fun success() {
                fail("Restore should require configure()")
            }

            override fun error(exception: Exception) {
                completionCalled = true
                assertSame(CustomExceptions.NOT_CONFIGURED, exception)
            }
        })

        assertTrue(completionCalled)
    }
}

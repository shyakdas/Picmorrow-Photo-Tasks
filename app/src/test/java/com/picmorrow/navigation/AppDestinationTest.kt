package com.picmorrow.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class AppDestinationTest {
    @Test
    fun routes_areCorrect() {
        assertEquals("welcome", AppDestination.Welcome.route)
        assertEquals("home", AppDestination.Home.route)
    }
}

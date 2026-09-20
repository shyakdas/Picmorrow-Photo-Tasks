package com.picmorrow.navigation

import app.cash.paparazzi.Paparazzi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AppDestinationTest {
    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun routes_areCorrect() {
        assertEquals("welcome", AppDestination.Welcome.route)
        assertEquals("home", AppDestination.Home.route)
        assertEquals("camera", AppDestination.Camera.route)
        assertEquals(
            "new-photo-task?photoPath={photoPath}&category={category}",
            AppDestination.NewPhotoTask.route,
        )
    }

    @Test
    fun newPhotoTaskRoute_encodesPhotoPathAndCategory() {
        assertEquals(
            "new-photo-task?photoPath=%2Fdata%2Fuser%2F0%2Fphoto%20one.jpg&category=Parking%20%26%20More",
            AppDestination.NewPhotoTask.route("/data/user/0/photo one.jpg", "Parking & More"),
        )
    }
}

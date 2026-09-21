package com.picmorrow.feature.phototasks.presentation.common.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoTaskCategoryTest {
    @Test
    fun categoryNamesRemainCompatibleWithStoredTasks() {
        assertEquals(
            listOf("Parking", "Buy", "Collect", "Remember"),
            PhotoTaskCategory.entries.map(PhotoTaskCategory::name),
        )
    }
}

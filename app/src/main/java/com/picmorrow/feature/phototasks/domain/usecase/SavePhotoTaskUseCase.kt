package com.picmorrow.feature.phototasks.domain.usecase

import com.picmorrow.feature.phototasks.domain.model.PhotoTaskDraft
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskRepository
import java.io.File

class SavePhotoTaskUseCase(private val repository: PhotoTaskRepository) {
    suspend operator fun invoke(draft: PhotoTaskDraft): Long {
        require(draft.title.isNotBlank()) { "A title is required" }
        require(File(draft.photoPath).isFile) { "The captured photo is unavailable" }

        return repository.savePhotoTask(
            draft.copy(
                title = draft.title.trim(),
                notes = draft.notes.trim(),
            ),
        )
    }
}

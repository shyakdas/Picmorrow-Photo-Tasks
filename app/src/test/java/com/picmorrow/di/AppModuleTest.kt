package com.picmorrow.di

import android.content.Context
import androidx.work.WorkerParameters
import com.picmorrow.feature.taskdetails.presentation.TaskDetailsViewModel
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.definition
import org.koin.test.verify.injectedParameters
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class AppModuleTest {
    @Test
    fun dependencyGraphIsComplete() {
        appModule.verify(
            extraTypes = listOf(
                Context::class,
                WorkerParameters::class,
            ),
            injections = injectedParameters(
                definition<TaskDetailsViewModel>(Long::class),
            ),
        )
    }
}

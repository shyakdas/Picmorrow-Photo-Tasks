package com.picmorrow.di

import com.picmorrow.MainViewModel
import com.picmorrow.core.data.local.AppDatabase
import com.picmorrow.core.data.local.createAppDatabase
import com.picmorrow.feature.camera.data.CameraPermissionRequestTracker
import com.picmorrow.feature.camera.presentation.CameraViewModel
import com.picmorrow.feature.home.presentation.HomeViewModel
import com.picmorrow.feature.onboarding.presentation.WelcomeViewModel
import com.picmorrow.feature.phototasks.data.reminder.AndroidTaskReminderNotifier
import com.picmorrow.feature.phototasks.data.reminder.AndroidTaskReminderScheduler
import com.picmorrow.feature.phototasks.data.reminder.TaskReminderDelivery
import com.picmorrow.feature.phototasks.data.reminder.TaskReminderNotificationPublisher
import com.picmorrow.feature.phototasks.data.reminder.TaskReminderWorker
import com.picmorrow.feature.phototasks.data.repository.PhotoTaskRepositoryImpl
import com.picmorrow.feature.phototasks.domain.reminder.TaskReminderScheduler
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskDetailsRepository
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskListingRepository
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskRepository
import com.picmorrow.feature.phototasks.domain.repository.PhotoTaskStatusRepository
import com.picmorrow.feature.phototasks.domain.usecase.HasPhotoTasksUseCase
import com.picmorrow.feature.phototasks.domain.usecase.SavePhotoTaskUseCase
import com.picmorrow.feature.phototasks.presentation.NewPhotoTaskViewModel
import com.picmorrow.feature.settings.data.local.ThemePreferencesDataSource
import com.picmorrow.feature.settings.data.repository.ThemePreferenceRepositoryImpl
import com.picmorrow.feature.settings.domain.repository.ThemePreferenceRepository
import com.picmorrow.feature.settings.domain.usecase.ObserveThemeModeUseCase
import com.picmorrow.feature.settings.domain.usecase.SetThemeModeUseCase
import com.picmorrow.feature.settings.presentation.ThemeSettingsViewModel
import com.picmorrow.feature.taskdetails.data.AndroidTaskPhotoStorage
import com.picmorrow.feature.taskdetails.domain.TaskPhotoStorage
import com.picmorrow.feature.taskdetails.presentation.TaskDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { createAppDatabase(androidContext()) }
    single { get<AppDatabase>().photoTaskDao() }
    single { CameraPermissionRequestTracker(androidContext()) }

    single<TaskReminderScheduler> { AndroidTaskReminderScheduler(androidContext()) }
    single<TaskReminderNotificationPublisher> { AndroidTaskReminderNotifier(androidContext()) }
    singleOf(::TaskReminderDelivery)
    workerOf(::TaskReminderWorker)

    singleOf(::PhotoTaskRepositoryImpl)
    single<PhotoTaskRepository> { get<PhotoTaskRepositoryImpl>() }
    single<PhotoTaskListingRepository> { get<PhotoTaskRepositoryImpl>() }
    single<PhotoTaskStatusRepository> { get<PhotoTaskRepositoryImpl>() }
    single<PhotoTaskDetailsRepository> { get<PhotoTaskRepositoryImpl>() }

    single { ThemePreferencesDataSource(androidContext()) }
    singleOf(::ThemePreferenceRepositoryImpl)
    single<ThemePreferenceRepository> { get<ThemePreferenceRepositoryImpl>() }
    single<TaskPhotoStorage> { AndroidTaskPhotoStorage(androidContext()) }

    factoryOf(::HasPhotoTasksUseCase)
    factoryOf(::SavePhotoTaskUseCase)
    factoryOf(::ObserveThemeModeUseCase)
    factoryOf(::SetThemeModeUseCase)

    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::NewPhotoTaskViewModel)
    viewModelOf(::ThemeSettingsViewModel)
    viewModelOf(::CameraViewModel)
    viewModelOf(::WelcomeViewModel)
    viewModel { (taskId: Long) -> TaskDetailsViewModel(taskId, get(), get()) }
}

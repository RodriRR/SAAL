package com.saal.util.koin

import com.saal.data.repository.DatabaseRepository
import com.saal.data.repository.DatabaseRepositoryImpl
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
    single<DatabaseRepository> {
        DatabaseRepositoryImpl(
            androidApplication()
        )
    }

    // MyViewModel ViewModel
    viewModel { MainViewModel(get()) }
}
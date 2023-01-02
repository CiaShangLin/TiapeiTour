package com.shang.taipeitour.di

import com.shang.taipeitour.repository.MainRepository
import com.shang.taipeitour.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(get())
    }
    single {
        MainRepository(get())
    }
}
package com.example.words.di

import com.example.words.ui.home.HomeViewModel
import com.example.words.ui.update.UpdateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val updateModule = module {
    viewModel {
        UpdateViewModel()
    }
}
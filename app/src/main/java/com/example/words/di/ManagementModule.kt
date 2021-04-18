package com.example.words.di

import com.example.words.ui.management.ManagementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val managementModule = module {
    viewModel {
        ManagementViewModel()
    }
}
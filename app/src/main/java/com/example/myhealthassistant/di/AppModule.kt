package com.example.myhealthassistant.di

import com.example.myhealthassistant.data.repository.AuthRepositoryImpl
import com.example.myhealthassistant.data.repository.ConsentRepositoryImpl
import com.example.myhealthassistant.data.repository.FileRepositoryImpl
import com.example.myhealthassistant.data.service.EncryptionService
import com.example.myhealthassistant.domain.repository.AuthRepository
import com.example.myhealthassistant.domain.repository.ConsentRepository
import com.example.myhealthassistant.domain.repository.FileRepository
import com.example.myhealthassistant.domain.usecase.LoginUseCase
import com.example.myhealthassistant.domain.usecase.consent.GetConsentUseCase
import com.example.myhealthassistant.domain.usecase.consent.GrantConsentUseCase
import com.example.myhealthassistant.domain.usecase.consent.RevokeConsentUseCase
import com.example.myhealthassistant.domain.usecase.filemanagement.GetFilesUseCase
import com.example.myhealthassistant.domain.usecase.filemanagement.SyncFilesUseCase
import com.example.myhealthassistant.domain.usecase.filemanagement.UploadFileUseCase
import com.example.myhealthassistant.presentation.consent.consent.ConsentViewModel
import com.example.myhealthassistant.presentation.filemanagement.filemanagement.FileManagementViewModel
import com.example.myhealthassistant.presentation.login.login.LoginViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<AuthRepository> { AuthRepositoryImpl() }
    single<ConsentRepository> { ConsentRepositoryImpl() }
    single<FileRepository> { FileRepositoryImpl() }

    // Services
    single { EncryptionService() }

    // Use Cases
    factory { LoginUseCase(get()) }
    factory { GetConsentUseCase(get()) }
    factory { GrantConsentUseCase(get()) }
    factory { RevokeConsentUseCase(get()) }
    factory { UploadFileUseCase(get()) }
    factory { GetFilesUseCase(get()) }
    factory { SyncFilesUseCase(get()) }

    // ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { ConsentViewModel(get(), get(), get()) }
    viewModel { FileManagementViewModel(get(), get(), get()) }
}
package com.example.sekvenia

import com.example.sekvenia.data.datasource.AmazonawsRemoteDataSource
import com.example.sekvenia.data.datasource.provideAmazonawsService
import com.example.sekvenia.data.remote.network.INetwork
import com.example.sekvenia.data.remote.network.Network
import com.example.sekvenia.data.remote.network.SupportInterceptor
import com.example.sekvenia.data.repo.HomeRepo
import com.example.sekvenia.domain.repo.IHomeRepo
import com.example.sekvenia.ui.home.HomePresenter
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule = module {
    single { SupportInterceptor() }
    single<INetwork> { Network(get()) }
    single { provideAmazonawsService(get()) }
}

val remoteModule = module {
    single { AmazonawsRemoteDataSource(get()) }
}

val repositoryModule = module {
    single<IHomeRepo> { HomeRepo(get()) }
    single { HomePresenter(get()) }
}

fun getModules(): List<Module> {
    return listOf(networkModule, remoteModule, repositoryModule)
}
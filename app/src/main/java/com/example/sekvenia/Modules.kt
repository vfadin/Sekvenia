package com.example.sekvenia

import com.test.project.data.remote.network.INetwork
import com.test.project.data.remote.network.Network
import com.test.project.data.remote.network.SupportInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule = module {
    single { SupportInterceptor() }
    single<INetwork> { Network(get()) }
}

val remoteModule = module {
}

val repositoryModule = module {
}

fun getModules(): List<Module> {
    return listOf(networkModule, remoteModule, repositoryModule)
}
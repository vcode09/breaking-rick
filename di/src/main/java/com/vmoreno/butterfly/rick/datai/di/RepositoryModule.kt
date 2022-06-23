package com.vmoreno.butterfly.rick.datai.di

import com.vmoreno.butterfly.rick.data.repository.IRepositoryImp
import com.vmoreno.butterfly.rick.usecases.repository.IRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<IRepository> {
        IRepositoryImp(get(), get())
    }
}

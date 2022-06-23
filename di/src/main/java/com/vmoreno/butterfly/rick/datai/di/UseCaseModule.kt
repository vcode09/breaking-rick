package com.vmoreno.butterfly.rick.datai.di

import com.vmoreno.butterfly.rick.usecases.breakingbad.UseCase
import com.vmoreno.butterfly.rick.usecases.breakingbad.UseCaseImp
import org.koin.dsl.module

val useCaseModule = module {
    factory<UseCase> {
        UseCaseImp(get())
    }
}
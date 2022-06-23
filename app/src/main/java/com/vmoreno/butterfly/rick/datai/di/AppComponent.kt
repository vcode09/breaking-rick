package com.vmoreno.butterfly.rick.datai.di


object AppComponent {
    val appComponent = listOf(
        networkModule,
        repositoryModule,
        useCaseModule,
        viewModelModule,
        dataSourceModule,
        messageExceptionModule
    )
}
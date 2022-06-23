package com.vmoreno.butterfly.rick.datai.di

import com.vmoreno.butterfly.rick.modules.character.viewmodel.CharacterViewModel
import com.vmoreno.butterfly.rick.utils.CoroutineContextProvider
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CharacterViewModel(get(), get(), get())
    }
    factory { CoroutineContextProvider() }
}

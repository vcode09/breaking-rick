package com.vmoreno.butterfly.rick.datai.di

import com.vmoreno.butterfly.rick.domain.messagesexception.*
import com.vmoreno.butterfly.rick.messageexception.MessageExceptionDetailResources
import com.vmoreno.butterfly.rick.messageexception.NetworkMessageExceptionDetailResources
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val messageExceptionModule = module {
    factory<MessageException> {
        MessageExceptionHandler(
            MessageExceptionDetailResources(androidContext().resources),
            ServerMessageException(MessageExceptionDetailResources(androidContext().resources)),
            NetworkMessageException(NetworkMessageExceptionDetailResources(androidContext().resources)),
            UnknownMessageException(MessageExceptionDetailResources(androidContext().resources)),
        )
    }
}
package com.vmoreno.butterfly.rick.data.repository

import com.vmoreno.butterfly.rick.data.database.Database
import com.vmoreno.butterfly.rick.data.database.RoomDataSource
import com.vmoreno.butterfly.rick.data.datasource.source.RemoteDataSource
import com.vmoreno.butterfly.rick.data.network.Api
import com.vmoreno.butterfly.rick.domain.exceptions.onSuccess
import com.vmoreno.butterfly.rick.usecases.repository.IRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IRepositoryImpTest {

    @Mock
    lateinit var DB: Database

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var roomDataSource: RoomDataSource

    @Mock
    lateinit var api: Api
    private lateinit var iRepository: IRepository
    private lateinit var sut: IRepositoryImp

    companion object {
        const val offset = 50
        const val limit = 20
    }

    @Before
    fun setUp() {
        roomDataSource = RoomDataSource(DB)
        remoteDataSource = RemoteDataSource(api)
        sut = IRepositoryImp(remoteDataSource, roomDataSource)
        iRepository = IRepositoryImp(remoteDataSource, roomDataSource)
    }

    @Test
    fun charactersAsync_parameters_called() {
        runBlocking {
            //given
            given(remoteDataSource.api.charactersAsync(offset, limit)).willReturn(
                listOf(mockedDto)
            )
            // when
            sut.getCharacters(offset, limit).onSuccess {
                // then
                assertEquals(listOf(mockedEntity), it)
            }
        }
    }
}
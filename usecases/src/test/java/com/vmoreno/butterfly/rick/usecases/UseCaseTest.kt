package com.vmoreno.butterfly.rick.usecases

import com.vmoreno.butterfly.rick.domain.exceptions.ErrorResponse
import com.vmoreno.butterfly.rick.domain.exceptions.onError
import com.vmoreno.butterfly.rick.domain.exceptions.resultHandlerOf
import com.vmoreno.butterfly.rick.usecases.breakingbad.UseCaseImp
import com.vmoreno.butterfly.rick.usecases.repository.IRepository
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {
    @Mock
    lateinit var iRepository: IRepository

    private lateinit var sut: UseCaseImp

    companion object {
        const val offset = 50
        const val limit = 20
    }

    @Before
    fun setup() {
        sut = UseCaseImp(iRepository)
    }

    @Test
    fun getCharacter_query_assertEquals() {
        // given
        runBlocking {
            val listEntity = resultHandlerOf { listOf(mockedEntity) }
            given(iRepository.getCharacters(offset, limit))
                .willReturn(listEntity)
            // when
            val result = sut.getCharacters(offset, limit)
            //then
            assertEquals(listEntity, result)
        }
    }

    @Test
    fun getCharacter_query_Error() {
        // given
        val error = Throwable()
        runBlocking {
            given(iRepository.getCharacters(offset, limit))
                .willReturn(ErrorResponse(error))
            //when
            val result = sut.getCharacters(offset, limit).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }
}
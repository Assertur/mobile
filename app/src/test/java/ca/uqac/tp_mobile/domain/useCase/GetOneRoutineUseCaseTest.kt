package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.fake.data.FakeDAO
import org.junit.jupiter.api.assertThrows
import ca.uqac.tp_mobile.utils.RoutineException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetOneRoutineUseCaseTest {

    lateinit var getOneRoutineUseCase: GetOneRoutineUseCase
    var dao = FakeDAO()

    @Before
    fun setUp () {
        dao.clear()
        getOneRoutineUseCase = GetOneRoutineUseCase(dao)
    }

    @Test
    fun `should throw error if id doesn't exist`() {
        assertThrows<RoutineException> {
            runBlocking {
                getOneRoutineUseCase(
                    0
                )
            }
        }
    }
    @Test
    fun `should return the routine if id is correct`() {
        // Arrange
        val routineToAdd =  Routine(
            id = 0,
            title = "title test",
            description = "routine test",
            day = listOf(1,2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        runBlocking { dao.insertRoutine(routineToAdd) }

        // Act
         val res = runBlocking {
            getOneRoutineUseCase(
                0
            )
        }

        // Assert {
        assertEquals(res, routineToAdd)
    }
}
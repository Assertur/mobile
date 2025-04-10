package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.fake.data.FakeDAO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetRoutinesUseCaseTest {

    lateinit var getRoutinesUseCase: GetRoutinesUseCase
    var dao = FakeDAO()

    @Before
    fun setUp () {
        dao.clear()
        getRoutinesUseCase = GetRoutinesUseCase(dao)
    }

    @Test
    fun `should return the list of routines`() {
        // Arrange
        val routineToAdd1 = Routine(
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
        val routineToAdd2 = Routine(
            id = 1,
            title = "new title test",
            description = "routine test",
            day = listOf(1,2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )
        runBlocking { dao.insertRoutine(routineToAdd1) }
        runBlocking { dao.insertRoutine(routineToAdd2) }

        // Act

        val res = runBlocking {
            getRoutinesUseCase()
        }


        // Assert
        val listres = runBlocking { res.first() }
        assertEquals(listres, listOf(routineToAdd1,routineToAdd2))
    }
}
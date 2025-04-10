package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.fake.data.FakeDAO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class DeleteRoutineUseCaseTest {
    lateinit var deleteRoutineUseCase: DeleteRoutineUseCase
    var dao = FakeDAO()

    @Before
    fun setUp () {
        deleteRoutineUseCase = DeleteRoutineUseCase(dao)
    }

    @Test
    fun `should reject if the routine doesn't exist`() {
        // Arrange
        val routine1 = Routine(
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

        runBlocking { dao.insertRoutine(routine1) }

        val routine2 = Routine(
            id = 1,
            title = "",
            description = "routine test",
            day = listOf(1,2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        // Act
        runBlocking { deleteRoutineUseCase(routine2) }

        // Assert
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(1, routines.size)
    }

    @Test
    fun `routine should be deleted if the it exists`() {
        // Arrange
        val routine1 = Routine(
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

        runBlocking { dao.insertRoutine(routine1) }

        val routine2 = Routine(
            id = 0,
            title = "",
            description = "routine test",
            day = listOf(1,2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        // Act
        runBlocking { deleteRoutineUseCase(routine2) }

        // Assert
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(0, routines.size)
    }
}
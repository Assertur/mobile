package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.fake.data.FakeDAO
import ca.uqac.tp_mobile.utils.RoutineException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class UpsertRoutineUseCaseTest {

    lateinit var upsertRoutineUseCase: UpsertRoutineUseCase
    var dao = FakeDAO()

    @Before
    fun setUp() {
        dao.clear()
        upsertRoutineUseCase = UpsertRoutineUseCase(dao)
    }

    @Test
    fun `routine should not be added if title is empty`() {
        val routine = Routine(
            title = "",
            description = "description ok",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location ok",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        assertThrows<RoutineException> { runBlocking { upsertRoutineUseCase(routine) } }
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(0, routines.size)
    }

    @Test
    fun `routine should be added if description is empty`() {
        // Arrange
        val routine = Routine(
            title = "title ok",
            description = "",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location ok",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        runBlocking { upsertRoutineUseCase(routine) }
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(1, routines.size)
    }

    @Test
    fun `routine should not be added if day is empty`() {
        val routine = Routine(
            title = "title ok",
            description = "description ok",
            day = listOf(),
            hour = "00:00",
            locationName = "location ok",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        assertThrows<RoutineException> { runBlocking { upsertRoutineUseCase(routine) } }
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(0, routines.size)
    }

    @Test
    fun `routine should not be added if hour is empty`() {
        val routine = Routine(
            title = "title ok",
            description = "description ok",
            day = listOf(1),
            hour = "",
            locationName = "location ok",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        assertThrows<RoutineException> { runBlocking { upsertRoutineUseCase(routine) } }
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(0, routines.size)
    }

    @Test
    fun `routine should not be added if locationName is empty`() {
        val routine = Routine(
            title = "title ok",
            description = "description ok",
            day = listOf(1),
            hour = "00:00",
            locationName = "",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        assertThrows<RoutineException> { runBlocking { upsertRoutineUseCase(routine) } }
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(0, routines.size)
    }

    @Test
    fun `routine should be added even if id doesn't exist`() {
        // Arrange
        val routine = Routine(
            id = 0,
            title = "title test",
            description = "",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        runBlocking { upsertRoutineUseCase(routine) }
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(0, routines.size)
    }

    @Test
    fun `routine should be updated if id already exists`() {
        // Arrange
        val routineToAdd = Routine(
            id = 0,
            title = "title test",
            description = "routine test",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )
        val routine2 = Routine(
            id = 0,
            title = "new title test",
            description = "routine test",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )
        runBlocking { dao.insertRoutine(routineToAdd) }

        // Act
        runBlocking { upsertRoutineUseCase(routine2) }

        // Assert
        val routines = runBlocking { dao.getRoutines().first() }
        println(routines)
        val routine = runBlocking { dao.getRoutine(0) }
        assertEquals(1, routines.size)
        assertEquals("new title test", routine?.title)
    }

    @Test
    fun `routine should be added if id is empty`() {
        // Arrange
        val routine1 = Routine(
            title = "title test",
            description = "routine test",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        // Act
        runBlocking { upsertRoutineUseCase(routine1) }

        // Assert
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(1, routines.size)
    }

    @Test
    fun `routine should be added if id is less than 0`() {
        // Arrange
        val routine1 = Routine(
            id = -1,
            title = "title test",
            description = "routine test",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        // Act
        runBlocking { upsertRoutineUseCase(routine1) }

        // Assert
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(1, routines.size)
    }

    @Test
    fun `routine should be added if id is null`() {
        // Arrange
        val routine1 = Routine(
            id = null,
            title = "title test",
            description = "routine test",
            day = listOf(1, 2),
            hour = "00:00",
            locationName = "location test",
            locationLat = 0.0,
            locationLng = 0.0,
            priority = 1
        )

        // Act
        runBlocking { upsertRoutineUseCase(routine1) }

        // Assert
        val routines = runBlocking { dao.getRoutines().first() }
        assertEquals(1, routines.size)
    }
}
package ca.uqac.tp_mobile.presentation.addEdit

import androidx.lifecycle.SavedStateHandle
import ca.uqac.tp_mobile.domain.useCase.DeleteRoutineUseCase
import ca.uqac.tp_mobile.domain.useCase.GetOneRoutineUseCase
import ca.uqac.tp_mobile.domain.useCase.GetRoutinesUseCase
import ca.uqac.tp_mobile.domain.useCase.RoutinesUseCases
import ca.uqac.tp_mobile.domain.useCase.UpsertRoutineUseCase
import ca.uqac.tp_mobile.fake.data.FakeContext
import ca.uqac.tp_mobile.fake.data.FakeDAO
import ca.uqac.tp_mobile.utils.NotificationScheduler
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class AddEditRoutineViewModelLocationTest {

    private lateinit var viewModel: AddEditRoutineViewModel

    private val fakeDAO = FakeDAO()
    private val useCases = RoutinesUseCases(
        getRoutines = GetRoutinesUseCase(fakeDAO),
        getOneRoutine = GetOneRoutineUseCase(fakeDAO),
        upsertRoutine = UpsertRoutineUseCase(fakeDAO),
        deleteRoutine = DeleteRoutineUseCase(fakeDAO)
    )
    private val fakeSavedStateHandle = SavedStateHandle()
    private val fakeNotificationScheduler = NotificationScheduler(FakeContext())

    @Before
    fun setup() {
        viewModel = AddEditRoutineViewModel(
            routinesUseCases = useCases,
            savedStateHandle = fakeSavedStateHandle,
            notificationScheduler = fakeNotificationScheduler
        )
    }

    @Test
    fun `on EnteredLocation event, routine state is updated`() {
        // When
        viewModel.onEvent(AddEditRoutineEvent.EnteredLocation("Parc", 48.0, -71.0))
        val state = viewModel.routine.value

        // Then
        assertEquals("Parc", state.locationName)
        assertEquals(48.0, state.locationLat, 0.0001)
        assertEquals(-71.0, state.locationLng, 0.0001)
    }

    @Test
    fun `on EnteredLocation with empty name, routine state is updated with empty name`() {
        viewModel.onEvent(AddEditRoutineEvent.EnteredLocation("", 48.0, -71.0))
        val state = viewModel.routine.value

        assertEquals("", state.locationName)
        assertEquals(48.0, state.locationLat, 0.0001)
        assertEquals(-71.0, state.locationLng, 0.0001)
    }

    @Test
    fun `on EnteredLocation with zero coordinates, routine state is updated with zeros`() {
        viewModel.onEvent(AddEditRoutineEvent.EnteredLocation("Lieu inconnu", 0.0, 0.0))
        val state = viewModel.routine.value

        assertEquals("Lieu inconnu", state.locationName)
        assertEquals(0.0, state.locationLat, 0.0001)
        assertEquals(0.0, state.locationLng, 0.0001)
    }

    @Test
    fun `on EnteredLocation twice, last event wins`() {
        viewModel.onEvent(AddEditRoutineEvent.EnteredLocation("Parc", 48.0, -71.0))
        viewModel.onEvent(AddEditRoutineEvent.EnteredLocation("Stade", 49.0, -72.0))
        val state = viewModel.routine.value

        assertEquals("Stade", state.locationName)
        assertEquals(49.0, state.locationLat, 0.0001)
        assertEquals(-72.0, state.locationLng, 0.0001)
    }
}
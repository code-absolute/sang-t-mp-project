package tech.codeabsolute.presentation.clients

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.use_cases.get_clients.GetClientsUseCase
import tech.codeabsolute.util.Resource
import tech.codeabsolute.util.random
import java.util.stream.Stream

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class ClientsSectionViewModelTest {

    @MockK
    private lateinit var getClientsUseCase: GetClientsUseCase

    private lateinit var mainDispatcher: CoroutineDispatcher
    private lateinit var viewModel: ClientsSectionViewModel

    @BeforeEach
    fun setUp() {
        mainDispatcher = StandardTestDispatcher()
        viewModel = ClientsSectionViewModel(getClientsUseCase, mainDispatcher)
    }

    @Test
    fun `On search query changed event`() {

        val searchQuery = String.random()
        val expectedUIState = ClientsSectionState(searchQuery = searchQuery)

        viewModel.onEvent(ClientsSectionEvent.OnSearchQueryChanged(searchQuery))
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @ParameterizedTest
    @EnumSource(SearchAttributes.Filter::class)
    fun `On search filter changed event`(filter: SearchAttributes.Filter) {

        val expectedUIState = ClientsSectionState(searchFilter = filter)

        viewModel.onEvent(ClientsSectionEvent.OnSearchFilterChanged(filter))
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @ParameterizedTest
    @EnumSource(SearchAttributes.Order::class)
    fun `On search order changed event`(order: SearchAttributes.Order) {

        val expectedUIState = ClientsSectionState(searchOrder = order)

        viewModel.onEvent(ClientsSectionEvent.OnSearchOrderChanged(order))
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @ParameterizedTest
    @MethodSource("provideResourceWithSearchQuery")
    fun `On search query changed event triggers get clients use case`(
        resource: Flow<Resource<List<Client>>>,
        expectedUIState: ClientsSectionState
    ) = runTest(mainDispatcher) {

        coEvery { getClientsUseCase(any()) } returns resource

        viewModel.onEvent(ClientsSectionEvent.OnSearchQueryChanged(expectedUIState.searchQuery))
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @ParameterizedTest
    @MethodSource("provideResourceWithSearchFilter")
    fun `On search filter changed event triggers get clients use case`(
        resource: Flow<Resource<List<Client>>>,
        expectedUIState: ClientsSectionState
    ) = runTest(mainDispatcher) {

        coEvery { getClientsUseCase(any()) } returns resource

        viewModel.onEvent(ClientsSectionEvent.OnSearchFilterChanged(expectedUIState.searchFilter))
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @ParameterizedTest
    @MethodSource("provideResourceWithSearchOrder")
    fun `On search order changed event triggers get clients use case`(
        resource: Flow<Resource<List<Client>>>,
        expectedUIState: ClientsSectionState
    ) = runTest(mainDispatcher) {

        coEvery { getClientsUseCase(any()) } returns resource

        viewModel.onEvent(ClientsSectionEvent.OnSearchOrderChanged(expectedUIState.searchOrder))
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    companion object {

        private val clients = List(30) { mockk<Client>() }
        private val noClients = emptyList<Client>()

        @JvmStatic
        fun provideResourceWithSearchQuery(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Error(message = String.random(), data = noClients)) },
                    ClientsSectionState(
                        false,
                        String.random(),
                        clients = noClients
                    )
                ),
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Loading(data = noClients)) },
                    ClientsSectionState(
                        true,
                        String.random(),
                        clients = noClients
                    )
                ),
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Success<List<Client>>(data = clients)) },
                    ClientsSectionState(
                        false,
                        String.random(),
                        clients = clients
                    ),
                )
            )
        }

        @JvmStatic
        fun provideResourceWithSearchFilter(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Error(message = String.random(), data = noClients)) },
                    ClientsSectionState(
                        false,
                        searchFilter = SearchAttributes.Filter.values().random(),
                        clients = noClients
                    )
                ),
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Loading(data = noClients)) },
                    ClientsSectionState(
                        true,
                        searchFilter = SearchAttributes.Filter.values().random(),
                        clients = noClients
                    )
                ),
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Success<List<Client>>(data = clients)) },
                    ClientsSectionState(
                        false,
                        searchFilter = SearchAttributes.Filter.values().random(),
                        clients = clients
                    ),
                )
            )
        }

        @JvmStatic
        fun provideResourceWithSearchOrder(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Error(message = String.random(), data = noClients)) },
                    ClientsSectionState(
                        false,
                        searchOrder = SearchAttributes.Order.values().random(),
                        clients = noClients
                    )
                ),
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Loading(data = noClients)) },
                    ClientsSectionState(
                        true,
                        searchOrder = SearchAttributes.Order.values().random(),
                        clients = noClients
                    )
                ),
                Arguments.of(
                    flow<Resource<List<Client>>> { emit(Resource.Success<List<Client>>(data = clients)) },
                    ClientsSectionState(
                        false,
                        searchOrder = SearchAttributes.Order.values().random(),
                        clients = clients
                    ),
                )
            )
        }
    }
}
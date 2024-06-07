import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jay.weatherforecastapp.data.WeatherRepository
import com.jay.weatherforecastapp.data.model.Forecast
import com.jay.weatherforecastapp.data.model.WeatherResponse
import com.jay.weatherforecastapp.ui.viewmodels.WeatherViewModel
import com.jay.weatherforecastapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    // Set the main dispatcher for testing
    private val testDispatcher = TestCoroutineDispatcher()

    // Executes each task synchronously using Architecture Components
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: WeatherViewModel

    // Swap the default dispatcher with the test dispatcher
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Initialize Mockito annotations
        repository = mock<WeatherRepository>()
        viewModel = WeatherViewModel(repository)
    }

    // Reset main dispatcher after testing
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test fetchWeather with success`() = testDispatcher.runBlockingTest {
        // Mock response
        val forecastList = listOf(Forecast(1, "25°C", "10 km/h"))
        val weatherResponse = WeatherResponse("30°C", "15 km/h", "Sunny", forecastList)
        val expectedResult = Result.Success(weatherResponse)

        // Mock the getWeather method directly
        `when`(repository.getWeather("London")).thenReturn(expectedResult)

        // Call the function under test
        viewModel.fetchWeather("London")

        // Ensure that loading state is emitted first
        assert(viewModel.weatherState.first() is Result.Loading)

        // Ensure that success state is emitted next
        assert(viewModel.weatherState.first() == expectedResult)
    }

    @Test
    fun `test fetchWeather with error`() = testDispatcher.runBlockingTest {
        // Mock error response
        val errorMessage = "City not found"
        val expectedResult = Result.Error(Exception(errorMessage))

        // Mock the getWeather method directly
        `when`(repository.getWeather("InvalidCity")).thenReturn(expectedResult)

        // Call the function under test
        viewModel.fetchWeather("InvalidCity")

        // Ensure that loading state is emitted first
        assert(viewModel.weatherState.first() is Result.Loading)

        // Ensure that error state is emitted next
        assert(viewModel.weatherState.first() == expectedResult)
    }
}

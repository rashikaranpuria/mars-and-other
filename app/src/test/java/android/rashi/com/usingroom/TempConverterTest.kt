package android.rashi.com.usingroom

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by rashi on 7/2/18.
 */
class TempConverterTest {

    private var mTestClass:TempConverter? = null

    @Before
    fun setUp() {
        mTestClass = TempConverter()
    }

    @After
    fun tearDown() {
        mTestClass = null
    }

    @Test
    fun celsiusToFahrenheitShouldConvertCorrect() {
        val celsiusTemp = 0.0
        val expectedFahrenheit = 32.0
        assertEquals(expectedFahrenheit, mTestClass?.celsiusToFahrenheit(celsiusTemp))
    }

    @Test
    fun fahrenheitToCelsius() {
        val fahrenheitTemp = 212.0
        val expectedCelsius = 100.0
        assertEquals(expectedCelsius, mTestClass?.fahrenheitToCelsius(fahrenheitTemp))
    }

}
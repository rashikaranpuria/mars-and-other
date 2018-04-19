package android.rashi.com.usingroom

/**
 * Created by rashi on 7/2/18.
 */
class TempConverter {

    fun celsiusToFahrenheit (cTemp: Double) : Double {
        return (9.0/5) * cTemp + 32.0
    }

    fun fahrenheitToCelsius (fTemp: Double) : Double {
        return (fTemp - 32.0) * (5.0/9)
    }
}
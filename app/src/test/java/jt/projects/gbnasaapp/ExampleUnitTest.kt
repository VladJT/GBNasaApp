package jt.projects.gbnasaapp

import jt.projects.gbnasaapp.model.pod.PODRetrofitImpl
import jt.projects.gbnasaapp.model.pod.PODServerResponseData
import jt.projects.gbnasaapp.model.retrofit.RetrofitCallback
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private var httpResult = ""
    private val httpResultExpected =
        """PODServerResponseData(copyright=Peter Kohlmann, date=2022-09-01, explanation=Framing a bright emission region, this telescopic view looks out along the plane of our Milky Way Galaxy toward the nebula rich constellation Cygnus the Swan. Popularly called the Tulip Nebula, the reddish glowing cloud of interstellar gas and dust is also found in the 1959 catalog by astronomer Stewart Sharpless as Sh2-101. Nearly 70 light-years across, the complex and beautiful Tulip Nebula blossoms about 8,000 light-years away. Ultraviolet radiation from young energetic stars at the edge of the Cygnus OB3 association, including O star HDE 227018, ionizes the atoms and powers the emission from the Tulip Nebula. Also in the field of view is microquasar Cygnus X-1, one of the strongest X-ray sources in planet Earth's sky. Blasted by powerful jets from a lurking black hole its fainter bluish curved shock front is only just visible though, beyond the cosmic Tulip's petals near the right side of the frame.   Back to School? Learn Science with NASA, mediaType=image, title=The Tulip and Cygnus X-1, url=https://apod.nasa.gov/apod/image/2209/TulipCygX-1_1024.jpg, hdurl=https://apod.nasa.gov/apod/image/2209/TulipCygX-1.jpg)"""

    private val callback = object : RetrofitCallback<PODServerResponseData> {
        override fun onResponse(data: PODServerResponseData) {
            httpResult = data.toString()
        }

        override fun onFailure(e: Throwable) {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    //  @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    fun testHttpRequest() {
        PODRetrofitImpl().getPictureOfTheDayByDate(callback, LocalDate.of(2022, 9, 1))
        Thread.sleep(3000)
        assertEquals(httpResultExpected, httpResult)
    }
}
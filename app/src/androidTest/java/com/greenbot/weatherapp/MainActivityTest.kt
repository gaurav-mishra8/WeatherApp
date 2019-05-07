package com.greenbot.weatherapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.greenbot.domain.model.WeatherForecast
import com.greenbot.weatherapp.view.MainActivity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val permissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubWeatherRepositoryResponse(Single.just<WeatherForecast>(buildMockWeatherData()))
        activity.launchActivity(null)
    }

    @Test
    fun testTemperatureIsDisplayed() {

        val weatherForecast = buildMockWeatherData()
        stubWeatherRepositoryResponse(Single.just(weatherForecast))
        activity.launchActivity(null)

        onView(withId(R.id.home_tv_temperature)).check(matches(isDisplayed()))
    }

    @Test
    fun testLocationIsDisplayed() {

        val weatherForecast = buildMockWeatherData()
        stubWeatherRepositoryResponse(Single.just(weatherForecast))
        activity.launchActivity(null)

        onView(withId(R.id.home_tv_location)).check(matches(isDisplayed()))
    }

    @Test
    fun testLocationMatches() {

        val weatherForecast = buildMockWeatherData()
        stubWeatherRepositoryResponse(Single.just(weatherForecast))

        val weatherViewData = buildMockWeatherViewData(weatherForecast)

        activity.launchActivity(null)

        onView(withId(R.id.home_tv_location)).check(matches(withText(weatherViewData.locationName)))
    }

    @Test
    fun testTemperatureMatches() {

        val weatherForecast = buildMockWeatherData()
        stubWeatherRepositoryResponse(Single.just(weatherForecast))

        val weatherViewData = buildMockWeatherViewData(weatherForecast)
        activity.launchActivity(null)

        onView(withId(R.id.home_tv_temperature)).check(matches(withText(weatherViewData.currentTemp)))
    }

    @Test
    fun testErrorTextIsDisplayed() {
        val errorMsg = "Something went wrong at our end!"
        stubWeatherRepositoryResponse(Single.error(Throwable(errorMsg)))

        activity.launchActivity(null)

        onView(withId(R.id.home_tv_error)).check(matches(isDisplayed()))
        onView(withId(R.id.home_tv_error)).check(matches(withText(errorMsg)))

    }

    private fun stubWeatherRepositoryResponse(observable: Single<WeatherForecast>) {
        whenever(TestApplication.appComponent().weatherRepository().getWeatherForecast(any(), any(), any())).thenReturn(
            observable
        )
    }

}
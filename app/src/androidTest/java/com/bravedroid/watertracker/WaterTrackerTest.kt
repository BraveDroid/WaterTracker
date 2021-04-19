package com.bravedroid.watertracker

//import com.nhaarman.mockitokotlin2.mock
import androidx.lifecycle.Observer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bravedroid.watertracker.data.PreferencesHelper
import com.bravedroid.watertracker.di.GlideModule
import com.bravedroid.watertracker.di.WaterTrackingModule
import com.bravedroid.watertracker.ui.activities.WaterTrackerActivity
import com.bravedroid.watertracker.ui.fragments.PanelFragment
import com.bravedroid.watertracker.ui.viewmodels.SingleLiveEvent
import com.bravedroid.watertracker.util.ImageLoader
import com.bravedroid.watertracker.util.Logger
import com.bumptech.glide.RequestManager
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.internal.verification.AtMost
import org.mockito.internal.verification.Times

@HiltAndroidTest
@UninstallModules(WaterTrackingModule::class, GlideModule::class)
// , ResourceModule::class, GlideModule::class)
@RunWith(AndroidJUnit4::class)
class WaterTrackerTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val preferencesHelper: PreferencesHelper = mock(PreferencesHelper::class.java)
//    val preferencesHelper: PreferencesHelper = mock<PreferencesHelper>()

    @BindValue
    @JvmField
    val imageLoader: ImageLoader = mock(ImageLoader::class.java)
    //val imageLoader: ImageLoader = mock<ImageLoader>()

    // this is a bad mocking , we better don't mock external dependencies
    // Do not mock types you don’t own, https://site.mockito.org/
    @BindValue
    @JvmField
    val glide: RequestManager = mock(RequestManager::class.java)
//    val glide: RequestManager = mock<RequestManager>()

    @BindValue
    @JvmField
    val logger: Logger = mock(Logger::class.java)
//    val glide: RequestManager = mock<RequestManager>()


    @Before
    fun unit() = hiltRule.inject()

    @Test
    fun waterCountDisplay() {
        val waterIntake = 5

        `when`(preferencesHelper.getWaterIntake())
            .thenReturn(waterIntake)

        ActivityScenario.launch(WaterTrackerActivity::class.java)
        Thread.sleep(4444)
        onView(withId(R.id.waterCountText))
            .check(matches(withText(waterIntake.toString())))
    }

    @Test
    fun test_WaterCountTextView_from_PanelFragment() {

        `when`(preferencesHelper.getWaterIntake())
            .thenReturn(10)

        launchFragmentInHiltContainer<PanelFragment>(null, R.style.Theme_WaterTracker)
        onView(withId(R.id.comment_tv))
            .check(matches(withText("10")))

        verify(logger, Times(1)).log("init")
        verify(logger, AtMost(1)).log("init")
    }


    @Test
    fun sutValueShouldChangeTheValueOfTheSingleLiveData() {
        val sut = SingleLiveEvent<Any>()
        val observerMock1 = mock(Observer::class.java) as Observer<Any>
        val observerMock2 = mock(Observer::class.java) as Observer<Any>
        launchFragmentInHiltContainer<PanelFragment>(null, R.style.Theme_WaterTracker) {
            sut.observe(this, observerMock1)
            sut.value = 4
            Mockito.verify(observerMock1).onChanged(4)

            sut.observe(this, observerMock2)
            Mockito.verify(observerMock2, never()).onChanged(4)
        }
    }
}

package com.sphtechapp.myapplicationsph.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sphtechapp.myapplicationsph.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textView = onView(
allOf(withId(R.id.tvDataUsageYear), withText("2008"),
withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
isDisplayed()))
        textView.check(matches(isDisplayed()))
        
        val textView2 = onView(
allOf(withId(R.id.tvDataUsageYear), withText("2018"),
withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
isDisplayed()))
        textView2.check(matches(withText("2018")))
        }
    }

package com.sphtechapp.myapplicationsph.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sphtechapp.myapplicationsph.R
import org.hamcrest.Matchers.allOf
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
        val recyclerView = onView(
            allOf(
                withId(R.id.rvDataUsageList),
                withParent(
                    allOf(
                        withId(R.id.rootLayout),
                        withParent(withId(R.id.container))
                    )
                ),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.rvDataUsageList),
                withParent(
                    allOf(
                        withId(R.id.rootLayout),
                        withParent(withId(R.id.container))
                    )
                ),
                isDisplayed()
            )
        )
        recyclerView2.check(matches(isDisplayed()))
    }
}

package com.sphtechapp.myapplicationsph.ui

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sphtechapp.myapplicationsph.R
import org.hamcrest.Description
import org.hamcrest.Matcher
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
        Thread.sleep(8000)
        onView(
            withId(R.id.rvDataUsageList)
        ).check(matches(isDisplayed()))

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(0, hasDescendant(withText("2008")))))

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(0, hasDescendant(withText("1.543719")))))

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(0, hasDescendant(withId(R.id.ivDecreased)))))

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(3, hasDescendant(withText("2011")))))

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(3, hasDescendant(withText("14.638703")))))

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(3, hasDescendant(withId(R.id.ivDecreased)))))

        onView(withId(R.id.rvDataUsageList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                clickOnViewChild(R.id.ivDecreased)
            )
        )
        onView(withId(R.id.rvDataUsageList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                clickOnViewChild(R.id.tvDataUsageYear)
            )
        )

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(10, hasDescendant(withText("2018")))))

        onView(withId(R.id.rvDataUsageList))
            .check(matches(atPosition(10, hasDescendant(withText("75.35964842")))))
    }

    private fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView?>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView?): Boolean {
                val viewHolder: RecyclerView.ViewHolder =
                    view?.findViewHolderForAdapterPosition(position)
                        ?: // has no item on such position
                        return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    private fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) =
            click().perform(uiController, view.findViewById(viewId))
    }
}

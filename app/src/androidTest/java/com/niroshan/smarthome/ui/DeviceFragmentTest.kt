package com.niroshan.smarthome.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.niroshan.smarthome.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DeviceFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun deviceFragmentTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.recycler),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.title), withText("Test Sensor"),
                childAtPosition(
                    allOf(
                        withId(R.id.price_wrapper),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Test Sensor")))

        val textView2 = onView(
            allOf(
                withId(R.id.price), withText("20"),
                childAtPosition(
                    allOf(
                        withId(R.id.price_wrapper),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("20")))

        val frameLayout = onView(
            allOf(
                withId(R.id.nav_host_fragment_container),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_filter), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val linearLayoutCompat = onView(
            allOf(
                withId(R.id.action_filter),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )

        val editText = onView(
            allOf(
                withId(R.id.search_src_text), withText("Search…"),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )

        val searchAutoComplete = onView(
            allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("light"), closeSoftKeyboard())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.recycler),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(1, click()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

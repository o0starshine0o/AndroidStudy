package com.abelhu.androidstudy.ui.main

import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.abelhu.androidstudy.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.Matchers.`is` as _is


class MainActivityTest {

    @Rule // run before every @Test fun
    @JvmField // not generate get and set
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    private fun androidHomeMatcher(): Matcher<View> {
        return allOf(
            withParent(withClassName(_is(Toolbar::class.java.name))),
            withClassName(
                anyOf(
                    _is(ImageButton::class.java.name),
                    _is(AppCompatImageButton::class.java.name)
                )
            )
        )
    }

    // step 1 : find a weight
    // step 2 : do some action
    // step 3 : verify is the weight in some state
    @Test
    fun onOpenDrawer() {
        onView(withId(R.id.drawerLayout)).check(matches(isClosed())).perform(open())
        onView(withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_send))
        onView(withId(R.id.rootView)).check(matches(isDisplayed()))
    }
}
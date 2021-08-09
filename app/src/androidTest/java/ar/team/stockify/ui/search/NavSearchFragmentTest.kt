package ar.team.stockify.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import ar.team.stockify.R
import ar.team.stockify.extensions.launchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavSearchFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNavigationToDetailsScreen() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Create a graphical FragmentScenario for the SearchScreen
        val searchScreen = launchFragmentInHiltContainer<SearchFragment> {
            navController.setGraph(R.navigation.main_nav_graph)
            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(this.requireView(), navController)
        }

        // Perform a click to search Stock and select it
        val appCompatImageView = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.search_button),
                ViewMatchers.withContentDescription("Search"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                            ViewMatchers.withId(R.id.search_view),
                            0
                        )
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageView.perform(ViewActions.click())

        val searchAutoComplete = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.search_src_text),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.search_plate),
                        childAtPosition(
                            ViewMatchers.withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchAutoComplete.perform(ViewActions.replaceText("msft"), ViewActions.closeSoftKeyboard())


        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )

        // Verify that performing a click changes the NavController’s state
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailFragment)
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
package ar.team.stockify.ui.splash


import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import ar.team.stockify.R
import ar.team.stockify.ui.HiltTestActivity
import ar.team.stockify.ui.user.UserFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@LargeTest
@RunWith(AndroidJUnit4::class)
class FindStockTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(SplashActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
    val mockNavController = mock(NavController::class.java)



    @Test
    fun findStockTest() {


        val userScenario = launchFragmentInHiltContainer<UserFragment>(
            /* Añadir argumento para llamar UserFragment
            bundleOf(
                "isFromSplash" to true
            )*/

        )

        /*
        Intento de asociar el UserFragment al navigation
        val user2Scenario =userScenario as FragmentScenario<UserFragment>
        user2Scenario.onFragment {
            Navigation.setViewNavController(
                it.requireView(),
                mockNavController
            )
        }*/
        val materialButton = onView(
            allOf(
                withId(R.id.imageButton), withText("Añade tu foto"),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.userNameEdit),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.userNameLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("marce"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.userNameEdit), withText("marce"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.userNameLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(pressImeActionButton())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button), withText("Guardar"),
                isDisplayed()
            )
        )

        materialButton2.perform(click())

        // Verificar que navega al MainContentFragment
        verify(mockNavController).navigate(R.id.mainContentFragment)

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

    /**
     * launchFragmentInContainer from the androidx.fragment:fragment-testing library
     * is NOT possible to use right now as it uses a hardcoded Activity under the hood
     * (i.e. [EmptyFragmentActivity]) which is not annotated with @AndroidEntryPoint.
     *
     * As a workaround, use this function that is equivalent. It requires you to add
     * [HiltTestActivity] in the debug folder and include it in the debug AndroidManifest.xml file
     * as can be found in this project.
     */
    inline fun <reified T : Fragment> launchFragmentInHiltContainer(
        fragmentArgs: Bundle? = null,
        @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
        crossinline action: Fragment.() -> Unit = {}
    ) {
        val startActivityIntent = Intent.makeMainActivity(
            ComponentName(
                ApplicationProvider.getApplicationContext(),
                HiltTestActivity::class.java
            )
        ).putExtra(
            "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
            themeResId
        )

        ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
            val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(T::class.java.classLoader),
                T::class.java.name
            )

            //Intento de dar tiempo a que cargue la activity
            //Thread.sleep(5000)

            // Intento de asociar navigation con Activity
                /*Navigation.setViewNavController(
                    fragment.requireView(),
                    mockNavController
                )*/


            fragment.arguments = fragmentArgs
            activity.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment, "")
                .commitNow()

            fragment.action()
        }
    }
}

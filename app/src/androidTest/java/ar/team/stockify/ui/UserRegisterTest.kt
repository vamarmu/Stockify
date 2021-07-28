package ar.team.stockify.ui


import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import ar.team.stockify.R
import ar.team.stockify.extensions.launchFragmentInHiltContainer
import ar.team.stockify.ui.user.UserFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before



@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UserRegisterTest {

    @get:Rule val hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    var mGrantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    open fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun labelRegisterTest() {
        launchUserFragmentFromSplash()
        onView(withId(R.id.titleUser)).check( ViewAssertions.matches(withText("¿QUIEN ERES?")))
    }


    private fun launchUserFragmentFromSplash() {
        launchFragmentInHiltContainer<UserFragment> (bundleOf(
            "isFromSplash" to true
        )){
            navController.setGraph(R.navigation.landing_nav_graph)
            navController.setCurrentDestination(R.id.userFragment)
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    // The fragment’s view has just been created
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }
}

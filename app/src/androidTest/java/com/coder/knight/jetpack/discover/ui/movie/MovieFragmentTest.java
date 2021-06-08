package com.coder.knight.jetpack.discover.ui.movie;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.rule.ActivityTestRule;

import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.ui.home.HomeActivity;
import com.coder.knight.jetpack.discover.ui.home.HomeActivityTest;
import com.coder.knight.jetpack.discover.utils.EspressoIdlingResource;
import com.coder.knight.jetpack.discover.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MovieFragmentTest {
    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void MovieMenuClickAndLoadMovieListTest() {
        onView(withId(R.id.home_bnv)).perform(HomeActivityTest.selectBnvItem(R.id.movie_menu));
        onView(withId(R.id.rv_movie_list)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_movie_list)).check(new RecyclerViewItemCountAssertion(20));
    }

    @Test
    public void recyclerScrollTest() {
        onView(withId(R.id.home_bnv)).perform(HomeActivityTest.selectBnvItem(R.id.movie_menu));
        onView(withId(R.id.rv_movie_list)).check(matches(isDisplayed()));

        RecyclerView rvMovieTest = activityTestRule.getActivity().findViewById(R.id.rv_movie_list);
        int itemCount = Objects.requireNonNull(rvMovieTest.getAdapter()).getItemCount();

        onView(withId(R.id.rv_movie_list))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityTestRule.getActivity().getWindow().getDecorView())
                )).perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
    }
}
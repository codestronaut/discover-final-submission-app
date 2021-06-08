package com.coder.knight.jetpack.discover.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.utils.EspressoIdlingResource;
import com.coder.knight.jetpack.discover.utils.FakeDummyData;
import com.coder.knight.jetpack.discover.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DetailActivityTest {

    /*
     *
     * For detail activity test purpose we use movie to test
     *
     * */

    private MovieEntity movieEntity = FakeDummyData.generateDummyMovie().getMovieList().get(0);

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<DetailActivity>(DetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent testIntent = new Intent(targetContext, DetailActivity.class);
            testIntent.putExtra(DetailActivity.EXTRA_MOVIE, movieEntity.getMovieId());
            return testIntent;
        }
    };

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadMovie() {
        // check if title view component is displayed to user
        onView(withId(R.id.detail_title_text)).check(matches(isDisplayed()));
        // check movie title
        onView(withId(R.id.detail_title_text)).check(matches(withText(movieEntity.getMovieTitle())));

        // add to favorite click
        onView(withId(R.id.favorite_button)).perform(click());
    }

    @Test
    public void loadTrailer() {
        // check trailer item count
        onView(withId(R.id.rv_trailer)).check(new RecyclerViewItemCountAssertion(3));
    }
}
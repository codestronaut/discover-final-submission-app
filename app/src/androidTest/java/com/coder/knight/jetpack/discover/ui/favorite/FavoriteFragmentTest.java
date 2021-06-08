package com.coder.knight.jetpack.discover.ui.favorite;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;

import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.ui.home.HomeActivity;
import com.coder.knight.jetpack.discover.ui.home.HomeActivityTest;
import com.coder.knight.jetpack.discover.utils.RecyclerViewItemCountAssertion;
import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

public class FavoriteFragmentTest {
    /*
     * To run Favorite Test successfully we must update version of database to clear all favorite
     * then we start testing all of androidTest to see the flow of this app features
     * because we using a database, RecyclerView itemCount is not fixed
     *
     * creator : coderKnight
     * */

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void tabLayoutClickTest() {
        // open favorite fragment
        onView(withId(R.id.home_bnv)).perform(HomeActivityTest.selectBnvItem(R.id.favorite_menu));

        onView(withId(R.id.favorite_tab)).perform(selectTabPosition(1));
        onView(withId(R.id.favorite_tab)).perform(selectTabPosition(0));
    }

    @Test
    public void loadFavoriteMovie() {
        // open favorite fragment
        onView(withId(R.id.home_bnv)).perform(HomeActivityTest.selectBnvItem(R.id.favorite_menu));

        onView(withId(R.id.favorite_tab)).perform(selectTabPosition(0));
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_fav_movie)).check(new RecyclerViewItemCountAssertion(1));
    }

    private static ViewAction selectTabPosition(final int position) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return "Tab opened at index: " + position;
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view instanceof TabLayout) {
                    TabLayout tabs = (TabLayout) view;
                    TabLayout.Tab tabPosition = tabs.getTabAt(position);

                    if (tabPosition != null) {
                        tabPosition.select();
                    }
                }
            }
        };
    }
}
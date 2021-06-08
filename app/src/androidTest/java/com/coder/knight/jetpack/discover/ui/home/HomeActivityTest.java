package com.coder.knight.jetpack.discover.ui.home;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.coder.knight.jetpack.discover.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class HomeActivityTest {
    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() {
        HomeActivity homeActivity = activityTestRule.getActivity();
        assertThat(homeActivity, notNullValue());
    }

    @Test
    public void setUpBottomNavigationView() {
        onView(withId(R.id.home_bnv)).check(matches(isDisplayed()));
    }

    @Test
    @LargeTest
    public void switchCheckedMenu() {
        // movie menu
        onView(withId(R.id.home_bnv)).perform(selectBnvItem(R.id.movie_menu));
        onView(withId(R.id.home_bnv)).check(matches(hasCheckedItem(R.id.movie_menu)));

        // tv show menu
        onView(withId(R.id.home_bnv)).perform(selectBnvItem(R.id.tv_show_menu));
        onView(withId(R.id.home_bnv)).check(matches(hasCheckedItem(R.id.tv_show_menu)));

        //
        onView(withId(R.id.home_bnv)).perform(selectBnvItem(R.id.favorite_menu));
        onView(withId(R.id.home_bnv)).check(matches(hasCheckedItem(R.id.favorite_menu)));
    }

    /*
     * Custom View Action for BottomNavigationView
     * */
    public static ViewAction selectBnvItem(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(BottomNavigationView.class));
            }

            @Override
            public String getDescription() {
                return "Menu opened with id " + id;
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view instanceof BottomNavigationView) {
                    BottomNavigationView bnv = (BottomNavigationView) view;
                    bnv.setSelectedItemId(id);
                }
            }
        };
    }

    /*
     * Custom Matcher for BottomNavigationView
     * */
    private static Matcher<View> hasCheckedItem(final int id) {
        return new BoundedMatcher<View, BottomNavigationView>(BottomNavigationView.class) {
            Set<Integer> checkedIds = new HashSet<>();
            boolean itemFound = false;
            boolean triedMatching = false;

            @Override
            public void describeTo(Description description) {
                if (!triedMatching) {
                    description.appendText("BottomNavigationView");
                    return;
                }

                description.appendText("BottomNavigationView to have a checked item with id = ");
                description.appendValue(id);
                if (itemFound) {
                    description.appendText(", but selection was = ");
                    description.appendValue(checkedIds);
                } else {
                    description.appendText(", but it doesn't have an item with such id");
                }
            }

            @Override
            protected boolean matchesSafely(BottomNavigationView item) {
                triedMatching = true;

                final Menu menu = item.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    final MenuItem menuItem = menu.getItem(i);
                    if (menuItem.isChecked()) {
                        checkedIds.add(menuItem.getItemId());
                    }

                    if (menuItem.getItemId() == id) {
                        itemFound = true;
                    }
                }
                return checkedIds.contains(id);
            }
        };
    }

}
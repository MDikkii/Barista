package com.schibsted.spain.barista;

import android.support.annotation.IdRes;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.widget.AdapterView;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class BaristaListViewActions {

    public static void clickListItem(@IdRes int listViewId, int position, Class<?> modelClass) {
        // This is ugly, I know. But depending on the layout only one of the following methods will work.
        // The try/catch let's us forget about it and always use the same generic method to clickScrollingIfNeeded on lists.
        try {
            clickListItemForMultipleListsOnScreen(listViewId, position, modelClass);
        } catch (NoMatchingViewException e) {
            clickListItemForSingleListOnScreen(position, modelClass);
        }
    }

    private static void clickListItemForMultipleListsOnScreen(@IdRes int listViewId, int position, Class<?> modelClass) {
        // This method only seems to work when there are multiple ListViews in the same activity
        onData(is(instanceOf(modelClass))).inAdapterView(
                allOf(
                        isAssignableFrom(AdapterView.class),
                        isDescendantOfA(withId(listViewId)),
                        isDisplayed()))
                .atPosition(position)
                .perform(ViewActions.click());
    }

    private static void clickListItemForSingleListOnScreen(int position, Class<?> modelClass) {
        // This method only seems to work when there is just one ListView in the same activity
        onData(is(instanceOf(modelClass)))
                .atPosition(position)
                .perform(ViewActions.click());
    }
}

package it.cosenonjaviste.testableandroidapps.v2;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.cosenonjaviste.testableandroidapps.utils.ErrorTextMatcher.hasErrorText;

public class ShareActivityTest {
    @Rule
    public final ActivityTestRule<TestableShareActivity> rule = new ActivityTestRule<TestableShareActivity>(TestableShareActivity.class) {
        @Override protected Intent getActivityIntent() {
            Intent intent = super.getActivityIntent();
            ShareActivity.populateIntent(intent, "title", "body");
            return intent;
        }
    };

    @Test public void checkParameters() {
        onView(withId(R.id.share_title)).check(matches(withText("title")));
        onView(withId(R.id.share_body)).check(matches(withText("body")));
    }

    @Test public void clickOnShare() {
        onView(withId(R.id.share_button)).perform(click());
    }

    @Test public void checkValidationWhenEmpty() {
        onView(withId(R.id.share_title)).perform(ViewActions.clearText());
        onView(withId(R.id.share_body)).perform(ViewActions.clearText());

        onView(withId(R.id.share_button)).perform(click());

        String errorText = rule.getActivity().getString(R.string.mandatory_field);

        onView(withId(R.id.share_title)).check(matches(hasErrorText(errorText)));
        onView(withId(R.id.share_body)).check(matches(hasErrorText(errorText)));
    }
}

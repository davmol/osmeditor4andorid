package de.blau.android;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

/**
 *
 * @author simon
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ModeTest {

    Main     main    = null;
    Logic    logic   = null;
    View     v       = null;
    UiDevice mDevice = null;

    @Rule
    public ActivityTestRule<Main> mActivityRule = new ActivityTestRule<>(Main.class);

    /**
     * Pre-test setup
     */
    @Before
    public void setup() {
        main = mActivityRule.getActivity();
        logic = App.getLogic();
        TestUtils.grantPermissons();
        TestUtils.dismissStartUpDialogs(main);
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        TestUtils.grantPermissons();
        TestUtils.dismissStartUpDialogs(main);
    }

    /**
     * Post-test teardown
     */
    @After
    public void teardown() {
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                App.getLogic().setMode(main, Mode.MODE_EASYEDIT);
            }
        });
    }

    /**
     * Lock, unlock, cycle through the modes
     */
    @Test
    public void lock() {
        logic.setLocked(true);
        logic.setZoom(main.getMap(), 20);
        UiObject map = mDevice.findObject(new UiSelector().resourceId("de.blau.android:id/map_view"));
        Assert.assertTrue(map.exists());

        UiObject snack = mDevice.findObject(new UiSelector().textStartsWith(main.getString(R.string.toast_unlock_to_edit)));
        try {
            map.click();
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(snack.waitForExists(5000));

        // need to be adapted for new menu
        App.getLogic().setMode(main, Mode.MODE_EASYEDIT); // start from a known state
        UiObject lock = mDevice.findObject(new UiSelector().resourceId("de.blau.android:id/floatingLock"));
        try {
            lock.click();
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(!logic.isLocked());

        Assert.assertEquals(Mode.MODE_EASYEDIT, logic.getMode()); // start with this and cycle through the modes
        try {
            TestUtils.longClick(mDevice, lock);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickText(mDevice, false, main.getString(R.string.mode_tag_only), true);
        Assert.assertEquals(Mode.MODE_TAG_EDIT, logic.getMode());

        try {
            TestUtils.longClick(mDevice, lock);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickText(mDevice, false, main.getString(R.string.mode_indoor), true);
        Assert.assertEquals(Mode.MODE_INDOOR, logic.getMode());

        try {
            TestUtils.longClick(mDevice, lock);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickText(mDevice, false, main.getString(R.string.mode_correct), true);
        Assert.assertEquals(Mode.MODE_CORRECT, logic.getMode());

        try {
            TestUtils.longClick(mDevice, lock);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickText(mDevice, false, main.getString(R.string.mode_easy), true);
        Assert.assertEquals(Mode.MODE_EASYEDIT, logic.getMode());
    }
}
package de.blau.android.propertyeditor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.orhanobut.mockwebserverplus.MockWebServerPlus;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import de.blau.android.App;
import de.blau.android.Logic;
import de.blau.android.Main;
import de.blau.android.Map;
import de.blau.android.R;
import de.blau.android.SignalHandler;
import de.blau.android.TestUtils;
import de.blau.android.exception.OsmIllegalOperationException;
import de.blau.android.osm.BoundingBox;
import de.blau.android.osm.Node;
import de.blau.android.osm.OsmElement;
import de.blau.android.osm.OsmElement.ElementType;
import de.blau.android.osm.Relation;
import de.blau.android.osm.RelationMember;
import de.blau.android.osm.Tags;
import de.blau.android.osm.Way;
import de.blau.android.prefs.AdvancedPrefDatabase;
import de.blau.android.prefs.Preferences;
import de.blau.android.presets.MRUTags;
import de.blau.android.presets.Preset;
import de.blau.android.presets.Preset.PresetGroup;
import de.blau.android.presets.Preset.PresetItem;
import de.blau.android.presets.PresetElementPath;
import de.blau.android.resources.DataStyle;
import de.blau.android.resources.TileLayerServer;
import okhttp3.HttpUrl;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PropertyEditorTest {

    MockWebServerPlus    mockServer      = null;
    Context              context         = null;
    ActivityMonitor      monitor         = null;
    AdvancedPrefDatabase prefDB          = null;
    Instrumentation      instrumentation = null;
    Main                 main            = null;
    UiDevice             mDevice         = null;

    @Rule
    public ActivityTestRule<Main> mActivityRule = new ActivityTestRule<>(Main.class);

    /**
     * Pre-test setup
     */
    @Before
    public void setup() {
        File mruTags = new File("/sdcard/Vespucci/mrutags.xml");
        mruTags.delete();
        instrumentation = InstrumentationRegistry.getInstrumentation();
        context = instrumentation.getTargetContext();
        monitor = instrumentation.addMonitor(PropertyEditor.class.getName(), null, false);
        main = (Main) mActivityRule.getActivity();
        Preferences prefs = new Preferences(context);
        prefs.setBackGroundLayer(TileLayerServer.LAYER_NONE); // try to avoid downloading tiles
        prefs.setOverlayLayer(TileLayerServer.LAYER_NOOVERLAY);
        main.getMap().setPrefs(main, prefs);
        mockServer = new MockWebServerPlus();
        HttpUrl mockBaseUrl = mockServer.server().url("/api/0.6/");
        System.out.println("mock api url " + mockBaseUrl.toString());
        prefDB = new AdvancedPrefDatabase(context);
        prefDB.deleteAPI("Test");
        prefDB.addAPI("Test", "Test", mockBaseUrl.toString(), null, null, "user", "pass", false);
        prefDB.selectAPI("Test");
        mDevice = UiDevice.getInstance(instrumentation);
        TestUtils.grantPermissons();
        TestUtils.dismissStartUpDialogs(main);
    }

    /**
     * Post-test teardown
     */
    @After
    public void teardown() {
        TestUtils.stopEasyEdit(main);
        try {
            mockServer.server().shutdown();
            instrumentation.removeMonitor(monitor);
        } catch (IOException ioex) {
            System.out.println("Stopping mock webserver exception " + ioex);
        }
        prefDB.selectAPI(AdvancedPrefDatabase.ID_DEFAULT);
    }

    /**
     * Change tags on an existing node
     */
    @Test
    public void existingNode() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Node n = (Node) App.getDelegator().getOsmElement(Node.NAME, 101792984);
        Assert.assertNotNull(n);

        main.performTagEdit(n, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);
        TestUtils.clickText(mDevice, true, main.getString(R.string.menu_tags), false);
        final String original = "Bergdietikon";
        final String edited = "dietikonBerg";
        mDevice.wait(Until.findObject(By.clickable(true).textStartsWith(original)), 500);
        UiObject editText = mDevice.findObject(new UiSelector().clickable(true).textStartsWith(original));
        try {
            editText.click(); // NOTE this seems to be necessary
            editText.setText(edited);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickUp(mDevice);
        Assert.assertEquals(edited, n.getTagWithKey(Tags.KEY_NAME));
    }

    /**
     * Add a tag to a new node
     */
    @Test
    public void newNode() {
        Logic logic = App.getLogic();
        Map map = main.getMap();
        logic.setZoom(map, 20);
        float tolerance = DataStyle.getCurrent().getWayToleranceValue();
        System.out.println("Tolerance " + tolerance);

        logic.setSelectedWay(null);
        logic.setSelectedNode(null);
        logic.setSelectedRelation(null);
        try {
            logic.performAdd(main, 1000.0f, 0.0f);
        } catch (OsmIllegalOperationException e1) {
            Assert.fail(e1.getMessage());
        }

        Node n = logic.getSelectedNode();
        Assert.assertNotNull(n);

        main.performTagEdit(n, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);
        TestUtils.clickText(mDevice, true, main.getString(R.string.tag_details), false);
        mDevice.wait(Until.findObject(By.clickable(true).res("de.blau.android:id/editKey")), 500);
        UiObject editText = mDevice.findObject(new UiSelector().clickable(true).resourceId("de.blau.android:id/editKey"));
        try {
            editText.setText("key");
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        editText = mDevice.findObject(new UiSelector().clickable(true).resourceId("de.blau.android:id/editValue"));
        try {
            editText.setText("value");
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickUp(mDevice);
        Assert.assertTrue(n.hasTag("key", "value"));
    }

    /**
     * Select an untagged node, then - apply restaurant preset - set cuisine and opening_hours
     */
    @Test
    public void node() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        main.getMap().getDataLayer().setVisible(true);
        TestUtils.stopEasyEdit(main);
        TestUtils.unlock();
        TestUtils.zoomToLevel(main, 21);
        // trying to get node click work properly is frustrating
        // TestUtils.clickAtCoordinates(main.getMap(), 8.3856255, 47.3894333, true);
        Node n = (Node) App.getDelegator().getOsmElement(Node.NAME, 599672192L);
        Assert.assertNotNull(n);
        final CountDownLatch signal2 = new CountDownLatch(1);
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main.getEasyEditManager().editElement(n);
                (new SignalHandler(signal2)).onSuccess();
            }
        });
        try {
            signal2.await(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertTrue(TestUtils.findText(mDevice, false, context.getString(R.string.actionmode_nodeselect)));
        // Node n = App.getLogic().getSelectedNode();
        // Assert.assertNotNull(n);

        Assert.assertTrue(TestUtils.clickMenuButton("Properties", false, true));
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);

        if (!((PropertyEditor) propertyEditor).paneLayout()) {
            Assert.assertTrue(TestUtils.clickText(mDevice, true, main.getString(R.string.tag_menu_preset), false));
        }
        boolean found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Facilities"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Food+Drinks"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetItemName("Restaurant"), true);
        Assert.assertTrue(found);
        UiObject2 cusine = null;
        try {
            cusine = getField("Cuisine", 1);
        } catch (UiObjectNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNotNull(cusine);
        cusine.click();
        Assert.assertTrue(TestUtils.clickText(mDevice, true, "Asian", false));
        Assert.assertTrue(TestUtils.clickText(mDevice, true, "German", false));
        Assert.assertTrue(TestUtils.clickText(mDevice, true, "SAVE", true));
        UiObject2 openingHours = null;
        try {
            openingHours = getField("Opening Hours", 1);
        } catch (UiObjectNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNotNull(openingHours);
        openingHours.click();
        Assert.assertTrue(TestUtils.findText(mDevice, false, context.getString(R.string.load_templates_title)));
        Assert.assertTrue(TestUtils.clickText(mDevice, false, "24 Hours", true));
        TestUtils.clickButton("de.blau.android:id/save", true);

        TestUtils.clickUp(mDevice);
        Assert.assertTrue(TestUtils.findText(mDevice, false, context.getString(R.string.actionmode_nodeselect)));
        Assert.assertTrue(n.hasTag("cuisine", "asian;german"));
        Assert.assertTrue(n.hasTag("opening_hours", "24/7"));
    }

    /**
     * Select a way and check if expected street name is there, then - check for max speed dropdown - check for bridge
     * and sidewalk:left checkboxes, change role in relation check that changed keys end up in the MRU tags, undo the
     * role change.
     */
    @Test
    public void way() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        main.getMap().getDataLayer().setVisible(true);
        TestUtils.stopEasyEdit(main);
        TestUtils.unlock();
        TestUtils.zoomToLevel(main, 22);
        TestUtils.clickAtCoordinates(main.getMap(), 8.3855809, 47.3892017, true);
        Assert.assertTrue(TestUtils.clickText(mDevice, false, "Kindhauserstrasse", false));
        Assert.assertTrue(TestUtils.findText(mDevice, false, context.getString(R.string.actionmode_wayselect)));
        Way w = App.getLogic().getSelectedWay();
        Assert.assertNotNull(w);

        Assert.assertTrue(TestUtils.clickMenuButton("Properties", false, true));
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);
        Assert.assertTrue(TestUtils.findText(mDevice, false, "Kindhauserstrasse"));
        try {
            UiObject2 valueField = getField("50", 1);
            // clicking doesn't work see https://issuetracker.google.com/issues/37017411
            valueField.click();
            valueField.setText("100");
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        //
        // Apply best preset
        Assert.assertTrue(TestUtils.clickMenuButton("Apply best preset", false, false));
        UiObject2 bridge = null;
        try {
            bridge = getField("Bridge", 1);
        } catch (UiObjectNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNotNull(bridge);
        bridge.click();
        UiObject2 sidewalk = null;
        try {
            sidewalk = getField("Sidewalk", 1);
        } catch (UiObjectNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNotNull(sidewalk);
        sidewalk.click();
        Assert.assertTrue(TestUtils.clickText(mDevice, true, "Only left side", true));

        //
        // switch to relation membership tab
        Assert.assertTrue(TestUtils.clickText(mDevice, true, main.getString(R.string.tag_details), false));
        Assert.assertTrue(TestUtils.clickText(mDevice, true, main.getString(R.string.relations), false));
        Assert.assertTrue(TestUtils.findText(mDevice, false, "bus route Bus 305"));
        try {
            UiObject2 roleField = getField("bus route Bus 305", 1);
            // clicking doesn't work see https://issuetracker.google.com/issues/37017411
            roleField.setText("platform");
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        // exit and test that everything has been set correctly
        TestUtils.clickUp(mDevice);
        Assert.assertTrue(TestUtils.findText(mDevice, false, context.getString(R.string.actionmode_wayselect)));
        Assert.assertTrue(w.hasTag("maxspeed", "100"));
        Assert.assertTrue(w.hasTag("bridge", "yes"));
        Assert.assertTrue(w.hasTag("sidewalk", "left"));

        List<Relation> parents = w.getParentRelations();
        Assert.assertNotNull(parents);
        Assert.assertTrue(findRole("platform", w, parents));
        TestUtils.clickMenuButton(context.getString(R.string.undo), false, true);
        Assert.assertFalse(findRole("platform", w, parents));

        //
        MRUTags mruTags = App.getMruTags();
        List<String> path = Arrays.asList(new String[] { "Highways", "Streets", "Tertiary" });
        PresetItem item = (PresetItem) Preset.getElementByPath(App.getCurrentRootPreset(context).getRootGroup(), new PresetElementPath(path));
        Assert.assertNotNull(item);
        Assert.assertTrue(mruTags.getValues(item, "bridge").contains("yes"));
        Assert.assertTrue(mruTags.getValues(item, "sidewalk").contains("left"));
        Assert.assertTrue(mruTags.getValues(item, "maxspeed").contains("100"));
        Assert.assertTrue(mruTags.getKeys(ElementType.WAY).contains("bridge"));
        Assert.assertTrue(mruTags.getKeys(ElementType.WAY).contains("sidewalk"));
        path = Arrays.asList(new String[] { "Transport", "Public Transport (Legacy)", "Public transport route (Legacy)" });
        item = (PresetItem) Preset.getElementByPath(App.getCurrentRootPreset(context).getRootGroup(), new PresetElementPath(path));
        Assert.assertNotNull(item);
        if (mruTags.getRoles(item) == null) { // hack: we matched the non-legacy version of the preset
            path = Arrays.asList(new String[] { "Transport", "Public Transport", "Public Transport Route (Bus)" });
            item = (PresetItem) Preset.getElementByPath(App.getCurrentRootPreset(context).getRootGroup(), new PresetElementPath(path));
            Assert.assertNotNull(item);
        }
        Assert.assertNotNull(mruTags.getRoles(item));
        Assert.assertTrue(mruTags.getRoles(item).contains("platform"));
    }

    /**
     * Check if the OsmELement e has a specific role in one of its parent relations
     * 
     * @param role the role
     * @param e the OsmElement
     * @param parents the parent Relations
     * @return true if the role was found
     */
    private boolean findRole(@NonNull String role, @NonNull OsmElement e, @NonNull List<Relation> parents) {
        for (Relation parent : parents) {
            List<RelationMember> members = parent.getAllMembers(e);
            for (RelationMember member : members) {
                if (role.equals(member.getRole())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Select a relation and check for a specific member
     */
    @Test
    public void relation() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Relation r = (Relation) App.getDelegator().getOsmElement(Relation.NAME, 2807173);
        Assert.assertNotNull(r);

        main.performTagEdit(r, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);

        TestUtils.clickText(mDevice, true, main.getString(R.string.tag_details), false);
        TestUtils.clickText(mDevice, true, main.getString(R.string.members), false);
        UiObject text = mDevice.findObject(new UiSelector().textStartsWith("Vorbühl"));
        Assert.assertTrue(text.exists());
    }

    /**
     * Test for max tag length
     */
    @SdkSuppress(minSdkVersion = 24)
    @Test
    public void maxTagLength() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Relation r = (Relation) App.getDelegator().getOsmElement(Relation.NAME, 2807173);
        Assert.assertNotNull(r);

        main.performTagEdit(r, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);

        String tooLongText = "This is a very long text string to test the that the API limit of 255 characters is enforced by the PropertyEditor by truncating and showing a toast."
                + "This is some more text so that we can actually test the limit by entering a string that is too long to trigger the check";

        TestUtils.clickText(mDevice, true, main.getString(R.string.menu_tags), false);

        mDevice.wait(Until.findObject(By.clickable(true).textStartsWith("Dietikon Bahnhof")), 500);
        UiObject editText = mDevice.findObject(new UiSelector().clickable(true).textStartsWith("Dietikon Bahnhof"));
        try {
            editText.click(); // NOTE this seems to be necessary
            editText.setText(tooLongText);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        TestUtils.clickText(mDevice, true, main.getString(R.string.tag_details), false);

        mDevice.wait(Until.findObject(By.clickable(true).textStartsWith("from")), 500);
        editText = mDevice.findObject(new UiSelector().clickable(true).textStartsWith("from"));
        try {
            editText.click(); // NOTE this seems to be necessary
            editText.setText(tooLongText);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        editText = mDevice.findObject(new UiSelector().clickable(true).textStartsWith("Kindhausen AG"));
        try {
            editText.click(); // NOTE this seems to be necessary
            editText.setText(tooLongText);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        TestUtils.clickText(mDevice, true, main.getString(R.string.members), false);

        mDevice.wait(Until.findObject(By.clickable(true).textStartsWith("stop")), 500);
        editText = mDevice.findObject(new UiSelector().clickable(true).textStartsWith("stop")); // this should be node
                                                                                                // #416064528
        try {
            editText.click(); // NOTE this seems to be necessary
            editText.setText(tooLongText);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        TestUtils.clickUp(mDevice); // close the PropertEditor and save

        Assert.assertEquals(255, r.getTagWithKey("to").length()); // value on the form

        String tooLongKey = tooLongText.substring(0, 255);
        Assert.assertTrue(r.hasTagKey(tooLongKey)); // key on details
        Assert.assertEquals(255, r.getTagWithKey(tooLongKey).length()); // value on details

        Assert.assertEquals(255, r.getMember(Node.NAME, 416064528).getRole().length()); // role of node #416064528
    }

    /**
     * Get the translated name of a preset group
     * 
     * @param name the group name
     * @return the translated name
     */
    String getTranslatedPresetGroupName(String name) {
        String result = null;
        Preset[] presets = App.getCurrentPresets(main);
        for (Preset p : presets) {
            if (p != null) {
                PresetGroup group = p.getGroupByName(name);
                if (group != null) {
                    return group.getTranslatedName();
                }
            }
        }
        return result;
    }

    /**
     * Get the translated name of a preset item
     * 
     * @param name the item name
     * @return the translated name
     */
    String getTranslatedPresetItemName(String name) {
        String result = null;
        Preset[] presets = App.getCurrentPresets(main);
        for (Preset p : presets) {
            if (p != null) {
                PresetItem item = p.getItemByName(name);
                if (item != null) {
                    return item.getTranslatedName();
                }
            }
        }
        return result;
    }

    /**
     * Navigate to a specific preset item
     */
    @Test
    public void presets() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Way w = (Way) App.getDelegator().getOsmElement(Way.NAME, 27009604L);
        Assert.assertNotNull(w);

        main.performTagEdit(w, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);

        if (!((PropertyEditor) propertyEditor).paneLayout()) {
            Assert.assertTrue(TestUtils.clickText(mDevice, true, main.getString(R.string.tag_menu_preset), false));
        }
        boolean found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Highways"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Streets"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetItemName("Motorway"), true);
        Assert.assertTrue(found);
    }
    
    /**
     * Navigate to a specific preset item
     */
    @Test
    public void applyOptional() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Way w = (Way) App.getDelegator().getOsmElement(Way.NAME, 27009604L);
        Assert.assertNotNull(w);

        main.performTagEdit(w, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);

        if (!((PropertyEditor) propertyEditor).paneLayout()) {
            Assert.assertTrue(TestUtils.clickText(mDevice, true, main.getString(R.string.tag_menu_preset), false));
        }
        boolean found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Highways"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Ways"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetItemName("Steps"), true);
        Assert.assertTrue(found);
        TestUtils.clickButton("de.blau.android:id/tag_menu_apply_preset", false);
        mDevice.waitForIdle(1000);
        UiObject2 handrail = null;
        try {
            handrail = getField("Handrail", 1);
        } catch (UiObjectNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNotNull(handrail);
        handrail.click();
        
        UiObject2 overtaking = null;
        try {
            overtaking = getField("Overtaking", 1);
        } catch (UiObjectNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNotNull(overtaking);
        overtaking.click();
        mDevice.waitForIdle(1000);
        TestUtils.clickText(mDevice, true, "In way direction", false);
        TestUtils.clickText(mDevice, true, "Save", true);
        Assert.assertTrue(TestUtils.findText(mDevice, false, "In way direction"));
    }


    /**
     * Add a conditional restriction, this is just a rough test without using the actual UI elements of the editor
     */
    @Test
    public void conditionalRestrictionEditor() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Way w = (Way) App.getDelegator().getOsmElement(Way.NAME, 27009604L);
        Assert.assertNotNull(w);

        main.performTagEdit(w, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);

        if (!((PropertyEditor) propertyEditor).paneLayout()) {
            Assert.assertTrue(TestUtils.clickText(mDevice, true, main.getString(R.string.tag_menu_preset), false));
        }
        boolean found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Highways"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetGroupName("Streets"), true);
        Assert.assertTrue(found);
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetItemName("Residential"), true);
        Assert.assertTrue(found);
        Assert.assertTrue(TestUtils.findText(mDevice, false, "Kindhauserstrasse"));
        found = TestUtils.clickText(mDevice, true, getTranslatedPresetItemName("Cond. & direct. max speed"), true);
        Assert.assertTrue(found);
        UiObject2 conditionalMaxSpeed = null;
        try {
            conditionalMaxSpeed = getField("Max speed @", 1);
        } catch (UiObjectNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNotNull(conditionalMaxSpeed);
        conditionalMaxSpeed.click();
        Assert.assertTrue(TestUtils.findText(mDevice, false, "50 @"));
        TestUtils.clickButton("de.blau.android:id/save", true);
    }

    /**
     * Add a tag and check if that has added a preset item to the MRU display
     */
    @Test
    public void presetsViaManualTag() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Way w = (Way) App.getDelegator().getOsmElement(Way.NAME, 210468113L);
        Assert.assertNotNull(w);
        try {
            logic.setTags(main, w, null);
        } catch (OsmIllegalOperationException e) {
            Assert.fail(e.getMessage());
        }

        main.performTagEdit(w, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);

        TestUtils.clickText(mDevice, true, main.getString(R.string.tag_details), false);
        mDevice.wait(Until.findObject(By.clickable(true).res("de.blau.android:id/editValue")), 500);

        UiObject keyEditText = mDevice.findObject(new UiSelector().clickable(true).resourceId("de.blau.android:id/editKey"));
        UiObject valueEditText = mDevice.findObject(new UiSelector().clickable(true).resourceId("de.blau.android:id/editValue"));

        String key = Tags.KEY_BUILDING;
        String value = Tags.VALUE_YES;
        try {
            keyEditText.click();
            keyEditText.setText(key);
            valueEditText.click();
            valueEditText.setText(value);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        if (!((PropertyEditor) propertyEditor).paneLayout()) {
            TestUtils.clickText(mDevice, true, main.getString(R.string.menu_tags), false);
        }
        PresetItem presetItem = ((PropertyEditor) propertyEditor).getBestPreset();
        Assert.assertNotNull(presetItem);
        Assert.assertEquals("Building", presetItem.getName());
        Assert.assertTrue(TestUtils.clickText(mDevice, true, presetItem.getTranslatedName(), false)); // building preset
                                                                                                      // should now be
                                                                                                      // added to MRU
    }

    /**
     * Test that keys with empty values get removed
     */
    @Test
    public void emptyKey() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockServer.enqueue("capabilities1");
        mockServer.enqueue("download1");
        Logic logic = App.getLogic();
        logic.downloadBox(main, new BoundingBox(8.3879800D, 47.3892400D, 8.3844600D, 47.3911300D), false, new SignalHandler(signal));
        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Node n = (Node) logic.performAddNode(main, 1.0, 1.0);
        Assert.assertNotNull(n);

        main.performTagEdit(n, null, false, false, false);
        Activity propertyEditor = instrumentation.waitForMonitorWithTimeout(monitor, 30000);
        Assert.assertTrue(propertyEditor instanceof PropertyEditor);
        TestUtils.clickText(mDevice, true, main.getString(R.string.tag_details), false);
        mDevice.wait(Until.findObject(By.clickable(true).res("de.blau.android:id/editValue")), 500);
        UiObject editText = mDevice.findObject(new UiSelector().clickable(true).resourceId("de.blau.android:id/editValue"));
        String edited = "edited";
        try {
            editText.click(); // NOTE this seems to be necessary
            editText.setText(edited);
        } catch (UiObjectNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        TestUtils.clickUp(mDevice);
        Assert.assertFalse(n.hasTag("", edited));
    }

    /**
     * Get the value field for a specific key
     * 
     * @param text the text display for the key
     * @param fieldIndex TODO
     * @return an UiObject2 for the value field
     * @throws UiObjectNotFoundException if we couldn't find the object with text
     */
    private UiObject2 getField(@NonNull String text, int fieldIndex) throws UiObjectNotFoundException {
        UiScrollable appView = new UiScrollable(new UiSelector().scrollable(true));
        appView.scrollIntoView(new UiSelector().text(text));
        BySelector bySelector = By.textStartsWith(text);
        UiObject2 keyField = mDevice.wait(Until.findObject(bySelector), 500);
        UiObject2 linearLayout = keyField.getParent();
        if (!linearLayout.getClassName().equals("android.widget.LinearLayout")) {
            // some of the text fields are nested one level deeper
            linearLayout = linearLayout.getParent();
        }
        return linearLayout.getChildren().get(fieldIndex);
    }
}

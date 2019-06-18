package de.blau.android.easyedit;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import de.blau.android.App;
import de.blau.android.Logic;
import de.blau.android.Main;
import de.blau.android.Map;
import de.blau.android.R;
import de.blau.android.SignalHandler;
import de.blau.android.TestUtils;
import de.blau.android.osm.ApiTest;
import de.blau.android.osm.OsmElement;
import de.blau.android.osm.Relation;
import de.blau.android.osm.RelationMember;
import de.blau.android.osm.Way;
import de.blau.android.prefs.AdvancedPrefDatabase;
import de.blau.android.prefs.Preferences;
import de.blau.android.resources.TileLayerServer;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RelationTest {

    Context              context = null;
    AdvancedPrefDatabase prefDB  = null;
    Main                 main    = null;
    UiDevice             device  = null;
    Map                  map     = null;
    Logic                logic   = null;

    @Rule
    public ActivityTestRule<Main> mActivityRule = new ActivityTestRule<>(Main.class);

    /**
     * Pre-test setup
     */
    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        main = mActivityRule.getActivity();
        Preferences prefs = new Preferences(context);
        prefs.setBackGroundLayer(TileLayerServer.LAYER_NONE); // try to avoid downloading tiles
        prefs.setOverlayLayer(TileLayerServer.LAYER_NOOVERLAY);
        map = main.getMap();
        map.setPrefs(main, prefs);
        TestUtils.grantPermissons();
        TestUtils.dismissStartUpDialogs(main);
        final CountDownLatch signal1 = new CountDownLatch(1);
        logic = App.getLogic();
        logic.deselectAll();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream("test2.osm");
        logic.readOsmFile(main, is, false, new SignalHandler(signal1));
        try {
            signal1.await(ApiTest.TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        try {
            is.close();
        } catch (IOException e1) {
        }
        TestUtils.stopEasyEdit(main);
    }

    /**
     * Post-test teardown
     */
    @After
    public void teardown() {
        logic.deselectAll();
        TestUtils.zoomToLevel(main, 18);
    }

    /**
     * Select, show info dialog, delete, undelete
     */
    @Test
    public void selectRelation() {
        map.getDataLayer().setVisible(true);
        TestUtils.unlock();
        TestUtils.zoomToLevel(main, 21);
        TestUtils.clickAtCoordinates(map, 8.3893820, 47.3895626, true);
        Assert.assertTrue(TestUtils.clickText(device, false, "Hiking", false));
        List<Relation> rels = App.getLogic().getSelectedRelations();
        Assert.assertNotNull(rels);
        Assert.assertEquals(1, rels.size());
        Relation relation = rels.get(0);
        List<RelationMember> origMembers = relation.getMembers();
        Assert.assertEquals(6490362L, relation.getOsmId());
        Assert.assertTrue(TestUtils.findText(device, false, context.getString(R.string.actionmode_relationselect)));
        Assert.assertTrue(TestUtils.clickOverflowButton());
        Assert.assertTrue(TestUtils.clickText(device, false, context.getString(R.string.menu_information), true));
        Assert.assertTrue(TestUtils.findText(device, false, "hiking"));
        Assert.assertTrue(TestUtils.clickText(device, false, context.getString(R.string.done), true));
        Assert.assertTrue(TestUtils.clickMenuButton(context.getString(R.string.delete), false, true));
        Assert.assertEquals(OsmElement.STATE_DELETED, relation.getState());
        Assert.assertTrue(TestUtils.clickMenuButton(context.getString(R.string.undo), false, false));
        Assert.assertEquals(OsmElement.STATE_UNCHANGED, relation.getState());
        Assert.assertNotNull(relation.getMember(Way.NAME, 104148456L));
        List<RelationMember> members = relation.getMembers();
        Assert.assertEquals(origMembers.size(), members.size());
        for (int i = 0; i < members.size(); i++) {
            Assert.assertEquals(origMembers.get(i), members.get(i));
        }
    }
}

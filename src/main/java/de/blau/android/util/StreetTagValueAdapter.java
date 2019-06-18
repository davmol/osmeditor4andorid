/**
 * TagKeyAutocompletionAdapter.java created: 12.06.2010 10:43:37 (c) 2010 by <a href="http://Wolschon.biz">Wolschon
 * Softwaredesign und Beratung</a> This file is part of OSMEditor by Marcus Wolschon
 * <a href="mailto:Marcus@Wolscon.biz">Marcus@Wolscon.biz</a>. You can purchase support for a sensible hourly rate or a
 * commercial license of this file (unless modified by others) by contacting him directly.
 *
 * OSMEditor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OSMEditor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with OSMEditor. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 ***********************************
 * Editing this file: -For consistent code-quality this file should be checked with the checkstyle-ruleset enclosed in
 * this project. -After the design of this file has settled it should get it's own JUnit-Test that shall be executed
 * regularly. It is best to write the test-case BEFORE writing this class and to run it on every build as a
 * regression-test.
 */
package de.blau.android.util;

// other imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import de.blau.android.exception.OsmException;
import de.blau.android.osm.StorageDelegator;
import de.blau.android.presets.ValueWithCount;
import de.blau.android.propertyeditor.PropertyEditor;

/**
 * Project: OSMEditor<br/>
 * TagKeyAutocompletionAdapter.java<br/>
 * created: 12.06.2010 10:43:37 <br/>
 * <br/>
 * <br/>
 * <b>Adapter for the {@link AutoCompleteTextView} in the {@link PropertyEditor} that is for the VALUE for the key
 * "addr:street" .</a>
 * 
 * @author <a href="mailto:Marcus@Wolschon.biz">Marcus Wolschon</a>
 */
public class StreetTagValueAdapter extends ArrayAdapter<ValueWithCount> {

    /**
     * The tag we use for Android-logging.
     */
    @SuppressWarnings("unused")
    private static final String DEBUG_TAG = StreetTagValueAdapter.class.getName();

    private ElementSearch es;

    /**
     * Get an Adapter containing near by street names
     * 
     * @param aContext Android context
     * @param aTextViewResourceId the resource id of the AutoCompleteTextView
     * @param delegator the current StorageDelegator instance
     * @param osmElementType the type of OsmElement
     * @param osmId the id of the OsmElement
     * @param extraValues any existing values
     */
    public StreetTagValueAdapter(@NonNull final Context aContext, final int aTextViewResourceId, @NonNull final StorageDelegator delegator,
            @NonNull final String osmElementType, final long osmId, @Nullable List<String> extraValues) {
        super(aContext, aTextViewResourceId);
        Log.d(DEBUG_TAG, "constructor called");

        HashMap<String, Integer> counter = new HashMap<>();
        if (extraValues != null && !extraValues.isEmpty()) {
            for (String t : extraValues) {
                if (t.equals("")) {
                    continue;
                }
                if (counter.containsKey(t)) {
                    counter.put(t, counter.get(t) + 1);
                } else {
                    counter.put(t, 1);
                }
            }
            ArrayList<String> keys = new ArrayList<>(counter.keySet());
            Collections.sort(keys);
            for (String t : keys) {
                ValueWithCount v = new ValueWithCount(t, counter.get(t));
                super.add(v);
            }
        }

        int[] center = Util.getCenter(delegator, osmElementType, osmId);
        if (center != null) {
            es = new ElementSearch(center, false);
            for (String s : es.getStreetNames()) {
                if (counter.size() > 0 && counter.containsKey(s)) {
                    continue; // skip values that we already have
                }
                ValueWithCount v = new ValueWithCount(s);
                super.add(v);
            }
        } else {
            Log.e(DEBUG_TAG, "center for " + osmElementType + " " + osmId + " is null");
        }
    }

    /**
     * Get the names in this adapter
     * 
     * @return a String array containing the names
     */
    public String[] getNames() {
        return es.getStreetNames();
    }

    /**
     * Get the osm id for a specific street
     * 
     * @param name the name
     * @return the osm id
     * @throws OsmException
     */
    public long getId(String name) throws OsmException {
        return es.getStreetId(name);
    }

    /**
     * This avoids generating everything twice
     * 
     * @return the ElementSearch instance used to create the adapter
     */
    public ElementSearch getElementSearch() {
        return es;
    }
}

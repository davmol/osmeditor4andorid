package de.blau.android.prefs;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.util.Log;
import de.blau.android.R;

public class AdvancedPrefEditorFragment extends ExtendedPreferenceFragment {

    private static final String DEBUG_TAG = "AdvancedPrefEditor";

    private Resources    r;
    AdvancedPrefDatabase db;
    private String       KEY_PREFAPI;
    private String       KEY_PREFLOGIN;
    private String       KEY_PREFGEOCODER;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Log.d(DEBUG_TAG, "onCreatePreferences " + rootKey);
        setPreferencesFromResource(R.xml.advancedpreferences, rootKey);
        r = getResources();
        KEY_PREFAPI = r.getString(R.string.config_api_button_key);
        KEY_PREFLOGIN = r.getString(R.string.config_loginbutton_key);
        KEY_PREFGEOCODER = r.getString(R.string.config_geocoder_button_key);
        setOnPreferenceClickListeners();
        setTitle();
        db = new AdvancedPrefDatabase(getActivity());
    }

    @Override
    public void onResume() {
        Log.d(DEBUG_TAG, "onResume");
        super.onResume();
        Preference apipref = getPreferenceScreen().findPreference(KEY_PREFAPI);
        if (apipref != null) {
            API current = db.getCurrentAPI();
            if (current.id.equals(AdvancedPrefDatabase.ID_DEFAULT)) {
                apipref.setSummary(R.string.config_apibutton_summary);
            } else {
                apipref.setSummary(current.name.equals("") ? current.url : current.name);
            }
            Preference loginpref = getPreferenceScreen().findPreference(KEY_PREFLOGIN);
            if (loginpref != null) {
                loginpref.setSummary(current.user != null && !"".equals(current.user) ? current.user : r.getString(R.string.config_username_summary));
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Util.setListPreferenceSummary(this, R.string.config_fullscreenMode_key);
        }
        Util.setListPreferenceSummary(this, R.string.config_mapOrientation_key);
        Util.setListPreferenceSummary(this, R.string.config_gps_source_key);
        Util.setEditTextPreferenceSummary(this, R.string.config_offsetServer_key);
        Util.setEditTextPreferenceSummary(this, R.string.config_osmoseServer_key);
        setTitle();
    }

    /**
     * Set listeners on special Preference entries
     * 
     * If we are just showing a sub-PreferenceScreen some of the keys may not be accessible
     */
    private void setOnPreferenceClickListeners() {
        Preference apiPref = getPreferenceScreen().findPreference(KEY_PREFAPI);
        if (apiPref != null) {
            apiPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.d(DEBUG_TAG, "onPreferenceClick 2");
                    APIEditorActivity.start(getActivity());
                    return true;
                }
            });
        }

        Preference geocoderPref = getPreferenceScreen().findPreference(KEY_PREFGEOCODER);
        if (geocoderPref != null) {
            geocoderPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.d(DEBUG_TAG, "onPreferenceClick");
                    GeocoderEditorActivity.start(getActivity());
                    return true;
                }
            });
        }
    }
}

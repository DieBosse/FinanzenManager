package com.example.mhaslehner.finanzmanager;

import android.animation.Animator;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by HP on 09.06.2016.
 */
public class PreferenceActivity extends android.preference.PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MyPreferenceFragment()).commit();
    }

    private class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
            addPreferencesFromResource(R.xml.prefs);
            return super.onCreateAnimator(transit, enter, nextAnim);

        }
    }
}

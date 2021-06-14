package com.wa.rumbo.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by webastral on 29/9/17.
 */

public class CommonData {
    private Context _context;
    private SharedPreferences shared;
    private String SHARED_NAME = "user_data";
    private SharedPreferences.Editor edit;

    public CommonData(Context c) {
        _context = c;
        shared = _context.getSharedPreferences(SHARED_NAME,	Context.MODE_PRIVATE);
        edit = shared.edit();
    }

    // ============================================//
    public void save(String key, String value) {
        edit.putString(key, value);
        edit.commit();
    }

	/*
	 * // ============================================// public void save(String
	 * key, int value) { edit.putInt(key, value); edit.commit(); }
	 */

    // ============================================//
    public void saveFloat(String key, float value) {
        edit.putFloat(key, value);
        edit.commit();
    }

    // ============================================//
    public void saveInt(String key, int value) {
        edit.putInt(key, value);
        edit.commit();
    }

    // ============================================//
    public void saveBoolean(String key, boolean value) {
        edit.putBoolean(key, value);
        edit.commit();
    }
    //===============================================//
    public void saveStringSet(String key, Set<String> values)
    {
        edit.putStringSet(key, values);
        edit.commit();
    }
    // ============================================//
    public void saveLong(String key, Long value) {
        edit.putLong(key, value);
        edit.commit();
    }

    // ============================================//
    public Long getLong(String key) {
        return shared.getLong(key, 0);

    }
    // ============================================//
    public String getString(String key) {
        return shared.getString(key, "");

    }
    // ============================================//
    public int getInt(String key) {
        return shared.getInt(key, 0);

    }

    // ============================================//
    public float getFloat(String key) {
        return shared.getFloat(key, 0);

    }

    //=============================================//

    public Set<String> getStringSet(String key)
    {
        return shared.getStringSet(key, null);
    }

    // ============================================//
    public boolean getBoolean(String key) {
        return shared.getBoolean(key, false);

    }

    // ============================================//
    public boolean isExist(String key) {
        return shared.contains(key);

    }


    // ============================================//
    public void remove(String key) {
        edit.remove(key);
        edit.commit();
    }

    public void clear()
    {
        edit.clear();
        edit.commit();
    }
}

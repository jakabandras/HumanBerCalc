package com.andrewsoft.humanbercalc;

import java.util.Map;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.*;
import android.widget.Toast;

public class NewSettingsActivity extends PreferenceActivity implements
    OnSharedPreferenceChangeListener
{
  private Map<String, ?> mPrefs;

  public NewSettingsActivity( )
  {
    // TODO Auto-generated constructor stub
  }

  private void RollBack( SharedPreferences preferences , String key )
  {
    String mValue = (String) mPrefs.get(key);
    preferences.edit().putString(key, mValue);
    preferences.edit().apply();
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.myprefs);
    SharedPreferences settings = PreferenceManager
        .getDefaultSharedPreferences(this);
    settings.registerOnSharedPreferenceChangeListener(this);
    mPrefs = settings.getAll();
  }

  @Override
  public void onSharedPreferenceChanged( SharedPreferences sharedPreferences ,
      String key )
  {
    // TODO Auto-generated method stub
    String s = sharedPreferences.getString(key, "");

    if (s == "")
    {
      Toast toast = Toast.makeText(this, R.string.toast_pref_ures,
          Toast.LENGTH_SHORT);
      toast.show();
      RollBack(sharedPreferences, key);
    }
    Integer mTmp = 0;
    try
    {
      mTmp = Integer.parseInt(s);
    }
    catch (NumberFormatException e)
    {
      Toast toast = Toast.makeText(this, R.string.toast_pref_hibas,
          Toast.LENGTH_SHORT);
      toast.show();
      RollBack(sharedPreferences, key);
    }
    if (mTmp == 0)
    {
      Toast toast = Toast.makeText(this, R.string.toast_pref_nulla,
          Toast.LENGTH_SHORT);
      toast.show();
      RollBack(sharedPreferences, key);
    }
    if (mTmp < 0)
    {
      Toast toast = Toast.makeText(this, R.string.toast_pref_minusz,
          Toast.LENGTH_SHORT);
      toast.show();
      RollBack(sharedPreferences, key);
    }
  }
}

package com.andrewsoft.humanbercalc;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.*;

public class NewSettingsActivity extends PreferenceActivity implements
    OnSharedPreferenceChangeListener
{

  public NewSettingsActivity( )
  {
    // TODO Auto-generated constructor stub
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
  }

  @Override
  public void onSharedPreferenceChanged( SharedPreferences sharedPreferences ,
      String key )
  {
    // TODO Auto-generated method stub

  }

}

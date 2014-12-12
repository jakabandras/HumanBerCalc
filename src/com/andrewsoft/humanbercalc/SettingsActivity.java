/**
 * 
 */
package com.andrewsoft.humanbercalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * @author Andrew
 * 
 */
public class SettingsActivity extends Activity
{

  /**
   * 
   */
  private MyConfig mycfg;

  public SettingsActivity( )
  {
    // TODO Auto-generated constructor stub
    setMycfg(new MyConfig(this.getBaseContext()));
  }

  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings);
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu )
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.settings_menu, menu);
    return true;
  }

  /**
   * @return the mycfg
   */
  public MyConfig getMycfg( )
  {
    return mycfg;
  }

  /**
   * @param mycfg
   *          the mycfg to set
   */
  private void setMycfg( MyConfig mycfg )
  {
    this.mycfg = mycfg;
  }
}

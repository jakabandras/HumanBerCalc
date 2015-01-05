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
  private MyConfig mycfg = null;

  public SettingsActivity( )
  {
    // TODO Auto-generated constructor stub
    MyConfig cfg = new MyConfig(this.getParent().getBaseContext());
    setMycfg(cfg);
  }

  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings);
    if (mycfg == null)
    {
      MyConfig cfg = new MyConfig(this.getApplicationContext());
      setMycfg(cfg);
    }

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

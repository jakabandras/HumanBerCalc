/**
 * 
 */
package com.andrewsoft.humanbercalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

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
    // MyConfig cfg = new MyConfig(this.getParent().getBaseContext());
    // setMycfg(cfg);
  }

  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings);
    if (mycfg == null)
    {
      String xmlconfig = getIntent().getStringExtra("DataPath");
      MyConfig cfg = new MyConfig(xmlconfig);
      setMycfg(cfg);
    }
    EditText etTmp = (EditText) findViewById(R.id.edit8de);
    etTmp.setText(((Integer) mycfg.ber_8_de).toString());
    etTmp = (EditText) findViewById(R.id.edit8du);
    etTmp.setText(((Integer) mycfg.ber_8_du).toString());
    etTmp = (EditText) findViewById(R.id.edit8ej);
    etTmp.setText(((Integer) mycfg.ber_8_ej).toString());
    etTmp = (EditText) findViewById(R.id.edit8szo);
    etTmp.setText(((Integer) mycfg.ber_8_szo).toString());
    etTmp = (EditText) findViewById(R.id.edit8va);
    etTmp.setText(((Integer) mycfg.ber_8_va).toString());
    etTmp = (EditText) findViewById(R.id.edit12na);
    etTmp.setText(((Integer) mycfg.ber_12_na).toString());
    etTmp = (EditText) findViewById(R.id.edit12ej);
    etTmp.setText(((Integer) mycfg.ber_12_ej).toString());
    etTmp = (EditText) findViewById(R.id.edit12szo);
    etTmp.setText(((Integer) mycfg.ber_12_szo).toString());
    etTmp = (EditText) findViewById(R.id.edit12va);
    etTmp.setText(((Integer) mycfg.ber_12_va).toString());
    etTmp = (EditText) findViewById(R.id.editSzabi);
    etTmp.setText(((Integer) mycfg.ber_szabi).toString());
    etTmp = (EditText) findViewById(R.id.editFizUnn);
    etTmp.setText(((Integer) mycfg.ber_fiz_unn).toString());
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

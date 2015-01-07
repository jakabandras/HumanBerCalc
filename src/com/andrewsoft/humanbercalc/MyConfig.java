/**
 * 
 */
package com.andrewsoft.humanbercalc;

import java.io.File;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

//import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

/**
 * @author Andrew
 * 
 */
@Root
@SuppressWarnings("unused")
public class MyConfig
{

  /**
   * 
   */
  // private final Context mycontext;
  private Serializer ser;
  private File       config_file;

  @Element
  public int         ber_8_de    = 1;
  @Element
  public int         ber_8_du    = 1;
  @Element
  public int         ber_8_ej    = 1;
  @Element
  public int         ber_8_szo   = 1;
  @Element
  public int         ber_8_va    = 1;
  @Element
  public int         ber_12_na   = 1;
  @Element
  public int         ber_12_ej   = 1;
  @Element
  public int         ber_12_szo  = 1;
  @Element
  public int         ber_12_va   = 1;
  @Element
  public int         ber_szabi   = 1;
  @Element
  public int         ber_fiz_unn = 1;

  public MyConfig( )
  {

  }

  public MyConfig( String xmlPath )
  {
    // TODO Auto-generated constructor stub
    String s = "";
    config_file = new File(xmlPath + "/MyConfig.xml");
    ser = new Persister();
    if (config_file.exists()) try
    {
      read_config();
    }
    catch (Exception e1)
    {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    else try
    {
      def_config();
    }
    catch (Exception e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void def_config( ) throws Exception
  {
    // TODO Auto-generated method stub
    ser.write(this, config_file);
  }

  public void saveConfig( ) throws Exception
  {
    def_config();
  }

  private void read_config( ) throws Exception
  {
    // TODO Auto-generated method stub
    MyConfig cfg = ser.read(MyConfig.class, config_file);
    this.ber_12_ej = cfg.ber_12_ej;
    this.ber_12_na = cfg.ber_12_na;
    this.ber_12_szo = cfg.ber_12_szo;
    this.ber_12_va = cfg.ber_12_va;
    this.ber_8_de = cfg.ber_8_de;
    this.ber_8_du = cfg.ber_8_du;
    this.ber_8_ej = cfg.ber_8_ej;
    this.ber_8_szo = cfg.ber_8_szo;
    this.ber_8_va = cfg.ber_8_va;
    this.ber_fiz_unn = cfg.ber_fiz_unn;
    this.ber_szabi = cfg.ber_szabi;
  }

}

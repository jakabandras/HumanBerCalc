package com.andrewsoft.humanbercalc;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.*;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings(
  { "unused", "deprecation" })
public class MainActivity extends ActionBarActivity implements
    ActionBar.OnNavigationListener
{

  /**
   * The serialization (saved instance state) Bundle key representing the
   * current dropdown position.
   */
  private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

  static AlertDialog          AlertDialogStores;
  int                         actMonth;
  View                        rv1                            = null;
  WorkTime                    wt;
  MyEventClass                actEvents;
  SharedPreferences           myPrefs;

  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);
    wt = new WorkTime();
    setContentView(R.layout.activity_main);
    Date dt = new Date();
    actMonth = dt.getMonth();
    actEvents = new MyEventClass(this);
    myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    // myPrefs.

    // Set up the action bar to show a dropdown list.
    final ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

    // Set up the dropdown list navigation in the action bar.
    actionBar.setListNavigationCallbacks(
    // Specify a SpinnerAdapter to populate the dropdown list.
        new ArrayAdapter<String>(actionBar.getThemedContext(),
            android.R.layout.simple_list_item_1, android.R.id.text1,
            new String[]
              { getString(R.string.title_section1),
                  getString(R.string.title_section2),
                  getString(R.string.title_section3), }), this);
  }

  // protected void onCreateView

  @Override
  public void onRestoreInstanceState( Bundle savedInstanceState )
  {
    // Restore the previously serialized current dropdown position.
    if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
    {
      getSupportActionBar().setSelectedNavigationItem(
          savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
    }
  }

  @Override
  public void onSaveInstanceState( Bundle outState )
  {
    // Serialize the current dropdown position.
    outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getSupportActionBar()
        .getSelectedNavigationIndex());
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu )
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected( MenuItem item )
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    switch (id)
    {
    case R.id.action_exit:
      finish();
      break;
    case R.id.newSettings:
      Intent setIntent = new Intent(this, NewSettingsActivity.class);
      startActivity(setIntent);
      return true;
    case R.id.action_settings:
      Toast toast = Toast.makeText(this,
          "8 óra délelőtt:" + myPrefs.getString("ber8de", "1"),
          Toast.LENGTH_LONG);
      toast.show();
      break;
    }

    /*
     * if (id == R.id.action_settings) { PackageManager pm =
     * this.getPackageManager(); String mypath = ""; try { mypath =
     * pm.getPackageInfo(getPackageName(), 0).applicationInfo.dataDir; } catch
     * (NameNotFoundException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); }
     * 
     * Intent myIntent = new Intent(this, SettingsActivity.class);
     * myIntent.putExtra("DataPath", mypath); startActivity(myIntent); return
     * true; }
     */return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected( int position , long id )
  {
    // When the given dropdown item is selected, show its contents in the
    // container view.
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
        .commit();
    return true;
  }

  public static void ShowPopUp( Context context )
  {
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
    ObjectItem[] items = new ObjectItem[12];

    for (int i = 0; i < 12; i++)
    {
      dt.setMonth(i);
      items[i] = new ObjectItem(i, sdf.format(dt));
    }
    ArrayAdapterItem adapter = new ArrayAdapterItem(context,
        R.layout.list_view_row_item, items);
    ListView lv = new ListView(context);
    lv.setAdapter(adapter);
    lv.setOnItemClickListener(new OnItemClickListenerListViewItem());

    AlertDialogStores = new AlertDialog.Builder(context).setView(lv)
        .setTitle("Hónap kiválasztása").show();
  }

  @SuppressWarnings("static-access")
  private int Hetvege( long dt )
  {
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(cal.MONDAY);
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date dat = new Date();
    dat.setTime(dt);
    cal.setTime(dat);
    String strDate = sdf.format(dat);
    int dow = cal.get(cal.DAY_OF_WEEK);
    if (strDate == "szombat") return 2;
    if (strDate == "vasárnap") return 3;
    return 1;
  }

  public void SetWorkTimeText( )
  {
    ArrayList<MyEventInstance> events = actEvents.GetMonthEvents(actMonth);
    wt.SetToZero();
    for (int i = 0; (i < events.size()); i++)
    {
      MyEventInstance aEvent = events.get(i);
      switch (aEvent.Title)
      {
      case "8 óra délelőtt":
        if (Hetvege(aEvent.stDate) == 1) // H�tk�znap
        {
          wt.work_8_de = wt.work_8_de + 1;
        } else if (Hetvege(aEvent.stDate) == 2) // Szombat
        {
          wt.work_8_szo = wt.work_8_szo + 1;
        } else wt.work_8_va = wt.work_8_va + 1;
        break;
      case "8 óra délután":
        if (Hetvege(aEvent.stDate) == 1) // H�tk�znap
        {
          wt.work_8_du = wt.work_8_du + 1;
        } else if (Hetvege(aEvent.stDate) == 2) // Szombat
        {
          wt.work_8_szo = wt.work_8_szo + 1;
        } else wt.work_8_va = wt.work_8_va + 1;
        break;
      case "8 óra éjszaka":
        if (Hetvege(aEvent.stDate) == 1) // H�tk�znap
        {
          wt.work_8_ej = wt.work_8_ej + 1;
        } else if (Hetvege(aEvent.stDate) == 2) // Szombat
        {
          wt.work_8_szo = wt.work_8_szo + 1;
        } else wt.work_8_va = wt.work_8_va + 1;
        break;
      case "12 óra nappal":
        if (Hetvege(aEvent.stDate) == 1) // H�tk�znap
        {
          wt.work_12_na = wt.work_12_na + 1;
        } else if (Hetvege(aEvent.stDate) == 2) // Szombat
        {
          wt.work_12_szo = wt.work_12_szo + 1;
        } else wt.work_12_va = wt.work_12_va + 1;
        break;
      case "12 óra éjszaka":
        if (Hetvege(aEvent.stDate) == 1) // H�tk�znap
        {
          wt.work_12_ej = wt.work_12_ej + 1;
        } else if (Hetvege(aEvent.stDate) == 2) // Szombat
        {
          wt.work_12_szo = wt.work_12_szo + 1;
        } else wt.work_12_va = wt.work_12_va + 1;
        break;
      case "Szabadság":
        wt.work_szabi = wt.work_szabi + 1;
        break;
      case "Fiz.ünn.":
        wt.work_fiz_unn = wt.work_fiz_unn + 1;
        break;
      }
    }

    TextView tv = (TextView) rv1.findViewById(R.id.txt8de);
    tv.setText(((Integer) wt.work_8_de).toString());
    tv = (TextView) rv1.findViewById(R.id.txt8du);
    tv.setText(((Integer) wt.work_8_du).toString());
    tv = (TextView) rv1.findViewById(R.id.txt8ej);
    tv.setText(((Integer) wt.work_8_ej).toString());
    tv = (TextView) rv1.findViewById(R.id.txt8szo);
    tv.setText(((Integer) wt.work_8_szo).toString());
    tv = (TextView) rv1.findViewById(R.id.txt8va);
    tv.setText(((Integer) wt.work_8_va).toString());
    tv = (TextView) rv1.findViewById(R.id.txt12na);
    tv.setText(((Integer) wt.work_12_na).toString());
    tv = (TextView) rv1.findViewById(R.id.txt12ej);
    tv.setText(((Integer) wt.work_12_ej).toString());
    tv = (TextView) rv1.findViewById(R.id.txt12szo);
    tv.setText(((Integer) wt.work_12_szo).toString());
    tv = (TextView) rv1.findViewById(R.id.txt12va);
    tv.setText(((Integer) wt.work_12_va).toString());
    tv = (TextView) rv1.findViewById(R.id.txtSzabi);
    tv.setText(((Integer) wt.work_szabi).toString());
    tv = (TextView) rv1.findViewById(R.id.txtFizUnn);
    tv.setText(((Integer) wt.work_fiz_unn).toString());
  }

  private void ShowCalculate( View root )
  {
    Integer mBerOssz = 0;
    TextView tv = (TextView) root.findViewById(R.id.textNap8de);
    tv.setText(((Integer) wt.work_8_de).toString());
    tv = (TextView) root.findViewById(R.id.textNap8du);
    tv.setText(((Integer) wt.work_8_du).toString());
    tv = (TextView) root.findViewById(R.id.textNap8ej);
    tv.setText(((Integer) wt.work_8_ej).toString());
    tv = (TextView) root.findViewById(R.id.textNap8szo);
    tv.setText(((Integer) wt.work_8_szo).toString());
    tv = (TextView) root.findViewById(R.id.textNap8va);
    tv.setText(((Integer) wt.work_8_va).toString());
    tv = (TextView) root.findViewById(R.id.textNap12na);
    tv.setText(((Integer) wt.work_12_na).toString());
    tv = (TextView) root.findViewById(R.id.textNap12ej);
    tv.setText(((Integer) wt.work_12_ej).toString());
    tv = (TextView) root.findViewById(R.id.textNap12szo);
    tv.setText(((Integer) wt.work_12_szo).toString());
    tv = (TextView) root.findViewById(R.id.textNap12va);
    tv.setText(((Integer) wt.work_12_va).toString());

    tv = (TextView) root.findViewById(R.id.textBer8de); // 8 óra délelőtt
    Integer mBer = Integer.parseInt(myPrefs.getString("ber8de", "1"))
        * wt.work_8_de;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer8du); // 8 óra délután
    mBer = Integer.parseInt(myPrefs.getString("ber8du", "1")) * wt.work_8_du;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer8ej); // 8 óra éjszaka
    mBer = Integer.parseInt(myPrefs.getString("ber8ej", "1")) * wt.work_8_ej;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer8szo); // 8 óra szombaton
    mBer = Integer.parseInt(myPrefs.getString("ber8szo", "1")) * wt.work_8_szo;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer8va); // 8 óra vasárnap
    mBer = Integer.parseInt(myPrefs.getString("ber8va", "1")) * wt.work_8_va;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer12na); // 12 óra nappal
    mBer = Integer.parseInt(myPrefs.getString("ber12na", "1")) * wt.work_12_na;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer12ej); // 12 óra éjszaka
    mBer = Integer.parseInt(myPrefs.getString("ber12ej", "1")) * wt.work_12_ej;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer12szo); // 12 óra szombat
    mBer = Integer.parseInt(myPrefs.getString("ber12szo", "1"))
        * wt.work_12_szo;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBer12va); // 12 óra vasárnap
    mBer = Integer.parseInt(myPrefs.getString("ber12va", "1")) * wt.work_12_va;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBerSzab); // Szabadság
    mBer = Integer.parseInt(myPrefs.getString("ber_szabi", "1"))
        * wt.work_szabi;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.textBerUnn); // Fizetett ünnep
    mBer = Integer.parseInt(myPrefs.getString("ber_fiz_unn", "1"))
        * wt.work_fiz_unn;
    tv.setText(mBer.toString());
    mBerOssz = mBerOssz + mBer;

    tv = (TextView) root.findViewById(R.id.texBerOsszesen);
    tv.setText(mBerOssz.toString());
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment
  {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static PlaceholderFragment newInstance( int sectionNumber )
    {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      // fragment.
      return fragment;
    }

    public PlaceholderFragment( )
    {
    }

    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container ,
        Bundle savedInstanceState )
    {
      Bundle args = this.getArguments();
      int sec = args.getInt(ARG_SECTION_NUMBER, 1);
      int[] frags =
        { R.layout.fragment_main, R.layout.fragment_other,
            R.layout.fragment_summary };
      View rootView = inflater.inflate(frags[sec - 1], container, false);

      switch (sec)
      {
      case 1:
        ((MainActivity) getActivity()).rv1 = rootView;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd");
        TextView tv = (TextView) rootView.findViewById(R.id.textDatum);
        tv.setText(sdf.format(dt).toString());
        ((MainActivity) getActivity()).SetWorkTimeText();
        Button but = (Button) rootView.findViewById(R.id.btnMonth);
        sdf = new SimpleDateFormat("MMMM");
        but.setText(sdf.format(dt));
        but.setOnClickListener(new OnClickListener()
        {
          @Override
          public void onClick( View v )
          {
            // TODO Auto-generated method stub
            // Toast.makeText(getActivity(),
            // "Megnyomtad a gombot",Toast.LENGTH_SHORT).show();
            ShowPopUp(getActivity());
          }
        });
        break;
      case 2:
        ((MainActivity) getActivity()).ShowCalculate(rootView);
        break;

      }
      return rootView;
    }
  }

}

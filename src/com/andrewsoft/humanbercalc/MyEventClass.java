/**
 * 
 */
package com.andrewsoft.humanbercalc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import dk.CalendarService.*;

/**
 * @author Andrew
 *
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) @SuppressWarnings("unused")
public class MyEventClass
{
  private ArrayList<MyEventInstance> listEvents;
  private ContentResolver resolver;
  private Uri calendar_uri = Calendars.CONTENT_URI;
  private Uri event_uri = Events.CONTENT_URI;
  private Context mContext;
  
  /**
   * 
   */
  public MyEventClass(Context context )
  {
    // TODO Auto-generated constructor stub
    mContext = context;
    listEvents = new ArrayList<MyEventInstance>();
    
    resolver = mContext.getContentResolver();
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    
    cal1.set(Calendar.MONTH, ((MainActivity)mContext).actMonth);
    cal1.set(Calendar.DAY_OF_MONTH, 1);
    
    cal2.set(Calendar.MONTH, ((MainActivity)mContext).actMonth+1);
    cal2.set(Calendar.DAY_OF_MONTH, 1);
    //CalendarService.readCalendar(mContext);
    Cursor cur =  CalendarContract.Instances.query(resolver, (new String[] {"_id", "title", "description", "dtstart", "dtend"}), cal1.getTimeInMillis(), cal2.getTimeInMillis());
    
 //   Cursor cur = resolver.query(Uri.parse("content://com.android.calendar/events"), (new String[] {"_id", "title", "description", "dtstart", "dtend"}), null, null, null);
    cur.moveToFirst();
    int cnt = cur.getCount();
    String[] col_names = cur.getColumnNames();
    cur.moveToFirst();
    for (int i=0; i<cnt;i++)
    {
      String ttl = cur.getString(1);
      if (
          ttl.matches("12 óra éjszaka") | 
          ttl.matches("12 óra nappal") |
          ttl.matches("8 óra délelõtt") |
          ttl.matches("8 óra délután") |
          ttl.matches("8 óra éjszaka") |
          ttl.matches("Szabadság") |
          ttl.matches("Fiz.ünn.")
          )
      {
        MyEventInstance mEvent = new MyEventInstance();
        mEvent.Title = ttl;
        
        listEvents.add(mEvent);
      }
      cur.moveToNext();
    }
    
  }
  
  @SuppressWarnings("deprecation")
  public ArrayList<MyEventInstance> GetMonthEvents(int month)
  {
    ArrayList<MyEventInstance> tmpEvents = new ArrayList<MyEventInstance>();
    Iterator<MyEventInstance> iterator = listEvents.iterator();
    while (iterator.hasNext())
    {
      MyEventInstance mEvent = iterator.next();
      Date dtStart = new Date();
      dtStart.setTime(mEvent.stDate);
      if (dtStart.getMonth() == month) tmpEvents.add(mEvent);
    }
    
    return tmpEvents;
  }
  
  

}

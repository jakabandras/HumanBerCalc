package com.andrewsoft.humanbercalc;

import java.util.HashMap;
import java.util.Map;

public class FizuSzamol
{
  private WorkTime myWt;
  private Map<String, Integer> times = new HashMap<>();
  public FizuSzamol( WorkTime worktime)
  {
    // TODO Auto-generated constructor stub
    myWt = worktime;
    times.put("8 óra délelõtt",(Integer) myWt.work_8_de);
    times.put("8 óra délután", (Integer) myWt.work_8_de);
  }

  public int Calculate(String what)
  {
    
    return 0;
  }
}

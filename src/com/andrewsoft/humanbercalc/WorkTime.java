/**
 * 
 */
package com.andrewsoft.humanbercalc;

/**
 * @author Andrew
 *
 */
public class WorkTime
{
  public int work_8_de = 0;
  public int work_8_du = 0;
  public int work_8_ej = 0;
  public int work_8_szo = 0;
  public int work_8_va = 0;
  public int work_12_na = 0;
  public int work_12_ej = 0;
  public int work_12_szo = 0;
  public int work_12_va = 0;
  public int work_szabi = 0;
  public int work_fiz_unn = 0;

  /**
   * 
   */
  public WorkTime( )
  {
    // TODO Auto-generated constructor stub
  }
  
  public void SetToZero()
  {
    work_8_de = work_8_du = work_8_ej = 0;
    work_8_szo = work_8_va = 0;
    work_12_na = work_12_ej = 0;
    work_12_szo = work_12_va = 0;
    work_szabi = work_fiz_unn = 0;
  }

}

package org.omg.Security;

/**
* org/omg/Security/DayOfTheWeekHolder.java .
* Error reading Messages File.
* Error reading Messages File.
* Thursday, January 14, 2010 1:08:58 AM PST
*/

public final class DayOfTheWeekHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.Security.DayOfTheWeek value = null;

  public DayOfTheWeekHolder ()
  {
  }

  public DayOfTheWeekHolder (org.omg.Security.DayOfTheWeek initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.Security.DayOfTheWeekHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.Security.DayOfTheWeekHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.Security.DayOfTheWeekHelper.type ();
  }

}

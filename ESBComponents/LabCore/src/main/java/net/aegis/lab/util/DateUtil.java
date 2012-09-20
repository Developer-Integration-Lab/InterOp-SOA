package net.aegis.lab.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class contains utility methods related to handling of dates and times.
 * 
 * @author  venkat.keesara
 */
public class DateUtil {
	
	 //CONSTANTS

    public static final int HOURS_PER_DAY = 24;
    public static final int MINUTES_PER_HOUR = 60;
    public static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static final int SECONDS_PER_DAY = SECONDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY;
    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE;
    public static final int MILLISECONDS_PER_HOUR = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static final int MILLISECONDS_PER_DAY = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY;
    public static final int ZERO = 0;
    public static final String DAYS_SHORT_FORMAT = "D";
    public static final String HOURS_SHORT_FORMAT = "H";
    public static final String MINS_SHORT_FORMAT = "M";
    public static final String COLON = ":";
    public static final String MM_DD_YYYY_FORMAT = "MM/dd/yyyy";

    public static String getElapsedTime(String msg, long timeStarted, long timeEnded) {
        return getElapsedTime(msg, timeEnded - timeStarted);
    }

    public static String getElapsedTime(long timeStarted, long timeEnded) {
        return getElapsedTime(null, timeStarted, timeEnded);
    }

    public static String getElapsedTime(long timeStarted, long timeEnded, boolean includeMillis) {
       return getElapsedTime(null, timeEnded-timeStarted, includeMillis);
    }

    public static String getElapsedTime(long elapsed, boolean includeMillis) {
        return getElapsedTime(null, elapsed, includeMillis);
    }

    public static String getElapsedTime(long elapsed) {
        return getElapsedTime(null, elapsed);
    }

    public static String getElapsedTime(String msg, long elapsed) {
        return getElapsedTime(msg, elapsed, true);
    }

    public static String getElapsedTime(String msg, long elapsed, boolean includeMillis) {
        if (elapsed<1000) {
            includeMillis = true;
        }
        StringBuffer fullMsgBuff = new StringBuffer();
        if (msg != null) {
            fullMsgBuff.append(msg);
        }
        long hrs = (elapsed / 3600000);
        long mins = (elapsed / 60000);
        long secs = ((elapsed / 1000) - (elapsed / 60000) * 60);
        if (hrs > 0) {
            mins = mins - (hrs * 60);
            secs = secs - (mins * 60);
            fullMsgBuff.append(hrs);
            fullMsgBuff.append(" hr(s) ");
        }
        if (mins > 0) {
            fullMsgBuff.append(mins);
            fullMsgBuff.append(" min(s) ");
        }
        if (secs > 0) {
            fullMsgBuff.append(secs);
            fullMsgBuff.append(" sec(s) ");
        }
        if (includeMillis) {
            long millis = 0;
            if (secs > 0) {
                millis = elapsed % (secs * 1000);
            } else {
                millis = elapsed;
            }
            fullMsgBuff.append(millis);
            fullMsgBuff.append(" ms(s)");
        }
        return fullMsgBuff.toString();
    }
    public static Date addDays(Date aDate, int numOfDays)
    {
    	return computeDateDaysFrom(aDate, numOfDays);
    }
    
    public static Date atDate(
        int aYear,
        int aZeroBasedMonthIndex,
        int aDayOfMonth,
        int anHourOfDay,
        int aMinute,
        int aSecond,
        int aMillisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, aYear);
        calendar.set(Calendar.MONTH, aZeroBasedMonthIndex);
        calendar.set(Calendar.DAY_OF_MONTH, aDayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, anHourOfDay);
        calendar.set(Calendar.MINUTE, aMinute);
        calendar.set(Calendar.SECOND, aSecond);
        calendar.set(Calendar.MILLISECOND, aMillisecond);
        return calendar.getTime();
    }

    public static Date atMidnight(
	    int aYear,
	    int aZeroBasedMonthIndex,
	    int aDayOfMonth) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.YEAR, aYear);
	    calendar.set(Calendar.MONTH, aZeroBasedMonthIndex);
	    calendar.set(Calendar.DAY_OF_MONTH, aDayOfMonth);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
    
    public static Date atMidnight(Date aDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date atOneMinuteBeforeMidnight(Date aDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static Date atOneSecondBeforeMidnight(Date aDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date atNoon(Date aDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date atNoon(
	    int aYear,
	    int aZeroBasedMonthIndex,
	    int aDayOfMonth) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.YEAR, aYear);
	    calendar.set(Calendar.MONTH, aZeroBasedMonthIndex);
	    calendar.set(Calendar.DAY_OF_MONTH, aDayOfMonth);
	    calendar.set(Calendar.HOUR_OF_DAY, 12);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}

    public static Time atZeroEpoch(Date aDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Time(calendar.getTimeInMillis());
    }

    public static Time atTime(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Time(calendar.getTimeInMillis());
    }
    
    public static Date atDayOfMonth(Date aDate, int desiredDayOfMonth)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.DAY_OF_MONTH,desiredDayOfMonth);
        return calendar.getTime();
    }
    
    public static Date atDayOfMonthAtMidnight(Date aDate, int desiredDayOfMonth)
    {
       return atMidnight(atDayOfMonth(aDate, desiredDayOfMonth));
    }
    
    public static Date atFirstDayOfMonthAtMidnight(Date aDate)
    {
       return atMidnight(atDayOfMonth(aDate, 1));
    }
    
    public static Date atLastDayOfMonthAtMidnight(Date aDate)
    {
        return atMidnight(atLastDayOfMonth(aDate));
    }
    
    /**
     * Computes a Date instance that represents the last day of the month, for
     * the same month represented by aDate, at the same time of day housed in
     * aDate.
     * @param aDate Must be non-null.
     * @return Will be non-null.
     */
    
    public static int getLastDayOfMonth(Date aDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        
    }
    
    public static Date atLastDayOfMonth(Date aDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date farFutureDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2999);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Time(calendar.getTimeInMillis());
    }
    
    //TRUNCATING

    public static Date truncateToSecond(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date truncateToMinute(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date truncateToHour(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date truncateToDay(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Returns <code>aDate</code> normalized to <code>aPrecision</code>.
     * <p/>
     * The normalization process involves setting fields that are
     * more precise than the requested precision to zero via
     * {@link Calendar#set(int, int)}.
     * <p/>
     * As such, the allowable values for <code>aPrecision</code> are:
	 * <ul>
	 * <li>{@link Calendar#MILLISECOND}</li>
	 * <li>{@link Calendar#SECOND}</li>
	 * <li>{@link Calendar#MINUTE}</li>
	 * <li>{@link Calendar#HOUR}</li>
	 * <li>{@link Calendar#DATE}</li>
	 * <li>{@link Calendar#MONTH}</li>
	 * <li>{@link Calendar#YEAR}</li>
	 * </ul>
	 * Providing any other value for the argument <code>aPrecision</code>
	 * will simply result in the argument <code>aDate</code> being returned.
	 * 
     * @return <code>aDate</code> truncated to <code>aPrecision</code>
	 * 
     * @param aDate the base Date
     * @param aPrecision the precision
     */
	public static Date toPrecision(Date aDate, int aPrecision)
	{
		if (aDate == null) return null;
		Calendar c = Calendar.getInstance();
		c.setTime(aDate);
		switch (aPrecision)
		{
			case Calendar.YEAR:
				c.set(Calendar.MONTH, 0);
			case Calendar.MONTH:
				c.set(Calendar.DATE, 0);
			case Calendar.DATE:
				c.set(Calendar.HOUR, 0);
			case Calendar.HOUR:
				c.set(Calendar.MINUTE, 0);
			case Calendar.MINUTE:
				c.set(Calendar.SECOND, 0);
			case Calendar.SECOND:
				c.set(Calendar.MILLISECOND, 0);
			default:
				break;
		}
		return c.getTime();
	}


    //DATE ARITHMETIC
	
	/**
     * Returns the Date that is <code>numberOfMinutes</code> after <code>aDate</code>.
     *
     * @return the Date that is <code>numberOfMinutes</code> after <code>aDate</code>
     *
     * @param aDate the starting Date
     * @param numberOfMinutes the number of minutes after the start date
     */
    public static Date computeDateMinutesFrom(Date aDate, int numberOfMinutes)
    {
        return computeDateUnitFrom(aDate, Calendar.MINUTE, numberOfMinutes);
    }
    
    /**
     * Returns the Date that is <code>numberOfHours</code> after <code>aDate</code>.
     *
     * @return the Date that is <code>numberOfHours</code> after <code>aDate</code>
     *
     * @param aDate the starting Date
     * @param numberOfHours the number of hours after the start date
     */
    public static Date computeDateHoursFrom(Date aDate, int numberOfHours)
    {
        return computeDateUnitFrom(aDate, Calendar.HOUR_OF_DAY, numberOfHours);        
    }

    /**
     * Returns the Date that is <code>aNumberOfDays</code> after <code>aDate</code>.
     *
     * @return the Date that is <code>aNumberOfDays</code> after <code>aDate</code>
     *
     * @param aDate the starting Date
     * @param aNumberOfDays the number of days after the start date. Can be less than zero.
     */
    public static Date computeDateDaysFrom(Date aDate, int aNumberOfDays)
    {
        return computeDateUnitFrom(aDate, Calendar.DATE, aNumberOfDays);
    }
    
    /**
     * Returns the Date that is <code>numberOfMonths</code> after <code>aDate</code>.
     *
     * @return the Date that is <code>numberOfMonths</code> after <code>aDate</code>
     *
     * @param aDate the starting Date
     * @param numberOfMonths the number of months after aDate. Can be less than zero.
     */
    public static Date computeDateMonthsFrom(Date aDate, int numberOfMonths)
    {
        return computeDateUnitFrom(aDate, Calendar.MONTH, numberOfMonths);
    }
    
    /**
     * Returns the Date that is <code>numberOfYears</code> after <code>aDate</code>.
     *
     * @return the Date that is <code>numberOfYears</code> after <code>aDate</code>
     *
     * @param aDate the starting Date
     * @param numberOfYears the number of years after aDate. Can be less than zero.
     */
    public static Date computeDateYearsFrom(Date aDate, int numberOfYears)
    {
        return computeDateUnitFrom(aDate, Calendar.YEAR, numberOfYears);
    }
    
    /**
     * Returns the Date that is <code>numberOfUnits</code> after <code>aDate</code> given a <code>unit</code> of measure.
     *
     * @return the Date that is <code>numberOfUnits</code> after <code>aDate</code> given a <code>unit</code> of measure.
     *
     * @param aDate the starting Date
     * @param unit the unit or scale used for computation, ie: Minute, Second
     * @param numberOfUnits the number of days,months, minutes, etc after the start date. Can be less than zero.
     */
    public static Date computeDateUnitFrom(Date aDate, int unit, int numberOfUnits)
    {
        if (aDate == null) return null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(aDate);
        calendar.add(unit, numberOfUnits);
        return calendar.getTime();
    }
    
    /**
     * Determines the number of days difference between the two days,
     * in the form d1 - d2.
     * @param d1 Must be non-null.
     * @param d2 Must be non-null.
     * @return The computed number of days difference.
     */
    public static int computeDaysDifference(Date d1, Date d2)
    {
        if (d1 == null || d2 == null)
        {
            return 0;
        }
    	BigDecimal msDifference = new BigDecimal(truncateToDay(d1).getTime() - truncateToDay(d2).getTime());
    	BigDecimal days = msDifference.divide(new BigDecimal(MILLISECONDS_PER_DAY), BigDecimal.ROUND_HALF_UP);
    	return days.intValue();
    }
    
    /**
     * @return {@link Calendar#DAY_OF_YEAR} for today
     */
    public static int dayOfYear() 
    {
        Date today = new Date();
        return dayOfYear(today);
    }
    
    /**
     * @param date non-null date
     * @return {@link Calendar#DAY_OF_YEAR} for <code>date</code>
     */
    public static int dayOfYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * @param date non-null date
     * @return {@link Calendar#DAY_OF_MONTH} for <code>date</code>
     */
    public static int dayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    //COMPARING

    /**
     * Compares two Dates for order with the given precision. 
     * 
     * Returns a negative integer, zero, or a positive integer as the first 
     * argument is less than, equal to, or greater than the second. 
     *
     * @return the comparison
     *
     * @param field the Calender constant to limit the precision of the comparison
     * @param aDate the first Date 
     * @param bDate the second Date
     */
    public static int compareDates(int field, Date aDate, Date bDate) {
    	if (aDate != null && bDate == null)
    		return 1;
    	else if (aDate == null && bDate != null)
    		return -1;
        else if (aDate == null && bDate == null)
            return 0;
    	else
    	{
	        // Create Calendar instances for each Date
	        Calendar aCal = Calendar.getInstance();
	        aCal.setTime(aDate);
	        Calendar bCal = Calendar.getInstance();
	        bCal.setTime(bDate);
	        // Zero out the fields that are not relevent
	        switch ( field ) {
	            case Calendar.YEAR :
	                aCal.set(Calendar.MONTH, 0);
	                bCal.set(Calendar.MONTH, 0);
	            case Calendar.MONTH :
	                aCal.set(Calendar.DATE, 0);
	                bCal.set(Calendar.DATE, 0);
	            case Calendar.DATE :
	                aCal.set(Calendar.HOUR, 0);
	                bCal.set(Calendar.HOUR, 0);
	                aCal.set(Calendar.HOUR_OF_DAY, 0);
	                bCal.set(Calendar.HOUR_OF_DAY, 0);
	            case Calendar.HOUR_OF_DAY :
	            case Calendar.HOUR :
	                aCal.set(Calendar.MINUTE, 0);
	                bCal.set(Calendar.MINUTE, 0);
	            case Calendar.MINUTE :
	                aCal.set(Calendar.SECOND, 0);
	                bCal.set(Calendar.SECOND, 0);
	            case Calendar.SECOND :
	                aCal.set(Calendar.MILLISECOND, 0);
	                bCal.set(Calendar.MILLISECOND, 0);
	        }

	        // return the comparison
	        return aCal.getTime().compareTo(bCal.getTime());
    	}
    }

    public static Comparator<Date> compareDatesToTheYear()
    {
        return new Comparator<Date>()
        {
            public int compare(Date d1, Date d2)
            {
                return compareDates(Calendar.YEAR, d1, d2);
            }
        };
    } 

    public static int compareDatesToTheYear(Date d1, Date d2)
    {
        return compareDates(Calendar.YEAR, d1, d2);
    }

    public static Comparator<Date> compareDatesToTheDay()
    {
        return new Comparator<Date>()
        {
            public int compare(Date d1, Date d2)
            {
                return compareDates(Calendar.DATE, d1, d2);
            }
        };
    } 

    public static int compareDatesToTheDay(Date d1, Date d2)
    {
    	return compareDates(Calendar.DATE, d1, d2);
    }
    
    public static int compareDatesToTheMonth(Date d1, Date d2)
    {
    	return compareDates(Calendar.MONTH, d1, d2);
    }

    public static Comparator<Date> compareDatesToTheHour()
    {
    	return new Comparator<Date>()
        {
            public int compare(Date d1, Date d2)
            {
                return compareDates(Calendar.HOUR, d1, d2);
            }
        };
    } 

    public static int compareDatesToTheHour(Date d1, Date d2)
    {
    	return compareDates(Calendar.HOUR, d1, d2);
    }

    public static Comparator<Date> compareDatesToTheMinute()
    {
        return new Comparator<Date>()
        {
            public int compare(Date d1, Date d2)
            {
                return compareDates(Calendar.MINUTE, d1, d2);
            }
        };
    }    

    public static int compareDatesToTheMinute(Date d1, Date d2)
    {
    	return compareDates(Calendar.MINUTE, d1, d2);
    }

    
    //FORMATTING
    
    public static String dateToString(Date aDate, String format) {
        if (aDate == null)
            return new String();
        else {
            return new SimpleDateFormat(format).format(aDate);
        }
    }
    
    public static Date stringToDate(String aDate, String format)
    {
    	if(StringUtils.isNullOrEmpty(aDate))
    	{
    		return null;
    	}
    	try 
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat(format); 
			sdf.setLenient(false);
    		return sdf.parse(aDate);
		} 
    	catch (ParseException e) 
		{
			e.printStackTrace();
			return null;
		}
    }
    

//TESTING
    public static boolean isValidDateFormat(String aDate, String format)
    {
    	try
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		sdf.setLenient(false);
    		sdf.parse(aDate);
    		return true;
    	}
    	catch (ParseException e)
    	{
    		e.printStackTrace();
    		return false;
		}
    }
    
    /**
     * Determines if aDate falls between a start and end date, including the
     * start and end dates.
     * @param start a start Date
     * @param end a end Date
     * @param aDate for comparison
     */
    public static boolean isBetweenInclusive(Date start, Date end, Date aDate) {
        return 
        (isSameDay(start, aDate) || isBefore(start, aDate)) &&
        (isSameDay(end, aDate) || isAfter(end, aDate));
    }

    public static boolean isBetweenInclusiveTime(Date start, Date end, Date aDate)
    {
        return ( isAfterOrEqual(aDate, start) && isBeforeOrEqual(aDate, end) );
    }
    
    /**
     * Determines if the given date falls within the given date range, where the
     * end of the date range can be null.
     * @param startDate Cannot be null.
     * @param endDate Can be null.
     * @param aDate Cannot be null.
     * @return
     */
    public static boolean isWithinDateRange(Date startDate,
                                            Date endDate,
                                            Date aDate)
    {
    	if (aDate == null)
    		return false;

        if (endDate == null)
        {
            return (compareDates(Calendar.DATE, startDate, aDate) <= 0);
        }
        
        return ((compareDates(Calendar.DATE, startDate, aDate) <= 0) &&
                (compareDates(Calendar.DATE, aDate, endDate) <= 0));
    }
    
    /**
     * Determines if the given date falls within the given date range, where the
     * end of the date range can be null.
     * @param startDateTime Cannot be null.
     * @param endDateTime Can be null.
     * @param pointInTime Cannot be null.
     * @return
     */
    public static boolean isWithinTimestampRange(Date startDateTime,
                                                 Date endDateTime,
                                                 Date pointInTime)
    {
        if (endDateTime == null)
        {
            return isAfterOrEqual(pointInTime, startDateTime);
        }
        
        return isBetweenInclusiveTime(startDateTime,endDateTime,pointInTime);
    }
    
    /**
     * Returns true if <code>aDate</code> is equal to <code>anotherDate</code>,
     * to a precision of one minute.
     * 
     * @return true if <code>aDate</code> is equal to <code>anotherDate</code>,
     * to a precision of one minute
     * 
     * @param aDate the {@link Date} to compare to <code>anotherDate</code>
     * @param anotherDate the {@link Date} to compare to <code>aDate</code>
     */
    public static boolean isEqual(Date aDate, Date anotherDate)
    {
        return compareDatesToTheMinute(aDate, anotherDate) == 0;
    }

    /**
     * Returns true if <code>aDate</code> is before <code>anotherDate</code>,
     * to a precision of one minute.
     * 
     * @return true if <code>aDate</code> is before <code>anotherDate</code>,
     * to a precision of one minute
     * 
     * @param aDate the {@link Date} to compare to <code>anotherDate</code>
     * @param anotherDate the {@link Date} to compare to <code>aDate</code>
     */
    public static boolean isBefore(Date aDate, Date anotherDate)
    {
        return
            aDate != null &&
            anotherDate != null &&
            compareDatesToTheMinute(aDate, anotherDate) < 0;
    }

    /**
     * Returns true if <code>aDate</code> is equal to, or before,
     * <code>anotherDate</code>, to a precision of one minute.
     * 
     * @return true if <code>aDate</code> is equal to, or before,
     * <code>anotherDate</code>, to a precision of one minute
     * 
     * @param aDate the {@link Date} to compare to <code>anotherDate</code>
     * @param anotherDate the {@link Date} to compare to <code>aDate</code>
     */
    public static boolean isBeforeOrEqual(Date aDate, Date anotherDate)
    {
        return
            aDate != null &&
            anotherDate != null &&
            compareDatesToTheMinute(aDate, anotherDate) <= 0;
    }
    
    /**
     * Returns true if <code>aDate</code> is after <code>anotherDate</code>,
     * to a precision of one minute.
     * 
     * @return true if <code>aDate</code> is after <code>anotherDate</code>,
     * to a precision of one minute
     * 
     * @param aDate the {@link Date} to compare to <code>anotherDate</code>
     * @param anotherDate the {@link Date} to compare to <code>aDate</code>
     */
    public static boolean isAfter(Date aDate, Date anotherDate)
    {
        return
            aDate != null &&
            anotherDate != null &&
            compareDatesToTheMinute(aDate, anotherDate) > 0;
    }

    /**
     * Returns true if <code>aDate</code> is equal to, or after,
     * <code>anotherDate</code>, to a precision of one minute.
     * 
     * @return true if <code>aDate</code> is equal to, or after,
     * <code>anotherDate</code>, to a precision of one minute
     * 
     * @param aDate the {@link Date} to compare to <code>anotherDate</code>
     * @param anotherDate the {@link Date} to compare to <code>aDate</code>
     */
    public static boolean isAfterOrEqual(Date aDate, Date anotherDate)
    {
        return
            aDate != null &&
            anotherDate != null &&
            compareDatesToTheMinute(aDate, anotherDate) >= 0;
    }

    public static boolean isToday(Date aDate) {
        return isSameDay(new Date(), aDate);
    }

    public static boolean isSameDay(Date d1, Date d2) {
        d1 = atMidnight(d1);
        d2 = atMidnight(d2);
        return d1.equals(d2);
    }

    public static boolean isLastDayOfMonth(Date d) {
    	int m1 = d.getMonth();
    	Date d2 = computeDateDaysFrom(d, 1);
    	int m2 = d2.getMonth();
    	return m1 != m2;
    }

    /**
     * Returns a positive int (ranging from one to five) that
     * indicates whether the day of week corresponding to
     * <code>aDate</code> is the first, second, third, fourth
     * or fifth occurrence of that day of week in the month.
     * <p/>
     * For example, Feb 29, 2008 was the 5th Friday of February
     * in the year 2008, so this method would return 5. March
     * 17, 2008 was the 3rd Monday of March in the year 2008,
     * so this method would return 3.
     * 
     * @return a positive int (ranging from one to five) that
     * indicates whether the day of week corresponding to
     * <code>aDate</code> is the first, second, third, fourth
     * or fifth occurrence of that day of week in the month
     * 
     * @param aDate the {@link Date} to be analyzed
     */
    public static int getOccurrenceOfDayOfWeekInMonth(Date aDate)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(aDate);

    	int targetDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    	int targetDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    	int occurrence = 0;
    	for (int currentDayOfMonth = 1; currentDayOfMonth <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); currentDayOfMonth++)
    	{
        	calendar.set(Calendar.DAY_OF_MONTH, currentDayOfMonth);
        	int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        	if (targetDayOfWeek == currentDayOfWeek)
        		occurrence++;
        	if (targetDayOfMonth == currentDayOfMonth)
        		break;
    	}

    	assert occurrence > 0;

    	return occurrence;
    }

    public static int getYear()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }
    
    public static int getDay(Date aDate)
    {
        Calendar calendar = Calendar.getInstance();
        
        if(aDate == null)
        {
            calendar.setTime(new Date());            
        }
        else
        {
            calendar.setTime(aDate);
        }
        
        return calendar.get(Calendar.DATE);
    }
    /*
     *  get the days range in the month including the date that you passed
     */
    public static ArrayList<Integer> getDaysRangeForNumberOfDays(Date aDate , int numberOfDays)
    {
        ArrayList<Integer> daysList = new ArrayList<Integer>() ; 
        Calendar calendar = Calendar.getInstance();
        
        if(aDate == null)
        {
            calendar.setTime(new Date());            
        }
        else
        {
            calendar.setTime(aDate);
        }
        if(numberOfDays > 0)
        {
            for(int i = 1 ; i <= numberOfDays ; i++)
            {
                daysList.add(calendar.get(Calendar.DATE));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        else
        {
            //calendar.add(Calendar.DAY_OF_MONTH, numberOfDays + 1);
            for(int i = numberOfDays ; i < 0 ; i++)
            {
                daysList.add(calendar.get(Calendar.DATE));
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                
            }
        }
        return daysList;
    }
    /**
     * This method gets the total number of seconds based on a 4 byte Integer
     * parameter in the format HHMM.
     * @param hhmm
     * @return
     */
    public static int getNumberOfSeconds(Integer hhmm)
    {
        String hours = hhmm.toString().substring(0, 2);
        String minutes = hhmm.toString().substring(2, 4);
        int intHours = Integer.parseInt(hours);
        int intMin = Integer.parseInt(minutes);
        int numberOfSecondsFromHours = intHours*SECONDS_PER_HOUR;
        int numberOfSecondsFromMinutes = intMin*SECONDS_PER_MINUTE;
        int totalNumberOfSeconds = numberOfSecondsFromHours + numberOfSecondsFromMinutes;
        return totalNumberOfSeconds;

    }
    
    /**
     * This method finds the difference in years, months and days between 
     * two dates
     * @param d1
     * @param d2
     * @return array containing the difference : [0] = YEARS, [1] = MONTHS, [2] = DAYS
     */
    public static int[] getDifferenceInYearsMonthsDays(Date d1, Date d2)
    {
    	int YEARS = 0;
    	int MONTHS = 1;
    	int DAYS = 2;
    	int diff[] = new int[3];
    	
    	Date beforeDate = null;
    	Date afterDate = null;
    	
    	if (d1.compareTo(d2) < 0)
    	{
    		beforeDate = d1;
    		afterDate = d2;
    	}
    	else if (d1.compareTo(d2) > 0)
    	{
    		beforeDate = d2;
    		afterDate = d1;
    	}
    	else
    	{
    		return diff;
    	}
    	
    	Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(beforeDate);
		cal2.setTime(afterDate);
		
		int years = 0;
		while (cal1.compareTo(cal2) <= 0)
		{
			cal1.add(Calendar.YEAR, 1);
			years++;
		}
		--years;
		cal1.add(Calendar.YEAR, -1);
		
		int months = 0;
		while (cal1.compareTo(cal2) <= 0)
		{
			cal1.add(Calendar.MONTH, 1);
			months++;
		}
		--months;
		cal1.add(Calendar.MONTH, -1);
		
		int days = 0;
		while (cal1.compareTo(cal2) <= 0)
		{
			cal1.add(Calendar.DATE, 1);
			days++;
		}
		--days;
		cal1.add(Calendar.DATE, -1);
		
		diff[YEARS] = years;
		diff[MONTHS] = months;
		diff[DAYS] = days;
		
    	return diff;
    }
    
    /**
     * This method returns minimum date/time of given input date/time.
     * The minimum date is being calculated by adding second and millisecond parts as 0 (ZERO).  
     * @param aDate the {@link Date} to be calculated
     * @return aDate
     */
    public static Date getMinimumDateTimeFor(Date inputDateTime)
    {
    	Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(inputDateTime);
        calendar.add(Calendar.SECOND, 0);
        calendar.add(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    
    /**
     * This method returns maximum date/time of given input date/time.
     * The maximum date is being calculated by adding seconds as 59 and milliseconds as 999.  
     * @param aDate the {@link Date} to be calculated
     * @return aDate
     */
    public static Date getMaximumDateTimeFor(Date inputDateTime)
    {
    	Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(inputDateTime);
        calendar.add(Calendar.SECOND, 59);
        calendar.add(Calendar.MILLISECOND, 999);
        
        return calendar.getTime();
    }
    
    public static String computeHrsAndMinutesDifference(Date d1, Date d2)
    {
        if (d1 == null || d2 == null)
        {
            return StringUtils.EMPTY_STRING;
        }
        
        int[] differenceInHrsMins = getDifferenceInHrsMins(d1,d2);
        StringBuffer hrsMinsBuffer = new StringBuffer();
        
        // Hrs
        if(differenceInHrsMins[0] < 10)
        {
            hrsMinsBuffer.append(ZERO) ;
           
        }
        hrsMinsBuffer.append(differenceInHrsMins[0]) ;        
        hrsMinsBuffer.append(HOURS_SHORT_FORMAT + COLON) ;
        
        //Mins
        if(differenceInHrsMins[1] < 10)
        {
            hrsMinsBuffer.append(ZERO) ;
        }
        hrsMinsBuffer.append(differenceInHrsMins[1]) ;
        hrsMinsBuffer.append(MINS_SHORT_FORMAT) ;

        return hrsMinsBuffer.toString();
    }
    
    public static int[] getDifferenceInHrsMins(Date d1, Date d2)
    {
        int HRS = 0;
        int MINS = 1;
       
        int diff[] = new int[2];
        
        Date beforeDate = null;
        Date afterDate = null;
        
        if (d1.compareTo(d2) < 0)
        {
            beforeDate = d1;
            afterDate = d2;
        }
        else if (d1.compareTo(d2) > 0)
        {
            beforeDate = d2;
            afterDate = d1;
        }
        else
        {
            return diff;
        }
        
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(beforeDate);
        cal2.setTime(afterDate);
        
        int hrs = 0;
        while (cal1.compareTo(cal2) <= 0)
        {
            cal1.add(Calendar.HOUR_OF_DAY, 1);
            hrs++;
        }
        --hrs;
        cal1.add(Calendar.HOUR_OF_DAY, -1);
        
        int mins = 0;
        while (cal1.compareTo(cal2) <= 0)
        {
            cal1.add(Calendar.MINUTE, 1);
            mins++;
        }
        --mins;
        cal1.add(Calendar.MINUTE, -1); 
        
        diff[HRS] = hrs;
        diff[MINS] = mins;
        
        return diff;
    }
    
    public static int[] getDifferenceInDaysHrsMins(Date d1, Date d2)
    {
        int DAYS = 0;
        int HRS = 1;
        int MINS = 2;
       
        int diff[] = new int[3];
        
        Date beforeDate = null;
        Date afterDate = null;
        
        if (d1.compareTo(d2) < 0)
        {
            beforeDate = d1;
            afterDate = d2;
        }
        else if (d1.compareTo(d2) > 0)
        {
            beforeDate = d2;
            afterDate = d1;
        }
        else
        {
            return diff;
        }
        
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(beforeDate);
        cal2.setTime(afterDate);
        
        int days = 0;
        while (cal1.compareTo(cal2) <= 0)
        {
            cal1.add(Calendar.DATE, 1);
            days++;
        }
        --days;
        cal1.add(Calendar.DATE, -1);
        
        int hrs = 0;
        while (cal1.compareTo(cal2) <= 0)
        {
            cal1.add(Calendar.HOUR_OF_DAY, 1);
            hrs++;
        }
        --hrs;
        cal1.add(Calendar.HOUR_OF_DAY, -1);
        
        int mins = 0;
        while (cal1.compareTo(cal2) <= 0)
        {
            cal1.add(Calendar.MINUTE, 1);
            mins++;
        }
        --mins;
        cal1.add(Calendar.MINUTE, -1);
       
        diff[DAYS] = days;
        diff[HRS] = hrs;
        diff[MINS] = mins;
        
        return diff;
    }
    
    public static String computeDaysHrsMinutesDifference(Date d1, Date d2)
    {
        int[] differenceInDaysHrsMins = getDifferenceInDaysHrsMins(d1,d2);
        
       StringBuffer daysHrsMinsBuffer = new StringBuffer();
       
       //Days
       if(differenceInDaysHrsMins[0] < 10)
       {
           daysHrsMinsBuffer.append(ZERO) ;
           daysHrsMinsBuffer.append(differenceInDaysHrsMins[0]) ;
       }else
       {
           daysHrsMinsBuffer.append(differenceInDaysHrsMins[0]) ;
       }
       daysHrsMinsBuffer.append(DAYS_SHORT_FORMAT + COLON) ;
       //Hrs
       if(differenceInDaysHrsMins[1] < 10)
       {
           daysHrsMinsBuffer.append(ZERO) ;
           daysHrsMinsBuffer.append(differenceInDaysHrsMins[1]) ;
       }else
       {
           daysHrsMinsBuffer.append(differenceInDaysHrsMins[1]) ;
       }
       daysHrsMinsBuffer.append(HOURS_SHORT_FORMAT + COLON) ;
       
       //Mins
       if(differenceInDaysHrsMins[2] < 10)
       {
           daysHrsMinsBuffer.append(ZERO) ;
           daysHrsMinsBuffer.append(differenceInDaysHrsMins[2]) ;
       }else
       {
           daysHrsMinsBuffer.append(differenceInDaysHrsMins[2]) ;
       }
       daysHrsMinsBuffer.append(MINS_SHORT_FORMAT) ;
       
       return daysHrsMinsBuffer.toString();
    }   
    
    /**
     * Convert a Date's datetime values to a newTimeZone given that the Date is in the origTimeZone.
     * A Date doesn't have a TimeZone property but when it's displayed via toString() then
     * the formatting adds the system timezone code. This method's purpose is to modify
     * the date and time values so that they're appropriate in the TimeZones passed in
     * as a params. This method is 'where all the magic happens', as the kids say.
     * 
     */
    public static Date convertDateForTimeZone(Date aDate, TimeZone origTimeZone, TimeZone newTimeZone)
    {
        if (aDate != null && origTimeZone != null && newTimeZone != null)
        {
            long dateMillis = aDate.getTime();
            long origTzOffset = origTimeZone.getOffset(dateMillis);
            long newTzOffset = newTimeZone.getOffset(dateMillis);
            long totalOffsetMillis = newTzOffset - origTzOffset;
            long newDateMillis = dateMillis + totalOffsetMillis;
    
            //created a new Date here for debugging instead of just returning it
            Date convertedDate = new Date(newDateMillis);
            return convertedDate;
        }
        else
        {
            return aDate;
        }
        
    }

    public static Date convertDateForTimeZone(Date aDate, TimeZone newTimeZone)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        TimeZone currentTz = calendar.getTimeZone();
        
        return convertDateForTimeZone(aDate, currentTz, newTimeZone);
    }
    
    public static Timestamp getTodayDateTime(){
    	
    	 java.util.Date todayDate= new java.util.Date();
    	 return new Timestamp(todayDate.getTime()); 
    	 
    }
    public static void main(String[] args)
    {
        ArrayList<Integer> daysRangeForNumberOfDays = getDaysRangeForNumberOfDays(new Date(), 3);
        System.out.println("daysRangeForNumberOfDays==" + daysRangeForNumberOfDays.toString());
        
        ArrayList<Integer> numberOfDays = getDaysRangeForNumberOfDays(DateUtil.addDays(new Date(),-10), -7);
        System.out.println("numberOfDays==" + numberOfDays.toString());
    }
}

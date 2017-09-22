package org.breder.time.copy;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe de Data implementada atraves de opera��es aritm�ticas.
 * 
 * 
 * @author Tecgraf
 */
public class YearTime implements Comparable<Time> {

	/** Tempo */
	protected final long time;

	/**
	 * Construtor
	 * 
	 * @param time
	 */
	public YearTime(Time time) {
		this(time.getYear());
	}

	/**
	 * Construtor
	 * 
	 * @param year
	 *            ano entre 0 e 9999
	 * @param month
	 *            mes entre 0 e 11
	 */
	public YearTime(int year) {
		if (year < 0 || year > 9999) {
			throw new IllegalArgumentException("" + year);
		}
		this.time = getTime(year);
	}

	/**
	 * Converte para inteiro o ano e m�s
	 * 
	 * @param year
	 * @param month
	 * @return time
	 */
	protected long getTime(int year) {
		return year * 100000000000000l;
	}

	/**
	 * Construtor
	 * 
	 * @param date
	 */
	public YearTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		this.time = getTime(year);
	}

	/**
	 * Construtor
	 * 
	 * @param calendar
	 */
	public YearTime(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		this.time = getTime(year);
	}

	/**
	 * Construtor de agora
	 */
	public YearTime() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		this.time = getTime(year);
	}

	/**
	 * Construtor interno
	 * 
	 * @param time
	 */
	private YearTime(long time) {
		this.time = time;
	}

	/**
	 * Adiciona anos
	 * 
	 * @param amount
	 *            quantidade de anos
	 * @return novo objeto modificado
	 */
	public YearTime addYear(int amount) {
		return new YearTime(this.getYear() + amount);
	}

	/**
	 * @return ano da data
	 */
	public int getYear() {
		return (int) (time / 100000000000000l);
	}

	/**
	 * {@inheritDoc}
	 */
	public Date toDate() {
		return this.toCalendar().getTime();
	}

	/**
	 * {@inheritDoc}
	 */
	public java.sql.Date toSqlDate() {
		return new java.sql.Date(this.toCalendar().getTimeInMillis());
	}

	/**
	 * {@inheritDoc}
	 */
	public Calendar toCalendar() {
		Calendar c = Calendar.getInstance();
		int year = this.getYear();
		c.set(year, 0, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		int year = this.getYear();
		StringBuilder sb = new StringBuilder();
		if (year < 1000) {
			sb.append('0');
		}
		if (year < 100) {
			sb.append('0');
		}
		if (year < 10) {
			sb.append('0');
		}
		sb.append(year);
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public YearTime clone() {
		return new YearTime(time);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Time o) {
		if (o == null || o instanceof YearTime == false) {
			return 1;
		}
		return this.time - ((YearTime) o).time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || o instanceof YearTime == false) {
			return false;
		}
		return this.time == ((YearTime) o).time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return (int) this.time;
	}

	/**
	 * @return indice do m�s
	 */
	public int toIndex() {
		return (int) time;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean before(Time date) {
		if (date instanceof YearTime) {
			YearTime monthTime = (YearTime) date;
			return this.time < monthTime.time;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean after(Time date) {
		if (date instanceof YearTime) {
			YearTime monthTime = (YearTime) date;
			return this.time > monthTime.time;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Deprecated
	public int getDay() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Deprecated
	public Time addDay(int i) {
		throw new RuntimeException();
	}

	@Override
	public DayTime toDayTime() {
		throw new IllegalStateException();
	}

}

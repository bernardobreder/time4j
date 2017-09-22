package org.breder.time.copy;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe de Data implementada atraves de opera��es aritm�ticas.
 * 
 * 
 * @author Tecgraf
 */
public class MonthTime {

	/** Tempo */
	protected final long time;

	/**
	 * Construtor
	 * 
	 * @param time
	 */
	public MonthTime(Time time) {
		this(time.getYear(), time.getMonth());
	}

	/**
	 * Construtor
	 * 
	 * @param year
	 *            ano entre 0 e 9999
	 * @param month
	 *            mes entre 0 e 11
	 */
	public MonthTime(int year, int month) {
		if (year < 0 || year > 9999) {
			throw new IllegalArgumentException("" + year);
		}
		if (month < 0 || month > 11) {
			throw new IllegalArgumentException("" + month);
		}
		this.time = getTime(year, month);
	}

	/**
	 * Converte para inteiro o ano e m�s
	 * 
	 * @param year
	 * @param month
	 * @return time
	 */
	protected int getTime(int year, int month) {
		return year * 100 + month;
	}

	/**
	 * Construtor
	 * 
	 * @param date
	 */
	public MonthTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		this.time = getTime(year, month);
	}

	/**
	 * Construtor
	 * 
	 * @param calendar
	 */
	public MonthTime(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		this.time = getTime(year, month);
	}

	/**
	 * Construtor de agora
	 */
	public MonthTime() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		this.time = getTime(year, month);
	}

	/**
	 * Construtor interno
	 * 
	 * @param time
	 */
	private MonthTime(int time) {
		this.time = time;
	}

	/**
	 * Adiciona meses
	 * 
	 * @param amount
	 *            quantidade de meses
	 * @return novo objeto modificado
	 */
	@Override
	public MonthTime addMonth(int amount) {
		int month = this.getMonth();
		int newMonth = (month + amount) % 12;
		int incYear = (month + amount) / 12;
		if (amount < 0 && newMonth < 0) {
			incYear--;
			newMonth = 12 + newMonth;
		}
		return new MonthTime(this.getYear() + incYear, newMonth);
	}

	/**
	 * Adiciona anos
	 * 
	 * @param amount
	 *            quantidade de anos
	 * @return novo objeto modificado
	 */
	public MonthTime addYear(int amount) {
		return new MonthTime(this.getYear() + amount, this.getMonth());
	}

	/**
	 * @return o n�mero de dias do m�s
	 */
	public int getDaysInMonth() {
		int year = this.getYear();
		int month = this.getMonth();
		return getLastDayOfMonth(month, year);
	}

	/**
	 * Retorna a quantidade de dia de um m�s
	 * 
	 * @param month
	 * @param year
	 * @return quantidade de dia de um m�s
	 */
	public static int getLastDayOfMonth(int month, int year) {
		switch (month) {
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			return 31;
		case 3:
		case 5:
		case 8:
		case 10:
			return 30;
		case 1: {
			return 28 + (((((year % 4) == 0) && (year % 100) != 0) || ((year % 400) == 0)) ? 1
					: 0);
		}
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return m�s da data
	 */
	@Override
	public int getMonth() {
		return time - (time / 100) * 100;
	}

	/**
	 * @return ano da data
	 */
	@Override
	public int getYear() {
		return time / 100;
	}

	/**
	 * Retorna a diferen�a de meses de duas datas
	 * 
	 * @param other
	 * @return diferen�a de meses de duas datas
	 */
	public int getElapsedMonths(MonthTime other) {
		return Math.abs(this.time - other.time);
	}

	/**
	 * Retorna um objeto de dia com o primeiro dia do m�s
	 * 
	 * @return objeto dia com o primeiro dia do m�s
	 */
	public DayTime toFirstDayTime() {
		int year = this.getYear();
		int month = this.getMonth();
		return new DayTime(year, month, 1);
	}

	/**
	 * Retorna um objeto de dia com o �ltimo dia do m�s
	 * 
	 * @return objeto dia com o �ltimo dia do m�s
	 */
	public DayTime toLastDayTime() {
		int year = this.getYear();
		int month = this.getMonth();
		return new DayTime(year, month, getLastDayOfMonth(month, year));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date toDate() {
		return this.toCalendar().getTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Date toSqlDate() {
		return new java.sql.Date(this.toCalendar().getTimeInMillis());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar toCalendar() {
		Calendar c = Calendar.getInstance();
		int year = this.getYear();
		int month = this.getMonth();
		c.set(year, month, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		int year = this.getYear();
		int month = this.getMonth();
		StringBuilder sb = new StringBuilder();
		if (month + 1 < 10) {
			sb.append('0');
		}
		sb.append(month + 1);
		sb.append('/');
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
	 * Retorna uma string que representa a data no formato especifico. O formato
	 * � especificado pelo formato do SimpleDateFormat.
	 * 
	 * @param format
	 * @return data no formatato
	 */
	@Override
	public String toString(String format) {
		StringBuilder sb = new StringBuilder();
		int year = this.getYear();
		int month = this.getMonth();
		int length = format.length();
		for (int n = 0; n < length; n++) {
			switch (format.charAt(n)) {
			case 'M': {
				if (n + 1 < length && format.charAt(n + 1) == 'M') {
					if (month < 10) {
						sb.append('0');
					}
					n++;
				}
				sb.append(month + 1);
				break;
			}
			case 'y': {
				if (n + 3 < length && format.charAt(n + 1) == 'y'
						&& format.charAt(n + 2) == 'y'
						&& format.charAt(n + 3) == 'y') {
					n += 3;
					int aux = year;
					if (aux < 1000) {
						sb.append('0');
					}
					if (aux < 100) {
						sb.append('0');
					}
					if (aux < 10) {
						sb.append('0');
					}
					sb.append(aux);
				} else if (n + 2 < length && format.charAt(n + 1) == 'y'
						&& format.charAt(n + 2) == 'y') {
					n += 2;
					int aux = year - (year / 1000) * 1000;
					if (aux < 100) {
						sb.append('0');
					}
					if (aux < 10) {
						sb.append('0');
					}
					sb.append(aux);
				} else if (n + 1 < length && format.charAt(n + 1) == 'y') {
					n++;
					int aux = year - (year / 1000) * 1000;
					aux -= (aux / 100) * 100;
					if (aux < 10) {
						sb.append('0');
					}
					sb.append(aux);
				} else {
					int aux = year - (year / 1000) * 1000;
					aux -= (aux / 100) * 100;
					aux -= (aux / 10) * 10;
					sb.append(aux);
				}
				break;
			}
			default: {
				sb.append(format.charAt(n));
			}
			}
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthTime clone() {
		return new MonthTime(time);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Time o) {
		if (o == null || o instanceof MonthTime == false) {
			return 1;
		}
		return this.time - ((MonthTime) o).time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || o instanceof MonthTime == false) {
			return false;
		}
		return this.time == ((MonthTime) o).time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return this.time;
	}

	/**
	 * @return indice do m�s
	 */
	public int toIndex() {
		return this.getYear() * 12 + this.getMonth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthTime toMonthTime() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean before(Time date) {
		if (date instanceof MonthTime) {
			MonthTime monthTime = (MonthTime) date;
			return this.time < monthTime.time;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean after(Time date) {
		if (date instanceof MonthTime) {
			MonthTime monthTime = (MonthTime) date;
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

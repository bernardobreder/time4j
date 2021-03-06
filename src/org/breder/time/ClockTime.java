package org.breder.time;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe de Data implementada atraves de operacoes aritmeticas.
 * 
 * 
 * @author Tecgraf
 */
public class ClockTime implements Time {

	/** Tempo */
	protected long time;

	/**
	 * Construtor
	 * 
	 * @param year
	 *            ano entre 0 e 9999
	 * @param month
	 *            mes entre 0 e 11
	 * @param day
	 *            dia entre 1 e 31
	 */
	public ClockTime(int year, int month, int day) {
		if (year < 0 || year > 9999) {
			throw new IllegalArgumentException("" + year);
		}
		if (month < 0 || month > 11) {
			throw new IllegalArgumentException("" + month);
		}
		if (day < 1 || day > MonthTime.getLastDayOfMonth(month, year)) {
			throw new IllegalArgumentException("" + day);
		}
		this.time = getTime(year, month, day, 0, 0, 0);
	}

	/**
	 * Construtor
	 * 
	 * @param year
	 *            ano entre 0 e 9999
	 * @param month
	 *            mes entre 0 e 11
	 * @param day
	 *            dia entre 1 e 31
	 */
	public ClockTime(int year, int month, int day, int hour, int minute,
			int second) {
		if (year < 0 || year > 9999) {
			throw new IllegalArgumentException("" + year);
		}
		if (month < 0 || month > 11) {
			throw new IllegalArgumentException("" + month);
		}
		if (day < 1 || day > MonthTime.getLastDayOfMonth(month, year)) {
			throw new IllegalArgumentException("" + day);
		}
		if (hour < 0 || hour > 23) {
			throw new IllegalArgumentException("" + hour);
		}
		if (minute < 0 || minute > 59) {
			throw new IllegalArgumentException("" + minute);
		}
		if (second < 0 || second > 59) {
			throw new IllegalArgumentException("" + second);
		}
		this.time = getTime(year, month, day, hour, minute, second);
	}

	/**
	 * Converte para um inteiro o ano, mes e dia
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return valor do time
	 */
	protected long getTime(int year, int month, int day, int hour, int minute,
			int second) {
		long time = ((long) year * 10000 + month * 100 + day) * 1000000;
		time += hour * 10000 + minute * 100 + second;
		return time;
	}

	/**
	 * Construtor
	 * 
	 * @param date
	 */
	public ClockTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		this.time = getTime(year, month, day, hour, minute, second);
	}

	/**
	 * Construtor
	 * 
	 * @param c
	 */
	public ClockTime(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		this.time = getTime(year, month, day, hour, minute, second);
	}

	/**
	 * Construtor de agora
	 */
	public ClockTime() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		this.time = getTime(year, month, day, hour, minute, second);
	}

	/**
	 * Construtor interno
	 * 
	 * @param time
	 */
	private ClockTime(long time) {
		this.time = time;
	}

	/**
	 * Adiciona dias
	 * 
	 * @param amount
	 *            quantidade de dias
	 * @return novo objeto modificado
	 */
	@Override
	public ClockTime addDay(int amount) {
		if (amount == 0) {
			return this;
		}
		int year = this.getYear();
		int month = this.getMonth();
		int day = this.getDay();
		if (amount > 0) {
			while (amount > 0) {
				int delta = Math.min(amount,
						MonthTime.getLastDayOfMonth(month, year) - day);
				amount -= delta;
				day += delta;
				if (amount == 0) {
					break;
				}
				amount--;
				day = 1;
				if (month == 11) {
					month = 0;
					year++;
				} else {
					month++;
				}
			}
		} else {
			while (amount < 0) {
				int delta = Math.min(-amount, day - 1);
				amount += delta;
				day -= delta;
				if (amount == 0) {
					break;
				}
				amount++;
				if (month == 0) {
					month = 11;
					year--;
				} else {
					month--;
				}
				day = MonthTime.getLastDayOfMonth(month, year);
			}
		}
		return new ClockTime(year, month, day);
	}

	/**
	 * Adiciona meses
	 * 
	 * @param amount
	 *            quantidade de meses
	 * @return novo objeto modificado
	 */
	@Override
	public ClockTime addMonth(int amount) {
		if (amount == 0) {
			return this;
		}
		int year = this.getYear();
		int month = this.getMonth();
		int day = this.getDay();
		if (amount > 0) {
			while (amount > 0) {
				int delta = Math.min(amount, 11 - month);
				amount -= delta;
				month += delta;
				if (amount == 0) {
					break;
				}
				amount--;
				month = 0;
				year++;
			}
		} else {
			while (amount < 0) {
				int delta = Math.min(-amount, month);
				amount += delta;
				month -= delta;
				if (amount == 0) {
					break;
				}
				amount++;
				month = 11;
				year--;
			}
		}
		int lastDayOfMonth = MonthTime.getLastDayOfMonth(month, year);
		if (day > lastDayOfMonth) {
			day = lastDayOfMonth;
		}
		return new ClockTime(year, month, day);
	}

	/**
	 * Adiciona anos
	 * 
	 * @param amount
	 *            quantidade de anos
	 * @return novo objeto modificado
	 */
	public ClockTime addYear(int amount) {
		if (amount == 0) {
			return this;
		}
		int year = this.getYear();
		int month = this.getMonth();
		int day = this.getDay();
		year += amount;
		int lastDayOfMonth = MonthTime.getLastDayOfMonth(month, year);
		if (day > lastDayOfMonth) {
			day = lastDayOfMonth;
		}
		return new ClockTime(year, month, day);
	}

	/**
	 * @return dia da data
	 */
	@Override
	public int getDay() {
		long t = time / 1000000l;
		return (int) (t - (t / 10000) * 10000 - ((t / 100) * 100 - (t / 10000) * 10000));
	}

	/**
	 * @return m�s da data
	 */
	@Override
	public int getMonth() {
		long t = time / 1000000l;
		return (int) (t / 100 - (t / 10000) * 100);
	}

	/**
	 * @return ano da data
	 */
	@Override
	public int getYear() {
		long t = time / 1000000l;
		return (int) (t / 10000);
	}

	public int getSecond() {
		long t = time / 1000000l;
		t = time - t * 1000000l;
		return (int) (t - (t / 10000) * 10000 - ((t / 100) * 100 - (t / 10000) * 10000));
	}

	public int getMinute() {
		long t = time / 1000000l;
		t = time - t * 1000000l;
		return (int) (t / 100 - (t / 10000) * 100);
	}

	public int getHour() {
		long t = time / 1000000;
		t = time - t * 1000000;
		return (int) (t / 10000);
	}

	/**
	 * Retorna a diferen�a de dias entre duas datas. <br/>
	 * <br/>
	 * A id�ia do algoritmo � primeiro calcular o n�mero de dias que o dia da
	 * data m�nima precisa incrementar para ficar com o mesmo dia da data
	 * m�xima. Depois disso, incrementa com o calculo do n�mero de meses em dias
	 * da data minima necess�rio para ficar com o mesmo m�s da data m�xima. Por
	 * fim, incrementa com o calculo do n�mero de anos em dias da data m�nima
	 * necess�rio para ficar com o mesmo ano da data m�xima. O resultado do
	 * algoritmo � somar os tr�s calculos.<br/>
	 * <br/>
	 * � importante comentar que o primeiro e o segundo calculo pode mudar o m�s
	 * e o ano da data minima. Com isso, a melhor implementa��o � trabalhar com
	 * um array de inteiro para que o m�s e o ano posso ser atualizar de um
	 * calculo para o outro.
	 * 
	 * @param other
	 * @return diferenca de meses de duas datas
	 */
	public int getElapsedDays(ClockTime other) {
		if (this.equals(other)) {
			return 0;
		}
		ClockTime min = this.compareTo(other) > 0 ? other : this;
		ClockTime max = this.compareTo(other) > 0 ? this : other;
		int minYear = min.getYear();
		int minMonth = min.getMonth();
		int minDay = min.getDay();
		int maxYear = max.getYear();
		int maxMonth = max.getMonth();
		int maxDay = max.getDay();
		int[] fields = new int[6];
		fields[0] = minYear;
		fields[1] = minMonth;
		fields[2] = minDay;
		fields[3] = maxYear;
		fields[4] = maxMonth;
		fields[5] = maxDay;
		int elapsed = 0;
		elapsed += getElapsedInDay(fields);
		elapsed += getElapsedInMonth(fields);
		elapsed += getElapsedInYear(fields);
		return elapsed;
	}

	/**
	 * Retorna o n�mero de dias considerando somente os dias da diferen�a de uma
	 * data.
	 * 
	 * @param fields
	 * @return n�mero de dias
	 */
	private int getElapsedInDay(int[] fields) {
		int minDay = fields[2];
		int maxDay = fields[5];
		if (minDay == maxDay) {
			return 0;
		} else if (minDay > maxDay) {
			int minMonth = fields[1]++;
			int minYear = fields[0];
			if (minMonth == 11) {
				fields[1] = 0;
				fields[0]++;
			}
			return MonthTime.getLastDayOfMonth(minMonth, minYear) - minDay
					+ maxDay;
		} else {
			return maxDay - minDay;
		}
	}

	/**
	 * Retorna o n�mero de dias considerando somente os dias da diferen�a de uma
	 * data.
	 * 
	 * @param fields
	 * @return n�mero de dias
	 */
	private int getElapsedInMonth(int[] fields) {
		int minMonth = fields[1];
		int maxMonth = fields[4];
		if (minMonth == maxMonth) {
			return 0;
		}
		int minYear = fields[0];
		int elapsed = 0;
		while (minMonth != maxMonth) {
			elapsed += MonthTime.getLastDayOfMonth(minMonth, minYear);
			minMonth++;
			if (minMonth == 12) {
				fields[1] = minMonth = 0;
				fields[0] = ++minYear;
			}
		}
		return elapsed;
	}

	/**
	 * Retorna o n�mero de dias considerando somente os dias da diferen�a de uma
	 * data.
	 * 
	 * @param fields
	 * @return n�mero de dias
	 */
	private int getElapsedInYear(int[] fields) {
		int minYear = fields[0];
		int maxYear = fields[3];
		if (minYear >= maxYear) {
			return 0;
		}
		int maxMonth = fields[4];
		int m = maxMonth;
		int elapsed = 0;
		while (minYear <= maxYear) {
			int size = minYear == maxYear ? maxMonth : 12;
			for (int n = m; n < size; n++) {
				elapsed += MonthTime.getLastDayOfMonth(n, minYear);
			}
			m = 0;
			minYear++;
		}
		return elapsed;
	}

	/**
	 * @return objeto que considera somente o m�s e ano
	 */
	@Override
	public MonthTime toMonthTime() {
		return new MonthTime(this.getYear(), this.getMonth());
	}

	/**
	 * Retorna um objeto de dia com o primeiro dia do m�s
	 * 
	 * @return objeto dia com o primeiro dia do m�s
	 */
	public ClockTime toFirstDayTime() {
		return new ClockTime(this.getYear(), this.getMonth(), 1);
	}

	/**
	 * Retorna um objeto de dia com o �ltimo dia do m�s
	 * 
	 * @return objeto dia com o �ltimo dia do m�s
	 */
	public ClockTime toLastDayTime() {
		int year = this.getYear();
		int month = this.getMonth();
		return new ClockTime(year, month, MonthTime.getLastDayOfMonth(month,
				year));
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
		int year = this.getYear();
		int month = this.getMonth();
		int day = this.getDay();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClockTime clone() {
		return new ClockTime(time);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Time o) {
		if (o == null || o instanceof ClockTime == false) {
			return 1;
		}
		int result = (int) (this.time - ((ClockTime) o).time);
		if (result == 0 && this.time != ((ClockTime) o).time) {
			return this.time < ((ClockTime) o).time ? -1 : 1;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || o instanceof ClockTime == false) {
			return false;
		}
		return this.time == ((ClockTime) o).time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return (int) this.time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		int year = this.getYear();
		int month = this.getMonth();
		int day = this.getDay();
		StringBuilder sb = new StringBuilder();
		if (day < 10) {
			sb.append('0');
		}
		sb.append(day);
		sb.append('/');
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
		int day = this.getDay();
		int length = format.length();
		for (int n = 0; n < length; n++) {
			switch (format.charAt(n)) {
			case 'd': {
				if (n + 1 < length && format.charAt(n + 1) == 'd') {
					if (day < 10) {
						sb.append('0');
					}
					n++;
				}
				sb.append(day);
				break;
			}
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
	 * @param time
	 * @return indica se é menor
	 */
	@Override
	public boolean before(Time time) {
		if (time instanceof ClockTime) {
			ClockTime dayTime = (ClockTime) time;
			return this.time < dayTime.time;
		} else if (time instanceof MonthTime) {
			MonthTime monthTime = (MonthTime) time;
			return this.time < monthTime.toFirstDayTime().time;
		}
		return this.toDate().compareTo(time.toDate()) < 0;
	}

	/**
	 * @param time
	 * @return indica se � menor
	 */
	@Override
	public boolean after(Time time) {
		if (time instanceof ClockTime) {
			ClockTime dayTime = (ClockTime) time;
			return this.time > dayTime.time;
		} else if (time instanceof MonthTime) {
			MonthTime monthTime = (MonthTime) time;
			return this.time > monthTime.toLastDayTime().time;
		}
		return this.toDate().compareTo(time.toDate()) > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DayTime toDayTime() {
		return new DayTime(getYear(), getMonth(), getDay());
	}

	/**
	 * @param s
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		this.time = s.readLong();
	}

	/**
	 * @param s
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		s.writeLong(time);
	}

	/**
	 * @param s
	 * @return day
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ClockTime readStaticObject(java.io.ObjectInputStream s)
			throws IOException, ClassNotFoundException {
		return new ClockTime(s.readLong());
	}

	/**
	 * @param s
	 * @param o
	 * @throws IOException
	 */
	public static void writeStaticObject(java.io.ObjectOutputStream s,
			ClockTime o) throws IOException {
		s.writeLong(o.time);
	}

}

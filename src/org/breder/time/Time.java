package org.breder.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe de Data implementada atraves de operacoes aritmeticas.
 * 
 * 
 * @author Tecgraf
 */
public interface Time extends Comparable<Time>, Serializable {

	/**
	 * Converte a data para {@link Date}
	 * 
	 * @return date do jdk
	 */
	public Date toDate();

	/**
	 * Converte a data para {@link java.sql.Date}
	 * 
	 * @return date do jdk
	 */
	public java.sql.Date toSqlDate();

	/**
	 * Converte a data para o {@link Calendar}
	 * 
	 * @return calend�rio do jdk
	 */
	public Calendar toCalendar();

	/**
	 * Formata a sa�da de uma String
	 * 
	 * @param format
	 * @return String formatada
	 */
	public String toString(String format);

	/**
	 * Constroi um objeto que soh considera m�s e ano
	 * 
	 * @return retorna um objeto mes
	 */
	public MonthTime toMonthTime();

	/**
	 * @param date
	 * @return indica se eh antes a data
	 */
	public boolean before(Time date);

	/**
	 * @param date
	 * @return indica se � depois da data
	 */
	public boolean after(Time date);

	/**
	 * Constroi um objeto que s� considera dia, m�s e ano
	 * 
	 * @return retorna um objeto dia
	 */
	public DayTime toDayTime();

	/**
	 * Recupera o m�s da data
	 * 
	 * @return m�s no intervalo de 0-11
	 */
	public int getMonth();

	/**
	 * Recupera o ano da data
	 * 
	 * @return ano
	 */
	public int getYear();

	/**
	 * Recupera o dia da data
	 * 
	 * @return dia no intervalo de 1-31
	 */
	public int getDay();

	/**
	 * Adiciona anos
	 * 
	 * @param amount
	 * @return
	 */
	public Time addYear(int amount);

	/**
	 * Adiciona meses na data e recupere uma nova data
	 * 
	 * @param count
	 * @return nova data
	 */
	public Time addMonth(int count);

	/**
	 * Adiciona meses na data e recupere uma nova data
	 * 
	 * @param count
	 * @return nova data
	 */
	public Time addDay(int count);

	/**
	 * Obriga a sobrescrever Object.clone().
	 * 
	 * @return A c�pia do objeto
	 */
	public Time clone();

}

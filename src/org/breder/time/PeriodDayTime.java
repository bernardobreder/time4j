package org.breder.time;

import java.io.Serializable;

/**
 * Classe de Data implementada atrav�s de opera��es aritm�ticas.
 * 
 * 
 * @author Tecgraf
 */
public class PeriodDayTime implements Serializable {

	/** Tempo */
	protected DayTime initTime;
	/** Tempo Final */
	protected DayTime endTime;

	/**
	 * Construtor
	 * 
	 * @param initTime
	 * @param endTime
	 */
	public PeriodDayTime(DayTime initTime, DayTime endTime) {
		this.initTime = initTime;
		this.endTime = endTime;
	}

	/**
	 * Retorna
	 * 
	 * @return initTime
	 */
	public DayTime getInitTime() {
		return initTime;
	}

	/**
	 * Retorna
	 * 
	 * @return endTime
	 */
	public DayTime getEndTime() {
		return endTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PeriodDayTime clone() {
		return new PeriodDayTime(initTime.clone(), endTime.clone());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || o instanceof PeriodDayTime == false) {
			return false;
		}
		PeriodDayTime other = (PeriodDayTime) o;
		return this.initTime.equals(other.initTime)
				&& this.endTime.equals(other.endTime);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return this.initTime.hashCode() + 31 * this.endTime.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.initTime.toString());
		sb.append(" - ");
		sb.append(this.endTime.toString());
		return sb.toString();
	}

	/**
	 * Retorna uma string que representa a data no formato especifico. O formato
	 * � especificado pelo formato do SimpleDateFormat.
	 * 
	 * @param format
	 * @return data no formatato
	 */
	public String toString(String format) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.initTime.toString(format));
		sb.append(" - ");
		sb.append(this.endTime.toString(format));
		return sb.toString();
	}

	/**
	 * @return n�mero de dias
	 */
	public int getNumberOfDays() {
		return this.initTime.getElapsedDays(endTime);
	}

}

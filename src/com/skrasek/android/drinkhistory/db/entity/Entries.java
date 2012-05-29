package com.skrasek.android.drinkhistory.db.entity;


import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="Entries")
public class Entries implements Serializable
{
	/** 
	 * This attribute maps to the column entryId in the ENTRIES table.
	 */
@DatabaseField(generatedId=true,columnName="entryId")
	protected long entryId;

	/** 
	 * This attribute maps to the column visitId in the ENTRIES table.
	 */
@DatabaseField(columnName="visitId")
	protected int visitId;

	/** 
	 * This attribute represents whether the primitive attribute visitId is null.
	 */
	protected boolean visitIdNull = true;

	/** 
	 * This attribute maps to the column drinkId in the ENTRIES table.
	 */
@DatabaseField(columnName="drinkId")
	protected int drinkId;

	/** 
	 * This attribute represents whether the primitive attribute drinkId is null.
	 */
	protected boolean drinkIdNull = true;

	/** 
	 * This attribute maps to the column addedTime in the ENTRIES table.
	 */
@DatabaseField(columnName="addedTime")
	protected Date addedTime;

	/**
	 * Method 'Entries'
	 * 
	 */
	public Entries()
	{
	}

	/**
	 * Method 'getEntryId'
	 * 
	 * @return long
	 */
	public long getEntryId()
	{
		return entryId;
	}

	/**
	 * Method 'setEntryId'
	 * 
	 * @param entryId
	 */
	public void setEntryId(long entryId)
	{
		this.entryId = entryId;
	}

	/**
	 * Method 'getVisitId'
	 * 
	 * @return int
	 */
	public int getVisitId()
	{
		return visitId;
	}

	/**
	 * Method 'setVisitId'
	 * 
	 * @param visitId
	 */
	public void setVisitId(int visitId)
	{
		this.visitId = visitId;
		this.visitIdNull = false;
	}

	/**
	 * Method 'setVisitIdNull'
	 * 
	 * @param value
	 */
	public void setVisitIdNull(boolean value)
	{
		this.visitIdNull = value;
	}

	/**
	 * Method 'isVisitIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isVisitIdNull()
	{
		return visitIdNull;
	}

	/**
	 * Method 'getDrinkId'
	 * 
	 * @return int
	 */
	public int getDrinkId()
	{
		return drinkId;
	}

	/**
	 * Method 'setDrinkId'
	 * 
	 * @param drinkId
	 */
	public void setDrinkId(int drinkId)
	{
		this.drinkId = drinkId;
		this.drinkIdNull = false;
	}

	/**
	 * Method 'setDrinkIdNull'
	 * 
	 * @param value
	 */
	public void setDrinkIdNull(boolean value)
	{
		this.drinkIdNull = value;
	}

	/**
	 * Method 'isDrinkIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isDrinkIdNull()
	{
		return drinkIdNull;
	}

	/**
	 * Method 'getAddedTime'
	 * 
	 * @return Date
	 */
	public Date getAddedTime()
	{
		return addedTime;
	}

	/**
	 * Method 'setAddedTime'
	 * 
	 * @param addedTime
	 */
	public void setAddedTime(Date addedTime)
	{
		this.addedTime = addedTime;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof Entries)) {
			return false;
		}
		
		final Entries _cast = (Entries) _other;
		if (entryId != _cast.entryId) {
			return false;
		}
		
		if (visitId != _cast.visitId) {
			return false;
		}
		
		if (visitIdNull != _cast.visitIdNull) {
			return false;
		}
		
		if (drinkId != _cast.drinkId) {
			return false;
		}
		
		if (drinkIdNull != _cast.drinkIdNull) {
			return false;
		}
		
		if (addedTime == null ? _cast.addedTime != addedTime : !addedTime.equals( _cast.addedTime )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + (int) (entryId ^ (entryId >>> 32));
		_hashCode = 29 * _hashCode + visitId;
		_hashCode = 29 * _hashCode + (visitIdNull ? 1 : 0);
		_hashCode = 29 * _hashCode + drinkId;
		_hashCode = 29 * _hashCode + (drinkIdNull ? 1 : 0);
		if (addedTime != null) {
			_hashCode = 29 * _hashCode + addedTime.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.mycompany.myapp.dto.Entries: " );
		ret.append( "entryId=" + entryId );
		ret.append( ", visitId=" + visitId );
		ret.append( ", drinkId=" + drinkId );
		ret.append( ", addedTime=" + addedTime );
		return ret.toString();
	}

}

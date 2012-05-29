package com.skrasek.android.drinkhistory.db.entity;


import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="Visits")
public class Visits implements Serializable
{
	/** 
	 * This attribute maps to the column visitId in the VISITS table.
	 */
@DatabaseField(generatedId=true,columnName="visitId")
	protected long visitId;

	/** 
	 * This attribute maps to the column pubId in the VISITS table.
	 */
@DatabaseField(columnName="pubId")
	protected int pubId;

	/** 
	 * This attribute represents whether the primitive attribute pubId is null.
	 */
	protected boolean pubIdNull = true;

	/** 
	 * This attribute maps to the column createdTime in the VISITS table.
	 */
@DatabaseField(columnName="createdTime")
	protected Date createdTime;

	/** 
	 * This attribute maps to the column lat in the VISITS table.
	 */
@DatabaseField(columnName="lat")
	protected float lat;

	/** 
	 * This attribute represents whether the primitive attribute lat is null.
	 */
	protected boolean latNull = true;

	/** 
	 * This attribute maps to the column lon in the VISITS table.
	 */
@DatabaseField(columnName="lon")
	protected float lon;

	/** 
	 * This attribute represents whether the primitive attribute lon is null.
	 */
	protected boolean lonNull = true;

	/**
	 * Method 'Visits'
	 * 
	 */
	public Visits()
	{
	}

	/**
	 * Method 'getVisitId'
	 * 
	 * @return long
	 */
	public long getVisitId()
	{
		return visitId;
	}

	/**
	 * Method 'setVisitId'
	 * 
	 * @param visitId
	 */
	public void setVisitId(long visitId)
	{
		this.visitId = visitId;
	}

	/**
	 * Method 'getPubId'
	 * 
	 * @return int
	 */
	public int getPubId()
	{
		return pubId;
	}

	/**
	 * Method 'setPubId'
	 * 
	 * @param pubId
	 */
	public void setPubId(int pubId)
	{
		this.pubId = pubId;
		this.pubIdNull = false;
	}

	/**
	 * Method 'setPubIdNull'
	 * 
	 * @param value
	 */
	public void setPubIdNull(boolean value)
	{
		this.pubIdNull = value;
	}

	/**
	 * Method 'isPubIdNull'
	 * 
	 * @return boolean
	 */
	public boolean isPubIdNull()
	{
		return pubIdNull;
	}

	/**
	 * Method 'getCreatedTime'
	 * 
	 * @return Date
	 */
	public Date getCreatedTime()
	{
		return createdTime;
	}

	/**
	 * Method 'setCreatedTime'
	 * 
	 * @param createdTime
	 */
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	/**
	 * Method 'getLat'
	 * 
	 * @return float
	 */
	public float getLat()
	{
		return lat;
	}

	/**
	 * Method 'setLat'
	 * 
	 * @param lat
	 */
	public void setLat(float lat)
	{
		this.lat = lat;
		this.latNull = false;
	}

	/**
	 * Method 'setLatNull'
	 * 
	 * @param value
	 */
	public void setLatNull(boolean value)
	{
		this.latNull = value;
	}

	/**
	 * Method 'isLatNull'
	 * 
	 * @return boolean
	 */
	public boolean isLatNull()
	{
		return latNull;
	}

	/**
	 * Method 'getLon'
	 * 
	 * @return float
	 */
	public float getLon()
	{
		return lon;
	}

	/**
	 * Method 'setLon'
	 * 
	 * @param lon
	 */
	public void setLon(float lon)
	{
		this.lon = lon;
		this.lonNull = false;
	}

	/**
	 * Method 'setLonNull'
	 * 
	 * @param value
	 */
	public void setLonNull(boolean value)
	{
		this.lonNull = value;
	}

	/**
	 * Method 'isLonNull'
	 * 
	 * @return boolean
	 */
	public boolean isLonNull()
	{
		return lonNull;
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
		
		if (!(_other instanceof Visits)) {
			return false;
		}
		
		final Visits _cast = (Visits) _other;
		if (visitId != _cast.visitId) {
			return false;
		}
		
		if (pubId != _cast.pubId) {
			return false;
		}
		
		if (pubIdNull != _cast.pubIdNull) {
			return false;
		}
		
		if (createdTime == null ? _cast.createdTime != createdTime : !createdTime.equals( _cast.createdTime )) {
			return false;
		}
		
		if (lat != _cast.lat) {
			return false;
		}
		
		if (latNull != _cast.latNull) {
			return false;
		}
		
		if (lon != _cast.lon) {
			return false;
		}
		
		if (lonNull != _cast.lonNull) {
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
		_hashCode = 29 * _hashCode + (int) (visitId ^ (visitId >>> 32));
		_hashCode = 29 * _hashCode + pubId;
		_hashCode = 29 * _hashCode + (pubIdNull ? 1 : 0);
		if (createdTime != null) {
			_hashCode = 29 * _hashCode + createdTime.hashCode();
		}
		
		_hashCode = 29 * _hashCode + Float.floatToIntBits(lat);
		_hashCode = 29 * _hashCode + (latNull ? 1 : 0);
		_hashCode = 29 * _hashCode + Float.floatToIntBits(lon);
		_hashCode = 29 * _hashCode + (lonNull ? 1 : 0);
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
		ret.append( "com.mycompany.myapp.dto.Visits: " );
		ret.append( "visitId=" + visitId );
		ret.append( ", pubId=" + pubId );
		ret.append( ", createdTime=" + createdTime );
		ret.append( ", lat=" + lat );
		ret.append( ", lon=" + lon );
		return ret.toString();
	}

}

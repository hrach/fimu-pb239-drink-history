package com.skrasek.android.drinkhistory.db.entity;


import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="Pubs")
public class Pubs implements Serializable
{
	/** 
	 * This attribute maps to the column pubId in the PUBS table.
	 */
@DatabaseField(generatedId=true,columnName="pubId")
	protected long pubId;

	/** 
	 * This attribute maps to the column name in the PUBS table.
	 */
@DatabaseField(columnName="name")
	protected String name;

	/** 
	 * This attribute maps to the column lat in the PUBS table.
	 */
@DatabaseField(columnName="lat")
	protected float lat;

	/** 
	 * This attribute represents whether the primitive attribute lat is null.
	 */
	protected boolean latNull = true;

	/** 
	 * This attribute maps to the column lon in the PUBS table.
	 */
@DatabaseField(columnName="lon")
	protected float lon;

	/** 
	 * This attribute represents whether the primitive attribute lon is null.
	 */
	protected boolean lonNull = true;

	/**
	 * Method 'Pubs'
	 * 
	 */
	public Pubs()
	{
	}

	/**
	 * Method 'getPubId'
	 * 
	 * @return long
	 */
	public long getPubId()
	{
		return pubId;
	}

	/**
	 * Method 'setPubId'
	 * 
	 * @param pubId
	 */
	public void setPubId(long pubId)
	{
		this.pubId = pubId;
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
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
		
		if (!(_other instanceof Pubs)) {
			return false;
		}
		
		final Pubs _cast = (Pubs) _other;
		if (pubId != _cast.pubId) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
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
		_hashCode = 29 * _hashCode + (int) (pubId ^ (pubId >>> 32));
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
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
		ret.append( "com.mycompany.myapp.dto.Pubs: " );
		ret.append( "pubId=" + pubId );
		ret.append( ", name=" + name );
		ret.append( ", lat=" + lat );
		ret.append( ", lon=" + lon );
		return ret.toString();
	}

}

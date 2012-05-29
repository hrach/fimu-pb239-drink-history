package com.skrasek.android.drinkhistory.db.entity;


import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="DRINK_TYPES")
public class DrinkTypes implements Serializable
{
	/** 
	 * This attribute maps to the column drinkTypeId in the DRINK_TYPES table.
	 */
@DatabaseField(generatedId=true,columnName="drinkTypeId")
	protected long drinkTypeId;

	/** 
	 * This attribute maps to the column name in the DRINK_TYPES table.
	 */
@DatabaseField(columnName="name")
	protected String name;

	/** 
	 * This attribute maps to the column type in the DRINK_TYPES table.
	 */
@DatabaseField(columnName="type")
	protected String type;

	/**
	 * Method 'DrinkTypes'
	 * 
	 */
	public DrinkTypes()
	{
	}

	/**
	 * Method 'getDrinkTypeId'
	 * 
	 * @return long
	 */
	public long getDrinkTypeId()
	{
		return drinkTypeId;
	}

	/**
	 * Method 'setDrinkTypeId'
	 * 
	 * @param drinkTypeId
	 */
	public void setDrinkTypeId(long drinkTypeId)
	{
		this.drinkTypeId = drinkTypeId;
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
	 * Method 'getType'
	 * 
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
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
		
		if (!(_other instanceof DrinkTypes)) {
			return false;
		}
		
		final DrinkTypes _cast = (DrinkTypes) _other;
		if (drinkTypeId != _cast.drinkTypeId) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (type == null ? _cast.type != type : !type.equals( _cast.type )) {
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
		_hashCode = 29 * _hashCode + (int) (drinkTypeId ^ (drinkTypeId >>> 32));
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (type != null) {
			_hashCode = 29 * _hashCode + type.hashCode();
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
		ret.append( "com.mycompany.myapp.dto.DrinkTypes: " );
		ret.append( "drinkTypeId=" + drinkTypeId );
		ret.append( ", name=" + name );
		ret.append( ", type=" + type );
		return ret.toString();
	}

}

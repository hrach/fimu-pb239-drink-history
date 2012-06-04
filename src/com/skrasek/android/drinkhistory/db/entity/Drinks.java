package com.skrasek.android.drinkhistory.db.entity;


import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="Drinks")
public class Drinks implements Serializable
{
	/** 
	 * This attribute maps to the column drinkId in the DRINKS table.
	 */
@DatabaseField(generatedId=true,columnName="drinkId")
	protected long drinkId;

	/** 
	 * This attribute maps to the column name in the DRINKS table.
	 */
@DatabaseField(columnName="name")
	protected String name;

	/** 
	 * This attribute maps to the column price in the DRINKS table.
	 */
@DatabaseField(columnName="price")
	protected float price;

	/** 
	 * This attribute represents whether the primitive attribute price is null.
	 */
	protected boolean priceNull = true;

	/** 
	 * This attribute maps to the column createdTime in the DRINKS table.
	 */
@DatabaseField(columnName="createdTime")
	protected Date createdTime;

/** 
 * This attribute maps to the column createdTime in the DRINKS table.
 */
@DatabaseField(columnName="visitId")
protected int visitId;



	public int getVisitId() {
	return visitId;
}

public void setVisitId(int visitId) {
	this.visitId = visitId;
}

	/**
	 * Method 'Drinks'
	 * 
	 */
	public Drinks()
	{
	}

	/**
	 * Method 'getDrinkId'
	 * 
	 * @return long
	 */
	public long getDrinkId()
	{
		return drinkId;
	}

	/**
	 * Method 'setDrinkId'
	 * 
	 * @param drinkId
	 */
	public void setDrinkId(long drinkId)
	{
		this.drinkId = drinkId;
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
	 * Method 'getPrice'
	 * 
	 * @return float
	 */
	public float getPrice()
	{
		return price;
	}

	/**
	 * Method 'setPrice'
	 * 
	 * @param price
	 */
	public void setPrice(float price)
	{
		this.price = price;
		this.priceNull = false;
	}

	/**
	 * Method 'setPriceNull'
	 * 
	 * @param value
	 */
	public void setPriceNull(boolean value)
	{
		this.priceNull = value;
	}

	/**
	 * Method 'isPriceNull'
	 * 
	 * @return boolean
	 */
	public boolean isPriceNull()
	{
		return priceNull;
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
		
		if (!(_other instanceof Drinks)) {
			return false;
		}
		
		final Drinks _cast = (Drinks) _other;
		if (drinkId != _cast.drinkId) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (price != _cast.price) {
			return false;
		}
		
		if (priceNull != _cast.priceNull) {
			return false;
		}
		
		if (createdTime == null ? _cast.createdTime != createdTime : !createdTime.equals( _cast.createdTime )) {
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
		_hashCode = 29 * _hashCode + (int) (drinkId ^ (drinkId >>> 32));
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		_hashCode = 29 * _hashCode + Float.floatToIntBits(price);
		_hashCode = 29 * _hashCode + (priceNull ? 1 : 0);
		if (createdTime != null) {
			_hashCode = 29 * _hashCode + createdTime.hashCode();
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
		ret.append( "com.mycompany.myapp.dto.Drinks: " );
		ret.append( "drinkId=" + drinkId );
		ret.append( ", name=" + name );
		ret.append( ", price=" + price );
		ret.append( ", createdTime=" + createdTime );
		return ret.toString();
	}

}

package com.skrasek.android.drinkhistory.db;






public class SelectRow {

	private int id;
	private String text;
	private int idp;
	
	public void SetID(int ID)
	{
		id=ID;
	}
	
	public void SetIDP(int IDP)
	{
		idp=IDP;
	}
	
	public void SetText(String Text)
	{
		text=Text;
	}
	
	public int GetID()
	{
		return id;
	}
	
	public int GetIDP()
	{
		return idp;
	}
	
	public String GetText()
	{
		return text;
	}
}

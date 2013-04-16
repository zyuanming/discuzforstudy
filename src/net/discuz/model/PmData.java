package net.discuz.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PmData
{

	public PmData()
	{
		valueList = null;
		pmList = null;
	}

	public ArrayList getPmList()
	{
		return pmList;
	}

	public HashMap getValueList()
	{
		return valueList;
	}

	public void setPmList(ArrayList arraylist)
	{
		pmList = arraylist;
	}

	public void setValueList(HashMap hashmap)
	{
		valueList = hashmap;
	}

	public ArrayList pmList;
	public HashMap valueList;
}
//2131296256
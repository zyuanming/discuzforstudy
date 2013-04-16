package net.discuz.model;

import java.util.HashMap;

public class ProfileData
{

	public ProfileData()
	{
		myProfile = null;
		extcreditList = null;
	}

	public HashMap getExtCredits()
	{
		return extcreditList;
	}

	public HashMap getMyProfile()
	{
		return myProfile;
	}

	public void setExtCredits(HashMap hashmap)
	{
		extcreditList = hashmap;
	}

	public void setMyProfile(HashMap hashmap)
	{
		myProfile = hashmap;
	}

	public HashMap extcreditList;
	public HashMap myProfile;
}
//2131296256
package net.discuz.source.InterFace;

import org.json.JSONObject;

public interface OnLogin
{

	public abstract void loginError();

	public abstract void loginSuceess(String s, JSONObject jsonobject);
}

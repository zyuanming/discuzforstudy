package net.discuz.source;

import android.util.Log;
import java.io.*;

import org.json.*;

public class DJsonReader
{

	public DJsonReader(InputStream inputstream)
	{
		BufferedReader bufferedreader;
		isjson = true;
		jsonString = null;
		inputReader = null;
		jsonObj = null;
		version = 0;
		cloudMessage = null;
		cloudMessageVal = null;
		cloudMessage_arr = null;
		error = null;
		errorval = null;
		clouderror_arr = null;
		code = null;
		variables = null;
		variablesSimple = null;
		messageVal = null;
		messageStr = null;
		StringBuilder stringbuilder;
		String s;
		try
		{
			inputReader = new InputStreamReader(inputstream, "utf-8");
		} catch (UnsupportedEncodingException unsupportedencodingexception)
		{
			Log.e("Exception", "UnsupportedEncodingException");
			Log.e("Exception", unsupportedencodingexception.getMessage());
		}
		bufferedreader = new BufferedReader(inputReader);
		stringbuilder = new StringBuilder();
		while (true)
		{
			try
			{
				s = bufferedreader.readLine();
				if (s != null)
					stringbuilder.append(s).append("\n");
				else
					break;
			} catch (IOException e)
			{
				Log.e("Exception", "IOException");
				Log.e("Exception", e.getMessage());
				try
				{
					bufferedreader.close();
					inputstream.close();
				} catch (IOException ioexception)
				{
					ioexception.printStackTrace();
				}
			}
		}
		if (stringbuilder.length() > 0)
		{
			jsonString = stringbuilder.toString();
		}
	}

	public DJsonReader(String s)
	{
		isjson = true;
		jsonString = null;
		inputReader = null;
		jsonObj = null;
		version = 0;
		cloudMessage = null;
		cloudMessageVal = null;
		cloudMessage_arr = null;
		error = null;
		errorval = null;
		clouderror_arr = null;
		code = null;
		variables = null;
		variablesSimple = null;
		messageVal = null;
		messageStr = null;
		jsonString = s;
	}

	public void _cloudMessageParse(JSONObject jsonobject)
	{
		cloudMessage = jsonobject.optString("cloudmessage");
		cloudMessageVal = jsonobject.optString("cloudmessageval");
	}

	public void _debug()
	{}

	public void _errorParse(JSONObject jsonobject)
	{
		try
		{
			error = jsonobject.getString("error");
			errorval = jsonobject.getString("errorval");
			return;
		} catch (JSONException jsonexception)
		{
			jsonexception.printStackTrace();
		}
	}

	public String[] _getCloudMessage()
	{
		if (cloudMessageVal != null)
		{
			String as[] = new String[2];
			as[0] = cloudMessageVal;
			as[1] = cloudMessage;
			cloudMessage_arr = as;
		}
		return cloudMessage_arr;
	}

	public String[] _getError()
	{
		if (errorval != null)
		{
			String as[] = new String[2];
			as[0] = errorval;
			as[1] = error;
			clouderror_arr = as;
		}
		return clouderror_arr;
	}

	public String[] _getMessage()
	{
		String as[] = new String[2];
		as[0] = messageVal;
		as[1] = messageStr;
		return as;
	}

	public JSONObject _getVariables()
	{
		return variables;
	}

	public int _getVersion()
	{
		return version;
	}

	public void _jsonParse() throws JSONException
	{
		if (jsonString != null)
		{
			jsonObj = new JSONObject(jsonString);
			JSONArray jsonarray = jsonObj.names();
			int i = 0;
			if (jsonarray != null)
				while (i < jsonarray.length())
				{
					String s = jsonarray.getString(i);
					if (s.equals("Variables"))
					{
						_variableParse(jsonObj.optJSONObject(s));
					} else
					{
						if (s.equals("CloudError"))
						{
							_errorParse(jsonObj.optJSONObject(s));
							return;
						}
						if (s.equals("CloudMessage"))
							_cloudMessageParse(jsonObj.optJSONObject(s));
						else if (s.equals("Message"))
							_messageParse(jsonObj.optJSONObject(s));
						else if (s.equals("Version"))
							_versionParse(jsonObj.optInt(s));
						else if (s.equals("res"))
							_variableParse(jsonObj.optJSONObject(s));
						else if (s.equals("code"))
							code = jsonObj.getString("code");
					}
					i++;
				}
		} else
			isjson = false;
	}

	public JSONObject _jsonParseSimple()
	{
		if (jsonString != null)
			try
			{
				jsonObj = new JSONObject(jsonString);
			} catch (JSONException jsonexception)
			{
				jsonexception.printStackTrace();
				isjson = false;
			}
		return jsonObj;
	}

	public void _messageParse(JSONObject jsonobject)
	{
		messageVal = jsonobject.optString("messageval");
		messageStr = jsonobject.optString("messagestr");
	}

	public void _variableParse(JSONArray jsonarray) throws JSONException
	{}

	public void _variableParse(JSONObject jsonobject) throws JSONException
	{
		variables = jsonobject;
	}

	public void _versionParse(int i)
	{
		version = i;
	}

	public String getJsonString()
	{
		return jsonString;
	}

	public String cloudMessage;
	public String cloudMessageVal;
	public String cloudMessage_arr[];
	public String clouderror_arr[];
	public String code;
	public String error;
	public String errorval;
	public InputStreamReader inputReader;
	public boolean isjson;
	public JSONObject jsonObj;
	public String jsonString;
	public String messageStr;
	public String messageVal;
	public JSONObject variables;
	public JSONObject variablesSimple;
	public int version;
}
// 2131296256
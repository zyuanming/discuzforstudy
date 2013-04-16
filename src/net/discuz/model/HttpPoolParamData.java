package net.discuz.model;

import net.discuz.source.DEBUG;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class HttpPoolParamData implements Parcelable
{

	public HttpPoolParamData(String s)
	{
		mBundle = new Bundle();
		setFilter(s);
	}

	private void setFilter(String s)
	{
		mBundle.putString("filter", s);
	}

	public int describeContents()
	{
		return 0;
	}

	public Boolean getDelCache()
	{
		return Boolean.valueOf(mBundle.getBoolean("delcache"));
	}

	public String getFilter()
	{
		return mBundle.getString("filter");
	}

	public String getId()
	{
		return mBundle.getString("id");
	}

	public String getUrl()
	{
		return mBundle.getString("url");
	}

	public void setBundle(Bundle bundle)
	{
		mBundle = bundle;
	}

	public void setDelCache()
	{
		mBundle.putBoolean("delcache", true);
	}

	public void setId(String s)
	{
		mBundle.putString("id", s);
	}

	public void setUrl(String s)
	{
		DEBUG.o((new StringBuilder()).append("HttpPoolParamData set url:")
				.append(s).toString());
		mBundle.putString("url", s);
	}

	public void writeToParcel(Parcel parcel, int i)
	{
		parcel.writeBundle(mBundle);
	}

	public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator()
	{

		public Object createFromParcel(Parcel parcel)
		{
			DEBUG.o("HttpPool createFromParcel.");
			Bundle bundle = parcel.readBundle();
			HttpPoolParamData httppoolparamdata = null;
			if (bundle != null)
			{
				httppoolparamdata = new HttpPoolParamData(
						bundle.getString("filter"));
				httppoolparamdata.setBundle(bundle);
			}
			return httppoolparamdata;
		}

		public Object[] newArray(int i)
		{
			return new HttpPoolParamData[i];
		}

	};
	private Bundle mBundle;

}
//2131296256
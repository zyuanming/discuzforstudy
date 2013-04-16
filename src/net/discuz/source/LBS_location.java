package net.discuz.source;

import android.location.*;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import net.discuz.source.activity.DiscuzBaseActivity;
import org.json.JSONArray;
import org.json.JSONObject;

public class LBS_location
{
	private class DLocationListner implements LocationListener
	{

		public void onLocationChanged(Location location1)
		{
			DEBUG.o((new StringBuilder())
					.append("Got New Location of provider:")
					.append(location1.getProvider()).toString());
			unRegisterLocationListener();
			synchronized (thread)
			{
				try
				{
					parseLatLon(location1.getLatitude(),
							location1.getLongitude());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				looper.quit();
				thread.notify();
			}
			return;
		}

		public void onProviderDisabled(String s)
		{}

		public void onProviderEnabled(String s)
		{}

		public void onStatusChanged(String s, int i, Bundle bundle)
		{}
	}

	public class LocationData
	{

		String address;
		String lat;
		String lon;

		public LocationData()
		{
			super();
		}
	}

	private class LocationThread extends Thread
	{

		public void run()
		{
			setName("location tread");
			registerLocationListener();
		}

		private LocationThread()
		{
			super();
		}

	}

	private LBS_location(DiscuzBaseActivity discuzbaseactivity)
	{
		context = discuzbaseactivity;
		locationManager = (LocationManager) context
				.getSystemService("location");
	}

	public static LBS_location getInstance(DiscuzBaseActivity discuzbaseactivity)
	{
		if (instance == null)
			instance = new LBS_location(discuzbaseactivity);
		return instance;
	}

	private void parseLatLon(double d, double d1) throws Exception
	{
		try
		{
			JSONObject jsonobject = new JSONObject(
					(new HttpRequest())
							._get((new StringBuilder())
									.append("http://ditu.google.cn/maps/geo?output=json&q=")
									.append(d).append(",").append(d1)
									.toString()));
			if (jsonobject.optJSONArray("Placemark") != null)
			{
				location = new LocationData();
				location.lat = (new StringBuilder()).append(d).append("")
						.toString();
				location.lon = (new StringBuilder()).append(d1).append("")
						.toString();
				location.address = jsonobject.optJSONArray("Placemark")
						.optJSONObject(0).optString("address");
			}
			return;
		} catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	private void registerLocationListener()
	{
		Looper.prepare();
		looper = Looper.myLooper();
		if (isGPSEnabled())
		{
			gpsListener = new DLocationListner();
			locationManager.requestLocationUpdates("gps", 5000L, 0.0F,
					gpsListener, looper);
		}
		if (isNetworkEnabled())
		{
			networkListner = new DLocationListner();
			locationManager.requestLocationUpdates("network", 3000L, 0.0F,
					networkListner, looper);
		}
		Looper.loop();
	}

	private void unRegisterLocationListener()
	{
		if (gpsListener != null)
		{
			locationManager.removeUpdates(gpsListener);
			gpsListener = null;
		}
		if (networkListner != null)
		{
			locationManager.removeUpdates(networkListner);
			networkListner = null;
		}
	}

	public LocationData getLocation(long l)
	{
		LocationThread locationthread;
		location = null;
		thread = new LocationThread();
		thread.start();
		if (l <= 0L)
			l = 0L;
		locationthread = thread;
		try
		{
			thread.wait(l);
		} catch (Exception e)
		{
			thread = null;
		}
		return location;
	}

	public boolean isGPSEnabled()
	{
		return locationManager.isProviderEnabled("gps");
	}

	public boolean isNetworkEnabled()
	{
		return isTelephonyEnabled() || isWIFIEnabled();
	}

	public boolean isTelephonyEnabled()
	{
		TelephonyManager telephonymanager = (TelephonyManager) context
				.getSystemService("phone");
		return telephonymanager != null
				&& telephonymanager.getNetworkType() != 0;
	}

	public boolean isWIFIEnabled()
	{
		return ((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
	}

	private static LBS_location instance;
	private DiscuzBaseActivity context;
	private DLocationListner gpsListener;
	private LocationData location;
	private LocationManager locationManager;
	private Looper looper;
	private DLocationListner networkListner;
	private LocationThread thread;

}
//2131296256
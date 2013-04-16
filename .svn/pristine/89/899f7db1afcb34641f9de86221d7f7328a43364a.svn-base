// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import net.discuz.json.helper.HotThreadParseHelper;
import net.discuz.tools.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

public class HotThreadParseHelperX25 extends HotThreadParseHelper
{

    public HotThreadParseHelperX25(String s)
    {
        super(s);
    }

    protected void parseData()
    {
        JSONArray jsonarray = json.optJSONArray(DataName);
        json.length();
        int i = jsonarray.length();
        String as[] = DataKeys.split("\\|");
        for(int j = 0; j < i; j++)
        {
            HashMap hashmap = new HashMap();
            int k = 0;
            while(k < as.length) 
            {
                if(jsonarray.optJSONObject(j).optString(as[k]) != null)
                    if("dblastpost".equals(as[k]))
                    {
                        if(jsonarray.optJSONObject(j).optString(as[k]) != null && !"".equals(jsonarray.optJSONObject(j).optString(as[k])))
                            hashmap.put("lastpost", Tools._getNumToDateTime(jsonarray.optJSONObject(j).optString(as[k]), null));
                        else
                            hashmap.put("lastpost", jsonarray.optJSONObject(j).optString("dateline"));
                    } else
                    if("lastposter".equals(as[k]))
                    {
                        if(jsonarray.optJSONObject(j).optString(as[k]) != null && !"".equals(jsonarray.optJSONObject(j).optString(as[k])))
                            hashmap.put(as[k], jsonarray.optJSONObject(j).optString(as[k]));
                        else
                            hashmap.put("lastposter", jsonarray.optJSONObject(j).optString("author"));
                    } else
                    {
                        hashmap.put(as[k], jsonarray.optJSONObject(j).optString(as[k]));
                    }
                k++;
            }
            HotThreadsList.add(hashmap);
        }

    }
}
//2131296256
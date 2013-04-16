// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import net.discuz.json.helper.MyFavThreadParseHelper;
import net.discuz.tools.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyFavThreadParseHelperX25 extends MyFavThreadParseHelper
{

    public MyFavThreadParseHelperX25(String s)
    {
        super(s);
    }

    protected void parseData()
    {
        JSONArray jsonarray = json.optJSONArray(DataName);
        int i = jsonarray.length();
        String as[] = DataKeys.split("\\|");
        for(int j = 0; j < i; j++)
        {
            HashMap hashmap = new HashMap();
            int k = 0;
            while(k < as.length) 
            {
                if("dateline".equals(as[k]))
                    hashmap.put("dateline", Tools._getNumToDateTime(jsonarray.optJSONObject(j).optString(as[k]), null));
                else
                    hashmap.put(as[k], jsonarray.optJSONObject(j).optString(as[k]));
                k++;
            }
            MyFavThreadsList.add(hashmap);
        }

    }
}
//2131296256
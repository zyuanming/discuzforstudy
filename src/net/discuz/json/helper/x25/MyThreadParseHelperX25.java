// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.discuz.json.helper.MyThreadParseHelper;
import net.discuz.tools.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyThreadParseHelperX25 extends MyThreadParseHelper
{

    public MyThreadParseHelperX25(String s)
    {
        super(s);
    }

    protected void parseData()
    {
        JSONArray jsonarray = json.optJSONArray(DataName);
        int i = jsonarray.length();
        String as[] = DataKeys.split("\\|");
        Pattern pattern = Pattern.compile("<span title=.+>(.+)</span>");
        for(int j = 0; j < i; j++)
        {
            HashMap hashmap = new HashMap();
            int k = 0;
            while(k < as.length) 
            {
                if("dblastpost".equals(as[k]))
                {
                    if(jsonarray.optJSONObject(j).optString(as[k]) != null && !"".equals(jsonarray.optJSONObject(j).optString(as[k])))
                        hashmap.put("lastpost", Tools._getNumToDateTime(jsonarray.optJSONObject(j).optString(as[k]), null));
                } else
                {
                    Matcher matcher = pattern.matcher(jsonarray.optJSONObject(j).optString(as[k]));
                    if(matcher.matches())
                        hashmap.put(as[k], matcher.group(1));
                    else
                        hashmap.put(as[k], jsonarray.optJSONObject(j).optString(as[k]));
                }
                k++;
            }
            MyThreadsList.add(hashmap);
        }

    }
}
//2131296256
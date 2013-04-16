// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import net.discuz.json.helper.MyPmParseHelper;
import net.discuz.model.PmData;
import net.discuz.tools.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyPmParseHelperX25 extends MyPmParseHelper
{

    public MyPmParseHelperX25(String s)
    {
        super(s);
    }

    protected void parseData()
    {
        ArrayList arraylist = new ArrayList();
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
                {
                    String s = jsonarray.optJSONObject(j).optString(as[k]);
                    hashmap.put(as[k], Tools._getNumToDateTime(s, null));
                } else
                {
                    hashmap.put(as[k], jsonarray.optJSONObject(j).optString(as[k]));
                }
                k++;
            }
            arraylist.add(hashmap);
        }

        myPmData.setPmList(arraylist);
        HashMap hashmap1 = new HashMap();
        hashmap1.put("count", json.optString("count"));
        hashmap1.put("perpage", json.optString("perpage"));
        if(!"".equals(json.optString("page")))
            hashmap1.put("page", String.valueOf(Math.round(Double.valueOf(json.optString("page")).doubleValue())));
        myPmData.setValueList(hashmap1);
    }
}
//2131296256
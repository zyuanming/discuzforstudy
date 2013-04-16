// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper.x25;

import net.discuz.json.helper.FriendParseHelper;
import org.json.JSONArray;
import org.json.JSONObject;

public class FriendParseHelperX25 extends FriendParseHelper
{

    public FriendParseHelperX25(String s)
    {
        super(s);
    }

    protected void parseData()
    {
        String as[];
        String as1[];
        if(json.optString("count") != null)
        {
            int i = Integer.valueOf(json.optString("count")).intValue();
            String as2[] = new String[i];
            String as3[] = new String[i];
            JSONArray jsonarray = json.optJSONArray("list");
            for(int j = 0; j < jsonarray.length(); j++)
            {
                JSONObject jsonobject = jsonarray.optJSONObject(j);
                as2[j] = jsonobject.optString("uid");
                as3[j] = jsonobject.optString("username");
            }

            as = as3;
            as1 = as2;
        } else
        {
            as = null;
            as1 = null;
        }
        friendData.setUids(as1);
        friendData.setUsernames(as);
    }
}
//2131296256
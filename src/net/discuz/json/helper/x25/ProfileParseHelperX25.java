// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import net.discuz.json.helper.ProfileParseHelper;
import net.discuz.model.ProfileData;
import net.discuz.source.DEBUG;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileParseHelperX25 extends ProfileParseHelper
{

    public ProfileParseHelperX25(String s)
    {
        super(s);
    }

    protected void parseData()
    {
        JSONObject jsonobject = json.optJSONObject("space");
        HashMap hashmap = new HashMap();
        hashmap.put("uid", jsonobject.optString("uid"));
        hashmap.put("username", jsonobject.optString("username"));
        hashmap.put("email", jsonobject.optString("email"));
        hashmap.put("thread", jsonobject.optString("thread"));
        hashmap.put("posts", jsonobject.optString("posts"));
        hashmap.put("credits", jsonobject.optString("credits"));
        hashmap.put("grouptitle", jsonobject.optJSONObject("group").optString("grouptitle"));
        hashmap.put("regdate", jsonobject.optString("regdate"));
        hashmap.put("oltime", jsonobject.optString("oltime"));
        hashmap.put("lastactivity", jsonobject.optString("lastactivity"));
        hashmap.put("lastpost", jsonobject.optString("lastpost"));
        profileData.setMyProfile(hashmap);
        HashMap hashmap1 = new HashMap();
        hashmap1.put("extcredits1", jsonobject.optString("extcredits1"));
        hashmap1.put("extcredits2", jsonobject.optString("extcredits2"));
        hashmap1.put("extcredits3", jsonobject.optString("extcredits3"));
        hashmap1.put("extcredits4", jsonobject.optString("extcredits4"));
        hashmap1.put("extcredits5", jsonobject.optString("extcredits5"));
        hashmap1.put("extcredits6", jsonobject.optString("extcredits6"));
        hashmap1.put("extcredits7", jsonobject.optString("extcredits7"));
        hashmap1.put("extcredits8", jsonobject.optString("extcredits8"));
        new ArrayList();
        HashMap hashmap2 = new HashMap();
        JSONObject jsonobject1 = json.optJSONObject("extcredits");
        DEBUG.o((new StringBuilder()).append("extcreditsObj").append(jsonobject1).toString());
        for(int i = 0; i < jsonobject1.names().length(); i++)
        {
            JSONObject jsonobject2 = jsonobject1.optJSONObject(jsonobject1.names().optString(i));
            HashMap hashmap3 = new HashMap();
            for(int j = 0; j < jsonobject2.names().length(); j++)
            {
                hashmap3.put("count", jsonobject.optString((new StringBuilder()).append("extcredits").append(jsonobject1.names().optString(i)).toString()));
                hashmap3.put("title", jsonobject2.optString("title"));
                hashmap3.put("unit", jsonobject2.optString("unit"));
            }

            hashmap2.put((new StringBuilder()).append("extcredits_").append(jsonobject1.names().optString(i)).toString(), hashmap3);
        }

        profileData.setExtCredits(hashmap2);
    }
}
//2131296256
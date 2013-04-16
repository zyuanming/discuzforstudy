// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import net.discuz.json.helper.ForumDisplayParseHelper;
import net.discuz.model.ForumDisplayData;
import net.discuz.tools.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

public class ForumDisplayParseHelperX25 extends ForumDisplayParseHelper
{

    public ForumDisplayParseHelperX25(String s)
    {
        super(s);
        ForumDisplayData = new ForumDisplayData();
    }

    protected void parseData()
    {
        int i = 0;
        JSONArray jsonarray = json.optJSONArray("forum_threadlist");
        ArrayList arraylist = new ArrayList();
        int j = 0;
        while(jsonarray.length() > j) 
        {
            HashMap hashmap = new HashMap();
            JSONObject jsonobject = jsonarray.optJSONObject(j);
            hashmap.put("tid", jsonobject.optString("tid"));
            if(jsonobject.optInt("authorid") > 0 && "".equals(jsonobject.optString("author")))
            {
                hashmap.put("author", "\u533F\u540D");
                if("".equals(jsonobject.optString("lastposter")))
                    hashmap.put("lastposter", "\u533F\u540D");
                else
                    hashmap.put("lastposter", jsonobject.optString("lastposter"));
            } else
            {
                hashmap.put("author", jsonobject.optString("author"));
                hashmap.put("lastposter", jsonobject.optString("lastposter"));
            }
            hashmap.put("authorid", jsonobject.optString("authorid"));
            hashmap.put("subject", Tools.htmlSpecialCharDecode(jsonobject.optString("subject")));
            hashmap.put("dateline", jsonobject.optString("dateline"));
            hashmap.put("lastpost", Tools._getNumToDateTime(jsonobject.optString("dblastpost"), null));
            hashmap.put("attachment", jsonobject.optString("attachment"));
            hashmap.put("replies", String.valueOf(jsonobject.optInt("replies")));
            hashmap.put("views", String.valueOf(jsonobject.optInt("views")));
            arraylist.add(hashmap);
            j++;
        }
        ForumDisplayData.setThreadList(arraylist);
        JSONArray jsonarray1 = json.optJSONArray("sublist");
        if(jsonarray1 != null)
        {
            ArrayList arraylist1 = new ArrayList();
            for(; i < jsonarray1.length(); i++)
            {
                JSONObject jsonobject3 = jsonarray1.optJSONObject(i);
                HashMap hashmap2 = new HashMap();
                hashmap2.put("fid", jsonobject3.optString("fid"));
                hashmap2.put("name", jsonobject3.optString("name"));
                hashmap2.put("threads", jsonobject3.optString("threads"));
                hashmap2.put("posts", jsonobject3.optString("posts"));
                hashmap2.put("todayposts", jsonobject3.optString("todayposts"));
                arraylist1.add(hashmap2);
            }

            ForumDisplayData.setSubList(arraylist1);
        }
        JSONObject jsonobject1 = json.optJSONObject("forum");
        if(jsonobject1 != null)
        {
            HashMap hashmap1 = new HashMap();
            hashmap1.put("fid", jsonobject1.optString("fid"));
            hashmap1.put("name", jsonobject1.optString("name"));
            hashmap1.put("threads", jsonobject1.optString("threads"));
            hashmap1.put("posts", jsonobject1.optString("posts"));
            hashmap1.put("autoclose", jsonobject1.optString("autoclose"));
            hashmap1.put("rules", jsonobject1.optString("rules"));
            hashmap1.put("postperm", jsonobject1.optString("postperm"));
            hashmap1.put("replyperm", jsonobject1.optString("replyperm"));
            hashmap1.put("getattachperm", jsonobject1.optString("getattachperm"));
            hashmap1.put("postattachperm", jsonobject1.optString("postattachperm"));
            hashmap1.put("postimageperm", jsonobject1.optString("postimageperm"));
            hashmap1.put("password", jsonobject1.optString("password"));
            ForumDisplayData.setForumData(hashmap1);
        }
        JSONObject jsonobject2 = json.optJSONObject("allowperm");
        if(jsonobject2 != null)
            ForumDisplayData.setAllowPerm(jsonobject2);
    }
}
//2131296256
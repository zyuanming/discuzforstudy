// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper;

import java.util.ArrayList;
import org.json.JSONObject;

// Referenced classes of package net.discuz.json.helper:
//            JsonParseHelper

public abstract class TopListParseHelper extends JsonParseHelper
{

    public TopListParseHelper(String s)
    {
        super(s);
        ThreadList = null;
        DataName = "forum_threadlist";
        DataKeys = "tid|author|authorid|subject|dateline|lastpost|lastposter|views|replies|attachment";
        ThreadList = new ArrayList();
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(ThreadList);
    }

    public String DataKeys;
    public String DataName;
    public ArrayList ThreadList;
}
//2131296256
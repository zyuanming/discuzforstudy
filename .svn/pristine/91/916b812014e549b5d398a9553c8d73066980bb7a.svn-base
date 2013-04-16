// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper;

import net.discuz.model.PmData;
import org.json.JSONObject;

// Referenced classes of package net.discuz.json.helper:
//            JsonParseHelper

public abstract class PublicPmParseHelper extends JsonParseHelper
{

    public PublicPmParseHelper(String s)
    {
        super(s);
        pmData = null;
        DataName = "list";
        DataKeys = "id|message|authorid|dateline|author";
        pmData = new PmData();
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(pmData);
    }

    public String DataKeys;
    public String DataName;
    public PmData pmData;
}
//2131296256
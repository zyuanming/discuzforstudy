// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper;

import net.discuz.model.ProfileData;
import org.json.JSONObject;

// Referenced classes of package net.discuz.json.helper:
//            JsonParseHelper

public abstract class ProfileParseHelper extends JsonParseHelper
{

    public ProfileParseHelper(String s)
    {
        super(s);
        profileData = null;
        profileData = new ProfileData();
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(profileData);
    }

    protected ProfileData profileData;
}
//2131296256
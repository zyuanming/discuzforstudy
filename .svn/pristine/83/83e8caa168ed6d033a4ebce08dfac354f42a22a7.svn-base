// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.json.helper;

import net.discuz.model.ViewThreadData;
import net.discuz.source.DTemplate;
import net.discuz.source.activity.DiscuzBaseActivity;
import org.json.JSONObject;

// Referenced classes of package net.discuz.json.helper:
//            JsonParseHelper

public abstract class ViewThreadParseHelper extends JsonParseHelper
{

    public ViewThreadParseHelper(String s)
    {
        super(s);
        viewThreadData = new ViewThreadData();
    }

    public void createTemplate(DiscuzBaseActivity discuzbaseactivity)
    {
        context = discuzbaseactivity;
        dtemplate = new DTemplate(context);
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(viewThreadData);
    }

    public DiscuzBaseActivity context;
    private DTemplate dtemplate;
    public ViewThreadData viewThreadData;
}
//2131296256
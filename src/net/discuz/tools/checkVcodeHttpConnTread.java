// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.tools;

import net.discuz.source.HttpRequest;

// Referenced classes of package net.discuz.tools:
//            HttpConnThread

public class checkVcodeHttpConnTread extends HttpConnThread
{

    public checkVcodeHttpConnTread(String s, int i)
    {
        super(s, i);
    }

    public String getInputStream(String s, String s1, int i)
        throws Exception
    {
        return (new HttpRequest(mSiteappid))._get(s, null, "utf-8", new net.discuz.source.HttpRequest.LoginCallBack(mSiteappid));
    }

    private static final long serialVersionUID = 0xc5c7a7aaaafbe6f9L;
}
//2131296256
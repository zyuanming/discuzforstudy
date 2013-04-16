// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package net.discuz.tools;


// Referenced classes of package net.discuz.tools:
//            Tools

public class MobileData
{

    public MobileData()
    {
    }

    public String _getMobileDataParams(String as[], String s)
    {
        String s1 = "";
        String s2 = "";
        int i = 0;
        while(i < as.length) 
        {
            StringBuilder stringbuilder = (new StringBuilder()).append(s1);
            String s3;
            StringBuilder stringbuilder1;
            String s4;
            if(s1 == "")
                s3 = as[i];
            else
                s3 = (new StringBuilder()).append("|").append(as[i]).toString();
            s1 = stringbuilder.append(s3).toString();
            stringbuilder1 = (new StringBuilder()).append(s2);
            if(s2 == "")
                s4 = as[i];
            else
                s4 = (new StringBuilder()).append("|").append(as[i]).toString();
            s2 = stringbuilder1.append(s4).toString();
            i++;
        }
        return (new StringBuilder()).append(Tools._md5((new StringBuilder()).append(Tools._md5(s1)).append(s).toString())).append("|").append(s1).toString();
    }

    private static final char HEX_DIGITS[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };

}
//2131296256
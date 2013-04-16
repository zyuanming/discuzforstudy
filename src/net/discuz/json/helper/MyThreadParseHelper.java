
package net.discuz.json.helper;

import java.util.ArrayList;
import org.json.JSONObject;

public abstract class MyThreadParseHelper extends JsonParseHelper
{

    public MyThreadParseHelper(String s)
    {
        super(s);
        MyThreadsList = null;
        DataKeys = "tid|fid|author|authorid|subject|dateline|lastpost|lastposter|views|replies|attachment|dbdateline|dblastpost";
        DataName = "data";
        MyThreadsList = new ArrayList();
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(MyThreadsList);
        MyThreadsList = null;
    }

    public String DataKeys;
    public String DataName;
    public ArrayList MyThreadsList;
}
//2131296256
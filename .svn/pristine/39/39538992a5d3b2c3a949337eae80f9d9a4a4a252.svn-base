
package net.discuz.json.helper;

import java.util.ArrayList;
import org.json.JSONObject;

public abstract class MyFavThreadParseHelper extends JsonParseHelper
{

    public MyFavThreadParseHelper(String s)
    {
        super(s);
        MyFavThreadsList = null;
        DataKeys = "favid|uid|id|idtype|title|description|dateline";
        DataName = "list";
        MyFavThreadsList = new ArrayList();
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(MyFavThreadsList);
        MyFavThreadsList = null;
    }

    public String DataKeys;
    public String DataName;
    public ArrayList MyFavThreadsList;
}
//2131296256
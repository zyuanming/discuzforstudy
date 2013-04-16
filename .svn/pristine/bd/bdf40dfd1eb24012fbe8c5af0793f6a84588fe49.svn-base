package net.discuz.json.helper;

import java.util.ArrayList;
import org.json.JSONObject;


public abstract class MyFavForumParseHelper extends JsonParseHelper
{

    public MyFavForumParseHelper(String s)
    {
        super(s);
        myFavForumList = null;
        DataKeys = "favid|id|title|description|dateline";
        DataName = "list";
        myFavForumList = new ArrayList();
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(myFavForumList);
        myFavForumList = null;
    }

    public String DataKeys;
    public String DataName;
    public ArrayList myFavForumList;
}
//2131296256
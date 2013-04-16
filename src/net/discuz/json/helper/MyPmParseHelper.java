
package net.discuz.json.helper;

import net.discuz.model.PmData;
import org.json.JSONObject;


public abstract class MyPmParseHelper extends JsonParseHelper
{

    public MyPmParseHelper(String s)
    {
        super(s);
        myPmData = null;
        DataName = "list";
        DataKeys = "pmid|msgfrom|message|authorid|dateline|pmnum|touid|tousername|msgfromid|isnew";
        myPmData = new PmData();
    }

    public void parse(JSONObject jsonobject)
        throws Exception
    {
        onParseBegin();
        super.parse(jsonobject);
        onParseFinish(myPmData);
    }

    public String DataKeys;
    public String DataName;
    public PmData myPmData;
}
//2131296256
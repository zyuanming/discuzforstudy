package net.discuz.source;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CreateImageName
{

	public CreateImageName()
	{}

	public String _getCameraTmpFileName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"'IMG'yyyyMMddHHmmss");
		return (new StringBuilder()).append(simpledateformat.format(date))
				.append(".jpg").toString();
	}
}
//2131296256
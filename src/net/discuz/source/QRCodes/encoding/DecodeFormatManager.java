package net.discuz.source.QRCodes.encoding;

import android.content.Intent;
import android.net.Uri;
import com.google.zxing.BarcodeFormat;
import java.util.*;
import java.util.regex.Pattern;

final class DecodeFormatManager
{

	private DecodeFormatManager()
	{}

	static Vector parseDecodeFormats(Intent intent)
	{
		String s = intent.getStringExtra("SCAN_FORMATS");
		List list = null;
		if (s != null)
			list = Arrays.asList(COMMA_PATTERN.split(s));
		return parseDecodeFormats(((Iterable) (list)),
				intent.getStringExtra("SCAN_MODE"));
	}

	static Vector parseDecodeFormats(Uri uri)
	{
		List list = uri.getQueryParameters("SCAN_FORMATS");
		if (list != null && list.size() == 1 && list.get(0) != null)
			list = Arrays
					.asList(COMMA_PATTERN.split((CharSequence) list.get(0)));
		return parseDecodeFormats(((Iterable) (list)),
				uri.getQueryParameter("SCAN_MODE"));
	}

	private static Vector parseDecodeFormats(Iterable iterable, String s)
	{
		Vector vector = null;
		if (iterable != null)
		{
			vector = new Vector();
			try
			{
				for (Iterator iterator = iterable.iterator(); iterator
						.hasNext(); vector.add(BarcodeFormat
						.valueOf((String) iterator.next())))
					;
			} catch (IllegalArgumentException illegalargumentexception)
			{}
		}
		if (s != null)
		{
			if ("PRODUCT_MODE".equals(s))
				return PRODUCT_FORMATS;
			if ("QR_CODE_MODE".equals(s))
				return QR_CODE_FORMATS;
			if ("DATA_MATRIX_MODE".equals(s))
				return DATA_MATRIX_FORMATS;
			if ("ONE_D_MODE".equals(s))
				return ONE_D_FORMATS;
		}
		return vector;
	}

	private static final Pattern COMMA_PATTERN = Pattern.compile(",");
	static final Vector DATA_MATRIX_FORMATS;
	static final Vector ONE_D_FORMATS;
	static final Vector PRODUCT_FORMATS;
	static final Vector QR_CODE_FORMATS;

	static
	{
		PRODUCT_FORMATS = new Vector(5);
		ONE_D_FORMATS = new Vector(4 + PRODUCT_FORMATS.size());
		ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
		QR_CODE_FORMATS = new Vector(1);
		QR_CODE_FORMATS.add(BarcodeFormat.QR_CODE);
		DATA_MATRIX_FORMATS = new Vector(1);
		DATA_MATRIX_FORMATS.add(BarcodeFormat.DATA_MATRIX);
	}
}
// 2131296256
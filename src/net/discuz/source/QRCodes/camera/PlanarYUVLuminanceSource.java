package net.discuz.source.QRCodes.camera;

import android.graphics.Bitmap;
import com.google.zxing.LuminanceSource;

public final class PlanarYUVLuminanceSource extends LuminanceSource
{

	public PlanarYUVLuminanceSource(byte abyte0[], int i, int j, int k, int l,
			int i1, int j1)
	{
		super(i1, j1);
		if (k + i1 > i || l + j1 > j)
		{
			throw new IllegalArgumentException(
					"Crop rectangle does not fit within image data.");
		} else
		{
			yuvData = abyte0;
			dataWidth = i;
			dataHeight = j;
			left = k;
			top = l;
			return;
		}
	}

	public int getDataHeight()
	{
		return dataHeight;
	}

	public int getDataWidth()
	{
		return dataWidth;
	}

	public byte[] getMatrix()
	{
		int i = 0;
		int j = getWidth();
		int k = getHeight();
		byte abyte0[];
		if (j == dataWidth && k == dataHeight)
		{
			abyte0 = yuvData;
		} else
		{
			int l = j * k;
			abyte0 = new byte[l];
			int i1 = top * dataWidth + left;
			if (j == dataWidth)
			{
				System.arraycopy(yuvData, i1, abyte0, 0, l);
				return abyte0;
			}
			byte abyte1[] = yuvData;
			while (i < k)
			{
				System.arraycopy(abyte1, i1, abyte0, i * j, j);
				i1 += dataWidth;
				i++;
			}
		}
		return abyte0;
	}

	public byte[] getRow(int i, byte abyte0[])
	{
		if (i < 0 || i >= getHeight())
			throw new IllegalArgumentException((new StringBuilder())
					.append("Requested row is outside the image: ").append(i)
					.toString());
		int j = getWidth();
		if (abyte0 == null || abyte0.length < j)
			abyte0 = new byte[j];
		int k = (i + top) * dataWidth + left;
		System.arraycopy(yuvData, k, abyte0, 0, j);
		return abyte0;
	}

	public boolean isCropSupported()
	{
		return true;
	}

	public Bitmap renderCroppedGreyscaleBitmap()
	{
		int i = getWidth();
		int j = getHeight();
		int ai[] = new int[i * j];
		byte abyte0[] = yuvData;
		int k = top * dataWidth + left;
		int l = 0;
		int i1 = k;
		for (; l < j; l++)
		{
			int j1 = l * i;
			for (int k1 = 0; k1 < i; k1++)
			{
				int l1 = 0xff & abyte0[i1 + k1];
				ai[j1 + k1] = 0xff000000 | l1 * 0x10101;
			}

			i1 += dataWidth;
		}

		Bitmap bitmap = Bitmap.createBitmap(i, j,
				android.graphics.Bitmap.Config.ARGB_8888);
		bitmap.setPixels(ai, 0, i, 0, 0, i, j);
		return bitmap;
	}

	private final int dataHeight;
	private final int dataWidth;
	private final int left;
	private final int top;
	private final byte yuvData[];
}
// 2131296256
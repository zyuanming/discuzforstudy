package net.discuz.tools;

import java.io.IOException;

import net.discuz.source.DEBUG;

public class GzipUtil
{

	public GzipUtil()
	{
	}

	private static int[] createHuffmanTree(byte abyte0[], int i)
	{
		int ai[] = new int[17];
		for (int j = 0; j < abyte0.length; j++)
		{
			byte byte1 = abyte0[j];
			ai[byte1] = 1 + ai[byte1];
		}

		ai[0] = 0;
		int ai1[] = new int[17];
		int k = 1;
		int l = 0;
		for (; k <= 16; k++)
		{
			l = l + ai[k - 1] << 1;
			ai1[k] = l;
		}

		int ai2[] = new int[16 + (i << 1)];
		int i1 = 0;
		int j1 = 1;
		for (; i1 <= i; i1++)
		{
			byte byte0 = abyte0[i1];
			if (byte0 == 0)
				continue;
			int k1 = ai1[byte0];
			ai1[byte0] = k1 + 1;
			int l1 = byte0 - 1;
			int i2 = 0;
			while (l1 >= 0)
			{
				int k2;
				if ((k1 & 1 << l1) == 0)
				{
					int l2 = ai2[i2] >> 16;
					if (l2 == 0)
					{
						ai2[i2] = ai2[i2] | j1 << 16;
						k2 = j1 + 1;
					} else
					{
						k2 = j1;
						j1 = l2;
					}
				} else
				{
					int j2 = 0xffff & ai2[i2];
					if (j2 == 0)
					{
						ai2[i2] = j1 | ai2[i2];
						k2 = j1 + 1;
					} else
					{
						k2 = j1;
						j1 = j2;
					}
				}
				l1--;
				i2 = j1;
				j1 = k2;
			}
			ai2[i2] = 0x80000000 | i1;
		}

		return ai2;
	}

	private static byte[] decodeCodeLengths(byte abyte0[], int ai[], int i)
	{
		byte abyte1[] = new byte[i];
		int j = 0;
		for (int k = 0; k < i;)
		{
			int l = readCode(abyte0, ai);
			int i1;
			if (l >= 16)
			{
				int k1;
				if (l == 16)
				{
					int j2 = 3 + readBits(abyte0, 2);
					l = j;
					i1 = k;
					k1 = j2;
				} else
				{
					int j1;
					if (l == 17)
						j1 = 3 + readBits(abyte0, 3);
					else
						j1 = 11 + readBits(abyte0, 7);
					i1 = k;
					k1 = j1;
					l = 0;
				}
				do
				{
					int l1 = k1 - 1;
					if (k1 <= 0)
						break;
					int i2 = i1 + 1;
					abyte1[i1] = (byte) l;
					i1 = i2;
					k1 = l1;
				} while (true);
			} else
			{
				i1 = k + 1;
				abyte1[k] = (byte) l;
			}
			k = i1;
			j = l;
		}

		return abyte1;
	}

	// public static byte[] inflate1(byte abyte0[]) throws IOException
	// {
	// byte abyte1[];
	// int j;
	// gzipBit = 0;
	// gzipByte = 0;
	// gzipIndex = 0;
	// if (readBits(abyte0, 16) != 35615 || readBits(abyte0, 8) != 8)
	// throw new IOException("Invalid GZIP format");
	// readBits(abyte0, 8);
	// gzipIndex = 6 + gzipIndex;
	// int i = gzipIndex;
	// gzipIndex = -4 + abyte0.length;
	// abyte1 = new byte[readBits(abyte0, 16) | readBits(abyte0, 16) << 16];
	// gzipIndex = i;
	// j = 0;
	// int ai[];
	// int ai1[];
	// int j2;
	// j2 = readCode(abyte0, ai);
	// if (j2 == 256)
	// {
	// int k;
	// int l;
	// k = readBits(abyte0, 1);
	// if (k == 1)
	// DEBUG.o("=====LAST GZIP===");
	// l = readBits(abyte0, 2);
	// DEBUG.o((new StringBuilder()).append("=====GZIP FORMAT===")
	// .append(l).toString());
	// if (l != 0)
	// break;
	// gzipBit = 0;
	// int k5 = readBits(abyte0, 16);
	// if (k == 1)
	// DEBUG.o((new StringBuilder()).append("=====LAST READ1===")
	// .append(l).toString());
	// readBits(abyte0, 16);
	// if (k == 1)
	// DEBUG.o((new StringBuilder()).append("=====LAST READ2===")
	// .append(l).toString());
	// System.arraycopy(abyte0, gzipIndex, abyte1, j, k5);
	// if (k == 1)
	// DEBUG.o((new StringBuilder()).append("=====LAST READ3===")
	// .append(l).toString());
	// gzipIndex = k5 + gzipIndex;
	// j += k5;
	// DEBUG.o((new StringBuilder()).append("=====uncompressedIndex===")
	// .append(j).toString());
	//
	// if (l == 2)
	// {
	// int k4 = 257 + readBits(abyte0, 5);
	// int l4 = 1 + readBits(abyte0, 5);
	// int i5 = 4 + readBits(abyte0, 4);
	// byte abyte4[] = new byte[19];
	// for (int j5 = 0; j5 < i5; j5++)
	// abyte4[DYNAMIC_LENGTH_ORDER[j5]] = (byte) readBits(abyte0,
	// 3);
	//
	// int ai2[] = createHuffmanTree(abyte4, 18);
	// ai = createHuffmanTree(decodeCodeLengths(abyte0, ai2, k4),
	// k4 - 1);
	// ai1 = createHuffmanTree(decodeCodeLengths(abyte0, ai2, l4),
	// l4 - 1);
	// } else
	// {
	// byte abyte2[] = new byte[288];
	// for (int i1 = 0; i1 < 144; i1++)
	// abyte2[i1] = 8;
	//
	// for (int j1 = 144; j1 < 256; j1++)
	// abyte2[j1] = 9;
	//
	// for (int k1 = 256; k1 < 280; k1++)
	// abyte2[k1] = 7;
	//
	// for (int l1 = 280; l1 < 288; l1++)
	// abyte2[l1] = 8;
	//
	// ai = createHuffmanTree(abyte2, 287);
	// DEBUG.o("=====createHuffmanTree1===");
	// byte abyte3[] = new byte[32];
	// for (int i2 = 0; i2 < abyte3.length; i2++)
	// abyte3[i2] = 5;
	//
	// DEBUG.o("=====createHuffmanTree2===");
	// ai1 = createHuffmanTree(abyte3, 31);
	// DEBUG.o("=====createHuffmanTree3===");
	// }
	//
	// if (k != 0)
	// {
	// DEBUG.o("==========UNGZIP==========");
	// return abyte1;
	// }
	// } else
	// {
	// DEBUG.o("=====EOB_CODE0===");
	// if (j2 > 256)
	// {
	// DEBUG.o("=====EOB_CODE1===");
	// int l2 = j2 - 257;
	// int i3 = LENGTH_VALUES[l2];
	// int j3 = LENGTH_EXTRA_BITS[l2];
	// if (j3 > 0)
	// i3 += readBits(abyte0, j3);
	// int k3 = readCode(abyte0, ai1);
	// int l3 = DISTANCE_VALUES[k3];
	// int i4 = DISTANCE_EXTRA_BITS[k3];
	// if (i4 > 0)
	// l3 += readBits(abyte0, i4);
	// int j4 = j - l3;
	// for (; l3 < i3; l3 <<= 1)
	// {
	// System.arraycopy(abyte1, j4, abyte1, j, l3);
	// j += l3;
	// i3 -= l3;
	// }
	//
	// System.arraycopy(abyte1, j4, abyte1, j, i3);
	// j += i3;
	// } else
	// {
	// DEBUG.o("=====EOB_CODE n===");
	// int k2 = j + 1;
	// abyte1[j] = (byte) j2;
	// j = k2;
	// }
	// }
	// }

	public static byte[] inflate(byte[] paramArrayOfByte) throws IOException
	{
		gzipBit = 0;
		gzipByte = 0;
		gzipIndex = 0;
		if ((readBits(paramArrayOfByte, 16) != 35615)
				|| (readBits(paramArrayOfByte, 8) != 8))
			throw new IOException("Invalid GZIP format");
		readBits(paramArrayOfByte, 8);
		gzipIndex = 6 + gzipIndex;
		int i = gzipIndex;
		gzipIndex = -4 + paramArrayOfByte.length;
		byte[] arrayOfByte1 = new byte[readBits(paramArrayOfByte, 16)
				| readBits(paramArrayOfByte, 16) << 16];
		gzipIndex = i;
		int j = 0;
		int k = 0;
		int m = 0;
		while (k == 0)
		{
			k = readBits(paramArrayOfByte, 1);
			if (k == 1)
				DEBUG.o("=====LAST GZIP===");
			m = readBits(paramArrayOfByte, 2);
			DEBUG.o("=====GZIP FORMAT===" + m);
			if (m != 0)
				break;
			gzipBit = 0;
			int i18 = readBits(paramArrayOfByte, 16);
			if (k == 1)
				DEBUG.o("=====LAST READ1===" + m);
			readBits(paramArrayOfByte, 16);
			if (k == 1)
				DEBUG.o("=====LAST READ2===" + m);
			System.arraycopy(paramArrayOfByte, gzipIndex, arrayOfByte1, j, i18);
			if (k == 1)
				DEBUG.o("=====LAST READ3===" + m);
			gzipIndex = i18 + gzipIndex;
			j += i18;
			DEBUG.o("=====uncompressedIndex===" + j);
		}
		DEBUG.o("==========UNGZIP==========");

		int[] arrayOfInt1;
		int[] arrayOfInt2;
		if (m == 2)
		{
			int i14 = 257 + readBits(paramArrayOfByte, 5);
			int i15 = 1 + readBits(paramArrayOfByte, 5);
			int i16 = 4 + readBits(paramArrayOfByte, 4);
			byte[] arrayOfByte4 = new byte[19];
			for (int i17 = 0; i17 < i16; i17++)
				arrayOfByte4[DYNAMIC_LENGTH_ORDER[i17]] = ((byte) readBits(
						paramArrayOfByte, 3));
			int[] arrayOfInt3 = createHuffmanTree(arrayOfByte4, 18);
			arrayOfInt1 = createHuffmanTree(
					decodeCodeLengths(paramArrayOfByte, arrayOfInt3, i14),
					i14 - 1);
			arrayOfInt2 = createHuffmanTree(
					decodeCodeLengths(paramArrayOfByte, arrayOfInt3, i15),
					i15 - 1);
			while (true)
			{
				int i5 = readCode(paramArrayOfByte, arrayOfInt1);
				if (i5 == 256)
					break;
				DEBUG.o("=====EOB_CODE0===");
				if (i5 > 256)
				{
					DEBUG.o("=====EOB_CODE1===");
					int i7 = i5 - 257;
					int i8 = LENGTH_VALUES[i7];
					int i9 = LENGTH_EXTRA_BITS[i7];
					if (i9 > 0)
						i8 += readBits(paramArrayOfByte, i9);
					int i10 = readCode(paramArrayOfByte, arrayOfInt2);
					int i11 = DISTANCE_VALUES[i10];
					int i12 = DISTANCE_EXTRA_BITS[i10];
					if (i12 > 0)
						i11 += readBits(paramArrayOfByte, i12);
					int i13 = j - i11;
					while (true)
						if (i11 < i8)
						{
							System.arraycopy(arrayOfByte1, i13, arrayOfByte1,
									j, i11);
							j += i11;
							i8 -= i11;
							i11 <<= 1;
							byte[] arrayOfByte2 = new byte[288];
							for (int n = 0; n < 144; n++)
								arrayOfByte2[n] = 8;
							for (int i1 = 144; i1 < 256; i1++)
								arrayOfByte2[i1] = 9;
							for (int i2 = 256; i2 < 280; i2++)
								arrayOfByte2[i2] = 7;
							for (int i3 = 280; i3 < 288; i3++)
								arrayOfByte2[i3] = 8;
							arrayOfInt1 = createHuffmanTree(arrayOfByte2, 287);
							DEBUG.o("=====createHuffmanTree1===");
							byte[] arrayOfByte3 = new byte[32];
							for (int i4 = 0; i4 < arrayOfByte3.length; i4++)
								arrayOfByte3[i4] = 5;
							DEBUG.o("=====createHuffmanTree2===");
							arrayOfInt2 = createHuffmanTree(arrayOfByte3, 31);
							DEBUG.o("=====createHuffmanTree3===");
							break;
						}
					System.arraycopy(arrayOfByte1, i13, arrayOfByte1, j, i8);
					j += i8;
				} else
				{
					DEBUG.o("=====EOB_CODE n===");
					int i6 = j + 1;
					arrayOfByte1[j] = ((byte) i5);
					j = i6;
				}
			}
		}
		return arrayOfByte1;
	}

	private static int readBits(byte abyte0[], int i)
	{
		int j;
		int k;
		int l;
		if (gzipBit == 0)
		{
			int k1 = gzipIndex;
			gzipIndex = k1 + 1;
			j = 0xff & abyte0[k1];
			gzipByte = j;
		} else
		{
			j = gzipByte >> gzipBit;
		}
		k = 8 - gzipBit;
		l = j;
		for (int i1 = k; i1 < i; i1 += 8)
		{
			int j1 = gzipIndex;
			gzipIndex = j1 + 1;
			gzipByte = 0xff & abyte0[j1];
			l |= gzipByte << i1;
		}

		gzipBit = 7 & i + gzipBit;
		return l & -1 + (1 << i);
	}

	private static int readCode(byte abyte0[], int ai[])
	{
		int i = ai[0];
		while (i >= 0)
		{
			if (gzipBit == 0)
			{
				int j = gzipIndex;
				gzipIndex = j + 1;
				gzipByte = 0xff & abyte0[j];
			}
			if ((gzipByte & 1 << gzipBit) == 0)
				i = ai[i >> 16];
			else
				i = ai[i & 0xffff];
			gzipBit = 7 & 1 + gzipBit;
		}
		return i & 0xffff;
	}

	private static final int BTYPE_DYNAMIC = 2;
	private static final int BTYPE_NONE = 0;
	private static final int DISTANCE_EXTRA_BITS[] = { 0, 0, 0, 0, 1, 1, 2, 2,
			3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12,
			13, 13 };
	private static final int DISTANCE_VALUES[] = { 1, 2, 3, 4, 5, 7, 9, 13, 17,
			25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049,
			3073, 4097, 6145, 8193, 12289, 16385, 24577 };
	private static final int DYNAMIC_LENGTH_ORDER[] = { 16, 17, 18, 0, 8, 7, 9,
			6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15 };
	private static final int EOB_CODE = 256;
	private static final int LENGTH_EXTRA_BITS[] = { 0, 0, 0, 0, 0, 0, 0, 0, 1,
			1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0, 99, 99 };
	private static final int LENGTH_VALUES[] = { 3, 4, 5, 6, 7, 8, 9, 10, 11,
			13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131,
			163, 195, 227, 258, 0, 0 };
	private static final int MAX_BITS = 16;
	private static final int MAX_CODE_DISTANCES = 31;
	private static final int MAX_CODE_LENGTHS = 18;
	private static final int MAX_CODE_LITERALS = 287;
	private static int gzipBit;
	private static int gzipByte;
	private static int gzipIndex;

}
// 2131296256
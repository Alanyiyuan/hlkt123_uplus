package com.hlkt123.uplus.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

public class ImgUtil {
	/**
	 * ��BitmapתByte
	 * 
	 * @param bm
	 *            Bitmap ���͵�ͼƬ
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * change uri to path string
	 * 
	 * @param uri
	 * @return
	 */

	public static String getPath(Uri uri, Activity act) {
		String path = uri.getPath();
		File mfile = new File(path);
		if (mfile.exists())
			return mfile.getPath();

		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			ContentResolver cr = act.getContentResolver();
			Cursor cursor = cr.query(uri, projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * use to lessen pic 50%
	 * 
	 * @param path
	 *            sd card path
	 * @return bitmap
	 */
	public static Bitmap getBitmapByPath(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(path, options);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * ���þ����ͼƬ���з�����ʾ /����ת�Ƕ���ʾ; ��ͼƬ�Ŀ�Ȼ�߶�<1024ʱ��ֱ�ӷ���ԭͼ
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(String bmPath, float scale) {
		if (bmPath == null || bmPath.equals("")) {
			return null;
		}
		File dst = new File(bmPath);

		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = new BitmapFactory.Options();

			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(dst.getPath(), opts);
			opts.inSampleSize = (int) (scale);
			if (opts.inSampleSize == 0)
				opts.inSampleSize = 1;

			opts.inJustDecodeBounds = false;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			try {
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}
		return null;
	}

	/**
	 * ������������ͼƬ��ѹ�����ƶ��ĳߴ磬�����زü����ͼƬ���ѽ���OOM�� �쳣����
	 * @param dst
	 * @param width
	 *            �������ͼƬ���
	 * @param height
	 *            �������ͼƬ�߶�
	 * @return
	 */
	public static Bitmap zoomImg(String bmPath, int width, int height) {
		File dst = new File(bmPath);

		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// ����ͼƬ���ű���
				float out_pix = opts.outWidth * opts.outHeight;
				float max_pix = width * height;
				float scale = out_pix / max_pix;
				
				scale += 0.7;
				opts.inSampleSize = (int) (scale);
				if (opts.inSampleSize == 0)
					opts.inSampleSize = 1;

				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			Bitmap bm = null;
			Matrix matrix = chgOrctataion(bmPath, width, height);
			try {
				bm = BitmapFactory.decodeFile(dst.getPath(), opts);
				// ��ѹ�����ͼƬ������ת����֤ͼƬ���������ӽ�
				bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), matrix, true);
				return bm;
				
			} catch (OutOfMemoryError e) {
				//����ڴ�����ˣ�����gcһ��
				System.gc();
				System.runFinalization();
				e.printStackTrace();
				
				if (bm == null) {
					bm = BitmapFactory.decodeFile(dst.getPath(), opts);
					// ��ѹ�����ͼƬ������ת����֤ͼƬ���������ӽ�
					bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
							bm.getHeight(), matrix, true);
					return bm;
				}
			}
		}
		return null;
	}

	/** Save image to the SD card **/
	public static void saveImgToSD(String path, String photoName,
			Bitmap photoBitmap) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File photoFile = new File(path, photoName); // ��ָ��·���´����ļ�
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					if (fileOutputStream != null)
						fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ����ͼƬ�ķ���
	 * 
	 * @param bmPath
	 * @param bm
	 * @return
	 */
	public static Matrix chgOrctataion(String bmPath, int bmWeight, int bmHeight) {
		try {
			ExifInterface exifInterface = new ExifInterface(bmPath);
			int tag = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);
			int degree = 0;
			if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
				degree = 90;
			} else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
				degree = 180;
			} else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
				degree = 270;
			}

			if (degree != 0) {
				Matrix m = new Matrix();
				// ������תλ�ã�ΪͼƬ�����ĵ�
				m.setRotate(degree, bmWeight / 2, bmHeight / 2);
				return m;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * ����Բ��ͼƬ
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toCircleBitmap2(Bitmap bitmap)

	{
		if (null == bitmap) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		if (!(width > 0 && height > 0)) {
			return null;
		}
		int ovalLen = Math.min(width, height);
		Rect src = new Rect((width - ovalLen) / 2, (height - ovalLen) / 2,
				(width - ovalLen) / 2 * ovalLen, (height - ovalLen) / 2
						* ovalLen);
		Bitmap output = Bitmap.createBitmap(ovalLen, ovalLen, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		bitmap = Bitmap.createBitmap(bitmap, src.left, src.top, ovalLen,
				ovalLen, null, true);
		Path path = new Path();
		path.addOval(new RectF(0, 0, ovalLen, ovalLen), Path.Direction.CW);
		BitmapShader tempShader = new BitmapShader(bitmap,
				BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
		paint.setShader(tempShader);
		canvas.drawPath(path, paint);
		return output;
	}

	/**
	 * ��׽�ڴ������������ͼƬ
	 * 
	 * @param width
	 * @param height
	 * @param config
	 * @return
	 */
	public static Bitmap createBitmapWithOOM(int width, int height,
			Bitmap.Config config) {
		Bitmap bm = null;
		try {
			bm = Bitmap.createBitmap(width, height, config);
		} catch (OutOfMemoryError e) {
			while (bm == null) {
				System.gc();
				System.runFinalization();
				bm = createBitmapWithOOM(width, height, config);
			}
		}

		return bm;
	}

}

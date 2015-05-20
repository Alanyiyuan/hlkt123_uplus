package com.hlkt123.uplus.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {

	private HashMap<String, SoftReference<Drawable>> imageCache;
	private final static String PATH_IMAGES = Environment
			.getExternalStorageDirectory() + "/uplus/images/";// 账户图片的缓存在本地的路径

	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				if (drawable != null) {
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
				else //内存溢出的时候， drawahle = null
				{
					Message message = handler.obtainMessage(0,null);
					handler.sendMessage(message);
				}
			}
		}.start();
		return null;
	}

	public static Drawable loadImageFromUrl(String url) {
		if (url == null || url.equals("") || url.length() < 4) {
			return null;
		}
		if (!(url.endsWith(".jpg")|| url.endsWith(".jpeg")
				|| url.endsWith(".png") || url.endsWith(".JPG")
				|| url.endsWith(".JPEG") || url.endsWith(".PNG"))) {
			return null;
		}
		String fileName = MD5Util.md5(url)
				+ url.substring(url.lastIndexOf("."), url.length());
		String path = PATH_IMAGES + fileName;

		File file = new File(path);

		if (file.exists() && file.length() > 0) {
			LogUplus.upLog_i("loadImageFromUrl:", "file.exists()" + file.getAbsolutePath());
			try{
				return Drawable.createFromPath(file.getAbsolutePath());
			}catch(OutOfMemoryError e){
				System.out.println("图片内存溢出");
				return null;
			}
			
		}

		try {
			File rootFile = new File(PATH_IMAGES);
			if (!rootFile.exists()) {
				rootFile.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			} else {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			InputStream is = new URL(url).openStream();
			int data = is.read();
			while (data != -1) {
				fos.write(data);
				data = is.read();
				;
			}
			fos.close();
			is.close();
			return Drawable.createFromPath(file.getAbsolutePath());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if (file.exists()) {
				file.delete();
				LogUplus.upLog_i("loadImageFromUrl", "下载出错，删除空文件");
			}
		}
		catch(OutOfMemoryError e){
			System.out.println("图片内存溢出");
			return null;
		}

		return null;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

}

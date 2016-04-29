package com.example.administrator.market.application.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DrawableUtils {

	/**
	 * 创建一个图片
	 * @param contentColor 内部填充颜色
	 * @param strokeColor  描边颜色
	 * @param radius       圆角
	 */
	public static GradientDrawable createDrawable(int contentColor, int strokeColor, int radius) {
		GradientDrawable drawable = new GradientDrawable(); // 生成Shape
		drawable.setGradientType(GradientDrawable.RECTANGLE); // 设置矩形
		drawable.setColor(contentColor);// 内容区域的颜色
		drawable.setStroke(1, strokeColor); // 四周描边,描边后四角真正为圆角，不会出现黑色阴影。如果父窗体是可以滑动的，需要把父View设置setScrollCache(false)
		drawable.setCornerRadius(radius); // 设置四角都为圆角
		return drawable;
	}
	
	//drawable转换成Bitmap
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
	}

	/**
	 * 创建一个图片选择器
	 * @param normalState  普通状态的图片
	 * @param pressedState 按压状态的图片
	 */
	public static StateListDrawable createSelector(Drawable normalState, Drawable pressedState) {
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressedState);
		bg.addState(new int[]{android.R.attr.state_enabled}, normalState);
		bg.addState(new int[]{}, normalState);
		return bg;
	}

	/** 获取图片的存储大小 */
	public static int getDrawableSize(Drawable drawable) {
		if (drawable == null) {
			return 0;
		}
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		} else {
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
	}
	
	
	/**
	 * 圆角缩小处理高效版
	 * 
	 * @param resultBitmap
	 * @param length
	 * @param roundPx
	 * @return
	 */
	public static Bitmap roundAndScale(Bitmap resultBitmap, int length,
			float roundPx) {
		return roundAndScale(resultBitmap, length, roundPx, true);
	}
	public static Bitmap roundAndScale(Bitmap resultBitmap, int length,
			float roundPx, boolean antiAlias) {
		if (resultBitmap == null)
			return resultBitmap;
		int width = resultBitmap.getWidth();
		int height = resultBitmap.getHeight();
		Rect srcRect = new Rect(0, 0, width, height);
		Rect desRect = new Rect(0, 0, length, length);
		RectF rectF = new RectF(desRect);

		Paint paint = new Paint();
		paint.setAntiAlias(antiAlias);
		paint.setFilterBitmap(antiAlias);
		Bitmap.Config config = Bitmap.Config.ARGB_8888;

		Bitmap bitmap;
		try {
			bitmap = Bitmap.createBitmap(length, length, config);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		Canvas roundCanvas = new Canvas(bitmap);

		// 给canvas设置锯齿优化
		if (antiAlias) {
			roundCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
					Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		}

		roundCanvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
		PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);
		paint.setXfermode(porterDuffXfermode);
		roundCanvas.drawBitmap(resultBitmap, srcRect, desRect, paint);

		return bitmap;
	}
	/**
	 * 圆角缩放灰度处理高效版
	 * 
	 * @param resultBitmap
	 * @param length
	 * @param roundPx
	 * @return
	 */
	public static Bitmap roundAndScaleToGray(Bitmap resultBitmap, int length,
			float roundPx) {
		return roundAndScaleToGray(resultBitmap, length, roundPx, true);
	}

	public static Bitmap roundAndScaleToGray(Bitmap resultBitmap, int length,
			float roundPx, boolean antiAlias) {
		if (resultBitmap == null)
			return resultBitmap;
		int width = resultBitmap.getWidth();
		int height = resultBitmap.getHeight();
		Rect srcRect = new Rect(0, 0, width, height);
		Rect desRect = new Rect(0, 0, length, length);
		RectF rectF = new RectF(desRect);

		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		paint.setAntiAlias(antiAlias);

		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = Bitmap.createBitmap(length, length, config);
		Canvas roundCanvas = new Canvas(bitmap);
		if (antiAlias)
			roundCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
					Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		roundCanvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
		PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);
		paint.setXfermode(porterDuffXfermode);
		roundCanvas.drawBitmap(resultBitmap, srcRect, desRect, paint);
		return bitmap;
	}

	/**
	 * 
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * 
	 *            去色后的图片
	 */

	public static Bitmap toGrayscale(Bitmap bmpOriginal) {

		int width, height;

		height = bmpOriginal.getHeight();

		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		Canvas c = new Canvas(bmpGrayscale);

		Paint paint = new Paint();

		ColorMatrix cm = new ColorMatrix();

		cm.setSaturation(0);

		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);

		paint.setColorFilter(f);

		c.drawBitmap(bmpOriginal, 0, 0, paint);

		return bmpGrayscale;

	}
	/**
	 * 圆角
	 * 
	 * @param resultBitmap
	 * @param roundPx
	 * @return
	 */
	public static final Bitmap roundAndScale(Bitmap resultBitmap, float roundPx) {
		if (resultBitmap == null || roundPx < 0)
			return resultBitmap;
		Bitmap mBitmap = comp(resultBitmap, 0, 0);
		int height;
		int width = mBitmap.getWidth();
		if (roundPx == 0) {
			roundPx = mBitmap.getWidth() / 2;
			height = width;
		} else {
			height = mBitmap.getHeight();
		}
		Rect srcRect = new Rect(0, 0, width, height);
		Rect desRect = new Rect(0, 0, width, height);
		RectF rectF = new RectF(desRect);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas roundCanvas = new Canvas(bitmap);
		roundCanvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
		PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);
		paint.setXfermode(porterDuffXfermode);
		roundCanvas.drawBitmap(mBitmap, srcRect, desRect, paint);
		mBitmap.recycle();
		return bitmap;
	}


	/**
	 * 圆角
	 * 
	 * @param resultBitmap
	 * @param roundPx
	 * @return
	 */
	public static final Bitmap roundAndScale(Bitmap resultBitmap,
			float roundPx, int lenght) {
		if (resultBitmap == null || roundPx < 0)
			return resultBitmap;
		Bitmap newBitmap = resultBitmap;
		Bitmap mBitmap = comp(newBitmap, 0, 0);
		int height;
		int width = mBitmap.getWidth();
		if (roundPx == 0) {
			roundPx = mBitmap.getWidth() / 2;
			height = width;
		} else {
			height = mBitmap.getHeight();
		}
		Rect srcRect = new Rect(0, 0, width, height);
		Rect desRect = new Rect(0, 0, lenght, lenght);
		RectF rectF = new RectF(desRect);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = Bitmap.createBitmap(lenght, lenght, config);
		Canvas roundCanvas = new Canvas(bitmap);
		roundCanvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
		PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);
		paint.setXfermode(porterDuffXfermode);
		roundCanvas.drawBitmap(mBitmap, srcRect, desRect, paint);
		mBitmap.recycle();
		resultBitmap.recycle();
		return bitmap;
	}
	/**
	 * 压缩bitmap 防止oom
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap comp(Bitmap image, int widh, int heigt) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 20, baos);
		/*
		 * if( baos.toByteArray().length / 1024>1024)
		 * {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
		 * baos.reset();//重置baos即清空baos
		 * image.compress(Bitmap.CompressFormat.JPEG, 50,
		 * baos);//这里压缩50%，把压缩后的数据存放到baos中 }
		 */
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		//如果该值设为 true那么将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 1280f;// 这里设置高度为800f
		float ww = 720f;// 这里设置宽度为480f
		if (widh != 0) {
			ww = widh;
		}
		if (heigt != 0) {
			hh = heigt;
		}
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		try {
			baos.close();
			isBm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 质量压缩
	 * 
	 * @param image
	 * @return
	 */

	private static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 20, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		/*
		 * while ( baos.toByteArray().length / 1024>100) {
		 * //循环判断如果压缩后图片是否大于100kb,大于继续压缩 baos.reset();//重置baos即清空baos
		 * image.compress(Bitmap.CompressFormat.JPEG, options,
		 * baos);//这里压缩options%，把压缩后的数据存放到baos中 options -= 10;//每次都减少10 }
		 */
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		try {
			baos.close();
			isBm.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return bitmap;
	}


//圆形头像
	 public Bitmap toRoundBitmap(Bitmap bitmap) {  
        //圆形图片宽高  
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        //正方形的边长  
        int r = 0;  
        //取最短边做边长  
        if(width > height) {  
            r = height;  
        } else {  
            r = width;  
        }  
        //构建一个bitmap  
        Bitmap backgroundBmp = Bitmap.createBitmap(width,  
                 height, Config.ARGB_8888);  
        //new一个Canvas，在backgroundBmp上画图  
        Canvas canvas = new Canvas(backgroundBmp);  
        Paint paint = new Paint();  
        //设置边缘光滑，去掉锯齿  
        paint.setAntiAlias(true);  
        //宽高相等，即正方形  
        RectF rect = new RectF(0, 0, r, r);  
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，  
        //且都等于r/2时，画出来的圆角矩形就是圆形  
        canvas.drawRoundRect(rect, r/2, r/2, paint);  
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉  
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上  
        canvas.drawBitmap(bitmap, null, rect, paint);  
        //返回已经绘画好的backgroundBmp  
        return backgroundBmp;  
    }  
}

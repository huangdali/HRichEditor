package com.huangdali.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by HDL on 2017/3/11.
 */

public class ImageCompereUtils {
    /**
     * 无损压缩图片
     *
     * @param path
     * @param quality
     * @return 压缩之后的图片
     */
    public static String compressImg(String path, int quality) {
        String resultPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            try {
                File sFile = new File(path);
                if ("gif".equals(sFile.getName().substring(sFile.getName().lastIndexOf(".") + 1))) {//动态图不压缩
                    return path;
                }
                if ((sFile.length() / 1024.0) > 3 * 1024) {
                    quality = 5;
                } else if ((sFile.length() / 1024.0) > 1024) {//大于1M的按10%压缩
                    quality = 10;
                } else if ((sFile.length() / 1024.0) < 100) {//小于80k的不压缩
                    return path;
                }
                File dir = new File("/sdcard/takephoto/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(dir, "IMG_" + System.currentTimeMillis() + "." + sFile.getName().substring(sFile.getName().lastIndexOf(".") + 1));
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                fos.flush();
                fos.close();
                resultPath = file.getAbsolutePath();
            } catch (Exception e) {
                return path;
            }
        } else {
            resultPath = path;
        }
        return resultPath;
    }
}

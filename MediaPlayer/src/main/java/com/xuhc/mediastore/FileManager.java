package com.xuhc.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.util.Log;

import com.xuhc.mediastore.bean.MediaBean;
import com.xuhc.mediastore.bean.MusicBean;
import com.xuhc.mediastore.bean.PictureBean;
import com.xuhc.mediastore.bean.VideoBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by wangqy on 2019/8/30.
 *
 * @author wangqy
 */
public class FileManager {

    private static final String TAG = "xhccc" + FileManager.class.getSimpleName();

    private volatile static FileManager instance;

    private ContentResolver contentResolver;

    private SimpleDateFormat simpleDateFormat;

    private Context context;

    private FileManager(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    }

    public static FileManager getInstance(Context context) {
        if (instance == null) {
            synchronized (FileManager.class) {
                if (instance == null) {
                    instance = new FileManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * 获取本机图片
     *
     * @return 图片列表
     */
    public List<MediaBean> getPhotos() {
        List<MediaBean> pictureBeans = new ArrayList<>();

        try (Cursor c = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + " DESC")) {
            if (c == null) {
                return pictureBeans;
            }
            while (c.moveToNext()) {
                //路径
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                if (!new File(path).exists()) {
                    continue;
                }
                //图片名
                String name = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //最后修改时间
                long time = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED));
                //大小
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                //PS：此处time需要乘以1000，否则转化出来均为1970年
                PictureBean photo = new PictureBean(name, path, formatSize(context, size));
                pictureBeans.add(photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pictureBeans;
    }

    /**
     * 是否是图片文件
     */
    private boolean isPicFile(String path) {
        path = path.toLowerCase();
        return path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png") || path.endsWith(".gif");
    }

    /**
     * 获取本机音乐列表
     *
     * @return 音乐列表
     */
    public List<MusicBean> getMusics() {
        List<MusicBean> musics = new ArrayList<>();

        try (Cursor c = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.AudioColumns.DATE_MODIFIED + " DESC")) {

            if (c == null) {
                return musics;
            }
            while (c.moveToNext()) {
                // 路径
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                if (!new File(path).exists()) {
                    continue;
                }

                // 歌曲名
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                // 音乐标题
                String title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                // 作者
                String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                // 大小
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                // 时长
                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                String format = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.MIME_TYPE));
                Log.d(TAG, "format=" + format + "name =" + name);
                MusicBean music = new MusicBean(name, title, path, artist, false);
                musics.add(music);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musics;
    }

    /**
     * 获取本机视频列表
     *
     * @return 视频列表
     */
    public List<MediaBean> getVideos() {
        List<MediaBean> videos = new ArrayList<>();

        try (Cursor c = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Video.VideoColumns.DATE_MODIFIED + " DESC")) {

            if (c == null) {
                return videos;
            }
            while (c.moveToNext()) {
                // 路径
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                if (!new File(path).exists()) {
                    continue;
                }

                // 视频的id
                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                // 视频名称
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                // 大小
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                // 时长
                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                //修改时间
                long time = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));

                VideoBean video = new VideoBean(name, path, formatDuration(duration));
                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }

    /**
     * 从文件的路径得到文件名
     *
     * @param path 文件路径
     * @return 文件名
     */
    public String getFilename(String path) {
        int dotPosition = path.lastIndexOf('/');
        if (dotPosition != -1) {
            return path.substring(dotPosition + 1);
        }
        return "";
    }

    /**
     * 将获取到的大小转化格式
     *
     * @param targetSize 获取到的大小（long）
     * @return 转化后的大小（kb,mb等等）；
     */
    private String formatSize(Context context, long targetSize) {
        return Formatter.formatFileSize(context, targetSize);
    }

    /**
     * 将视频时长转化格式
     *
     * @param duration 获取到的时长
     * @return 转化后的时长（时分秒）
     */
    public String formatDuration(int duration) {
        //默认会按照时区转化，需要设置为0区才能保证时长正确
        if (simpleDateFormat != null) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            return simpleDateFormat.format(duration);
        }
        return "00:00:00";
    }

}

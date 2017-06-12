package com.zhuoxin.hjj.geocaching.fragment;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.zhuoxin.hjj.geocaching.commons.ActivityUtils;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MainFragment extends Fragment implements TextureView.SurfaceTextureListener {

    private TextureView mTextureView;
    private ActivityUtils mActivityUtils;
    private MediaPlayer mediaPlayer;

    /**
     * 主页面视频播放
     * MediaPlayer 视频播放
     * TextureView和SurfaceView都可以展示播放的视频，SurfaceView效率高
     * TextureView主要用于展示渲染播放的视频，使用的时候TextureView需要SurfaceView
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Fragmeng全屏显示播放视频控件
        mTextureView = new TextureView(getContext());
        mActivityUtils = new ActivityUtils(this);
        return mTextureView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当视频控件准备好的时候，让MediaPlayer播放视频
        //设置监听，是否准备好播放
        mTextureView.setSurfaceTextureListener(this);
    }

    //xxxxxxxxxxx监听重写的方法xxxxxxxxxxxxx
    //准备好，可以展示播放视频
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
        /**
         * 视频展示准备好了
         * 1.找到视频播放的资源
         * 2.当MediaPlayer准备好时，使用MediaPlayer播放视频
         * 3.播放是视频在什么控件上展示，设置循环播放等
         */
        //打开资源文件
        try {
            AssetFileDescriptor openFd = getContext().getAssets().openFd("welcome.mp4");
            //拿到mediaPlayer需要的类型
            FileDescriptor fileDescriptor = openFd.getFileDescriptor();
            mediaPlayer = new MediaPlayer();
            //给mediaPlayer设置播放资源
            mediaPlayer.setDataSource(fileDescriptor,openFd.getStartOffset(),openFd.getLength());
            //异步准备
            mediaPlayer.prepareAsync();
            //设置循环播放
            mediaPlayer.setLooping(true);
            //设置准备监听，什么时候准备好
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //准备完成
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Surface mySurface = new Surface(surface);
                    mediaPlayer.setSurface(mySurface);
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            mActivityUtils.showToast("视频文件播放失败");
        }
    }
    //视频展示的大小发生变化时
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }
    //销毁的时候
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }
    //更新的时候
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer!=null){
            //释放资源
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}

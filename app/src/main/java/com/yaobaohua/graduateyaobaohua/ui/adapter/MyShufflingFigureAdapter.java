package com.yaobaohua.graduateyaobaohua.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.yaobaohua.graduateyaobaohua.ui.MyApplication;
import com.yaobaohua.graduateyaobaohua.ui.activity.PlayActivity;
import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;
import com.yaobaohua.graduateyaobaohua.utils.UtilJump;

import org.xutils.x;

/**
 * 图片轮播适配器
 *
 * @author Administrator
 */
public class MyShufflingFigureAdapter extends PagerAdapter {
    private ArrayList<String> urls;
    private Context context;

    public MyShufflingFigureAdapter() {

    }

    public MyShufflingFigureAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public Object instantiateItem(final ViewGroup container,  int position) {
        // 对ViewPager页号求模取出View列表中要显示的项
        position %= urls.size();
        if (position < 0) {
            position += urls.size();
        }
        final int temp = position;
        ImageView imageView = new ImageView(context);
        // 获取网上轮播图片设置到ImageView中
        x.image().bind(imageView,urls.get(position));

        imageView.setScaleType(ScaleType.FIT_XY);
        container.addView(imageView);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent=new Intent(context,PlayActivity.class);
                String path= Environment.getExternalStorageDirectory().getPath()+"/sss.mp4";
            //  String path="http://125.39.7.18:1863/200075500.flv?cdncode=%2f18907E7BE0798990%2f&time=1450760022&cdn=zijian&sdtfrom=v50221&platform=70202&scheduleflag=1&buname=qqlive&vkey=5D20C4F5FC36F4E6E856AFF993170CAEC5B7AB4D37FC556D20D36A587A8DEFFCBA4EE361F179DB9309191272625111D2AF4E7A46D4366EC5BF2B8937252E2B3B8D4D89426941726F50C2A1F2A4E30707&guid=B5B3AE746A57DD6020E240B4D37877C65872413B&refer=http%3A%2F%2Fwww.longzhu.com%2F&apptype=live";
                intent.putExtra("vName","极客学院");
                intent.putExtra("vUrl",path);
                intent.putExtra("vId","22");
                intent.putExtra("vType","1");
                context.startActivity(intent);
                ToastUtils.show(context,temp+"", Toast.LENGTH_LONG);
            }
        });


        // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        // ViewParent vp = imageView.getParent();
        //
        // if (vp != null)
        // {
        // container = (ViewGroup) vp;
        // container.removeView(imageView);
        // }
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Warning：不要在这里调用removeView
        // container.removeView(mImageViews[position]);
    }

    @Override
    public int getCount() {
        // 设置成最大，使用户看不到边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}

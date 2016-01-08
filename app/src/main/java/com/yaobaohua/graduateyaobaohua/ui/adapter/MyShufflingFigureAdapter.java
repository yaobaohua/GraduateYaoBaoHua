package com.yaobaohua.graduateyaobaohua.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.activity.MyPlayActivity;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;

import org.xutils.x;

import java.util.ArrayList;

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
    public Object instantiateItem(final ViewGroup container, int position) {
        // 对ViewPager页号求模取出View列表中要显示的项
        position %= urls.size();
        if (position < 0) {
            position += urls.size();
        }
        final int temp = position;
        ImageView imageView = new ImageView(context);
        // 获取网上轮播图片设置到ImageView中
        x.image().bind(imageView, urls.get(position));

        imageView.setScaleType(ScaleType.FIT_XY);
        container.addView(imageView);
        /**
         * 这里写轮播图的点击事件
         */
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyPlayActivity.class);
                String path ="http://www.114zhibo.com/player/huashu.html?id=474";
                String video_userId= (String) SPUtils.get(context,Constants.USER_OBJECT_ID,"");
                String video_previewImg="http://qzapp.qlogo.cn/qzapp/1104915383/573797EE46101CDF15930A955DD53D27/30";
                intent.putExtra("video", new Video( video_userId,"天堂","33", path,video_previewImg, "0", "0", "0", "3"));
                context.startActivity(intent);
            }
        });

       // Inventory  the history of the top ten computer viruses in recent years
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

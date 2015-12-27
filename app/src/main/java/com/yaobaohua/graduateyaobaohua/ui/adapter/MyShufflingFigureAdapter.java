package com.yaobaohua.graduateyaobaohua.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

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
//        imageView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MyPlayActivity.class);
//                String path = "http://www.modrails.com/videos/passenger_nginx.mov";
//                intent.putExtra("video", new Video("西游记", "33", path, "0", "3"));
//                context.startActivity(intent);
//            }
//        });


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

package com.yaobaohua.graduateyaobaohua.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.NativeVideoModel;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/25 11：56
 * @DESC :
 */
public class VideoAdapter extends BaseAdapter {

    private Context context;
    private List<Video> mDatas;
    ImageOptions options;

    public VideoAdapter(Context context, List<Video> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        options = new ImageOptions.Builder().setUseMemCache(true).build();

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_native_video_list, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.videoName.setText(mDatas.get(position).getVideo_Name());
        holder.videoCount.setText(mDatas.get(position).getVideo_id()+"");
        holder.videoSize.setText(mDatas.get(position).getVideo_Size());


        return convertView;
    }


    class MyViewHolder {

        @ViewInject(R.id.tv_native_name)
        private TextView videoName;

        @ViewInject(R.id.img_preview_native)
        private ImageView imgCut;

        @ViewInject(R.id.tv_native_size)
        private TextView videoSize;
        @ViewInject(R.id.tv_native_count)
        private TextView videoCount;

    }

}

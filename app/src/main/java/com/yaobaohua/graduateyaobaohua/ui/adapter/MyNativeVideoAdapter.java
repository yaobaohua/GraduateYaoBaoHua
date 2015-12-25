package com.yaobaohua.graduateyaobaohua.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.NativeVideoModel;
import com.yaobaohua.graduateyaobaohua.utils.FileSizeFormatUtils;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/23 16：51
 * @DESC :
 */
public class MyNativeVideoAdapter extends BaseAdapter {

    private Context context;
    private List<NativeVideoModel> mDatas;
    ImageOptions options;

    public MyNativeVideoAdapter(Context context, List<NativeVideoModel> mDatas) {
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

        holder.videoName.setText(mDatas.get(position).getVideoName());
        holder.videoCount.setText(mDatas.get(position).getId());
        holder.videoSize.setText(mDatas.get(position).getVideoSize());


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

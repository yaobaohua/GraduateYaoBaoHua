package com.yaobaohua.graduateyaobaohua.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;
import com.yaobaohua.graduateyaobaohua.ui.activity.MyPlayActivity;
import com.yaobaohua.graduateyaobaohua.ui.adapter.CommonAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.MyShufflingFigureAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.ViewHolder;
import com.yaobaohua.graduateyaobaohua.ui.widget.MyGirdView;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;
import com.yaobaohua.graduateyaobaohua.utils.ScreenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private DotImageHandler handler = new DotImageHandler(
            new WeakReference<MainFragment>(this));

    @ViewInject(R.id.home_viewPager)
    private ViewPager home_viewPager;

    private ArrayList<String> adPicture = new ArrayList<String>();

    private int[] dots = {R.mipmap.dot_blur, R.mipmap.dot_focus};

    private int dotIndex;

    public int count;

    private LinearLayout home_lin;

    @ViewInject(R.id.copy_of_home_rel)
    private RelativeLayout copy_of_home_rel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main, null);
        x.view().inject(this, view);
        initLunBoData();

        initGridDataAndFillGrid();

        return view;
    }


    public void initLunBoData() {
        //第一步,初始化存放图片地址的ArrayList<String>
        adPicture.clear();



        adPicture
                .add("http://img.mukewang.com/55237dcc0001128c06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/551de0570001134f06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/5518ecf20001cb4e06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/5513e20600017c1806000338.jpg");
        adPicture
                .add("http://img.mukewang.com/549bda090001c53e06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/547ed1c9000150cc06000338.jpg");



        //2.得到下面小圆圈的数量
        count = adPicture.size();
        //3.给ViewPager设置适配器
        home_viewPager
                .setAdapter(new MyShufflingFigureAdapter(getActivity(), adPicture));
        home_viewPager.setOnPageChangeListener(this);

        initPageResoure();
    }


    public void initPageResoure() {
        addDotImage();
        handler.sendEmptyMessageDelayed(DotImageHandler.MSG_UPDATE_IMAGE,
                DotImageHandler.MSG_DELAY);
    }

    public void addDotImage() {
        Context context = getActivity();
        home_lin = new LinearLayout(context);
        ImageView dotIV = null;
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llp.setLayoutDirection(LinearLayout.HORIZONTAL);
        llp.rightMargin = 20;
        llp.bottomMargin = 20;
        for (int i = 0; i < count; i++) {
            dotIV = new ImageView(context);
            dotIV.setImageResource(dots[0]);
            home_lin.addView(dotIV, llp);
        }
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        copy_of_home_rel.addView(home_lin, rlp);

        dotIndex = 0;
        ((ImageView) home_lin.getChildAt(dotIndex)).setImageResource(dots[1]);
    }

    private void setCurrentPoint(int position) {
        if (position < 0 || position == dotIndex)
            return;
        if (position > count - 1) {
            position %= count;
        }
        ((ImageView) home_lin.getChildAt(dotIndex)).setImageResource(dots[0]);
        ((ImageView) home_lin.getChildAt(position)).setImageResource(dots[1]);
        dotIndex = position;
    }

    /**
     * 实现轮播的Handler类
     */
    private static class DotImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED = 4;

        // 轮播间隔时间
        protected static final long MSG_DELAY = 3000;

        // 使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<MainFragment> weakReference;

        private int currentItem = 0;

        protected DotImageHandler(WeakReference<MainFragment> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainFragment home = weakReference.get();
            if (home == null) {
                // Activity已经回收，无需再处理UI了
                return;
            }
            // 检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (home.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                home.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    home.home_viewPager.setCurrentItem(currentItem);
                    // 准备下次播放
                    home.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
                            MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    // 只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    home.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
                            MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    // 记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                handler.sendEmptyMessage(DotImageHandler.MSG_KEEP_SILENT);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                handler.sendEmptyMessageDelayed(DotImageHandler.MSG_UPDATE_IMAGE,
                        DotImageHandler.MSG_DELAY);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentPoint(arg0);
        handler.sendMessage(Message.obtain(handler,
                DotImageHandler.MSG_PAGE_CHANGED, arg0, 0));
    }

    @ViewInject(R.id.gird_main_frag)
    private MyGirdView grid;

    private List<Video> listData = new ArrayList<>();

    private void initGridDataAndFillGrid() {

        initGridData();

    }


    private void initGridData() {
        final String video_userId = (String) SPUtils.get(getActivity(), Constants.USER_OBJECT_ID, "");
        String url = Constants.REQUEST_URL;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("action", "aaa");
        final int width = ScreenUtils.getScreenW(getContext()) / 3;

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    Video video;
                    if (listData.size() > 0) {
                        listData.clear();
                    }
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        int video_id = obj.getInt("video_id");
                        String video_desc = obj.getString("video_desc");
                        String video_name = obj.getString("video_name");
                        String video_path = obj.getString("video_path");
                        String video_img = obj.getString("video_img");
                        video = new Video(video_id, video_userId, video_img, video_name, "0", video_path, "0", "0", "0", "3");
                        listData.add(video);
                    }

                    final int width = ScreenUtils.getScreenW(getContext()) / 3;
                    final int widthDp = DensityUtil.px2dip(width * 97 / 100);
                    final int heightDp = DensityUtil.px2dip(width * 97 / 100 * 3 / 2);


                    grid.setAdapter(new CommonAdapter<Video>(getContext(), listData, R.layout.item_gird_online_main_frag) {
                        @Override
                        public void convert(ViewHolder holder, Video item) {

                            holder.setImageByUrlAndSize(R.id.iv_grid_main, item.getVideo_previewImg(), widthDp, heightDp);
                            holder.setText(R.id.tv_gird_main, item.getVideo_Name());

                        }
                    });
                    home_viewPager.setFocusable(true);
                    home_viewPager.setFocusableInTouchMode(true);
                    home_viewPager.requestFocus();
                    grid.setFocusable(false);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                            final Intent intent = new Intent(getActivity(), MyPlayActivity.class);

                            if (listData.get(position).getVideo_Type().equals("3")) {
                                //在Bmob数据库查询
                                BmobQuery<Video> query = new BmobQuery<>();
                                query.addWhereEqualTo(Constants.VIDEO_USER_ID, listData.get(position).getVideo_userId());
                                query.addWhereEqualTo(Constants.VIDEO_PATH, listData.get(position).getVideo_Path());
                                query.findObjects(getContext(), new FindListener<Video>() {
                                    @Override
                                    public void onSuccess(List<Video> list) {
                                        Video video = list.get(0);
                                        intent.putExtra("video", video);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        intent.putExtra("video", listData.get(position));
                                        startActivity(intent);
                                    }
                                });
                            }

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast("error");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                showToast("cancel");
            }

            @Override
            public void onFinished() {
                showToast("finish");
            }
        });
    }

}


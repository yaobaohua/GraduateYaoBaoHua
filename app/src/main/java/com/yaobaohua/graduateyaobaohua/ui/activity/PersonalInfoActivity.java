package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.api.MYProgressDialog;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.model.Users;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/27 22：06
 * @DESC :
 */
@ContentView(R.layout.act_personal_info)
public class PersonalInfoActivity extends BaseActivity {

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @ViewInject(R.id.tv_act_person_name)
    private TextView tvName;
    @ViewInject(R.id.tv_person_nickname)
    private TextView tvNickName;
    @ViewInject(R.id.tv_person_username)
    private TextView tvUserName;
    @ViewInject(R.id.tv_person_sex)
    private TextView tvSex;
    @ViewInject(R.id.tv_person_age)
    private TextView tvAge;
    @ViewInject(R.id.img_person_login)
    private ImageView imgHeadLogin;

    public MYProgressDialog pd;
    ImageOptions imageOptions;
    Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
        initView();
    }

    private void initView() {
        String objectId = (String) SPUtils.get(this, Constants.USER_OBJECT_ID, "");
        BmobQuery<Users> query = new BmobQuery<>();
        query.getObject(this, objectId, new GetListener<Users>() {
            @Override
            public void onSuccess(Users users) {
                x.image().bind(imgHeadLogin, users.getUser_head_img(), imageOptions);
                tvNickName.setText(users.getUser_name());
                tvSex.setText(users.getUser_sex());
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });


    }

    public void initEvent() {
        setToolbar();
        tvTitle.setText("个人信息");
        pd = new MYProgressDialog(this);
        imageOptions = new ImageOptions.Builder()
                .setCircular(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, this.getApplicationContext());
    }


    @Event({R.id.img_person_login,R.id.btn_login_login})
    private void onClick(View view) {
        if ((boolean) SPUtils.get(this, Constants.ISLOGIN, false)) {
            logout();
        } else {
            login();
        }
    }

    private void logout() {
        mTencent.logout(this);
        SPUtils.clear(this);
    }

    Users user = new Users();

    public void login() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, Constants.QQ_SCOPE, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    showToast("登录成功");
                    JSONObject obj = (JSONObject) o;
                    SPUtils.put(getApplicationContext(), Constants.ISLOGIN, true);
                    try {
                        tvUserName.setText(obj.getString("openid"));
                        tvAge.setText(obj.getString("access_token"));
                        user.setUser_openId(obj.getString("openid"));
                        user.setUser_access_token(obj.getString("access_token"));
                        user.setUser_expree_in(obj.getString("expires_in") + "");

                        mTencent.setOpenId(obj.getString("openid"));
                        mTencent.setAccessToken(obj.getString("access_token"), obj.getString("expires_in") + "");
                        getUserInfo();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    //获取用户信息
    public void getUserInfo() {
        UserInfo info = new UserInfo(this, mTencent.getQQToken());
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject obj = (JSONObject) o;
                try {
                    tvNickName.setText(obj.getString("nickname"));
                    tvSex.setText(obj.getString("gender" + ""));
                    user.setUser_sex(obj.getString("gender" + ""));
                    user.setUser_name(obj.getString("nickname"));
                    user.setUser_province(obj.getString("province"));
                    user.setUser_head_img(obj.getString("figureurl_qq_1"));
                    x.image().bind(imgHeadLogin, obj.getString("figureurl_qq_1"), imageOptions);
                    user.save(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            SPUtils.put(getApplicationContext(), Constants.USER_OBJECT_ID, user.getObjectId());
                            SPUtils.put(getApplicationContext(), Constants.VIDEO_USER_ID, user.getObjectId());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            saveOnFailInsert();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                showToast("获取用户信息失败");
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 当在其他设备上登录时也要保存objectid
     */
    public void saveOnFailInsert() {
        BmobQuery<Users> query = new BmobQuery<Users>();
        query.addWhereEqualTo(Constants.QQ_OPEN_ID, user.getUser_openId());
        query.findObjects(this, new FindListener<Users>() {
            @Override
            public void onSuccess(List<Users> list) {
                SPUtils.put(getApplicationContext(), Constants.USER_OBJECT_ID, list.get(0).getObjectId());
                SPUtils.put(getApplicationContext(), Constants.VIDEO_USER_ID, list.get(0).getObjectId());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResult(requestCode, resultCode, data);
    }


}

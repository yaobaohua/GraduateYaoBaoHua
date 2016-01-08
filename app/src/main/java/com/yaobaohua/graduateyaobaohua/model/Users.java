package com.yaobaohua.graduateyaobaohua.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/28 15：41
 * @DESC :
 */
public class Users extends BmobObject  implements Serializable{

    //用户姓名
    private String user_name;
    //性别
    private String user_sex;
    //用户qq的开放id唯一
    private String user_openId;
    //qq访问的token 唯一
    private String user_access_token;
    //qq访问的有效期
    private String user_expree_in;
    //省份
    private String user_province;
    //头像
    private String user_head_img;

    public Users(String user_name, String user_sex, String user_openId, String user_access_token, String user_expree_in, String user_province, String user_head_img) {
        this.user_name = user_name;
        this.user_sex = user_sex;
        this.user_openId = user_openId;
        this.user_access_token = user_access_token;
        this.user_expree_in = user_expree_in;
        this.user_province = user_province;
        this.user_head_img = user_head_img;
    }

    public Users() {
    }






    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_openId() {
        return user_openId;
    }

    public void setUser_openId(String user_openId) {
        this.user_openId = user_openId;
    }

    public String getUser_access_token() {
        return user_access_token;
    }

    public void setUser_access_token(String user_access_token) {
        this.user_access_token = user_access_token;
    }

    public String getUser_expree_in() {
        return user_expree_in;
    }

    public void setUser_expree_in(String user_expree_in) {
        this.user_expree_in = user_expree_in;
    }

    public String getUser_province() {
        return user_province;
    }

    public void setUser_province(String user_province) {
        this.user_province = user_province;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }


}

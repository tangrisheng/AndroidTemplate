package com.trs.template.net;

/**
 * Created by Administrator on 2015/10/29.
 */
public class LoginResponse extends BaseResponse {
    public String _id;
    public String tel;
    public String name;
    public String icon;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "_id='" + _id + '\'' +
                ", tel='" + tel + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}

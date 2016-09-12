package com.example.administrator.ylbapplication.hybrid.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GodXj on 2016/6/15.
 */
public class ForwardBean implements Parcelable {

    //跳转类型 native本地  h5web网络web h5native本地web
    public String type;

    //跳转的链接
    public String topage;

    public Params params;

    public Head UIHeader;

    public ShareBean shareAction;

    public static class Params implements Parcelable {
        public String experianceId;

        public Params() {
        }

        protected Params(Parcel in) {
            this.experianceId = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.experianceId);
        }

        public static final Creator<Params> CREATOR = new Creator<Params>() {
            public Params createFromParcel(Parcel source) {
                return new Params(source);
            }

            public Params[] newArray(int size) {
                return new Params[size];
            }
        };

    }

    public static class Head implements Parcelable {
        public String title;
        public ViewTiles right;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeParcelable(this.right, 0);
        }

        public Head() {
        }

        protected Head(Parcel in) {
            this.title = in.readString();
            this.right = in.readParcelable(ViewTiles.class.getClassLoader());
        }

        public static final Creator<Head> CREATOR = new Creator<Head>() {
            public Head createFromParcel(Parcel source) {
                return new Head(source);
            }

            public Head[] newArray(int size) {
                return new Head[size];
            }
        };
    }

    public static class ViewTiles implements Parcelable {
        public String type;
        public String content;
        public ForwardBean action;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.type);
            dest.writeString(this.content);
            dest.writeParcelable(this.action, 0);
        }

        public ViewTiles() {
        }

        protected ViewTiles(Parcel in) {
            this.type = in.readString();
            this.content = in.readString();
            this.action = in.readParcelable(ForwardBean.class.getClassLoader());
        }

        public static final Creator<ViewTiles> CREATOR = new Creator<ViewTiles>() {
            public ViewTiles createFromParcel(Parcel source) {
                return new ViewTiles(source);
            }

            public ViewTiles[] newArray(int size) {
                return new ViewTiles[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.topage);
        dest.writeParcelable(this.UIHeader, flags);
    }

    public ForwardBean() {
    }

    protected ForwardBean(Parcel in) {
        this.type = in.readString();
        this.topage = in.readString();
        this.UIHeader = in.readParcelable(Head.class.getClassLoader());
    }

    public static final Creator<ForwardBean> CREATOR = new Creator<ForwardBean>() {
        public ForwardBean createFromParcel(Parcel source) {
            return new ForwardBean(source);
        }

        public ForwardBean[] newArray(int size) {
            return new ForwardBean[size];
        }
    };
}

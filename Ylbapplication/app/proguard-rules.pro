-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature,InnerClasses

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.support.** {*;}

-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

-keep class **.R$* {*;}

# 保留在Activity中的方法参数是view的方法， # 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{ public void *(android.view.View); }


-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    public static ** valueOf(int);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


#-assumenosideeffects class android.util.Log {
# public static int v(...);
# public static int i(...);
# public static int w(...);
# public static int d(...);
# public static int e(...);
#}


#RxJava  混淆
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


#kryo
-dontwarn com.esotericsoftware.kryo.**
-keep class com.esotericsoftware.kryo.**{*;}

-dontwarn org.apache.http.conn.scheme.**
-keep class org.apache.http.conn.scheme.** {*;}

#Picasso
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}


#Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions


#Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson 下面替换成自己的实体类
-keep class com.example.administrator.ylbapplication.model.output.** { *; }
-keep class com.example.administrator.ylbapplication.model.BaseOutput { *; }
-keep class com.android.ylblib.base.model.bean.output.*{*;}



#Okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-keep class okio.**{*;}


# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }


#webkit
-keep public class android.net.http.SslError
-keep public class android.webkit.** {*;}
-dontwarn android.webkit.WebView
-dontwarn android.webkit.WebViewClient


#SnappyDB
-dontwarn com.esotericsoftware.kryo.**
-keep class com.esotericsoftware.kryo.io.** {*;}
-keep class com.esotericsoftware.kryo.serializers.** {*;}
-keep class com.esotericsoftware.kryo.** {*;}
-keep class com.esotericsoftware.kryo.serializers.**.R$* {*;}

-keep class io.supercharge.rxsnappy.** {*;}

-keep class com.snappydb.** {*;}

#okhttp3 log
-keep class okhttp3.logging.** {*;}


#okhttp download
-keep class com.jakewharton.picasso.** {*;}

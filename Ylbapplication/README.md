1.Android 采用的相关框架如下

        //请求组合框架
            compile 'com.squareup.retrofit2:retrofit:2.0.2'
            //响应式编程 , 异步处理
            compile 'io.reactivex:rxandroid:1.+'
            compile 'io.reactivex:rxjava:1.+'
            //请求框架
            compile 'com.squareup.okhttp3:okhttp:2.0.2'
            //返回数据解析
            compile 'com.squareup.retrofit2:converter-gson:2.0.2'
            compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
            //网络请求拦截器 log
            compile 'com.squareup.okhttp3:logging-interceptor:3.+'
            //图片显示
            compile 'com.jakewharton.picasso:picasso2-okhttp3-downloader:1.0.2'
            //数据存储
            compile 'io.supercharge:rxsnappy:0.4.0'
            //id 注解
            compile 'com.jakewharton:butterknife:7.0.1'


2.请求使用Okhttp3 的 client , Retrofit 组合请求 结合 Gson进行解析 ,
3.请求采用 拦截器方式进行修改 请求header 和 请求体参数
4.图片的异步加载使用okhttp组合的picasso
5.数据存储使用SnappyDB  NOSQL方式
6.异步处理事件采用Rxjava  Rxandroid
7.继续沿用永利宝的Hybrid模式


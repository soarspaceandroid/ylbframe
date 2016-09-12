package com.android.ylblib.net;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by GodXj on 2016/5/11.
 */
public class NetSecretHelper {

    public static String channel = "yonglibao";//记录渠道存全局

    /**
     * 加密参数
     */
//    public static String getUrlParam(HashMap<String, Object> map, String api) {
//        map.put("auth_key", "yonglibao");
//        map.put("auth_timestamp", System.currentTimeMillis() / 1000 + "");
//        map.put("auth_version", "1.0.0");
//        map.put("client", "android");
//        map.put("u", DeviceID.getDeviceId(BaseApplication.getContext()));
//        map.put("requesttime", System.currentTimeMillis() / 1000);
//        map.put("system", android.os.Build.MODEL + "("
//                + android.os.Build.VERSION.SDK + ","
//                + android.os.Build.VERSION.RELEASE + ")");
//        try {
//            map.put("version", BaseApplication.getContext().getPackageManager()
//                    .getPackageInfo("com.yonglibao.android", 0).versionName);
//            map.put("channel", channel);
//        } catch (PackageManager.NameNotFoundException e1) {
//            map.put("version", "4.0.nativeweb");
//        }
//        UserHelper userHelper = UserHelper.getInstance();
//        if (userHelper != null && userHelper.getUser() != null) {
//            if (NetApiName.SERVICE_URL.equals(api)) {
//                map.put("auth_signature", SubmitHelper.getHmacSignature(sortMapByKey(map), userHelper.getUser().token, api));
//            } else {
//                map.put("auth_signature", SubmitHelper.getHmacSignature(sortMapByKey(map), userHelper.getUser().token,
//                        Config.APIV + api));
//            }
//        }
//        Gson gson = Utils.createBuilder().create();
//        String content = gson.toJson(map);
//        LogUtil.json("NetRequest", content);
//        byte[] encodeContent;
//        String base64EncodeData = "";
//        try {
//            encodeContent = SubmitHelper.encryptMode(content.getBytes("UTF-8"));
//            base64EncodeData = Base64.encodeToString(encodeContent,
//                    Base64.DEFAULT);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return base64EncodeData;
//    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(HashMap<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    private static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }
}

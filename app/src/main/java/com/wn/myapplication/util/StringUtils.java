package com.wn.myapplication.util;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 * Created by liangbin.yuan on 2017/7/26.
 */

public class StringUtils {
    /**
     * 描述：将null转化为“”.
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return (isEmpty(str) || str.trim().length() == 0);
    }

    /**
     * 將數字轉成字符串
     *
     * @param num
     * @return
     */
    public static String intToString(int num) {
        if (num >= 10000) {
            float num_float = ((float) num) / 1000;
            int scale = 2;//设置位数
            int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
            BigDecimal bd = new BigDecimal((double) num_float);
            bd = bd.setScale(scale, roundingMode);
            num_float = bd.floatValue();
            return String.valueOf(num_float) + "K";
        } else {
            return String.valueOf(num);
        }
    }

    /**
     * @param seconds 进度(秒)
     * @return
     */
    public static String formatTime(int seconds) {
        String standardTime = "";
        if (seconds <= 0) {
            standardTime = "00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime =
                    String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d:%02d",
                    seconds / 3600,
                    seconds % 3600 / 60,
                    seconds % 60
            );
        }
        return standardTime;
    }

    public static String doubleToString(double count) {
        DecimalFormat decimalFormat;
        try {
            decimalFormat = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.ENGLISH);
            decimalFormat.applyPattern("#.#");
        }catch (Exception e){
            decimalFormat = new DecimalFormat("#.#");
        }
        if (count > 1000000000) {
            return decimalFormat.format(count / 1000000000) + "B";
        } else if (count >= 1000000) {
            return decimalFormat.format(count / 1000000) + "M";
        } else if (count >= 1000) {
            return decimalFormat.format(count / 1000) + "K";
        }
        return String.valueOf((long)count);
    }

    public static String longToString(long num) {
        return doubleToString(num);
    }

    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                /* 获取一个字符 */
                String temp = str.substring(i, i + 1);
                /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return 字符串的长度（中文字符计2个）
     */
    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    //中文字符长度为2
                    valueLength += 2;
                } else {
                    //其他字符长度为1
                    valueLength += 1;
                }
            }
        }

        return valueLength;
    }

    /**
     * 描述：获取指定长度的字符所在位置.
     *
     * @param str  指定的字符串
     * @param maxL 要取到的长度（字符长度，中文字符计2个）
     * @return 字符的所在位置
     */
    public static int subStringLength(String str, int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            //获取一个字符
            String temp = str.substring(i, i + 1);
            //判断是否为中文字符
            if (temp.matches(chinese)) {
                //中文字符长度为2
                valueLength += 2;
            } else {
                //其他字符长度为1
                valueLength += 1;
            }
            if (valueLength > maxL) {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

    public static final char MIN_HIGH_SURROGATE = '\uD800';
    public static final char MAX_HIGH_SURROGATE = '\uDBFF';

    /**
     * Character#isHighSurrogate 只用在判断emoji是否被裁剪了
     * @param ch
     * @return
     */
    public static boolean isHighSurrogate(char ch) {
        return (MIN_HIGH_SURROGATE <= ch && MAX_HIGH_SURROGATE >= ch);
    }

    public static String filterEndHighSurrogate(String str){
        int index = str.length() - 1;
        for (int i = index; i > 0; i--) {
            char c = str.charAt(i);
            if (!isHighSurrogate(c)) {
                return str.substring(0, i + 1);
            }
        }
        return str;
    }

    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
    public static Boolean isMobileNo(String str) {
        Boolean isMobileNo = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(str);
            isMobileNo = m.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMobileNo;
    }

    /**
     * 描述：是否只是字母和数字.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 描述：是否只是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isNumber(String str) {
        Boolean isNumber = false;
        String expr = "^[0-9]+$";
        if (str.matches(expr)) {
            isNumber = true;
        }
        return isNumber;
    }

//    /**
//     * 描述：是否是邮箱.
//     *
//     * @param str 指定的字符串
//     * @return 是否是邮箱:是为true，否则false
//     */
//    public static Boolean isEmail(String str) {
//        try {
//            str = str.trim().toLowerCase(Locale.getDefault());
//            if(str.length()==0||str.contains(" ")||(!str.contains("@"))||(!str.contains("."))){
//                return false;
//            }
//            char c = str.charAt(str.lastIndexOf("@")+1);
//            if(!((c>='a'&&c<='z')||(c>='0'&&c<='9'))){
//                return false;
//            }
//            char a = str.charAt(0);
//            if((a>='a'&&a<='z')||(a>='0'&&a<='9')){
//                return true;
//            }else{
//                return false;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }


    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                } else {

                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：从输入流中获得String.
     *
     * @param is 输入流
     * @return 获得的String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            //最后一个\n删除
            if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 描述：标准化日期时间类型的数据，不足两位的补0.
     *
     * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
     * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
     */
    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();
        try {
            if (isEmpty(dateTime)) {
                return null;
            }
            String[] dateAndTime = dateTime.split(" ");
            if (dateAndTime.length > 0) {
                for (String str : dateAndTime) {
                    if (str.indexOf("-") != -1) {
                        String[] date = str.split("-");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ");
                        String[] date = str.split(":");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    /**
     * 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    the str
     * @param length 指定字节长度
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length) {
        return cutString(str, length, "");
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    文本
     * @param length 字节长度
     * @param dot    省略符号
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length, String dot) {
        int strBLen = strlen(str, "GBK");
        if (strBLen <= length) {
            return str;
        }
        int temp = 0;
        StringBuffer sb = new StringBuffer(length);
        char[] ch = str.toCharArray();
        for (char c : ch) {
            sb.append(c);
            if (c > 256) {
                temp += 2;
            } else {
                temp += 1;
            }
            if (temp >= length) {
                if (dot != null) {
                    sb.append(dot);
                }
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 描述：截取字符串从第一个指定字符.
     *
     * @param str1   原文本
     * @param str2   指定字符
     * @param offset 偏移的索引
     * @return 截取后的字符串
     */
    public static String cutStringFromChar(String str1, String str2, int offset) {
        if (isEmpty(str1)) {
            return "";
        }
        int start = str1.indexOf(str2);
        if (start != -1) {
            if (str1.length() > start + offset) {
                return str1.substring(start + offset);
            }
        }
        return "";
    }

    /**
     * 描述：获取字节长度.
     *
     * @param str     文本
     * @param charset 字符集（GBK）
     * @return the int
     */
    public static int strlen(String str, String charset) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = 0;
        try {
            length = str.getBytes(charset).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取大小的描述.
     *
     * @param size 字节个数
     * @return 大小的描述
     */
    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024) {
            suffix = "K";
            size = size >> 10;
            if (size >= 1024) {
                suffix = "M";
                //size /= 1024;
                size = size >> 10;
                if (size >= 1024) {
                    suffix = "G";
                    size = size >> 10;
                    //size /= 1024;
                }
            }
        }
        return size + suffix;
    }

    /**
     * 描述：ip地址转换为10进制数.
     *
     * @param ip the ip
     * @return the long
     */
    public static long ip2int(String ip) {
        ip = ip.replace(".", ",");
        String[] items = ip.split(",");
        return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
    }

    public static int string2Integer(String value) {
        int num = 0;
        try {
            num = Integer.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    public static long string2Long(String value) {
        long num = 0;
        try {
            num = Long.valueOf(value);
        } catch (Exception e) {
//            e.printStackTrace();
            num = 0;
        }
        return num;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println(dateTimeFormat("2012-3-2 12:2:20"));
    }

    /**
     * 不区分大小写的indexOf
     *
     * @see TextUtils#indexOf(CharSequence, char, int)
     */
    public static int indexOfIgnoreCase(CharSequence sequence, CharSequence needle, int start) {
        if (sequence == null || needle == null) return -1;
        return TextUtils.indexOf(sequence.toString().toLowerCase(), needle.toString().toLowerCase(), start);
    }

    /**
     * 不区分大小写的indexOf
     *
     * @see TextUtils#indexOf(CharSequence, char, int, int)
     */
    public static int indexOfIgnoreCase(CharSequence sequence, CharSequence needle, int start, int end) {
        if (sequence == null || needle == null) return -1;
        return TextUtils.indexOf(sequence.toString().toLowerCase(), needle.toString().toLowerCase(), start, end);
    }

    /**
     * 向链接添加参数
     */
    public static String appenParamsToUrl(String url, String key, String value) {
        String params = key + "=" + value;
        int i = -1;
        if ((i = url.indexOf('?')) > 0) {
            if ((i != url.length() - 1)) {
                url = url + "&" + params;
            } else {
                url = url + params;
            }
        } else {
            url = url + "?" + params;
        }
        return url;
    }

    /**
     * 从url中移除参数
     *
     * @return may be null
     */
    public static String extractUrlNoParam(String url) {
        if (url == null) {
            return null;
        }
        int endIndex = url.indexOf('?') > 0 ? url.indexOf('?') : url.length();
        return url.substring(0, endIndex);
    }

    /**
     * 从链接中取出参数值，以&分隔
     */
    public static String extractParam(String paramName, String url) {
        String extract = "";
        String regex = "(?<=" + paramName + "=)[^&]*";
        Matcher m = Pattern.compile(regex).matcher(url);
        if (m.find()) {
            extract = m.group();
        }
        return extract;
    }

    /**
     * 从链接中取出参数值，以&分隔，并且对其进行url decode,主要用来提取链接类型的参数
     */
    public static String extractParamAndDecode(String paramName, String url) {
        String extract = StringUtils.extractParam(paramName, url);
        if (!TextUtils.isEmpty(extract)) {
            extract = urlDecode(extract);
        }
        return extract;
    }

    /**
     * 从链接中取出参数值，从“参数名=”到末尾
     */
    public static String extractParamToEnd(String paramName, String url) {
        String extract = "";
        String regex = "(?<=" + paramName + "=).*$";
        Matcher m = Pattern.compile(regex).matcher(url);
        if (m.find()) {
            extract = m.group();
        }
        return extract;
    }

    /**
     * 从链接中取出参数值，从“参数名=”到末尾，并以utf-8进行decode
     */
    public static String extractParamToEndAndDecode(String paramName, String url) {
        String extract = "";
        String regex = "(?<=" + paramName + "=).*$";
        Matcher m = Pattern.compile(regex).matcher(url);
        if (m.find()) {
            extract = m.group();
            extract = urlDecode(extract);
        }
        return extract;
    }

    /**
     * 截取数字，如果不能正常解析为数字，则返回默认值
     */
    public static int extractParamInt(String paramName, String url, int defaultInt) {
        String extract = extractParam(paramName, url);
        try {
            return Integer.valueOf(extract);
        } catch (Exception e) {
            return defaultInt;
        }
    }

    /**
     * 对字符串以utf-8进行encode
     */
    public static String urlEncode(String param) {
        String encode = "";
        try {
            encode = URLEncoder.encode(param, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            encode = "";
        }
        return encode;
    }

    /**
     * 对字符串以utf-8进行decode,并解决字符串原来含有'%'时，解码错误的问题
     */
    public static String urlDecode(String str) {
        String decode = "";
        try {
            str = str.replaceAll("%(?!\\w{2})", "%25");
            decode = URLDecoder.decode(str, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            decode = "";
        }
        return decode;
    }

    /**
     * 返回源字符串中符合目标字符串的第一个匹配Index
     *
     * @param sourceStr 源字符串
     * @param targetStr 目标字符串
     * @return 第一个目标字符串的索引
     */
    public static int firstStartIndexOf(String sourceStr, String targetStr) {
        if (TextUtils.isEmpty(sourceStr) || TextUtils.isEmpty(targetStr)) {
            return -1;
        }
        return sourceStr.indexOf(targetStr);
    }

    public static int firstStartIndexOf(SpannableStringBuilder builder, String targetStr) {
        if (TextUtils.isEmpty(builder) || TextUtils.isEmpty(targetStr)) {
            return -1;
        }
        String str = builder.toString();
        return str.indexOf(targetStr);
    }

    public static int firstStartIndexOf(Spannable builder, String targetStr) {
        if (TextUtils.isEmpty(builder) || TextUtils.isEmpty(targetStr)) {
            return -1;
        }
        String str = builder.toString();
        return str.indexOf(targetStr);
    }

    /**
     * 设置字体的TypeFace，粗体、斜体或者正常
     *
     * @param sourceStr 源字符串
     * @param fromIndex 开始替换的index
     * @param toIndex   替换到index
     * @param typeFace  需要替换成的样式
     */
    public static SpannableStringBuilder setTypeFace(String sourceStr, int fromIndex, int toIndex, int typeFace) {
        return setTypeFace(new SpannableStringBuilder(sourceStr), fromIndex, toIndex, typeFace);
    }

    public static SpannableStringBuilder setTypeFace(SpannableStringBuilder sourceBuilder, int fromIndex, int toIndex, int typeFace) {
        if (sourceBuilder == null || fromIndex < 0 ||
                toIndex < fromIndex || toIndex > sourceBuilder.length()) {
            return sourceBuilder;
        }
        sourceBuilder.setSpan(new StyleSpan(typeFace), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sourceBuilder;
    }

    public static SpannableStringBuilder setTypeFace(String sourceStr, String targetStr, int typeFace) {
        return setTypeFace(new SpannableStringBuilder(sourceStr), targetStr, typeFace);
    }

    public static SpannableStringBuilder setTypeFace(SpannableStringBuilder sourceBuilder, String targetStr, int typeFace) {
        int firstIndex = firstStartIndexOf(sourceBuilder, targetStr);
        if (firstIndex == -1) {
            return sourceBuilder;
        }
        return setTypeFace(sourceBuilder, firstIndex, firstIndex + targetStr.length(), typeFace);
    }

    public static SpannableStringBuilder setClickSpan(SpannableStringBuilder sourceBuilder, int fromIndex, int toIndex, ClickableSpan span) {
        if (sourceBuilder == null || fromIndex < 0 ||
                toIndex < fromIndex || toIndex > sourceBuilder.length() || span == null) {
            return sourceBuilder;
        }
        sourceBuilder.setSpan(span, fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sourceBuilder;
    }

    public static void setClickSpan(Spannable sourceBuilder, int fromIndex, int toIndex, ClickableSpan span) {
        if (sourceBuilder == null || fromIndex < 0 ||
                toIndex < fromIndex || toIndex > sourceBuilder.length() || span == null) {
            return;
        }
        sourceBuilder.setSpan(span, fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static SpannableStringBuilder setClickSpan(SpannableStringBuilder sourceBuilder, String targetStr, ClickableSpan span) {
        int firstIndex = firstStartIndexOf(sourceBuilder, targetStr);
        if (firstIndex == -1) {
            return sourceBuilder;
        }
        return setClickSpan(sourceBuilder, firstIndex, firstIndex + targetStr.length(), span);
    }

    public static void setClickSpan(Spannable sourceBuilder, String targetStr, ClickableSpan span) {
        int firstIndex = firstStartIndexOf(sourceBuilder, targetStr);
        if (firstIndex == -1) {
            return;
        }
        setClickSpan(sourceBuilder, firstIndex, firstIndex + targetStr.length(), span);
    }

    /**
     * 设置文字的大小
     *
     * @param sourceStr    源字符串
     * @param fromIndex    开始替换的index
     * @param toIndex      替换到index
     * @param textSizeInDp 文字的dp大小
     */
    public static SpannableStringBuilder setTextSize(String sourceStr, int fromIndex, int toIndex, int textSizeInDp) {
        return setTextSize(new SpannableStringBuilder(sourceStr), fromIndex, toIndex, textSizeInDp);
    }

    public static SpannableStringBuilder setTextSize(SpannableStringBuilder sourceBuilder, int fromIndex, int toIndex, int textSizeInDp) {
        if (sourceBuilder == null || fromIndex < 0 ||
                toIndex < fromIndex || toIndex > sourceBuilder.length() || textSizeInDp <= 0) {
            return sourceBuilder;
        }
        sourceBuilder.setSpan(new AbsoluteSizeSpan(textSizeInDp, true), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sourceBuilder;
    }

    public static SpannableStringBuilder setTextSize(String sourceStr, String targetStr, int textSizeInDp) {
        return setTextSize(new SpannableStringBuilder(sourceStr), targetStr, textSizeInDp);
    }

    public static SpannableStringBuilder setTextSize(SpannableStringBuilder sourceBuilder, String targetStr, int textSizeInDp) {
        int firstIndex = firstStartIndexOf(sourceBuilder, targetStr);
        if (firstIndex == -1) {
            return sourceBuilder;
        }
        return setTextSize(sourceBuilder, firstIndex, firstIndex + targetStr.length(), textSizeInDp);
    }


    /**
     * 设置文字的颜色
     *
     * @param sourceStr 源字符串
     * @param fromIndex 开始替换的index
     * @param toIndex   替换到index
     * @param colorId   颜色资源id
     */
    public static SpannableStringBuilder setTextColor(String sourceStr, int fromIndex, int toIndex, @ColorRes int colorId) {
        return setTextColor(new SpannableStringBuilder(sourceStr), fromIndex, toIndex, colorId);
    }

    public static SpannableStringBuilder setTextColor(SpannableStringBuilder sourceBuilder, int fromIndex, int toIndex, @ColorRes int colorId) {
        if (sourceBuilder == null || fromIndex < 0 ||
                toIndex < fromIndex || toIndex > sourceBuilder.length()) {
            return sourceBuilder;
        }
        sourceBuilder.setSpan(new ForegroundColorSpan(ResourceUtils.getColor(colorId)), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sourceBuilder;
    }

    public static SpannableStringBuilder setTextColor(String sourceStr, String targetStr, int textSizeInDp) {
        return setTextColor(new SpannableStringBuilder(sourceStr), targetStr, textSizeInDp);
    }

    public static SpannableStringBuilder setTextColor(SpannableStringBuilder sourceBuilder, String targetStr, @ColorRes int colorId) {
        int firstIndex = firstStartIndexOf(sourceBuilder, targetStr);
        if (firstIndex == -1) {
            return sourceBuilder;
        }
        return setTextColor(sourceBuilder, firstIndex, firstIndex + targetStr.length(), colorId);
    }

    /**
     * 高亮字符串中的关键词
     *
     * @param sequence      需要处理的字符串
     * @param needle        关键词
     * @param colorId       颜色资源id
     * @param caseSensitive 是否大小写敏感
     * @return 带高亮的字符串
     */
    public static SpannableStringBuilder highlight(CharSequence sequence, CharSequence needle, @ColorRes int colorId, boolean caseSensitive) {
//        SpannableStringBuilder result = null;
//        if (sequence != null && sequence.length() > 0) {
//            result = new SpannableStringBuilder(sequence);
//            if (needle != null && needle.length() >0) {
//                int start, end = 0;
//                if (caseSensitive) {
//                    while ((start = TextUtils.indexOf(result, needle, end)) != -1) {
//                        end = start + needle.length();
//                        result = StringUtils.setTextColor(result, start, end, colorId);
//                    }
//                } else {
//                    while ((start = StringUtils.indexOfIgnoreCase(result, needle, end)) != -1) {
//                        end = start + needle.length();
//                        result = StringUtils.setTextColor(result, start, end, colorId);
//                    }
//                }
//            }
//        } else {
//            result = new SpannableStringBuilder("");
//        }
//        return result;
        return highlight(sequence, needle.toString().split(" "), colorId, caseSensitive);
    }

    /**
     * 高亮字符串中的关键词
     *
     * @param sequence      需要处理的字符串
     * @param needleArray       关键词数组
     * @param colorId       颜色资源id
     * @param caseSensitive 是否大小写敏感
     * @return 带高亮的字符串
     */
    public static SpannableStringBuilder highlight(CharSequence sequence, CharSequence[] needleArray, @ColorRes int colorId, boolean caseSensitive) {
        SpannableStringBuilder result;
        if (sequence != null && sequence.length() > 0) {
            List<String> sequences;
            List<String> needles = new ArrayList<>();
            if(needleArray == null){
                needleArray = new CharSequence[0];
            }
            if(caseSensitive){
                sequences = Arrays.asList(sequence.toString().split(" "));
                for(CharSequence s : needleArray){
                    needles.add(s.toString());
                }
            } else {
                sequences = new ArrayList<>();
                String[] array = sequence.toString().split(" ");
                for (String s : array) {
                    sequences.add(s.toLowerCase());
                }
                for(CharSequence s : needleArray){
                    needles.add(s.toString().toLowerCase());
                }
            }
            result = new SpannableStringBuilder(sequence);
            if(needles.size() > 0){
                for(String needle : needles){
                    if(sequences.contains(needle)){
                        if (needle != null && needle.length() > 0) {
                            int start, end = 0;
                            if (caseSensitive) {
                                while ((start = TextUtils.indexOf(result, needle, end)) != -1) {
                                    end = start + needle.length();
                                    result = StringUtils.setTextColor(result, start, end, colorId);
                                }
                            } else {
                                while ((start = StringUtils.indexOfIgnoreCase(result, needle, end)) != -1) {
                                    end = start + needle.length();
                                    result = StringUtils.setTextColor(result, start, end, colorId);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            result = new SpannableStringBuilder("");
        }
        return result;
    }

    /**
     * 判断字符串是否只包含数字
     *
     * @param str 源字符串
     * @return true表示只包含数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static BigInteger getMD5(String str) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 计算md5函数
        String string = str;
        if (TextUtils.isEmpty(string)) {
            string = "";
        }
        messageDigest.update(string.getBytes());
        return new BigInteger(1, messageDigest.digest());
    }

    /**
     * 判断email格式是否正确@之前
     *
     * @param email 源字符串
     * @return true表示是合法email
     */
    public static boolean isEmail(String email) {
        //^[^.][a-z0-9+\.]*@([A-Za-z0-9][-A-Za-z0-9]+.)+[A-Za-z]{2,64}

        if (TextUtils.isEmpty(email)) {
            return false;
        }

        if (!email.contains("@")) {
            return false;
        }
        String[] mailSplit = email.split("@");
        if (mailSplit.length < 2) {
            return false;
        }
        String mailBefore = mailSplit[0];
        String mailAfter = mailSplit[1];

        if (TextUtils.isEmpty(mailBefore) || TextUtils.isEmpty(mailAfter)) {
            return false;
        }

        //放宽Email检测限制，避免正常邮箱无法进行注册和登录操作
        String regex = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]*$";
        return Pattern.compile(regex).matcher(email).matches() && mailAfter.length() < 64;
    }

    /**
     * 将email隐藏
     * 如285900599@qq.com 格式化为2***9@qq.com
     * @param email email
     * @return 如果目标字符串不是email，则返回null
     */
    public static String formatEmail(String email){
        if(isEmail(email)){
            String regex = "(?<=.)[^@]+(?=.@)";
            Pattern patten = Pattern.compile(regex);
            // 进行匹配
            Matcher matcher = patten.matcher(email);
            return matcher.replaceAll("***");
        }else{
            return null;
        }
    }

    /**
     * 判断用户昵称格式是否正确
     *
     * @param username 源字符串
     * @return true表示是合法用户昵称
     */
    public static boolean isNickname(String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        }
        //http://phabricator.ushow.media/T4812 本地检验放开"_"和"."
        //http://phabricator.ushow.media/T6422 支持多语言字符,准确的校验在服务端做
        Pattern p = Pattern.compile("[^\n]{5,30}");
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static String formatNum(float num) {
        return doubleToString(num);
    }

    public static String formatNum(double num) {
        return doubleToString(num);
    }

    public static String formatNum(int num) {
        return doubleToString(num);
    }

    public static String formatNum(long num) {
        return doubleToString(num);
    }

    /**
     * 格式化一串String中的数字
     *
     * @param numString
     * @return
     */
    public static String formatNum(String numString) {
        StringBuilder resultStringBuilder = new StringBuilder();
        String[] splitStringArray = numString.split(" ");
        for (String split : splitStringArray) {
            if (StringUtils.isNumber(split)) {
                Integer num = Integer.valueOf(split);
                resultStringBuilder.append(formatNum(num));
            } else {
                resultStringBuilder.append(split);
            }
            resultStringBuilder.append(" ");
        }
        return String.valueOf(resultStringBuilder);
    }

    /**
     * 对大于99的数字显示99+处理
     */
    public static String formatNum99(int num) {
        if (num > 99) {
            return "99+";
        } else {
            return String.valueOf(num);
        }
    }

    /**
     * 有点耗时，不建议在feed中bindview中使用
     * https://phabricator.ushow.media/T10726
     * ME页面铭牌数字显示规则：
     * 1.超过3位数换算为k，超过7位数换算为m；
     * 2.显示到小数后1位。
     */
    public static String formatNumSev(int num) {
        return formatNumSev((long) num);
    }

    /**
     * 有点耗时，不建议在feed中bindview中使用
     */
    public static String formatNumSev(long num) {
        return longToString(num);
    }

    /*
     * 剪裁名字为20个字符以内
     */
    public static CharSequence getShortNickName(String nickName) {
        if (!isEmpty(nickName) && nickName.length() > 20) {
            return (new StringBuilder(nickName.substring(0, 16))).append("...").toString();
        } else {
            return nickName;
        }
    }

    public static boolean containSpace(String text) {
        if (text == null) {
            return false;
        }
        return text.contains(" ");
    }

    public static SpannableStringBuilder replaceWordToSpanTextWithColor(
            SpannableStringBuilder builder, String originStr, CharSequence replaceStr, int typeface, int size, @Nullable ClickableSpan span,
            boolean hasColor, @ColorRes int color) {
        if (TextUtils.isEmpty(builder) || TextUtils.isEmpty(originStr) || TextUtils.isEmpty(replaceStr)) {
            return builder;
        }
        String sourceStr = builder.toString();
        int originPosition = sourceStr.indexOf(originStr);
        if(originPosition < 0) {
            return builder;
        }
        int endPosition = originPosition + replaceStr.length();
        SpannableStringBuilder result = builder.replace(originPosition, originPosition + originStr.length(), replaceStr);

        result = StringUtils.setTypeFace(result, originPosition, endPosition, typeface);
        result = StringUtils.setClickSpan(result, originPosition, endPosition, span);
        result = StringUtils.setTextSize(result, originPosition, endPosition, size);
        if (hasColor) {
            result = StringUtils.setTextColor(result, originPosition, endPosition, color);
        }

        return result;
    }

    public static SpannableStringBuilder replaceWordToSpanTextWithColor(
            SpannableStringBuilder builder, String originStr, CharSequence replaceStr, int typeface, int size, @Nullable ClickableSpan span, @ColorRes int color) {
        return replaceWordToSpanTextWithColor(builder, originStr, replaceStr, typeface, size, span, true, color);
    }

    public static SpannableStringBuilder replaceWordToSpanTextWithColor(SpannableStringBuilder sourceBuilder, String originStr, int typeface, @Nullable ClickableSpan span) {
        String sourceStr = sourceBuilder.toString();
        int originPosition = sourceStr.indexOf(originStr);
        int endPosition = originPosition + originStr.length();
        SpannableStringBuilder builder = StringUtils.setTypeFace(sourceBuilder, originPosition, endPosition, typeface);
        builder = StringUtils.setClickSpan(builder, originPosition, endPosition, span);
        return builder;
    }

    public static SpannableStringBuilder replaceWordToSpanTextWithColor(String sourceStr, String originStr, int typeface, @Nullable ClickableSpan span) {
        return StringUtils.replaceWordToSpanTextWithColor(new SpannableStringBuilder(sourceStr), originStr, typeface, span);
    }

    public static String birthdayFormatDealYear(String time) {
        String yearBirthday = birthdayFormat("2000" + time);
        if (isEmpty(yearBirthday)) {
            return time;
        }
        //如果截取的字符串,长度不够截取,那么直接返回time,否则就越界了
        if (yearBirthday.length() < 5) {
            return time;
        }
        return yearBirthday.substring(5, yearBirthday.length());
    }

    /**
     * 将生日时间格式化 传入格式 yyyymmdd
     */
    public static String birthdayFormat(String time) {
        if (isEmpty(time) || time.contains(".")) {
            return time;
        }
        try {
            if (time.length() == 6) {
                String year = time.substring(0, 4);
                String month = time.substring(4, 6);
                if (month.startsWith("0")) {
                    month = month.replace("0", "");
                }
                String day = "";
                return year + "." + month + "." + day;
            } else if (time.length() == 7) {
                String year = time.substring(0, 4);
                String month = time.substring(4, 6);
                if (month.startsWith("0")) {
                    month = month.replace("0", "");
                }
                String day = time.substring(6, 7);

                return year + "." + month + "." + day;
            } else if (time.length() == 8) {
                String year = time.substring(0, 4);
                String month = time.substring(4, 6);
                if (month.startsWith("0")) {
                    month = month.replace("0", "");
                }
                String day = time.substring(6, 8);
                if (day.startsWith("0")) {
                    day = day.replace("0", "");
                }
                return year + "." + month + "." + day;
            }
        } catch (Exception e) {
        }
        return time;
    }

    /**
     * 时间格式化 传入格式 yyyymmdd
     */
    public static String workAndEducationTimeFormat(String time) {
        if (isEmpty(time)) {
            return "";
        }
        try {
            time = time.replace(".", "");
            if (time.length() <= 5) {
                String year = time.substring(0, 4);
                String month = time.substring(4, 5);
                return year + "." + month;
            } else {
                String year = time.substring(0, 4);
                String month = time.substring(4, 6);
                return year + "." + month;
            }

        } catch (Exception e) {
        }
        return time;
    }

    /**
     * 时间格式化 传入格式 yyyy.mm.dd
     */
    public static String getTimeParam(String time) {
        if (isEmpty(time)) {
            return "";
        }
        try {
            time = time.replace(".", "");
            if (time.length() <= 5) {
                String year = time.substring(0, 4);
                String month = "0" + time.substring(4, 5);
                return year + month;
            } else {
                String year = time.substring(0, 4);
                String month = time.substring(4, 6);
                return year + month;
            }

        } catch (Exception e) {
        }
        return time;
    }

    /**
     * 截取字符串，通过偏移量截取。如果有emoji表情的可能会乱码。
     * @param source
     * @param start
     * @param end
     * @return
     */
    public static String substringHasEmoJi(String source, int start, int end) {
        String result;
        try {
            // source.offsetByCodePoints(0, end), 当文本包含中文、emoji等特殊字符时，这个接口返回的值可能会大于end
            // 从而导致返回的字符串的长度会超过 (end - start) 长度，某些使用该接口在onTextChanged回调中纠正字符串长度的地方会导致死循环调用，从而stack overflow
            result = source.substring(source.offsetByCodePoints(0, start),
                    source.offsetByCodePoints(0, end));
        } catch (Exception e) {
            result = source;
        }
        return result;
    }

   //去除空格,换行
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 去除字符前后和中间多余的空格，字符中间只保留1个空格
     * @param str
     * @return
     */
    public static String trimBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s+");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest.trim();
    }


    public static String cutString(String source, float textSize,int lengthInPx, int defaultStringLength) {
        if (TextUtils.isEmpty(source) || textSize<=0 || lengthInPx <=0) {
            return source;
        }
        try {
            Paint paint = new Paint();
            paint.setTextSize(textSize);
            Rect rect = new Rect();
            paint.getTextBounds(source, 0, source.length(), rect);
            if (rect.width() > lengthInPx) {
                int measureNumbers = paint.breakText(source, true, lengthInPx, null);
                if (measureNumbers > source.length()) {
                    measureNumbers = source.length();
                }
                if (measureNumbers == source.length()) {
                    return source;
                } else {
                    return subStr(source, measureNumbers) + "...";
                }
            } else {
                return source;
            }
        } catch(Exception e) {
            if(source.length() > defaultStringLength){
                return subStr(source,defaultStringLength) + "...";
            }else{
                return source;
            }
        }
    }

    private static String subStr(String recourse, int length) {
        try {
            return recourse.substring(recourse.offsetByCodePoints(0, 0), recourse.offsetByCodePoints(0, length));
        } catch (Exception e) {
            return recourse;
        }
    }

    public static String nonEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }
}

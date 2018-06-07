package com.timmy.baselib.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;


import com.timmy.baselib.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TextUtil {
    /**
     * 将价格转换成大小不一的文本
     * @return
     */
    public static SpannableStringBuilder getPrice(Context context,float price){
        String[] strings = {"¥",  getTwoPointNum(price)};
        return getCustomSizeText(context,strings,new int[]{12,18});
    }
    public static SpannableStringBuilder getPrice(Context context,double price){
        String[] strings = {"¥",  getDoubleTwoPointNum(price)};
        return getCustomSizeText(context,strings,new int[]{12,18});
    }
    public static SpannableStringBuilder getPrice(Context context,String price){
        String[] strings = {"¥",  price};
        return getCustomSizeText(context,strings,new int[]{12,18});
    }


    /**
     * 获取一条指定颜色的文本字符串
     * @param contexts
     * @param text 需要转换颜色的字符串
     * @param color
     * @return
     */
    public static SpannableStringBuilder getCustomColorText(Context contexts,String text,@ColorRes int color){
        return getCustomColorText(contexts,new String[]{text},new int[]{color});
    }

    /**
     * 获取一条指定大小的文本字符串
     * @param context
     * @param text
     * @param size
     * @return
     */
    public static SpannableStringBuilder getCustomSizeText(Context context,String text,int size){
        return getCustomSizeText(context,new String[]{text},new int[]{size});
    }


    /**
     * 获取特定颜色的文本
     * @param contexts
     * @param texts 要设置颜色的文本数组与colors程对应关系 例：texts[0]的颜色为colors[0]
     * @param colors 要设置的文本的颜色数组，与texts程对应关系
     * @return
     */
    public static SpannableStringBuilder getCustomColorText(Context contexts,String[] texts,@ColorRes int[] colors){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(texts != null && texts.length > 0){
            int startIndex = 0;
            int colorId = ContextCompat.getColor(contexts, R.color.c_redeem);
//            int colorId = ContextCompat.getColor(contexts, Color.GREEN);
            for (int i = 0; i < texts.length; i++) {
                if(texts[i] == null){
                    texts[i] = "";
                }
                builder.append(texts[i]);
                if(i < colors.length) {
                    colorId = contexts.getResources().getColor(colors[i]);
                }
                ForegroundColorSpan span = new ForegroundColorSpan(colorId);
                builder.setSpan(span, startIndex, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIndex = builder.length();
            }
        }
        return builder;
    }


    /**
     * 获取特定大小的文本
     * @param context
     * @param texts 要设置大小的文本数组与colors程对应关系 例：texts[0]的大小为sizes[0]
     * @param sizes 要设置的文本的大小数组，与texts程对应关系
     * @return
     */
    public static SpannableStringBuilder getCustomSizeText(Context context,String[] texts,int[] sizes){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(texts != null && texts.length > 0){
            int startIndex = 0;
            int textSize = (int) DensityUtils.sp2px(context, 15);
            for (int i = 0; i < texts.length; i++) {
                if(texts[i] == null){
                    texts[i] = "";
                }
                builder.append(texts[i]);
                if(i < sizes.length) {
                    textSize = (int) DensityUtils.sp2px(context, sizes[i]);
                }
                builder.setSpan(new AbsoluteSizeSpan(textSize), startIndex, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIndex = builder.length();
            }
        }
        return builder;
    }


    /**
     * 判断字符是否是空格
     * @param text
     * @return
     */
    public static boolean isBlank(String text){
        if(text != null && text.length() > 0 && TextUtils.isEmpty(text.trim())){
            return true;
        }
        return false;
    }

    /**
     * 移动EditText的光标至最后
     * @param editText
     */
    public static void moveCursorToLast(EditText editText) {
        if (editText == null) return;
        int index = 0;
        if (editText.getText() != null) {
            index = editText.getText().toString().length();
        }
        editText.setSelection(index);
    }



    /**
     * 获得只有2位小数点的数据-->且直接返回String类型
     *
     * @return
     */
    public static String getTwoPointNum(float number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }

    /**
     * 获得只有2位小数点的数据-->且直接返回String类型
     *
     * @return
     */
    public static String getDoubleTwoPointNum(float number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }

    public static String getDoubleTwoPointNum(double number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }

    public static String getStringTwoPointNum(String number) {
        if (number.contains(".")) {
            int index = number.indexOf(".");
            if (number.length() > index + 3) {
                number = number.substring(0, index + 3);
            }
        }
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(new BigDecimal(number));
    }
}

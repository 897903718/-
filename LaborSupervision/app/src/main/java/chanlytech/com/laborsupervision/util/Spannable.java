package chanlytech.com.laborsupervision.util;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lyy on 2016/1/14.
 */
public class Spannable {
    /**
     *      * 关键字标红
     *      * @param text
     *      * @param keyword
     *      * @return
     *      
     */
//    public static SpannableStringBuilder matcherSearchTitle(String text, String[] keyword) {
//        SpannableStringBuilder s = new SpannableStringBuilder(text);
//        for (int i = 0; i < keyword.length; i++) {
//            Pattern p = Pattern.compile(keyword[i]);
//            Matcher m = p.matcher(s);
//            while (m.find()) {
//                int start = m.start();
//                int end = m.end();
//                s.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return s;
//    }

    /**
     * 单个字黑
     *      * 关键字标
     *      * @param text
     *      * @param keyword
     *      * @param color//标记颜色
     *      * @return
     *      
     */
    public static SpannableStringBuilder matcherSearchTitle(String text, String keyword,int color) {
        SpannableStringBuilder s = new SpannableStringBuilder(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }
}
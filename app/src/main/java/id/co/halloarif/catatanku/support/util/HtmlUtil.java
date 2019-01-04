package id.co.halloarif.catatanku.support.util;

import android.text.TextUtils;

public class HtmlUtil {
    public static String checkBr(String sHtml) {
        String[] split = sHtml.split("<br>");
        if (split.length > 1) {
            if (TextUtils.isEmpty(split[1])) {
                return sHtml.replace("<br>", "\n");
            } else {
                return sHtml.replace("<br>", "");
            }
        }
        return sHtml;
    }
}

package cn.moon.sell.common.util;

import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/15
 * @Desc
 */
public class List2StringUtil {

    /**
     * list 转成string
     * @param stringList
     * @return
     */
    public static String stringFormat(List<String> stringList){
        StringBuffer stringBuffer = new StringBuffer();
        if (!stringList.isEmpty()){
            for (int i = 0; i < stringList.size(); i++) {
                stringBuffer.append("\'")
                        .append(stringList.get(i))
                        .append("\'");
                if (i < (stringList.size()-1)){
                    stringBuffer.append(",");
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * list 自定义转换 string
     * @param stringList
     * @param symbol 符号
     * @return
     */
    public static String stringFormat(List<String> stringList,String symbol){
        StringBuffer stringBuffer = new StringBuffer();
        if (!stringList.isEmpty()){
            for (int i = 0; i < stringList.size(); i++) {
                stringBuffer.append(stringList.get(i));
                if (i < (stringList.size()-1)){
                    stringBuffer.append(symbol);
                }
            }
        }
        return stringBuffer.toString();
    }
}

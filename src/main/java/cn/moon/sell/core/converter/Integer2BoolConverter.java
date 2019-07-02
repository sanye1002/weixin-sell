package cn.moon.sell.core.converter;

/**
 * @Author zhaoxiang
 * @Date 2019/01/14
 * @Desc
 */
public class Integer2BoolConverter {
    public static Boolean converter(Integer integer) {
        if (integer == null){
            return false;
        }
        if (integer == 0){
            return true;
        }else {
            return false;
        }
    }
    public static Integer reverse(Boolean b) {
        if (b){
            return 0;
        }else {
            return 1;
        }
    }

}

package rhzhou;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        //String str = "ABC+R + DE";
        //String str = "ABC+R";
        //String str = "R";
        String str = "RABC";



        String org = "R";
        //String reg = "\\(^" + org + "\\W*\\)|\\(\\W*"+ org + "$\\)";
        String reg = "^R\\W+|\\W+R$|\\W+R\\W+|^R$";
        //String reg = "\\W*R\\W*";


        System.out.println(str.matches(reg));

        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str); // 获取 matcher 对象
        int count = 0;

        while (m.find()) {
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + m.start());
            System.out.println("end(): " + m.end());
            System.out.println("group(0): " + m.group(0));
            //System.out.println("group(1): "+m.group(1));
            //System.out.println("group(2): "+m.group(2));
            //System.out.println("group(2): "+m.group(3));
        }

        System.out.println("CAN'T FIND ANYMORE!");

    }
}

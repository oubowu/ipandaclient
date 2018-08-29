package com.oubowu.ipanda;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    class Person extends Object {
        private String name;

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }


    // 必须有main函数，参数通过args传入，结果请用print输出


    private static int[] mArray = new int[]{2, 3, 8, 1, 9, 33, 14};

    // 33 3 8 14 9 2 1

    public static void main(String args[]) {
        // int x=10;
        // int y=25;
        // int z=x+y;

        // System.out.println("Sum of x+y = " + z);

        int max = mArray[0];
        int min = mArray[0];
        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 1; i < mArray.length; i++) {
            if (max <= mArray[i]) {
                maxIndex = i;
                max = mArray[i];
            }
            if (min >= mArray[i]) {
                minIndex = i;
                min = mArray[i];
            }
        }

        System.out.println(max+";"+min);


        int firstItem = mArray[0];
        int lastItem = mArray[mArray.length - 1];

        mArray[0] = max;
        mArray[maxIndex] = firstItem;
        mArray[mArray.length - 1] = min;
        mArray[minIndex] = lastItem;

        for (int i = 0; i < mArray.length; i++) {
            System.out.print(mArray[i] + " ");
        }

    }

    @Test
    public void addition_isCorrect() throws Exception {

        //        String sss = "【江西聚梦】官方不会以任何理由要求您提供验证码！请勿向他人提供相关资料，这将威胁到您的帐号安全！欢迎您使用新的密保卡！您的密保卡序列号为：(.*)}使用序列号和密码即可登陆进行激活绑定帐号。您的密码为(.*),您的密码为(.*)。欢迎使用，如有疑问请与客服联系！回TD退订";
        //        Pattern pattern = Pattern.compile(sss,Pattern.DOTALL);
        //        Matcher matcher = pattern.matcher(
        //                "移动宽带业务工单：PT1012016092321210，需要您加急处理，请处理。该任务需要在：11点11分前完成发布");
        //        if (matcher.find()){
        //            int i = matcher.groupCount();
        //            for (int j = 0; j <= i; j++) {
        //                String group = matcher.group(j);
        //                System.out.println(group);
        //
        //            }
        //        }

        main(null);

        //        Person p1 = new Person();
        //        p1.name = "xxx";
        //
        //        Person p2 = new Person();
        //        p2.name = "xxx";
        //
        //        Person p3 = new Person();
        //        p3.name = "wohao";
        //
        //        Person p4 = new Person();
        //        p4.name = "wohao1111111111111111";
        //
        //        int p11 = p1.hashCode();
        //        int p22 = p2.hashCode();
        //        int p33 = p3.hashCode();
        //        int p44 = p4.hashCode();
        //
        //        System.out.println(p1.hashCode() + ";" + p2.hashCode() + ";" + p3.hashCode() + ";" + p4.hashCode());
        //
        //
        //        assertEquals(4, 2 + 2);
    }
}
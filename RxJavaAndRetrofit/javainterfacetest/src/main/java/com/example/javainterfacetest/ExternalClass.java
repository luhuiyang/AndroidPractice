package com.example.javainterfacetest;

/**
 * Created by ylh on 16-7-7.
 */
public class ExternalClass {
    public static void send(int a, int b, final Send sd) {
        calculate(a, b, new InterfaceTest2() {
            @Override
            public void onSucc(int a) {
                if (a == 2) {
                    sd.onS("a !=2, a = " + a);
                } else {
                    sd.onF("a !=2, a = " + a);
                }
            }

            @Override
            public void onFail(int b) {
                if (b == 2) {
                    sd.onS("b ==2, b = " + b);
                } else {
                    sd.onF("b !=2, b = " + b);
                }
            }
        });
    }

    private static void calculate(int a, int b, final InterfaceTest2 it2) {
        if (a + b == 2) {
            it2.onSucc(a + b);
        } else {
            it2.onFail(a - b);
        }
    }

    public interface Send {
        void onS(String s);
        void onF(String f);
    }


    public interface InterfaceTest2 {
        void onSucc(int a);
        void onFail(int b);
    }
}

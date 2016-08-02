package com.example.javainterfacetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_a)
    EditText editTextA;
    @BindView(R.id.et_b)
    EditText editTextB;

    private Context context;

    @OnClick(R.id.btn_check1)
    public void clickButton1() {
        interfaceA(Integer.valueOf(editTextA.getText().toString()), Integer.valueOf(editTextB.getText().toString()));
    }
    @OnClick(R.id.btn_check2)
    public void clickButton2() {
        interfaceB(Integer.valueOf(editTextA.getText().toString()), Integer.valueOf(editTextB.getText().toString()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
    }

    void interfaceA(final int a, final int b) {
        initview(a, b, new InterfaceTest() {
            @Override
            public void onSucc(int a) {
                Toast.makeText(context, a + " is a and " + "a + b = " + (a + b), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(int b) {
                Toast.makeText(context, b + " is b and " + "a + b = " + (a + b), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initview(int a, int b, final InterfaceTest inf) {
        if (a + b == 2) {
            inf.onSucc(a);
        } else {
            inf.onFail(b);
        }
    }

    public interface InterfaceTest {
        void onSucc(int a);
        void onFail(int b);
    }

    void interfaceB(int a, int b) {
        ExternalClass.send(a, b, new ExternalClass.Send() {
            @Override
            public void onS(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onF(String f) {
                Toast.makeText(context, f, Toast.LENGTH_SHORT).show();
            }
        });
    }


}

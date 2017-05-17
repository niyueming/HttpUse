package net.nym.httpuse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.nym.aphhttpreturn.Util;
import net.nym.aphhttpreturn.VO.BaseVO;
import net.nym.aphhttpreturn.VO.Test;
import net.nym.aphhttpreturn.VO.TestVO;
import net.nym.aphhttpreturn.callback.APHCallback;
import net.nym.aphhttpreturn.callback.APHSerializerCallback;
import net.nym.httplibrary.NHttpManager;
import net.nym.httplibrary.http.METHOD;
import net.nym.httplibrary.http.NGenericsSerializer;
import net.nym.httplibrary.http.NRequest;
import net.nym.okhttp3library.OkHttp3Manager;
import net.nym.okhttp3library.callback.JSONObjectSerializerCallback;
import net.nym.okhttp3library.callback.OkHttp3Callback;
import net.nym.okhttp3library.request.OkHttp3Request;
import net.nym.okhttp3library.serializer.FastJsonGenericsSerializer;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private NGenericsSerializer serializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        serializer = new FastJsonGenericsSerializer();
        request();
    }

    private void request() {

//        APHSerializerCallback<TestVO> callback = new APHSerializerCallback<TestVO>(new APHCallback<TestVO>() {
//
//
//            @Override
//            public void onError(Exception e, String error, int id) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onAPHCode(int errcode, String errmsg, int id) {
//
//            }
//
//            @Override
//            public void onResponse(TestVO response, int id) {
//                Test Test = response.getData().get(0);
//                System.out.println(Test.getORS_NAME());
//                System.out.println(Test.getRA_NAME());
//                System.out.println(Test.getSTA_DUEDATE());
//                System.out.println(Test.getSTAFF_NUMBER());
//            }
//        }){};



        NRequest<OkHttp3Callback, Response> request = new OkHttp3Request.Builder(this)
                .url("http://www.baidu.com")
                .method(METHOD.GET)
                .build();
//        request.enqueue(callback);

        request.enqueue(new JSONObjectSerializerCallback<String>(serializer) {

            @Override
            public void onError(Exception e, String error, int id) {
                Log.e("request", error);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("response", response);
                textView.setText(response);
            }

        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NHttpManager manager = OkHttp3Manager.getInstance(this);
        manager.cancelByTag(this);
    }

}

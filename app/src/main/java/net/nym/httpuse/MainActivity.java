package net.nym.httpuse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.nym.httplibrary.NHttpManager;
import net.nym.httplibrary.http.METHOD;
import net.nym.httplibrary.http.NGenericsSerializer;
import net.nym.httplibrary.http.NRequest;
import net.nym.okhttp3library.OkHttp3Manager;
import net.nym.okhttp3library.callback.JSONObjectSerializerCallback;
import net.nym.okhttp3library.callback.OkHttp3Callback;
import net.nym.okhttp3library.request.OkHttp3Request;
import net.nym.okhttp3library.serializer.FastJsonGenericsSerializer;

import okhttp3.Response;

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
        NRequest<OkHttp3Callback, Response> request = new OkHttp3Request.Builder(this)
                .url("http://www.baidu.com")
                .method(METHOD.GET)
                .build();
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

package com.example.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rc;
    TextView welcomtxt;
    EditText ed;
    ImageButton sendbtn;
    List<message> messageList;
    message_adapter messageAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rc = findViewById(R.id.rec);
        welcomtxt = findViewById(R.id.welcometxt);
        ed = findViewById(R.id.edit_txt);
        sendbtn = findViewById(R.id.sendbtn);
        messageList = new ArrayList<>();

        //setup recview
        messageAdapter = new message_adapter(messageList);
        rc.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rc.setLayoutManager(llm);



        sendbtn.setOnClickListener((v)->{

            String question = ed.getText().toString().trim();
            addTochat(question,message.SENT_BY_ME);
            ed.setText("");
            callApi(question);
            welcomtxt.setVisibility(View.GONE);
        });
    }

    void addTochat(String message, String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                messageList.add(new message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                rc.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        addTochat(response,message.SENT_BY_bot);
    }
    void callApi(String questio){
        //okHttp

        JSONObject jsonbody = new JSONObject();
        try {
            jsonbody.put("model","text-davinci-003");
            jsonbody.put("prompt",questio);
            jsonbody.put("max_tokens",4000);
            jsonbody.put("temperature",0);
            
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonbody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
               addResponse("Failed to load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    addResponse("Failed to load response due to "+response.body().toString());
                }
            }
        });

    }
}
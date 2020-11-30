package com.example.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddActivity extends AppCompatActivity {
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_list);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Agregar
                createTodo();
            }
        });
    }
    private void createTodo(){
        Todo todo = new Todo();

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);
        Call<Todo> call = api.createTodo(fields);

        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Todo postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Completed: " + postResponse.getCompleted() + "\n\n";

                textViewResult.append(content);
                Toast.makeText(getApplicationContext(),"Agregado",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}

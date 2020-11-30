package com.example.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_todo_list);

        String title = getIntent().getExtras().getString("title");
        EditText etTitle= findViewById(R.id.etTitle);

        String authorId = getIntent().getExtras().getString("authorId");
        EditText etAuthorId= findViewById(R.id.etAuthorId);

        String completed = getIntent().getExtras().getString("completed");
        CheckBox etCompleted= findViewById(R.id.etCompleted);

        if(completed.equals("true")){
            etCompleted.setChecked(true);
        }
        etTitle.setText(title);
        etAuthorId.setText(authorId);

        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Eliminar
                deleteTodo();
            }
        });

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Editar
                updateTodo();
            }
        });

    }



    private void updateTodo() {
        Todo todo = new Todo();

        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);
        Call<Todo> call = api.putTodo(5,todo);

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
                content += "Text: " + postResponse.getCompleted() + "\n\n";

                textViewResult.append(content);

                Toast.makeText(getApplicationContext(),"Modificado",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deleteTodo(){
        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);
        Call<Void> call = api.deleteTodo(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
                Toast.makeText(getApplicationContext(),"Eliminado",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }



}

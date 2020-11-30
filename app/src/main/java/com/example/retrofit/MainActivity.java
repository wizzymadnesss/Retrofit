package com.example.retrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private ListView lvTodos;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        lvTodos = (ListView)findViewById(R.id.lvTodos);
        loadToDos();

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), AddActivity.class);
                startActivityForResult(intent, 0);

            }
        });


    }

    protected void loadToDos(){
        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);

        Call<List<Todo>> listCall = api.getAllTodos();

        listCall.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                TodoList list = new TodoList(activity,response.body());
                lvTodos.setAdapter(list);
                lvTodos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent (view.getContext(), DetailActivity.class);
                        intent.putExtra("title", response.body().get(i).getTitle());
                        intent.putExtra("authorId", String.valueOf(response.body().get(i).getUserId()));
                        intent.putExtra("completed", response.body().get(i).getCompleted().toString());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something went wrong"
                        ,Toast.LENGTH_SHORT).show();
                Log.d("RetrofitError",t.toString());
            }
        });
    }




}

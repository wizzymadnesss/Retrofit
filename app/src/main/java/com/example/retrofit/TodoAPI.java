package com.example.retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoAPI {
    @GET("/todos")
    Call<List<Todo>> getAllTodos();

    @GET("/todos/{id}")
    Call<List<Todo>> getTodo(@Path("id") String id);

    @FormUrlEncoded
    @POST("todos")
    Call<Todo> createTodo(@FieldMap Map<String, String> fields);

    @PUT("todos/{id}")
    Call<Todo> putTodo(@Path("id") int id, @Body Todo todo);

    @PATCH("todos/{id}")
    Call<Todo> patchTodo(@Path("id") int id, @Body Todo todo);

    @DELETE("todos/{id}")
    Call<Void> deleteTodo(@Path("id") int id);

}

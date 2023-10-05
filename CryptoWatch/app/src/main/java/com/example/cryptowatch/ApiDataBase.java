package com.example.cryptowatch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiDataBase {
    @POST("Users/reg")
    Call<User> regUser(@Body User user);

    @POST("Users/auth")
    Call<User> authUser(@Body User user);

    @GET("Comments")
    Call<ArrayList<Message>> getComments();

    @GET("Users/{id}")
    Call<User> getUser(@Path("id") int userId);

    @POST("Comments/")
    Call<Message> postComments(@Body Message message);

    @POST("TrackingLists/")
    Call<TrackingList> postTrackingList(@Body TrackingList trackingList);

    @GET("TrackingLists")
    Call<ArrayList<TrackingList1>> getTrackingList();

    @GET("TrackingLists")
    Call<ArrayList<TrackingList2>> getTrackingList2();

    @DELETE("TrackingLists/{id}")
    Call<TrackingList1> deleteTrackingList(@Path("id") int trackingListId);

}

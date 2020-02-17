package com.example.easyfind.store;

import com.example.easyfind.models.BaseBusiness;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetDataService {

    @Headers({"Authorization: Bearer H7_n0PCOUjVZEMUpa1HCeC6LFPts3LXArEZbjpURqiis-40Cu3wQER1SAENdf4t08Td08hODQQMinYB4U97GpJ4vMptJHQdOz1ELeONy7KxyiQiBB1aLyHUJBczOXXYx"})
    @GET("businesses/search")
    Call<BaseBusiness> getBaseBusiness(@Query("location") String location,
                                       @Query("limit") int limit);
}

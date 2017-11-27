package com.company.shidoris.butler.apis;

import com.company.shidoris.butler.model.MapsDirection.DirectionRes;
import com.company.shidoris.butler.model.RoadsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Erik on 23/11/2017.
 */

public interface MapsInterface {
    @GET("snapToRoads?")
    Call<RoadsResponse> getRoads (@Query("path") String path, @Query("key") String key );

    @GET("json?")
    Call<DirectionRes> getDirectionPlace (@Query("origin") String origin, @Query("destination") String destination,@Query("waypoints") String waypoint ,@Query("key") String key );

    @GET("json?")
    Call<DirectionRes> getDirection (@Query("origin") String origin, @Query("destination") String destination,@Query("key") String key );
}

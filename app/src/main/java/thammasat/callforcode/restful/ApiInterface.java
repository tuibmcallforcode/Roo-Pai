package thammasat.callforcode.restful;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.DisasterMap;
import thammasat.callforcode.model.Prepareness;

/**
 * Created by Macintosh on 8/8/2017 AD.
 */

public interface ApiInterface {

    int limit = 20;

    @GET("app/pdcs")
    Call<List<DisasterMap>> getDisasterMap();

    @GET("app/analysed")
    Call<List<Disaster>> getDisasterEn();

    @GET("app/analysed")
    Call<List<Disaster>> getDisasterOthers(@Query("limit") int limit, @Query("page") int page, @Query("target") String target);

    @GET("/app/prepareness/{category}")
    Call<List<Prepareness>> getPrepareness(@Path("category") String category);
}


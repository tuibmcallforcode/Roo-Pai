package thammasat.callforcode.restful;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.DisasterMap;

/**
 * Created by Macintosh on 8/8/2017 AD.
 */

public interface ApiInterface {

    @GET("app/pdcs")
    Call<List<DisasterMap>> getDisasterMap();
    @GET("app/relief_raws")
    Call<List<Disaster>> getDisaster();
}


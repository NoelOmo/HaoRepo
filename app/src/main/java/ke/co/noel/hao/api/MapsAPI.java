package ke.co.noel.hao.api;

import ke.co.noel.hao.models.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HP on 15/10/2017.
 */

public interface MapsAPI {
    @GET("api/directions/json?key=AIzaSyC22GfkHu9FdgT9SwdCWMwKX1a4aohGifM")
    Call<Example> getDistanceDuration(
            @Query("units") String units,
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("mode") String mode);
}

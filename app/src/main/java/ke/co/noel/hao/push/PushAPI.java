package ke.co.noel.hao.push;

import ke.co.noel.hao.models.NotiApiModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by HP on 12/10/2017.
 */

public interface PushAPI {

    @FormUrlEncoded
    @POST("push.php")
    Call<NotiApiModel> pushToDevice(
            @Field("message")
                    String message,
            @Field("id")
                    String id);
}

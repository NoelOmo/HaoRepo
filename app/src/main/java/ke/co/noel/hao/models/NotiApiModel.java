package ke.co.noel.hao.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HP on 12/10/2017.
 */

public class NotiApiModel {

    @SerializedName("multicast_id")
    @Expose
    public Integer multicastId;
    @SerializedName("success")
    @Expose
    public Integer success;
    @SerializedName("failure")
    @Expose
    public Integer failure;
    @SerializedName("canonical_ids")
    @Expose
    public Integer canonicalIds;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
}

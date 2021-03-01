package com.sofi.giphy.model;

import com.google.gson.annotations.SerializedName;
import lombok.Value;
import java.util.List;

@Value
public class GifDataList {

    @SerializedName("data")
    private List<GifData> gifDataList;

}

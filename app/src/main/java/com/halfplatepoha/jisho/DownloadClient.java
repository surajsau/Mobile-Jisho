package com.halfplatepoha.jisho;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface DownloadClient {

    @GET("v0/b/links-b04aa.appspot.com/o/dictionary.db?alt=media&token=db9d4685-625a-4ff8-8c4a-1cdac2bc526a")
    @Streaming
    Call<ResponseBody> downloadDb();

    @GET("v0/b/links-b04aa.appspot.com/o/jsons.zip?alt=media&token=3be5e11a-90f6-4541-a417-3d2e4de0f8a3")
    @Streaming
    Call<ResponseBody> downloadKanjiStrokes();
}
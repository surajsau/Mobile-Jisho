package com.halfplatepoha.jisho.apimodel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by surjo on 21/04/17.
 */

public interface SearchApi {

    @GET("searchOnSearchClicked/words")
    Observable<SearchResponse> search(@Query("keyword") String searchWord);
}

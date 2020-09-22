package com.cool.taobaojava.model;

import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.model.domain.HomePagerContent;
import com.cool.taobaojava.model.domain.OnSellContent;
import com.cool.taobaojava.model.domain.SelectedCategory;
import com.cool.taobaojava.model.domain.SelectedContent;
import com.cool.taobaojava.model.domain.TicketParams;
import com.cool.taobaojava.model.domain.TicketResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {
    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<HomePagerContent> getHomePageContent(@Url String url);

    @POST("tpwd")
    Call<TicketResult> getTicket(@Body TicketParams ticketParams);

    @GET("recommend/categories")
    Call<SelectedCategory> getSelectedPageCategory();

    @GET()
    Call<SelectedContent> getSelectedPageContent(@Url String url);

    @GET()
    Call<OnSellContent> getOnSellPageContent(@Url String url);
}

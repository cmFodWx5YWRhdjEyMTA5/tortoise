package com.android.kreators.tortoise.net;

import com.android.kreators.tortoise.model.activity.ActivityItemGroup;
import com.android.kreators.tortoise.model.auth.AuthItemGroup;
import com.android.kreators.tortoise.model.balance.BalanceItemGroup;
import com.android.kreators.tortoise.model.bank.BankItemGroup;
import com.android.kreators.tortoise.model.myaccount.MyAccountGroup;
import com.android.kreators.tortoise.model.synapse.AccountItem;
import com.android.kreators.tortoise.model.config.SettingItem;
import com.android.kreators.tortoise.model.friend.FriendGroup;
import com.android.kreators.tortoise.model.history.HistoryGroup;
import com.android.kreators.tortoise.model.step.chart.UserStepGroup;
import com.android.kreators.tortoise.model.version.VersionGroup;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseService {

    //  TODO Static   ====================================================================================

    @GET("version_android.json")
    Call<VersionGroup> getVersion();

    @GET("server/setting.json")
    Call<SettingItem> getSetting();

    //  TODO Fitbit     ========================================================================================
    @GET("oauth2/authorize")
    Call<String> getFitbitToken(@Query("response_type") String response_type, @Query("client_id") String client_id,
                                @Query("redirect_uri") String redirect_uri, @Query("scope") String scope);

    //  TODO SynapsePay     ====================================================================================
    @GET("/v3/institutions/show")
    Call<ArrayList<AccountItem>> getBanks();

    //  TODO Auth    ========================================================================================

    @POST("server/v1/users/email")
    Call<AuthItemGroup> postSignUp(@Body HashMap<String, String> body);

    @POST("server/v1/auths/signin/email")
    Call<AuthItemGroup> postSignIn(@Body HashMap<String, String> body);

    @FormUrlEncoded
    @POST("server/v1/auths/signin/facebook")
    Call<AuthItemGroup> postFacebook(@Field("facebook_id") String facebook_id, @Field("email") String email,
                                     @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("profile") String profile);

    @FormUrlEncoded
    @POST("server/v1/auths/changeInclude")
    Call<AuthItemGroup> postFacebookInclude(@Field("seq") int seq, @Field("include") int include);

    @FormUrlEncoded
    @POST("server/v1/friends/step/facebook")
    Call<FriendGroup> postFriends(@Field("facebook_id") String facebook_id);

    //  TODO Steps     ====================================================================================

    @FormUrlEncoded
    @POST("server/v1/steps")
    Call<FriendGroup> postSteps(@Field("facebook_id") String facebook_id);

    //  TODO transaction    ================================================================================

    @GET("server/v1/transactions/users/{user_seq}")
    Call<ActivityItemGroup> getTransaction(@Path("user_seq") long user_seq, @Query("offset") int offset, @Query("limit") int limit);

    //  TODO History

    @GET("server/v1/transactions/users/{user_seq}")
    Call<ActivityItemGroup> getDailyHistory(@Path("user_seq") long user_seq,
                                            @Query("target_date") String target_date, @Query("end_date") String end_date, @Query("offset") int offset, @Query("limit") int limit);

    @GET("server/v1/balance/users/{user_seq}/history")
    Call<HistoryGroup> getTotalHistory(@Path("user_seq") long user_seq, @Query("start_date") String start_date, @Query("end_date") String end_date);

    //  TODO Steps Daily
    @GET("server/v1/steps/users/{user_seq}/daily")
    Call<UserStepGroup> getDailySteps(@Path("user_seq") long user_seq, @Query("start_date") String start_date, @Query("end_date") String end_date);

    //  TODO Steps Friend
    @POST("server/v1/friends/step/facebook")
    Call<FriendGroup> getFriendSteps(@Query("facebook_id") String facebook_id);

    //  TODO bank
    @GET("server/v1/banks/users/{user_seq}")
    Call<BankItemGroup> getBankItem(@Path("user_seq") long user_seq);

    //  TODO balance
    @GET("server/v1/balance/users/{user_seq}")
    Call<BalanceItemGroup> getBalance(@Path("user_seq") long user_seq);

    //  TODO MyAccount
    @GET("server/v1/balance/myaccount/users/{user_seq}")
    Call<MyAccountGroup> getMyAccount(@Path("user_seq") long user_seq, @Query("recent_status") String recent_status);

}


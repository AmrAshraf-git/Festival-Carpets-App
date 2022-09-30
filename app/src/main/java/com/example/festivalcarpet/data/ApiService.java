package com.example.festivalcarpet.data;

import com.example.festivalcarpet.constant.Credentials;
import com.example.festivalcarpet.model.Booking;
import com.example.festivalcarpet.model.BookingHistoryResponse;
import com.example.festivalcarpet.model.BookingResponse;
import com.example.festivalcarpet.model.ForgetPasswordResponse;
import com.example.festivalcarpet.model.NewUser;
import com.example.festivalcarpet.model.SignInResponse;
import com.example.festivalcarpet.model.SignUpResponse;
import com.example.festivalcarpet.model.User;
import com.example.festivalcarpet.model.VehicleResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @GET(Credentials.ALL_VEHICLES_URL)
    Call<VehicleResponse> getJsonModel();

    @GET(Credentials.SEARCHED_VEHICLES_URL)
    Call<VehicleResponse> getSearchedItems(@Query("serch") String model);


    @GET(Credentials.BOOKING_HISTORY+"{id}")
    Call<BookingHistoryResponse> getHistory(@Path("id") String userID);

    @GET(Credentials.AVAILABLE_RATE+"{id}")
    Call<BookingHistoryResponse> getAvailableRate(@Path("id") String userID);

    @GET(Credentials.FAVORITE_LIST+"{id}")
    Call<VehicleResponse> getFavorite(@Path("id") String userID);



    @POST(Credentials.REGISTER)
    Call<SignUpResponse> signUp(@Body NewUser newUser);

    @POST(Credentials.SIGN_IN)
    Call<SignInResponse> signIn(@Body User user);

    @POST(Credentials.BOOKING)
    Call<BookingResponse> RentRequest(@Header("authorization") String token, @Body Booking booking);

    @FormUrlEncoded
    @POST(Credentials.FORGET_PASSWORD)
    Call<ForgetPasswordResponse> forgetPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST(Credentials.UPDATE_VEHICLE_RATE+"{id}")
    Call<ForgetPasswordResponse> updateVehicleRate(@Path("id") String userID, @Field("VehicleRate") int vehicleRate,
                                                   @Field("VehicleID") String vehicleID);

    @FormUrlEncoded
    @POST(Credentials.UPDATE_COMPANY_RATE+"{id}")
    Call<ForgetPasswordResponse> updateCompanyRate(@Path("id") String userID, @Field("companyRate") int companyRate,
                                                   @Field("CompanyID") String companyID);

    @FormUrlEncoded
    @POST(Credentials.ADD_TO_FAVORITE_LIST+"{id}")
    Call<ForgetPasswordResponse> AddToFavorite(@Path("id") String userID, @Field("like") boolean like, @Field("VehicleID") String VehicleID);

/*
    @POST(Credentials.UPDATE_VEHICLE_RATE+"{id}")
    Call<ForgetPasswordResponse> updateVehicleRate(@Path("id") String userID, @Body UpdateRate updateRate);
*/
    /**
     * UrlEncoded
     @FormUrlEncoded
     @POST(Credentials.REGISTER)
     Call<ResponseBody> signUp(@Field("firstName") String firstName,
     @Field("lastName") String lastName,
     @Field("email") String email,
     @Field("password") String password,
     @Field("cpassword") String cpassword,
     @Field("phone") int phone
     );
     */

}

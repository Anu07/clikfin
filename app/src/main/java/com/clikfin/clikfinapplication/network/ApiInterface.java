package com.clikfin.clikfinapplication.network;

import com.clikfin.clikfinapplication.externalRequests.Request.ApplicationStatusRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.BankDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.DocumentReportRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.DocumentStatusRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.DocumentUrlGenerateRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.EmployeeDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.FCMTokenRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.Login;
import com.clikfin.clikfinapplication.externalRequests.Request.OTPSendRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.OTPVerifyRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.PersonalDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.ReferAndEarn;
import com.clikfin.clikfinapplication.externalRequests.Request.ReferenceDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.RegisterUser;
import com.clikfin.clikfinapplication.externalRequests.Request.UpwardLoanRequestModel;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.AuthenticatedUser;
import com.clikfin.clikfinapplication.externalRequests.Response.BankDetailsResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocName;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse.EnquireResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.LoanTapAddApplicationResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.upload.UploadDocResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.UpwardLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.docUpload.DocumentURLGenerationResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.documentRespone.DocumentReportResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.loanStatus.LoanStatusResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.updateStatus.StatusDocumentResponse;
import com.clikfin.clikfinapplication.externalRequests.loantap.AddApplication1;
import com.clikfin.clikfinapplication.externalRequests.loantap.LoanTapUploadDocumentRequest;
import com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest.EnquireRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
    @GET("/health")
    Call<String> healthCheck();

    @Headers("Content-type: application/json")
    @POST("/user/register")
    Call<AuthenticatedUser> registerUser(@Body RegisterUser registerUser);

    @Headers("Content-type: application/json")
    @POST("/user/login")
    Call<AuthenticatedUser> login(@Body Login login);


    @GET("/info/salaryMode")
    Call<String[]> getSalaryMode(@Header("x-clikfin-auth") String authToken);

    @GET("/info/addressProof")
    Call<String[]> getAddressProof(@Header("x-clikfin-auth") String authToken);

    @Headers("Content-type: application/json")
    @POST("/application/v2/personalInfo")
    Call<ApplyLoanResponse> postPersonalDetails(@Header("x-clikfin-auth") String authToken, @Body PersonalDetails personalDetails);

    @Headers("Content-type: application/json")
    @POST
    Call<ApplyLoanResponse> postEmployeeDetails(@Url String url, @Header("x-clikfin-auth") String authToken, @Body EmployeeDetails employeeDetails);

    @GET("/info/tenure")
    Call<String[]> getTenure(@Header("x-clikfin-auth") String authToken);

    @GET
    Call<ApplyLoanResponse> getLoanApplicationStatus(@Header("x-clikfin-auth") String authToken, @Url String url);


    @Headers("Content-type: application/json")
    @POST
    Call<ApplyLoanResponse> postReferencesDetails(@Url String url, @Header("x-clikfin-auth") String authToken, @Body ReferenceDetails referenceDetails);

    @Headers("Content-type: application/json")
    @POST("/application/referAndEarnInfo")
    Call<String> postReferAndEarnDetails(@Header("x-clikfin-auth") String authToken, @Body ReferAndEarn referAndEarn);

    @Headers("Content-type: application/json")
    @POST
    Call<BankDetailsResponse> postBankDetails(@Url String url, @Header("x-clikfin-auth") String authToken, @Body BankDetails bankDetails);

    @Multipart
    @PUT
    Call<UploadDocumentResponse> putDocumentUpload(@Part MultipartBody.Part body, @Url String url, @Header("x-clikfin-auth") String authToken, @Query("documentName") String documentName, @Query("password") String password);

    @GET("/info/bank")
    Call<String[]> getBank(@Header("x-clikfin-auth") String authToken);

    @GET("/info/state")
    Call<String[]> getState(@Header("x-clikfin-auth") String authToken);

    @GET
    Call<UploadDocName> getUploadDocument(@Url String url, @Header("x-clikfin-auth") String authToken);

    @POST
    Call<Void> postAllDocumentUploadStatus(@Url String url, @Header("x-clikfin-auth") String authToken);

    @GET("/info/loanPurpose")
    Call<String[]> getLoanPurpose(@Header("x-clikfin-auth") String authToken);

    @GET("/info/organizationType")
    Call<String[]> getOrganizationType(@Header("x-clikfin-auth") String authToken);

    @GET("/info/educationalQualification")
    Call<String[]> getEducationalQualification(@Header("x-clikfin-auth") String authToken);

    @POST("/user/login")
    Call<String> postFCMToken(@Body FCMTokenRequest fcmTokenRequest);

    @Headers("Content-type: application/json")
    @POST("/otp/send")
    Call<String> postLoginWithMobileNumber(@Body OTPSendRequest otpSendRequest);

    @Headers("Content-type: application/json")
    @POST("/otp/verify")
    Call<String> postVerifyOtp(@Body OTPVerifyRequest otpVerifyRequest);

   /* @POST("/user/login")
    Call<String> postResendOtp(@Query("mobileNumber") String mobileNumber);*/

    //Upwards
    @Headers("Content-type: application/json")
    @POST
    Call<UpwardLoanResponse> generateLoanApplicationUpwards(@Url String url,
                                                            @Header("Affiliated-User-Id") String AffiliateUserId,
                                                            @Header("Affiliated-User-Session-Token") String AffiliateSessionToken,
                                                            @Body UpwardLoanRequestModel upwardRequest);

    @Headers("Content-type: application/json")
    @POST
    Call<LoanStatusResponse> getAppStatus(@Url String url,
                                          @Header("Affiliated-User-Id") String AffiliateUserId,
                                          @Header("Affiliated-User-Session-Token") String AffiliateSessionToken,
                                          @Body ApplicationStatusRequest request);


    @Headers("Content-type: application/json")
    @POST
    Call<DocumentURLGenerationResponse> UploadDocumentURLGeneration(@Url String url,
                                                                    @Header("Affiliated-User-Id") String AffiliateUserId,
                                                                    @Header("Affiliated-User-Session-Token") String AffiliateSessionToken,
                                                                    @Body DocumentUrlGenerateRequest request);

    @Headers("Content-Type: image/jpeg")
    @PUT
    Call<Void> UploadDocumentUpward(@Url String url,
                                      @Body RequestBody file);

    @Headers("Content-Type: application/json")
    @POST
    Call<StatusDocumentResponse> UploadDocumentStatusUpward(@Url String url,
                                                            @Header("Affiliated-User-Id") String AffiliateUserId,
                                                            @Header("Affiliated-User-Session-Token") String AffiliateSessionToken,
                                                            @Body DocumentStatusRequest docRequest);

    @Headers("Content-Type: application/json")
    @POST
    Call<DocumentReportResponse> getDocumentReport(@Url String url,
                                                   @Header("Affiliated-User-Id") String AffiliateUserId,
                                                   @Header("Affiliated-User-Session-Token") String AffiliateSessionToken,
                                                   @Body DocumentReportRequest docRequest);


    //LoanTap
    @Headers("Content-type: application/json")
    @POST
    Call<LoanTapAddApplicationResponse> generateLoanTapApplication(@Url String url,
                                                                   @Header("X-API-AUTH") String APIkey,
                                                                   @Header("REQ-PRODUCT-ID") String productId,
                                                                   @Header("PARTNER-ID") String partnerId,
                                                                   @Body AddApplication1 loanTapRequest);

    @Headers("Content-type: application/json")
    @POST
    Call<EnquireResponse> enquireLoanTapDocuments(@Url String url,
                                                  @Header("X-API-AUTH") String APIkey,
                                                  @Header("REQ-PRODUCT-ID") String productId,
                                                  @Header("PARTNER-ID") String partnerId,
                                                  @Body EnquireRequest enquireRequest);


    @Headers("Content-type: application/json")
    @POST
    Call<UploadDocResponse> uploadLoanTapDocument(@Url String url,
                                                  @Header("X-API-AUTH") String APIkey,
                                                  @Header("REQ-PRODUCT-ID") String productId,
                                                  @Header("PARTNER-ID") String partnerId,
                                                  @Body LoanTapUploadDocumentRequest loanTapUploadRequest);


}

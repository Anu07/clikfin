package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.externalRequests.Request.ApplicationStatusRequest;
import com.clikfin.clikfinapplication.externalRequests.Response.LoanStatusResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.UpwardLoanResponse;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsAuthToken;
import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsUserID;
import static com.clikfin.clikfinapplication.network.APIClient.BASE_UPWARD_WEB_URL;

public class UpwardTransitionFragment extends Fragment {
    WebView webView;
    SharedPreferences sharedPreferences;
    UpwardLoanResponse response;
    String dateTime;
    Button checkBttn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transition_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.transitionView);
        checkBttn = view.findViewById(R.id.checkBttn);
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String upwardResponse = sharedPreferences.getString(getString(R.string.upwardResponse), "");
        response = new Gson().fromJson(upwardResponse, UpwardLoanResponse.class);
        dateTime = getCurrentDateTime();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(BASE_UPWARD_WEB_URL + "affiliate/?customer_id=" + response.getData().getLoanData().getCustomerId() + "&affiliate_user_id="
                + upwardsUserID
                + "&hash_generation_datetime=" + dateTime +
                "&affiliate_hash=" + getMd5(upwardsAuthToken + dateTime));
        checkBttn.setOnClickListener(v -> checkLoanStatus(createApplicationStatusRequest()));
    }

    private ApplicationStatusRequest createApplicationStatusRequest() {
        return new ApplicationStatusRequest(1,response.getData().getLoanData().getCustomerId(),response.getData().getLoanData().getLoanId());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


    /**
     * get loan application status
     *
     * @param applicationStatusRequest
     */
    private void checkLoanStatus(ApplicationStatusRequest applicationStatusRequest) {
        String url = APIClient.BASE_UPWARD_URL+"/loan/status/";
        Call<LoanStatusResponse> call = APIClient.getClient(APIClient.type.JSON)
                .getAppStatus(url,upwardsUserID,upwardsAuthToken,applicationStatusRequest);
        call.enqueue(new Callback<LoanStatusResponse>() {
            @Override
            public void onResponse(Call<LoanStatusResponse> call, Response<LoanStatusResponse> response) {
                //super.onResponse(call, response);
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoanStatusResponse> call, Throwable t) {
                //super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(getActivity(), getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * to get MD5 value for hash
     *
     * @param input
     * @return
     */
    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

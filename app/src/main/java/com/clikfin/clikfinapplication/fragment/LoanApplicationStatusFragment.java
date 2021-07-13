package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.externalRequests.Request.ApplicationStatusRequest;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.UpwardLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.loanStatus.LoanStatusResponse;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsAuthToken;
import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsUserID;

public class LoanApplicationStatusFragment extends Fragment {
    TextView tvLoanApplicationStatusDescription, tvLoanStatusHeader;
    ImageView imgApplicationStatus;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    static FragmentActivity activity;
    Context context;
    String userName;
    private SharedPreferences sharedPreferences;
    UpwardLoanResponse response;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentActivity) {
            activity = (FragmentActivity) context;
        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_application_status, container, false);
        tvLoanApplicationStatusDescription = view.findViewById(R.id.tvLoanApplicationStatusDescription);
        tvLoanStatusHeader = view.findViewById(R.id.tvLoanStatusHeader);
        imgApplicationStatus = view.findViewById(R.id.imgApplicationStatus);
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.loan_application_status));
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String upwardResponse = sharedPreferences.getString(getString(R.string.upwardResponse), "");
        response = new Gson().fromJson(upwardResponse, UpwardLoanResponse.class);
//        if (sharedPreferences.getString(getString(R.string.loan_source), "").equals(getString(R.string.upward))) {
//            checkLoanStatus(createApplicationStatusRequest());
//        }else{
            fragmentNavigation();
//        }
        return view;
    }

    private void fragmentNavigation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String loanApplicationStatus = sharedPreferences.getString(getString(R.string.loan_application_status), "");
        userName = sharedPreferences.getString(getString(R.string.user_name), "");

        if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.under_review))) {
            tvLoanStatusHeader.setText(getString(R.string.congratulations));
            tvLoanApplicationStatusDescription.setText("Hi " + userName);
            tvLoanApplicationStatusDescription.setText(" " + tvLoanApplicationStatusDescription.getText().toString() + " " + getString(R.string.under_review_msg));
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.rejected))) {
            tvLoanStatusHeader.setText(getString(R.string.thank_you));
//            tvLoanApplicationStatusDescription.setText("Hi "+userName);
            tvLoanApplicationStatusDescription.setText("Sorry " + tvLoanApplicationStatusDescription.getText().toString() + " " + getString(R.string.rejected_msg));
            imgApplicationStatus.setImageResource(R.drawable.application_rejected);
            tvLoanApplicationStatusDescription.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursement_pending))) {
            tvLoanStatusHeader.setText(getString(R.string.congratulations));
            tvLoanApplicationStatusDescription.setText("Hi " + userName);
            tvLoanApplicationStatusDescription.setText(" " + tvLoanApplicationStatusDescription.getText().toString() + " " + getString(R.string.disbursed_pending_msg));
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursed))) {
            tvLoanStatusHeader.setText(getString(R.string.congratulations));
            tvLoanApplicationStatusDescription.setText("Hi " + userName);
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
            tvLoanApplicationStatusDescription.setText(" " + tvLoanApplicationStatusDescription.getText().toString() + " " + getString(R.string.disbursed_msg));
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.approved))) {
            tvLoanStatusHeader.setText(getString(R.string.congratulations));
            tvLoanApplicationStatusDescription.setText("Hi " + userName);
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
            tvLoanApplicationStatusDescription.setText(" " + tvLoanApplicationStatusDescription.getText().toString() + " " + getString(R.string.approved_msg));
        }
    }
    /**
     * get loan application status
     *
     * @param applicationStatusRequest
     */
    private void checkLoanStatus(ApplicationStatusRequest applicationStatusRequest) {
        String url = APIClient.BASE_UPWARD_PROD_URL + "loan/status/";
        Call<LoanStatusResponse> call = APIClient.getClient(APIClient.type.JSON)
                .getAppStatus(url, upwardsUserID, upwardsAuthToken, applicationStatusRequest);
        call.enqueue(new Callback<LoanStatusResponse>() {
            @Override
            public void onResponse(Call<LoanStatusResponse> call, Response<LoanStatusResponse> response) {
                //super.onResponse(call, response);
                if (response.code() == 200) {
                    SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(getActivity());
                    if(response.body().getData().getLoanData()!=null && response.body().getData().getLoanStatus().equalsIgnoreCase("approved")){
                        editor.putString(getString(R.string.loan_application_status), getString(R.string.approved)).apply();
                    }else if(response.body().getData().getLoanStatus().equalsIgnoreCase("pre-rejected")){
                        editor.putString(getString(R.string.loan_application_status), getString(R.string.pre_rejection)).apply();
                    }else{
                        editor.putString(getString(R.string.loan_application_status), getString(R.string.under_review)).apply();
                    }
                    fragmentNavigation();
                }else{
                    Toast.makeText(getActivity(),"Please check back later", Toast.LENGTH_LONG).show();
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

    private ApplicationStatusRequest createApplicationStatusRequest() {
        return new ApplicationStatusRequest(2, response.getData().getLoanData().getCustomerId(), response.getData().getLoanData().getLoanId());
    }
}

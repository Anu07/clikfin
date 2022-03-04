
package com.clikfin.clikfinapplication.fragment;

import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsAuthToken;
import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsUserID;
import static com.clikfin.clikfinapplication.network.APIClient.LOANTAPPARTNERID;
import static com.clikfin.clikfinapplication.network.APIClient.LOANTAPPRODUCTID;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.externalRequests.Request.BankDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.PersonalDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.UpwardLoanRequestModel;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.BankDetailsResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentResponse;
import com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest.AddApplication;
import com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest.AddApplicationRequest;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication.AddApplicationResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.UpwardLoanResponse;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.BaaSEncryptDecrypt;
import com.clikfin.clikfinapplication.util.Common;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

@SuppressWarnings("All")
public class BankDetailsFragment extends Fragment {
    private static final String TAG = BankDetailsFragment.class.getName();
    private Spinner spinnerBankName;
    Button btnBankInfoSubmit;
    String[] bankName = null;
    static FragmentActivity activity;
    Context context;
    ProgressBar progress;
    SharedPreferences sharedPreferences;
    private EditText edAccountHolderName, edAccountNo, edReEnterAccountNo, edIFSCCode, edBranchName;
    private TextView accountHolderName_error, accountNumber_error,bankBranchName_error, reEnterAccountNumber_error, IFSCCode_error, bankName_error;
    private ProgressDialog pDialog;
    String[] mCityArray;

    public BankDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentActivity) {
            activity = (FragmentActivity) context;
        }
    }


    public static BankDetailsFragment newInstance(String param1, String param2) {
        BankDetailsFragment fragment = new BankDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_details, container, false);
        edAccountHolderName = view.findViewById(R.id.edAccountHolderName);
        edAccountNo = view.findViewById(R.id.edAccountNo);
        edReEnterAccountNo = view.findViewById(R.id.edReEnterAccountNo);
        edIFSCCode = view.findViewById(R.id.edIFSCCOde);
        edBranchName = view.findViewById(R.id.edBranchName);
        spinnerBankName = view.findViewById(R.id.spinnerBankName);
        btnBankInfoSubmit = view.findViewById(R.id.btnBankInfoSubmit);
        accountHolderName_error = view.findViewById(R.id.accountHolderName_error);
        accountNumber_error = view.findViewById(R.id.accountNumber_error);
        bankBranchName_error = view.findViewById(R.id.bankBranchName_error);
        reEnterAccountNumber_error = view.findViewById(R.id.reEnterAccountNumber_error);
        IFSCCode_error = view.findViewById(R.id.IFSCCode_error);
        bankName_error = view.findViewById(R.id.bankName_error);
        sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        edAccountHolderName.setFilters(new InputFilter[]{Common.letterFilter});

        mCityArray = getResources().getStringArray(R.array.city_array);


        if (Common.isNetworkConnected(context)) {
            getBankNames();
        } else {
            Common.networkDisconnectionDialog(context);
        }

        btnBankInfoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidInput()) {
//                    sharedPreferences.edit().putString(getString(R.string.loan_source), "LOANTAP").apply();
                    if (Common.isNetworkConnected(context)) {
                        if (sharedPreferences.getString(getString(R.string.loan_source), "").equalsIgnoreCase(getString(R.string.upward))) {
                            generateLoanApplication(completeUpwardApplication(bankDetailsData()));
                            AddApplicationRequest requestData = createLoanApplication(bankDetailsData());
                            if(checkCityList(requestData)){
                                generateLoanTapApplication(requestData);
                            }
                        } /*else if (sharedPreferences.getString(getString(R.string.loan_source), "").equalsIgnoreCase(getString(R.string.loantap))) {
//todo                            generateLoanTapApplication(createLoanApplication(bankDetailsData()));
                            generateLoanApplication(completeUpwardApplication(bankDetailsData()));

                        }*/
                        postBankDetails(bankDetailsData());
                    } else {
                        Common.networkDisconnectionDialog(context);
                    }
                }
            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_bank_details));
        return view;
    }

    private boolean checkCityList(AddApplicationRequest requestData) {
        for (int i = 0; i < mCityArray.length; i++) {
            if(mCityArray[i].equalsIgnoreCase(requestData.getAddApplication1().getOfficeCity()) || mCityArray[i].equalsIgnoreCase(requestData.getAddApplication1().getHomeCity())){
                return true;
            }
        }
        return false;
    }


    /**
     * create application LoanTap
     *
     * @param completeUpwardApplication
     * @return
     * "permanent_addr_line1": "permanent_addr_line1",
     *                 "permanent_zipcode": "permanent_zipcode",
     *                 "permanent_city": "permanent_city",
     *                 "office_addr_line1": "office_addr_line1",
     *                 "office_city": "office_city",
     *                 "office_zipcode": "office_zipcode",
     *                 "official_email": "official_email",
     *                 "ecs_bank_acc_no": "ecs_bank_acc_no",
     *                 "ecs_bank_branch": "ecs_bank_branch",
     *                 "ecs_bank": "ecs_bank",
     *                 "ecs_cust_name": "ecs_cust_name",
     *                 "ecs_ifsc_code": "ecs_ifsc_code",
     *                 "ecs_bank_city": "ecs_bank_city",
     *                 "ecs_account_type": "ecs_account_type"
     */
    private AddApplicationRequest createLoanApplication(BankDetails bankDetailsData) {
        String employeeDetailsString = sharedPreferences.getString(getString(R.string.employment_details_loantap), "");
        AddApplication loanTapApplication = new Gson().fromJson(employeeDetailsString, AddApplication.class);
        String personalData = sharedPreferences.getString(getString(R.string.personal_details), "");
        PersonalDetails pDetails = new Gson().fromJson(personalData, PersonalDetails.class);
        loanTapApplication.setSalaryBankName(bankDetailsData.getBankName());
        loanTapApplication.setSalaryAccountNo(bankDetailsData.getAccountNumber());
        loanTapApplication.setMaritalStatus(loanTapApplication.getMaritalStatus().toLowerCase());
        loanTapApplication.setEcsBank(bankDetailsData.getBankName());
        loanTapApplication.setEcsBankAccNo(bankDetailsData.getAccountNumber());
        loanTapApplication.setEcsBankBranch(bankDetailsData.getAccountNumber());
        loanTapApplication.setEcsCustName(bankDetailsData.getAccountHolderName());
        loanTapApplication.setEcsIfscCode(bankDetailsData.getIFSCCode());
        loanTapApplication.setEcsBankCity(pDetails.getCurrentAddressCity());
        loanTapApplication.setEcsAccountType("salary account");
        loanTapApplication.setPermanentCity(loanTapApplication.getHomeCity());
        loanTapApplication.setPermanentZipcode(loanTapApplication.getHomeZipcode());
        loanTapApplication.setPermanentAddrLine1(pDetails.getPermanentAddressLine1());
        loanTapApplication.setOfficeAddrLine1(pDetails.getCurrentAddressLine1());
        loanTapApplication.setOfficeCity(pDetails.getCurrentAddressCity());
        loanTapApplication.setOfficeZipcode(pDetails.getCurrentAddressPinCode());
        Log.e(TAG, "createLoanApplication: " + new Gson().toJson(loanTapApplication));
        AddApplicationRequest mainRequest  = new AddApplicationRequest();
        mainRequest.setAddApplication1(loanTapApplication);
        return mainRequest;
    }

    private UpwardLoanRequestModel completeUpwardApplication(BankDetails bankDetailsData) {
        UpwardLoanRequestModel completeDetails = new UpwardLoanRequestModel();
        String personalDetailsString = sharedPreferences.getString(getString(R.string.personal_details), "");
        String employeeDetailsString = sharedPreferences.getString(getString(R.string.employment_details), "");
        PersonalDetails personalDetails = new Gson().fromJson(personalDetailsString, PersonalDetails.class);
        UpwardLoanRequestModel employmentDetails = new Gson().fromJson(employeeDetailsString, UpwardLoanRequestModel.class);
        employmentDetails.setFirstName(personalDetails.getFirstName());
        employmentDetails.setLastName(personalDetails.getLastName());
        employmentDetails.setFatherFirstName(personalDetails.getFatherName());
        employmentDetails.setFatherLastName(personalDetails.getFatherName());
        employmentDetails.setMotherFirstName(personalDetails.getMotherName());
        employmentDetails.setMotherLastName(personalDetails.getMotherName());
        employmentDetails.setAadhaar(Long.parseLong(personalDetails.getAadhaarNumber()));
        employmentDetails.setPan(personalDetails.getPanNumber());
        employmentDetails.setSocialEmailId(personalDetails.getEmail());
        employmentDetails.setAffiliateLoanIdentifier(Integer.parseInt(personalDetails.getAadhaarNumber().substring(7, 12)));       //todo
//        employmentDetails.setAffiliateLoanIdentifier(sharedPreferences.getString(getString(R.string.loan_application_id),""));
        employmentDetails.setCompany(employmentDetails.getCompany());
        employmentDetails.setCurrentAddressLine1(employmentDetails.getCurrentAddressLine1());
        employmentDetails.setCurrentAddressLine2(employmentDetails.getCurrentAddressLine2());
        employmentDetails.setCurrentCompanyAddressLine1(employmentDetails.getCurrentAddressLine1());
        employmentDetails.setCurrentCompanyAddressLine2(employmentDetails.getCurrentAddressLine2());
        employmentDetails.setCurrentCity(employmentDetails.getCurrentCity());
        employmentDetails.setProfessionTypeId(28);
        employmentDetails.setCurrentCompanyCity(employmentDetails.getCurrentCompanyCity());
        employmentDetails.setCurrentCompanyPincode(employmentDetails.getCurrentCompanyPincode());
        employmentDetails.setCurrentPincode(employmentDetails.getCurrentPincode());
        employmentDetails.setDob(personalDetails.getFormattedDate());
        employmentDetails.setGender(personalDetails.getGender());
        employmentDetails.setHighestEducationInstituteName(personalDetails.getEducationalQualification());
        employmentDetails.setBankAccountHolderFullName(bankDetailsData.getAccountHolderName());
        employmentDetails.setBankAccountNumber(bankDetailsData.getAccountNumber());
        employmentDetails.setBankBranch(bankDetailsData.getBankName());
        employmentDetails.setBankName(bankDetailsData.getBankName());
        employmentDetails.setIfsc(bankDetailsData.getIFSCCode());
        employmentDetails.setBankCity(employmentDetails.getCurrentCompanyCity());
        employmentDetails.setBankDistrict(employmentDetails.getCurrentCompanyState());
        employmentDetails.setBankCode(employmentDetails.getBankName());
        employmentDetails.setCurrentResidenceTypeId(4);
        employmentDetails.setCurrentState(personalDetails.getCurrentAddressState());
        employmentDetails.setBankState(employmentDetails.getCurrentCompanyState());
        employmentDetails.setPartialData(false);
        employmentDetails.setCurrentResidenceStayCategoryId(4);
        return employmentDetails;
    }

    private void getBankNames() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getBank(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                //super.onResponse(call, response);
                if (response.body() != null) {
                    bankName = new String[response.body().length + 1];
                    bankName[0] = "Select Bank Name";
                    for (int i = 0; i < response.body().length; i++) {
                        bankName[i + 1] = response.body()[i];
                    }
                    ArrayAdapter<String> namePrefixAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, bankName);
                    namePrefixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    spinnerBankName.setAdapter(namePrefixAdapter);
                    btnBankInfoSubmit.setBackground(context.getDrawable(R.drawable.custom_rect));
                    btnBankInfoSubmit.setTextColor(Color.parseColor("#ffffff"));
                    btnBankInfoSubmit.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                //super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postBankDetails(BankDetails bankDetails) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        if (!loanApplicationId.isEmpty()) {
            String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/bankDetails";
            Call<BankDetailsResponse> call = APIClient.getClient(APIClient.type.JSON).postBankDetails(url, authToken, bankDetails);
            call.enqueue(new APICallbackInterface<BankDetailsResponse>(context) {
                @Override
                public void onResponse(Call<BankDetailsResponse> call, Response<BankDetailsResponse> response) {
                    super.onResponse(call, response);
                    switch (response.code()) {
                        case 200:
                            if (response.body() != null) {
                                BankDetailsResponse bankDeatilRespons = response.body();
                                SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                                UploadDocumentResponse uploadDocumentResponse = response.body().getDocumentStatus();

                                editor.putBoolean(getString(R.string.isUpload_upload_pan_card), uploadDocumentResponse.getPAN_CARD().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_current_residency_proof), uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_pay_slip_1), uploadDocumentResponse.getPAY_SLIP_1().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_pay_slip_2), uploadDocumentResponse.getPAY_SLIP_2().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_pay_slip_3), uploadDocumentResponse.getPAY_SLIP_3().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_bank_statement_1), uploadDocumentResponse.getBANK_STATEMENT_1().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_bank_statement_2), uploadDocumentResponse.getBANK_STATEMENT_2().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_bank_statement_3), uploadDocumentResponse.getBANK_STATEMENT_3().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_aadhar_front), uploadDocumentResponse.getAADHAAR_CARD_FRONT().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_aadhar_back), uploadDocumentResponse.getAADHAAR_CARD_BACK().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_photo), uploadDocumentResponse.getPHOTO().isUploaded());
                                editor.putBoolean(getString(R.string.isUpload_company_id), uploadDocumentResponse.getCOMPANY_ID().isUploaded());


                                editor.putBoolean(getString(R.string.isMandatory_upload_pan_card), uploadDocumentResponse.getPAN_CARD().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_current_residency_proof), uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_pay_slip_1), uploadDocumentResponse.getPAY_SLIP_1().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_pay_slip_2), uploadDocumentResponse.getPAY_SLIP_2().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_pay_slip_3), uploadDocumentResponse.getPAY_SLIP_3().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_bank_statement_1), uploadDocumentResponse.getBANK_STATEMENT_1().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_bank_statement_2), uploadDocumentResponse.getBANK_STATEMENT_2().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_bank_statement_3), uploadDocumentResponse.getBANK_STATEMENT_3().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_aadhar_front), uploadDocumentResponse.getAADHAAR_CARD_FRONT().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_aadhar_back), uploadDocumentResponse.getAADHAAR_CARD_BACK().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_photo), uploadDocumentResponse.getPHOTO().isMandatory());
                                editor.putBoolean(getString(R.string.isMandatory_company_id), uploadDocumentResponse.getCOMPANY_ID().isMandatory());

                                editor.putString(getString(R.string.loan_application_id), bankDeatilRespons.getApplicationId());
                                editor.putString(getString(R.string.loan_application_status), bankDeatilRespons.getStatus());
                                editor.apply();
                                if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                    replaceFragment(new DocumentUploadFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                }
                            }
                            break;
                        case 400:
                            Converter<ResponseBody, BankDetails> bankDetailsConverter = APIClient.getRetrofit().responseBodyConverter(BankDetails.class, new Annotation[0]);
                            try {
                                setServerErrorMsg(bankDetailsConverter.convert(response.errorBody()));
                            } catch (Exception e) {
                                Common.logExceptionToCrashlaytics(e);
                            }
                            break;
                        case 403:
                        case 401:
                            Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentManager.popBackStack();
                            fragmentTransaction.replace(R.id.apply_loan_frame, new LoginFragment());
                            fragmentTransaction.commit();
                            break;
                        case 409:
                            try {
                                Converter<ResponseBody, ApplyLoanResponse> PersonalDetailsResponsConverter = APIClient.getRetrofit().responseBodyConverter(ApplyLoanResponse.class, new Annotation[0]);
                                ApplyLoanResponse bankDeatilRespons = PersonalDetailsResponsConverter.convert(response.errorBody());
                                SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                                editor.putString(getString(R.string.loan_application_id), bankDeatilRespons.getApplicationId());
                                editor.putString(getString(R.string.loan_application_status), bankDeatilRespons.getStatus());
                                editor.commit();
                                if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                    replaceFragment(new DocumentUploadFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                    replaceFragment(new LoanApplicationStatusFragment());
                                }

                            } catch (Exception e) {
                                Common.logExceptionToCrashlaytics(e);
                            }

                            break;
                        case 500:
                            Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                            break;
                    }

                }

                @Override
                public void onFailure(Call<BankDetailsResponse> call, Throwable t) {
                    super.onFailure(call, t);
                    Common.logAPIFailureToCrashlyatics(t);
                    Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private BankDetails bankDetailsData() {
        BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountHolderName(edAccountHolderName.getText().toString());
        bankDetails.setAccountNumber(edAccountNo.getText().toString());
        bankDetails.setReEnterAccountNumber(edReEnterAccountNo.getText().toString());
        bankDetails.setIFSCCode(edIFSCCode.getText().toString());
        bankDetails.setBankName(spinnerBankName.getSelectedItem().toString());
        return bankDetails;
    }

    private void setServerErrorMsg(BankDetails bankDetails) {
        if (bankDetails.getIFSCCode() != null) {
            Common.setError(IFSCCode_error, "IFSC Code " + bankDetails.getIFSCCode(), context);
            edIFSCCode.requestFocus();
        } else {
            Common.setError(IFSCCode_error, "", context);
        }
        if (bankDetails.getAccountNumber() != null) {
            Common.setError(accountNumber_error, "Account Number " + bankDetails.getAccountNumber(), context);
            edAccountNo.requestFocus();
        } else {
            Common.setError(accountNumber_error, "", context);
        }
        if (bankDetails.getBankName() != null) {
            Common.setError(bankName_error, "Bank Name " + bankDetails.getBankName(), context);
            spinnerBankName.setFocusable(true);
            spinnerBankName.requestFocus();
        } else {
            Common.setError(bankName_error, "", context);
        }
        if (bankDetails.getAccountHolderName() != null) {
            Common.setError(accountHolderName_error, "Account Holder Name " + bankDetails.getAccountHolderName(), context);
            edAccountHolderName.requestFocus();
        } else {
            Common.setError(accountHolderName_error, "", context);
        }


    }

    private boolean checkValidInput() {
        boolean returnValue = true;
        if (edAccountHolderName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(accountHolderName_error, "Account Holder Name " + getString(R.string.input_field_empty), context);
            edAccountHolderName.requestFocus();
            return false;
        } else {
            Common.setError(accountHolderName_error, "", context);
            returnValue = true;
        }
        if (spinnerBankName.getSelectedItem().toString().trim().equalsIgnoreCase("Select Bank Name")) {
            Common.setError(bankName_error, getString(R.string.select_bank_name), context);
            return false;
        } else {
            Common.setError(bankName_error, "", context);
            returnValue = true;
        }
        if (edAccountNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(accountNumber_error, "Account Number " + getString(R.string.input_field_empty), context);
            edAccountNo.requestFocus();
            return false;
        } else {
            Common.setError(accountNumber_error, "", context);
            returnValue = true;
        }
        if (edBranchName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(bankBranchName_error, "Account Number " + getString(R.string.input_field_empty), context);
            edBranchName.requestFocus();
            return false;
        } else {
            Common.setError(accountNumber_error, "", context);
            returnValue = true;
        }

        if (edReEnterAccountNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(reEnterAccountNumber_error, "Re-Enter Account Number " + getString(R.string.input_field_empty), context);
            edReEnterAccountNo.requestFocus();
            return false;
        } else {
            Common.setError(reEnterAccountNumber_error, "", context);
            returnValue = true;
        }
        if (!edAccountNo.getText().toString().trim().equals(edReEnterAccountNo.getText().toString().trim())) {
            Common.setError(reEnterAccountNumber_error, getString(R.string.ac_not_match), context);
            edReEnterAccountNo.requestFocus();
            return false;
        } else {
            Common.setError(reEnterAccountNumber_error, "", context);
            returnValue = true;
        }
        if (edIFSCCode.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(IFSCCode_error, "IFSC Code " + getString(R.string.input_field_empty), context);
            edIFSCCode.requestFocus();
            return false;
        } else {
            Common.setError(IFSCCode_error, "", context);
            returnValue = true;
        }

        return returnValue;
    }


    /**
     * generating loantap application
     * @param loantapApplication
     */
    private void generateLoanTapApplication(AddApplicationRequest loantapApplication) {
        showProgress(getActivity());
        String url = APIClient.BASE_LOANTAP_URL + "transact";
//        encrypt = encrypt(et.getText().toString().getBytes(), secretKey, IV);
        Call<AddApplicationResponse> call = null;
        try {
            BaaSEncryptDecrypt.setup();
            call = APIClient.getClient(APIClient.type.JSON)
                    .generateLoanTapApplication(url, BaaSEncryptDecrypt.encrypt(String.valueOf(System.currentTimeMillis() / 1000L)), LOANTAPPRODUCTID, LOANTAPPARTNERID, loantapApplication);
            call.enqueue(new Callback<AddApplicationResponse>() {
                @Override
                public void onResponse(Call<AddApplicationResponse> call, Response<AddApplicationResponse> response) {
                    Log.e(TAG, "Response ---" + response);
                    closeProgress();
                    if (response.body().getAddApplication1().getAnswer()!=null && response.body().getAddApplication1().getAnswer().getStatus().equalsIgnoreCase("success")) {
                        if (response.body().getAddApplication1() != null) {
                            Log.e(TAG, "onResponse: LOANTAPP APP "+response.body().getAddApplication1().getAnswer().getData().getLappId() );
                            sharedPreferences.edit().putString("LOANTAPAPPID", response.body().getAddApplication1().getAnswer().getData().getLappId()).apply();
//                            replaceFragment(new DocumentUploadFragment());
                        } else {
                            Toast.makeText(getActivity(), "Auth Token Expired", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            Toast.makeText(getActivity(),response.body().getAddApplication1().getError().getMessage(),Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("BankDetailsFragment", "Error received" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<AddApplicationResponse> call, Throwable t) {
                    closeProgress();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * generate loan application on Upwards
     *
     * @param upwardRequest
     */
    private void generateLoanApplication(UpwardLoanRequestModel upwardRequest) {
//        showProgress(getActivity());
        String url = APIClient.BASE_UPWARD_PROD_URL + "loan/data/";
        Call<UpwardLoanResponse> call = APIClient.getClient(APIClient.type.JSON)
                .generateLoanApplicationUpwards(url, upwardsUserID, upwardsAuthToken, upwardRequest);
        call.enqueue(new Callback<UpwardLoanResponse>() {
            @Override
            public void onResponse(Call<UpwardLoanResponse> call, Response<UpwardLoanResponse> response) {
                Log.d(TAG, "Response " + response.isSuccessful());
                closeProgress();
                if (response.code() == 200) {
                    sharedPreferences.edit().putString(context.getString(R.string.upwardResponse), new Gson().toJson((UpwardLoanResponse) response.body())).apply();
//todo                    replaceFragment(new DocumentUploadFragment());
                } else {
                    Log.e("BankDetailsFragment", "Error received" + response.code());
                }
//                postBankDetails(bankDetailsData());
            }

            @Override
            public void onFailure(Call<UpwardLoanResponse> call, Throwable t) {
                closeProgress();
                t.printStackTrace();
            }
        });
    }


    public void showProgress(Context ctx) {
        try {
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage("Please Wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void closeProgress() {
        try {
            if (pDialog != null) {
                pDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAttachedToActivity() {
        boolean attached = isVisible() && getActivity() != null;
        return attached;
    }
}
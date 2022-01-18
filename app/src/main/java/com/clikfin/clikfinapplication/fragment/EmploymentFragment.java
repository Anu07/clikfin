package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.clikfin.clikfinapplication.externalRequests.Request.EmployeeDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.PersonalDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.UpwardLoanRequestModel;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.loantap.AddApplication1;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class EmploymentFragment extends Fragment {

    private Spinner empTypeSpinner, tenureSpinner, spinnerModeOfSalary, spinnerOrganizationType, spinnerLoanPurpose;

    private EditText edCurrentWorkExpYear, edTotalWorkExpYear, edDesignation, edSalary, edOfficeAddressCity, edCompanyName, edOfficialEmailId, edOfficeAddressLine1, edOfficeAddressLine2, edOfficeAddressLandMark, edOfficeAddressPinCode, edOfficePhoneNumber, edApplyLoanAmount, edPreviousLoanAmount, edPreviousMonthlyEMI, edLoanFinancierName;
    final String[] employmentType = {"SALARIED"};
    String[] tenurePeriod, stateNameList, organizationTypeList, loanPurposeList;
    String[] modeOfSalary;
    Spinner spinnerOfficeAddressState;
    private TextView loanPurposeError, organizationTypeError, designationExpError, totalWorkExpError, currentWorkExpError, employeeTypeError, companyNameError, officialEmailIdError, modeOfSalaryError, salaryError, officeAddressLine1Error, officeAddressLine2Error, officeAddressLandMarkError, officeAddressCityError, officeAddressStateError, officeAddressPinCodeError, officePhoneNumberError, applyLoanAmountError, tenureError, previousLoanAmountError, previousMonthlyEMIError, loanFinancierNameError;
    static String upwardsUserID = "33";
    static String upwardsAuthToken = "upwards_affiliate_33_lqnQHlzf8oLrTI2pRxdGXcyF5DTxbYNY";
    FragmentActivity activity;
    Context context;
    Button btnNext;
    SharedPreferences sharedPreferences;
    HashMap<String, Integer> loanPurposeId = new HashMap<>();
    private String authToken;


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
        View view = inflater.inflate(R.layout.fragment_employement, container, false);
        sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        btnNext = view.findViewById(R.id.btnNext);
        empTypeSpinner = view.findViewById(R.id.empTypeSpinner);
        tenureSpinner = view.findViewById(R.id.tenureSpinner);
        spinnerOrganizationType = view.findViewById(R.id.spinnerOrganizationType);
        spinnerLoanPurpose = view.findViewById(R.id.spinnerLoanPurpose);
        spinnerModeOfSalary = view.findViewById(R.id.spinnerModeOfSalary);
        edCompanyName = view.findViewById(R.id.edCompanyName);
        edOfficialEmailId = view.findViewById(R.id.edOfficialEmailId);
        edSalary = view.findViewById(R.id.edSalary);
        edOfficeAddressLine1 = view.findViewById(R.id.edOfficeAddressLine1);
        edOfficeAddressLine2 = view.findViewById(R.id.edOfficeAddressLine2);
        edOfficeAddressLandMark = view.findViewById(R.id.edOfficeAddressLandMark);
        edOfficeAddressCity = view.findViewById(R.id.edOfficeAddressCity);
        spinnerOfficeAddressState = view.findViewById(R.id.spinnerOfficeAddressState);
        edOfficeAddressPinCode = view.findViewById(R.id.edOfficeAddressPinCode);
        edOfficePhoneNumber = view.findViewById(R.id.edOfficePhoneNumber);
        edApplyLoanAmount = view.findViewById(R.id.edApplyLoanAmount);
        edPreviousLoanAmount = view.findViewById(R.id.edPreviousLoanAmount);
        edPreviousMonthlyEMI = view.findViewById(R.id.edPreviousMonthlyEMI);
        edLoanFinancierName = view.findViewById(R.id.edLoanFinancierName);
        edDesignation = view.findViewById(R.id.edDesignation);
        edCurrentWorkExpYear = view.findViewById(R.id.edCurrentWorkExpYear);

        edTotalWorkExpYear = view.findViewById(R.id.edTotalWorkExpYear);

        employeeTypeError = view.findViewById(R.id.employeeTypeError);
        companyNameError = view.findViewById(R.id.companyNameError);
        officialEmailIdError = view.findViewById(R.id.officialEmailIdError);
        modeOfSalaryError = view.findViewById(R.id.modeOfSalaryError);
        salaryError = view.findViewById(R.id.salaryError);
        officeAddressLine1Error = view.findViewById(R.id.officeAddressLine1Error);
        officeAddressLine2Error = view.findViewById(R.id.officeAddressLine2Error);
        officeAddressLandMarkError = view.findViewById(R.id.officeAddressLandMarkError);
        officeAddressCityError = view.findViewById(R.id.officeAddressCityError);
        officeAddressStateError = view.findViewById(R.id.officeAddressStateError);
        officeAddressPinCodeError = view.findViewById(R.id.officeAddressPinCodeError);
        officePhoneNumberError = view.findViewById(R.id.officePhoneNumberError);
        applyLoanAmountError = view.findViewById(R.id.applyLoanAmountError);
        tenureError = view.findViewById(R.id.tenureError);
        previousLoanAmountError = view.findViewById(R.id.previousLoanAmountError);
        previousMonthlyEMIError = view.findViewById(R.id.previousMonthlyEMIError);
        loanFinancierNameError = view.findViewById(R.id.loanFinancierNameError);
        designationExpError = view.findViewById(R.id.designationExpError);
        currentWorkExpError = view.findViewById(R.id.currentWorkExpError);
        totalWorkExpError = view.findViewById(R.id.totalWorkExpError);
        organizationTypeError = view.findViewById(R.id.organizationTypeError);
        loanPurposeError = view.findViewById(R.id.loanPurposeError);

        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");


        if (Common.isNetworkConnected(context)) {
            getMadeOfSalary();
        } else {
            Common.networkDisconnectionDialog(context);
        }

        ArrayAdapter<String> employmentAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, employmentType);
        employmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        empTypeSpinner.setAdapter(employmentAdapter);
        populateLoanPurposeIdUpwards();

        btnNext.setOnClickListener(v -> {
            if (checkValidInput()) {
                if (Common.isNetworkConnected(context)) {
                    if (upwardConditionsMet(sendEmploymentInfoToServer())) {
                        if (sharedPreferences.getString(getString(R.string.loan_source), "").equalsIgnoreCase(getString(R.string.upward))) {
                            sharedPreferences.edit().putString(getString(R.string.employment_details), new Gson().toJson(createUpwardRequest(sendEmploymentInfoToServer()))).apply();
                            sharedPreferences.edit().putString(getString(R.string.employment_details_loantap), new Gson().toJson(createLoanTapRequest(sendEmploymentInfoToServer()))).apply();
                        }
//                        } else if (sharedPreferences.getString(getString(R.string.loan_source), "").equalsIgnoreCase(getString(R.string.loantap))) {
//                        }
                    }
                    postEmployeeInfo(sendEmploymentInfoToServer());
                } else {
                    Common.networkDisconnectionDialog(context);
                }
            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_employee_info));
        return view;
    }


    /**
     * create Loan Tap request
     *
     * @param empInfo
     * @return
     */
    private AddApplication1 createLoanTapRequest(EmployeeDetails empInfo) {
        String personalData = sharedPreferences.getString(getString(R.string.personal_details), "");
        PersonalDetails pDetails = new Gson().fromJson(personalData, PersonalDetails.class);
        AddApplication1 LoanTapApplication = new AddApplication1();
        LoanTapApplication.setFullName(pDetails.getFirstName() + " " + pDetails.getLastName());
        LoanTapApplication.setDob(pDetails.getDateOfBirth());
        LoanTapApplication.setFathersName(pDetails.getFatherName());
        LoanTapApplication.setGender(pDetails.getGender());
        LoanTapApplication.setJobType(empInfo.getEmploymentType());
        LoanTapApplication.setEmployerName(empInfo.getCompanyName());
        LoanTapApplication.setAddressLandmark(pDetails.getCurrentAddressLandmark());
        LoanTapApplication.setEducationalQualification(pDetails.getEducationalQualification());
        LoanTapApplication.setHomeAddrLine1(pDetails.getPermanentAddressLine1());
        LoanTapApplication.setHomeAddrLine2(pDetails.getPermanentAddressLine2());
        LoanTapApplication.setHomeCity(pDetails.getPermanentAddressCity());
        LoanTapApplication.setHomeOwnershipType(pDetails.getPermanentAddressType());
        LoanTapApplication.setHomeZipcode(pDetails.getPermanentAddressPinCode());
        LoanTapApplication.setFixedIncome(String.valueOf(empInfo.getSalary()));
        LoanTapApplication.setMobileNumber(pDetails.getPhoneNumber());
        LoanTapApplication.setEmploymentYear(empInfo.getCurrentExperience());
        LoanTapApplication.setEmiOutflow(String.valueOf(empInfo.getMonthlyEMI()));
        LoanTapApplication.setLoanCity(empInfo.getOfficialAddressCity());
        LoanTapApplication.setMaritalStatus(pDetails.getMaritalStatus());
        LoanTapApplication.setPanCard(pDetails.getPanNumber());
        LoanTapApplication.setMaritalStatus(pDetails.getMaritalStatus());
        LoanTapApplication.setMothersMaidenName(pDetails.getMotherName());
        LoanTapApplication.setReqAmount(String.valueOf(empInfo.getAmount()));
        LoanTapApplication.setReqTenure(empInfo.getTenure());
        return LoanTapApplication;
    }

    /**
     * Upwards Loan Purpose ID
     */
    private void populateLoanPurposeIdUpwards() {
        loanPurposeId.put("Family Function, Marriage etc", 3);
        loanPurposeId.put("Paying Other Loans, Credit Card Bills etc.", 8);
        loanPurposeId.put("Home Construction, Renovation etc.", 4);
        loanPurposeId.put("Medical Treatment, Hospital Bill etc.", 7);
        loanPurposeId.put("Two-Wheeler Purchase", 1);
        loanPurposeId.put("Laptop, Computer, Mobile Purchase", 6);
        loanPurposeId.put("Travel Expenses", 9);
        loanPurposeId.put("Job Related Needs, Purchases etc.", 10);
        loanPurposeId.put("Education, Skill Training Course Fee etc.", 11);
        loanPurposeId.put("Other", 5);
    }


    /**
     * Checking upwards conditions
     * for now, user above >12k will be sent to both upwards and loantap.
     *
     * @param employeeDetails
     * @return affiliate_user_id = 29 and affiliate_user_session_token = "upwards_affiliate_29_RgyR681BfHY9Vj3YF7yP6638xjhBqCry"
     */
    private boolean upwardConditionsMet(EmployeeDetails employeeDetails) {
//         && employeeDetails.getSalary() < 30000
        if ((employeeDetails.getSalary() >= 20000) && (employeeDetails.getAmount() >= 20000)) {
            sharedPreferences.edit().putString(getString(R.string.loan_source), getString(R.string.upward)).apply();
            return true;
//todo        } else if (employeeDetails.getSalary() >= 20000) {
//            sharedPreferences.edit().putString(getString(R.string.loan_source), getString(R.string.loantap)).apply();
//            return true;
        }
        return false;
    }

    private void postEmployeeInfo(EmployeeDetails employeeDetails) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/employmentInfo";
        Call<ApplyLoanResponse> call = APIClient.getClient(APIClient.type.JSON).postEmployeeDetails(url, authToken, employeeDetails);
        call.enqueue(new APICallbackInterface<ApplyLoanResponse>(context) {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ApplyLoanResponse> call, Response<ApplyLoanResponse> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                            editor.putString(getString(R.string.loan_application_status), response.body().getStatus());
                            editor.apply();
                            ApplyLoanResponse personalDetailsResponse = response.body();
                            if (personalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.references_pending))) {
                                replaceFragment(new ReferenceFragment());
                            } else if (personalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.bank_details_pending))) {
                                replaceFragment(new BankDetailsFragment());
                            } else if (personalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (personalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            }
                        }
                        break;
                    case 400:
                        Converter<ResponseBody, EmployeeDetails> employeeDetailsConverter = APIClient.getRetrofit().responseBodyConverter(EmployeeDetails.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(employeeDetailsConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            Common.logExceptionToCrashlaytics(e);
                        }
                        break;
                    case 403:
                    case 401:
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        replaceFragmentWithPopBackStack(new LoginFragment());
                        break;
                    case 409:
                        try {
                            Converter<ResponseBody, ApplyLoanResponse> employeeDetailsResponseConverter = APIClient.getRetrofit().responseBodyConverter(ApplyLoanResponse.class, new Annotation[0]);
                            ApplyLoanResponse PersonalDetailsResponse = employeeDetailsResponseConverter.convert(response.errorBody());
                            if (PersonalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.references_pending))) {
                                replaceFragment(new ReferenceFragment());
                            } else if (PersonalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.bank_details_pending))) {
                                replaceFragment(new BankDetailsFragment());
                            } else if (PersonalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (PersonalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (PersonalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (PersonalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (PersonalDetailsResponse.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            }

                        } catch (IOException e) {
                            Common.logExceptionToCrashlaytics(e);
                        }
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ApplyLoanResponse> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void replaceFragmentWithPopBackStack(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private boolean checkValidInput() {
        boolean returnValue = true;
        if (edCompanyName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(companyNameError, "Company Name " + getString(R.string.input_field_empty), context);
            edCompanyName.requestFocus();
            return false;
        } else {
            Common.setError(companyNameError, "", context);
        }
        if (spinnerOrganizationType.getSelectedItem().toString().trim().equalsIgnoreCase("Select Organization Type")) {
            Common.setError(organizationTypeError, getString(R.string.select_organization_type), context);
            spinnerOrganizationType.setFocusable(true);
            return false;
        } else {
            Common.setError(organizationTypeError, "", context);

        }
        if (edDesignation.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(designationExpError, "Designation " + getString(R.string.input_field_empty), context);
            edDesignation.requestFocus();
            return false;
        } else {
            Common.setError(designationExpError, "", context);

        }
        if (edCurrentWorkExpYear.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(currentWorkExpError, "Current Work Experience Year " + getString(R.string.input_field_empty), context);
            edCurrentWorkExpYear.requestFocus();
            return false;
        } else {
            Common.setError(currentWorkExpError, "", context);
        }
        if (edTotalWorkExpYear.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(totalWorkExpError, "Total Work Experience Year " + getString(R.string.input_field_empty), context);
            edTotalWorkExpYear.requestFocus();
            return false;
        } else if (!(edTotalWorkExpYear.getText().toString().equalsIgnoreCase("")) && !(edCurrentWorkExpYear.getText().toString().equalsIgnoreCase("")) && Integer.parseInt(edTotalWorkExpYear.getText().toString()) < Integer.parseInt(edCurrentWorkExpYear.getText().toString())) {
            Common.setError(totalWorkExpError, getString(R.string.total_exp_greater_error), context);
            edTotalWorkExpYear.requestFocus();
            return false;
        } else {
            Common.setError(totalWorkExpError, "", context);
        }
        if (edOfficialEmailId.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(officialEmailIdError, "Email Id " + getString(R.string.input_field_empty), context);
            edOfficialEmailId.requestFocus();
            return false;
        } else if (!Common.isValidEmail(edOfficialEmailId.getText().toString().trim())) {
            Common.setError(officialEmailIdError, "Email Id " + getString(R.string.input_field_empty), context);
            edOfficialEmailId.requestFocus();
            return false;
        } else {
            Common.setError(officialEmailIdError, "", context);
        }
        //Select Mode Of Salary
        if (spinnerModeOfSalary.getSelectedItem().toString().trim().equalsIgnoreCase("Select Mode Of Salary")) {
            Common.setError(modeOfSalaryError, getString(R.string.select_mode_of_salary), context);
            spinnerModeOfSalary.setFocusable(true);
            return false;
        } else {
            Common.setError(modeOfSalaryError, "", context);

        }
        if (edSalary.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(salaryError, "Salary " + getString(R.string.input_field_empty), context);
            edSalary.requestFocus();
            return false;
        } else if (Integer.parseInt(edSalary.getText().toString()) < 13000) {
            Common.setError(salaryError, getString(R.string.min_salary), context);
            edSalary.requestFocus();
            return false;
        } else {
            Common.setError(salaryError, "", context);

        }
        if (edOfficeAddressLine1.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(officeAddressLine1Error, "Office Address Line1  " + getString(R.string.input_field_empty), context);
            edOfficeAddressLine1.requestFocus();
            return false;
        } else {
            Common.setError(officeAddressLine1Error, "", context);

        }
        if (edOfficeAddressLine2.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(officeAddressLine2Error, "Office Address Line2  " + getString(R.string.input_field_empty), context);
            edOfficeAddressLine2.requestFocus();
            return false;
        } else {
            Common.setError(officeAddressLine2Error, "", context);

        }
        if (edOfficeAddressLandMark.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(officeAddressLandMarkError, "Office Address Landmark  " + getString(R.string.input_field_empty), context);
            edOfficeAddressLandMark.requestFocus();
            return false;
        } else {
            Common.setError(officeAddressLandMarkError, "", context);

        }
        if (edOfficeAddressCity.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(officeAddressCityError, "Office Address City  " + getString(R.string.input_field_empty), context);
            edOfficeAddressCity.requestFocus();
            return false;
        } else {
            Common.setError(officeAddressCityError, "", context);

        }
        if (spinnerOfficeAddressState.getSelectedItem().toString().trim().equalsIgnoreCase("Select State")) {
            Common.setError(officeAddressStateError, getString(R.string.select_state), context);
            spinnerOfficeAddressState.setFocusable(true);
            return false;
        } else {
            Common.setError(officeAddressStateError, "", context);

        }
        if (edOfficeAddressPinCode.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(officeAddressPinCodeError, "Office Address Pin Code  " + getString(R.string.input_field_empty), context);
            edOfficeAddressPinCode.requestFocus();
            return false;
        } else {
            Common.setError(officeAddressPinCodeError, "", context);

        }

        if (edOfficePhoneNumber.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(officePhoneNumberError, "Office Phone Number  " + getString(R.string.input_field_empty), context);
            edOfficePhoneNumber.requestFocus();
            return false;
        } /*else if (Common.isValidPhoneNumber(edOfficeAddressPinCode.getText().toString().trim())) {
            Common.setError(officePhoneNumberError, getString(R.string.contact_number_invalid_error), context);
            edOfficePhoneNumber.requestFocus();
            return false;
        } */ else {
            Common.setError(officePhoneNumberError, "", context);
        }
        if (spinnerLoanPurpose.getSelectedItem().toString().trim().equalsIgnoreCase("Select Loan Purpose")) {
            Common.setError(loanPurposeError, getString(R.string.select_loan_purpose), context);
            spinnerLoanPurpose.setFocusable(true);
            return false;
        } else {
            Common.setError(loanPurposeError, "", context);

        }
        if (edApplyLoanAmount.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(applyLoanAmountError, "Loan Amount  " + getString(R.string.input_field_empty), context);
            edApplyLoanAmount.requestFocus();
            return false;
        } else if (Float.parseFloat(edApplyLoanAmount.getText().toString().trim()) < 20000) {
            Common.setError(applyLoanAmountError, getString(R.string.min_loan_amount), context);
            edApplyLoanAmount.requestFocus();
            return false;
        } else {
            Common.setError(applyLoanAmountError, "", context);

        }
        if (tenureSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Select Tenure Period")) {
            Common.setError(tenureError, getString(R.string.select_tenure_period), context);
            tenureSpinner.setFocusable(true);
            return false;
        } else {
            Common.setError(tenureError, "", context);

        }
        if (edPreviousLoanAmount.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(previousLoanAmountError, "Previous Loan Amount " + getString(R.string.input_field_empty), context);
            edPreviousLoanAmount.requestFocus();
            return false;
        } else {
            Common.setError(previousLoanAmountError, "", context);
        }
        if (edPreviousMonthlyEMI.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(previousMonthlyEMIError, "Monthly EMI " + getString(R.string.input_field_empty), context);
            edPreviousMonthlyEMI.requestFocus();
            return false;
        } else {
            Common.setError(previousMonthlyEMIError, "", context);
        }
        if (edLoanFinancierName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(loanFinancierNameError, "Financier Name " + getString(R.string.input_field_empty), context);
            edLoanFinancierName.requestFocus();
            return false;
        } else {
            Common.setError(loanFinancierNameError, "", context);
        }
        return returnValue;
    }

    private void setServerErrorMsg(EmployeeDetails employeeDetails) {
        if (employeeDetails.getFinancierName() != null) {
            Common.setError(loanFinancierNameError, "Financier " + employeeDetails.getFinancierName(), context);
            edLoanFinancierName.requestFocus();
        } else {
            Common.setError(loanFinancierNameError, "", context);
        }
        if (employeeDetails.getMonthlyEMI() != 0.0) {
            Common.setError(previousMonthlyEMIError, "Previous Loan Monthly EMI " + employeeDetails.getMonthlyEMI(), context);
            edPreviousMonthlyEMI.requestFocus();
        } else {
            Common.setError(previousMonthlyEMIError, "", context);
        }
        if (employeeDetails.getPreviousLoanAmount() != 0.0) {
            Common.setError(previousLoanAmountError, "Previous Loan Amount " + employeeDetails.getPreviousLoanAmount(), context);
            edPreviousLoanAmount.requestFocus();
        } else {
            Common.setError(previousLoanAmountError, "", context);
        }
        if (employeeDetails.getTenure() != null) {
            Common.setError(tenureError, "Tenure " + employeeDetails.getTenure(), context);
            tenureSpinner.setFocusable(true);
        } else {
            Common.setError(tenureError, "", context);
        }
        if (employeeDetails.getAmount() != 0.0) {
            Common.setError(applyLoanAmountError, "Loan Amount " + employeeDetails.getAmount(), context);
            edApplyLoanAmount.requestFocus();
        } else {
            Common.setError(applyLoanAmountError, "", context);
        }
        if (employeeDetails.getPurpose() != null) {
            Common.setError(loanPurposeError, "Loan Purpose " + employeeDetails.getPurpose(), context);
            spinnerLoanPurpose.setFocusable(true);
        } else {
            Common.setError(loanPurposeError, "", context);
        }
        if (employeeDetails.getOfficePhoneNumber() != null) {
            Common.setError(officePhoneNumberError, "OfficePhone Number " + employeeDetails.getOfficePhoneNumber(), context);
            edOfficePhoneNumber.requestFocus();
        } else {
            Common.setError(officePhoneNumberError, "", context);
        }
        if (employeeDetails.getOfficialAddressPinCode() != null) {
            Common.setError(officeAddressPinCodeError, "Office Address Pin Code " + employeeDetails.getOfficialAddressPinCode(), context);
            edOfficeAddressPinCode.requestFocus();
        } else {
            Common.setError(officeAddressPinCodeError, "", context);
        }
        if (employeeDetails.getOfficialAddressState() != null) {
            Common.setError(officeAddressStateError, "Office Address State " + employeeDetails.getOfficialAddressState(), context);
            spinnerOfficeAddressState.setFocusable(true);
        } else {
            Common.setError(officeAddressStateError, "", context);
        }
        if (employeeDetails.getOfficialAddressCity() != null) {
            Common.setError(officeAddressCityError, "Office Address City " + employeeDetails.getOfficialAddressCity(), context);
            edOfficeAddressCity.requestFocus();
        } else {
            Common.setError(officeAddressCityError, "", context);
        }
        if (employeeDetails.getOfficialAddressLandmark() != null) {
            Common.setError(officeAddressLandMarkError, "Office Address Landmark " + employeeDetails.getOfficialAddressLandmark(), context);
            edOfficeAddressLandMark.requestFocus();
        } else {
            Common.setError(officeAddressLandMarkError, "", context);
        }
        if (employeeDetails.getOfficialAddressLine2() != null) {
            Common.setError(officeAddressLine2Error, "Office Address Line2 " + employeeDetails.getOfficialAddressLine2(), context);
            edOfficeAddressLine2.requestFocus();
        } else {
            Common.setError(officeAddressLine2Error, "", context);
        }
        if (employeeDetails.getOfficialAddressLine1() != null) {
            Common.setError(officeAddressLine1Error, "Office Address Line1 " + employeeDetails.getOfficialAddressLine1(), context);
            edOfficeAddressLine1.requestFocus();
        } else {
            Common.setError(officeAddressLine1Error, "", context);
        }
        if (employeeDetails.getSalary() != 0.0) {
            Common.setError(salaryError, "Salary " + employeeDetails.getSalary(), context);
            edSalary.requestFocus();
        } else {
            Common.setError(salaryError, "", context);
        }
        if (employeeDetails.getSalaryMode() != null) {
            Common.setError(modeOfSalaryError, "Salary Mode " + employeeDetails.getSalaryMode(), context);
            spinnerModeOfSalary.setFocusable(true);
        } else {
            Common.setError(modeOfSalaryError, "", context);
        }
        if (employeeDetails.getOfficialEmail() != null) {
            Common.setError(officialEmailIdError, "Email Id " + employeeDetails.getOfficialEmail(), context);
            edOfficialEmailId.requestFocus();
        } else {
            Common.setError(officialEmailIdError, "", context);
        }
        if (employeeDetails.getOverallExperience() != null) {
            Common.setError(totalWorkExpError, "Total Work Experience " + employeeDetails.getOverallExperience(), context);
            edTotalWorkExpYear.requestFocus();
        } else {
            Common.setError(totalWorkExpError, "", context);
        }
        if (employeeDetails.getCurrentExperience() != null) {
            Common.setError(currentWorkExpError, "Current Work Experience " + employeeDetails.getCurrentExperience(), context);
            edCurrentWorkExpYear.requestFocus();
        } else {
            Common.setError(currentWorkExpError, "", context);
        }
        if (employeeDetails.getDesignation() != null) {
            Common.setError(designationExpError, "Designation " + employeeDetails.getDesignation(), context);
            edDesignation.requestFocus();
        } else {
            Common.setError(designationExpError, "", context);
        }
        if (employeeDetails.getOrganizationType() != null) {
            Common.setError(organizationTypeError, "Organization Type " + employeeDetails.getOrganizationType(), context);
            spinnerOrganizationType.setFocusable(true);
        } else {
            Common.setError(organizationTypeError, "", context);
        }
        if (employeeDetails.getCompanyName() != null) {
            Common.setError(companyNameError, "Company Name " + employeeDetails.getCompanyName(), context);
            edCompanyName.requestFocus();
        } else {
            Common.setError(companyNameError, "", context);
        }
        if (employeeDetails.getEmploymentType() != null) {
            Common.setError(employeeTypeError, "Employee Type " + employeeDetails.getEmploymentType(), context);
            empTypeSpinner.setFocusable(true);
        } else {
            Common.setError(employeeTypeError, "", context);
        }

    }

    private void getMadeOfSalary() {

        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getSalaryMode(authToken);
        call.enqueue(new Callback<String[]>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                //  super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            modeOfSalary = new String[response.body().length + 1];
                            modeOfSalary[0] = "Select Mode Of Salary";
                            for (int i = 0; i < response.body().length; i++) {
                                modeOfSalary[i + 1] = response.body()[i];
                            }

                            ArrayAdapter<String> modeOfSalaryAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, modeOfSalary);
                            modeOfSalaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerModeOfSalary.setAdapter(modeOfSalaryAdapter);
                        }
                        getTenure();
                        break;
                    case 401:
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        replaceFragmentWithPopBackStack(new LoginFragment());
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;
                }


            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                // super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getStates() {

        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getState(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                // super.onResponse(call, response);
                if (response.body() != null) {
                    switch (response.code()) {
                        case 200:
                            stateNameList = new String[response.body().length + 1];
                            stateNameList[0] = "Select State";
                            for (int i = 0; i < response.body().length; i++) {
                                stateNameList[i + 1] = response.body()[i];
                            }
                            ArrayAdapter stateAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, stateNameList);
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerOfficeAddressState.setSelection(0);
                            spinnerOfficeAddressState.setAdapter(stateAdapter);
                            getOrganizationType();
                            break;
                        case 500:
                            Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                //  super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getOrganizationType() {

        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getOrganizationType(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                //super.onResponse(call, response);
                if (response.body() != null) {
                    switch (response.code()) {
                        case 200:
                            organizationTypeList = new String[response.body().length + 1];
                            organizationTypeList[0] = "Select Organization Type";
                            for (int i = 0; i < response.body().length; i++) {
                                organizationTypeList[i + 1] = response.body()[i];
                            }
                            ArrayAdapter stateAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, organizationTypeList);
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerOrganizationType.setSelection(0);
                            spinnerOrganizationType.setAdapter(stateAdapter);
                            getLoanPurpose();
                            break;
                        case 500:
                            Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                // super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getLoanPurpose() {

        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getLoanPurpose(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                //super.onResponse(call, response);
                if (response.body() != null) {
                    switch (response.code()) {
                        case 200:
                            loanPurposeList = new String[response.body().length + 1];
                            loanPurposeList[0] = "Select Loan Purpose";
                            for (int i = 0; i < response.body().length; i++) {
                                loanPurposeList[i + 1] = response.body()[i];
                            }
                            ArrayAdapter stateAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, loanPurposeList);
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerLoanPurpose.setSelection(0);
                            spinnerLoanPurpose.setAdapter(stateAdapter);
                            btnNext.setBackground(context.getDrawable(R.drawable.custom_rect));
                            btnNext.setTextColor(Color.parseColor("#ffffff"));
                            btnNext.setEnabled(true);
                            break;
                        case 500:
                            Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                // super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTenure() {
        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getTenure(authToken);
        call.enqueue(new Callback<String[]>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                // super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            //Select Tenure Period
                            tenurePeriod = new String[response.body().length + 1];
                            tenurePeriod[0] = "Select Tenure Period";
                            for (int i = 0; i < response.body().length; i++) {
                                tenurePeriod[i + 1] = response.body()[i];
                            }
                            ArrayAdapter<String> modeOfSalaryAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, tenurePeriod);
                            modeOfSalaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            tenureSpinner.setAdapter(modeOfSalaryAdapter);
                            getStates();
                        }
                        break;
                    case 401:
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        replaceFragmentWithPopBackStack(new LoginFragment());
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;
                }


            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                // super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private EmployeeDetails sendEmploymentInfoToServer() {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmploymentType(empTypeSpinner.getSelectedItem().toString());
        employeeDetails.setPurpose(spinnerLoanPurpose.getSelectedItem().toString());
        employeeDetails.setOrganizationType(spinnerOrganizationType.getSelectedItem().toString());
        employeeDetails.setCompanyName(edCompanyName.getText().toString());
        employeeDetails.setOfficialEmail(edOfficialEmailId.getText().toString());
        employeeDetails.setSalaryMode(spinnerModeOfSalary.getSelectedItem().toString());
        employeeDetails.setSalary(Float.parseFloat(edSalary.getText().toString()));
        employeeDetails.setOfficialAddressLine1(edOfficeAddressLine1.getText().toString());
        employeeDetails.setOfficialAddressLine2(edOfficeAddressLine2.getText().toString());
        employeeDetails.setOfficialAddressLandmark(edOfficeAddressLandMark.getText().toString());
        employeeDetails.setOfficialAddressCity(edOfficeAddressCity.getText().toString());
        employeeDetails.setOfficialAddressState(spinnerOfficeAddressState.getSelectedItem().toString());
        employeeDetails.setOfficialAddressPinCode(edOfficeAddressPinCode.getText().toString());
        employeeDetails.setOfficePhoneNumber(edOfficePhoneNumber.getText().toString());
        employeeDetails.setAmount(Float.parseFloat(edApplyLoanAmount.getText().toString()));
        employeeDetails.setTenure(tenureSpinner.getSelectedItem().toString());
        employeeDetails.setPreviousLoanAmount(Float.parseFloat(edPreviousLoanAmount.getText().toString()));
        employeeDetails.setMonthlyEMI(Float.parseFloat(edPreviousMonthlyEMI.getText().toString()));
        employeeDetails.setFinancierName(edLoanFinancierName.getText().toString());
        employeeDetails.setDesignation(edDesignation.getText().toString());
        String currentWorkExp = edCurrentWorkExpYear.getText().toString() + "year";
        employeeDetails.setCurrentExperience(currentWorkExp);
        String totalWorkExp = edTotalWorkExpYear.getText().toString() + "year";
        employeeDetails.setOverallExperience(totalWorkExp);
        return employeeDetails;
    }


    /**
     * Upward Request
     *
     * @param employeeDetails
     * @return
     */
    private UpwardLoanRequestModel createUpwardRequest(EmployeeDetails employeeDetails) {
        String personalData = sharedPreferences.getString(getString(R.string.personal_details), "");
        PersonalDetails pDetails = new Gson().fromJson(personalData, PersonalDetails.class);
        UpwardLoanRequestModel applicantDetails = new UpwardLoanRequestModel();
        applicantDetails.setSalary(Integer.parseInt(edSalary.getText().toString()));
        applicantDetails.setAadhaar(Long.parseLong(pDetails.getAadhaarNumber().trim()));
        applicantDetails.setLoanPurposeId(getLoanPurposeId(employeeDetails.getPurpose()));
        applicantDetails.setTotalWorkExperienceCategoryId(getOverallExperienceId(employeeDetails.getOverallExperience()));
        applicantDetails.setCompany(employeeDetails.getCompanyName());
        applicantDetails.setCurrentAddressLine1(pDetails.getCurrentAddressLine1());
        applicantDetails.setCurrentAddressLine2(pDetails.getCurrentAddressLine2());
        applicantDetails.setCurrentCity(pDetails.getCurrentAddressCity());
        applicantDetails.setCurrentCompanyJoiningDate(employeeDetails.getTenure());
        applicantDetails.setCurrentCompanyPincode(pDetails.getCurrentAddressPinCode());
        applicantDetails.setCurrentCompanyCity(pDetails.getCurrentAddressCity());
        applicantDetails.setCurrentCompanyState(pDetails.getCurrentAddressState());
        applicantDetails.setProfessionTypeId(getProfessionTypeId(employeeDetails.getDesignation()));
        applicantDetails.setPan(pDetails.getPanNumber());
        applicantDetails.setWorkEmailId(pDetails.getEmail());
        applicantDetails.setSocialEmailId(pDetails.getEmail());
        applicantDetails.setCurrentCompanyJoiningDate("2022-01-01");        //todo
        applicantDetails.setDob(pDetails.getDateOfBirth());
        applicantDetails.setFirstName(pDetails.getFirstName());
        applicantDetails.setFatherFirstName(pDetails.getFatherName());
        applicantDetails.setMotherFirstName(pDetails.getMotherName());
        applicantDetails.setLastName(pDetails.getLastName());
        applicantDetails.setGender(pDetails.getGender());
        applicantDetails.setCurrentPincode(pDetails.getCurrentAddressPinCode());
        applicantDetails.setMaritalStatusId(getMaritalStatusId(pDetails.getMaritalStatus()));
        applicantDetails.setHighestEducationInstituteName(pDetails.getEducationalQualification());
        applicantDetails.setMobileNumber1(pDetails.getPhoneNumber());
        applicantDetails.setPartialData(true);
        applicantDetails.setQualificationTypeId(getQualificationTypeId(pDetails.getEducationalQualification()));
        applicantDetails.setOrganizationTypeId(getOrganizationTypeId(employeeDetails.getOrganizationType()));
        applicantDetails.setCurrentCompanyJoiningDate("2021-03-01");        //todo
        applicantDetails.setSalaryPaymentModeId(getSalaryModeId(employeeDetails.getSalaryMode()));
        applicantDetails.setEmploymentStatusId(getCurrentemploymentStatusId(employeeDetails.getEmploymentType()));
        applicantDetails.setLoanPurposeId(getLoanPurposeId(employeeDetails.getPurpose()));
        applicantDetails.setCurrentEmploymentTenureCategoryId(getCurrentExperienceId(employeeDetails.getCurrentExperience()));
        return applicantDetails;
    }

    private int getMaritalStatusId(String marriageStatus) {
        if (marriageStatus.equals("Unmarried")) {
            return 2;
        } else if (marriageStatus.equals("Married")) {
            return 3;
        }
        return 4;
    }

    /**
     * hashmap populated with upwards data
     *
     * @param purpose
     * @return
     */
    private int getLoanPurposeId(String purpose) {
        Iterator hmIterator = loanPurposeId.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            if (mapElement.getKey().toString().contains(purpose)) {
                return (int) mapElement.getValue();
            }
        }
        return 0;
    }

    private int getOverallExperienceId(String overallExperience) {
        if (overallExperience.equals("0-1")) {
            return 2;
        } else if (overallExperience.equals("1-3")) {
            return 3;
        } else if (overallExperience.equals("3-5")) {
            return 4;
        }
        return 5;
    }

    /**
     * 28 Sales & Marketing
     * 29 Finance & Accounting
     * 30 Operations
     * 31 Software & IT
     * 17 Factory Worker
     * 26 Teacher
     * 32 Doctor/Nurse/Caregiver/Physio
     * 24 Core Engineering (Chemical, Civil, Mechanical, Pharma etc.)
     * 3 BPO/Back Office Executive
     * 8 Field Executive (Banks, BFSI etc.)
     * 9 Housekeeping/Facility Management
     * 7 Electrician/Plumber/Carpenter/Mechanic
     * 33 Delivery-Boy/Driver
     * 1 Security Guard
     * 18 Beautician/Salon Worker
     * 12 Retail Shop Owner/Business Owner
     * 21 Others
     *
     * @param Designation
     * @return
     */
    private int getProfessionTypeId(String Designation) {
        HashMap<Integer, String> professionType = new HashMap<Integer, String>();
        professionType.put(28, "Sales & Marketing");
        professionType.put(29, "Finance & Accounting");
        professionType.put(30, "Operations");
        professionType.put(31, "Software & IT");
        professionType.put(17, "Factory Worker");
        professionType.put(26, "Teacher");
        professionType.put(32, "Doctor/Nurse/Caregiver/Physio");
        professionType.put(24, "Core Engineering (Chemical, Civil, Mechanical, Pharma etc.)");
        professionType.put(3, "BPO/Back Office Executive");
        professionType.put(8, "Field Executive (Banks, BFSI etc.)");
        professionType.put(9, "Housekeeping/Facility Management");
        professionType.put(7, "Electrician/Plumber/Carpenter/Mechanic");
        professionType.put(33, "Delivery-Boy/Driver");
        professionType.put(1, "Security Guard");
        professionType.put(18, "Beautician/Salon Worker");
        professionType.put(12, "Retail Shop Owner/Business Owner");
        professionType.put(21, "Others");

        for (Map.Entry<Integer, String> entry1 : professionType.entrySet()) {
            if (entry1.getValue().contains(Designation)) {
                return entry1.getKey();
            }
        }
        return 21;
    }

    /**
     * 1 Cash
     * 2 Online
     * 3 Cheque
     * 4 Other
     *
     * @param SalaryMode
     * @return
     */
    private int getSalaryModeId(String SalaryMode) {
        if (SalaryMode.equals("Cash")) {
            return 1;
        } else if (SalaryMode.equals("Online")) {
            return 2;
        } else if (SalaryMode.equals("Cheque")) {
            return 3;
        }
        return 4;
    }


    /**
     * 2 Self Employed
     * 3 Salaried
     * 4 Unemployed
     * 5 Other
     *
     * @param empStatus
     * @return
     */
    private int getCurrentemploymentStatusId(String empStatus) {
        if (empStatus.equals("Unemployed")) {
            return 4;
        } else if (empStatus.equals("Self Employed")) {
            return 2;
        } else if (empStatus.equals("Salaried")) {
            return 3;
        }
        return 5;
    }


    /**
     * get current tenure Id
     *
     * @param currentExperienceMonths
     * @return
     */
    private int getCurrentExperienceId(String currentExperienceMonths) {
        if (currentExperienceMonths.equals("1-6")) {
            return 2;
        } else if (currentExperienceMonths.equals("6-12")) {
            return 3;
        } else if (currentExperienceMonths.equals("12-24")) {
            return 4;
        }
        return 5;
    }

    /**
     * 3 Private Limited
     * 4 Public Limited
     * 5 Proprietorship
     * 6 Partnership
     * 2 Other
     * get Organization type Id
     */
    private int getOrganizationTypeId(String organization) {
        if (organization.equals("Private Limited")) {
            return 3;
        } else if (organization.equals("Public Limited")) {
            return 4;
        } else if (organization.equals("Proprietorship")) {
            return 5;
        } else if (organization.equals("Partnership")) {
            return 6;
        }
        return 2;
    }

    /**
     * get qualifucatuointype Id
     * 3 Under Graduate
     * 4 Graduate
     * 5 Post Graduate
     * 2 Other
     */
    private int getQualificationTypeId(String qualification) {
        if (qualification.equals("Under Graduate")) {
            return 3;
        } else if (qualification.equals("Graduate")) {
            return 4;
        } else if (qualification.equals("Post Graduate")) {
            return 5;
        }
        return 2;
    }

}

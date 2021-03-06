package com.clikfin.clikfinapplication.fragment;


import static android.app.Activity.RESULT_OK;
import static com.clikfin.clikfinapplication.constants.Constants.CAMERA_REQUEST_CODE;
import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsAuthToken;
import static com.clikfin.clikfinapplication.fragment.EmploymentFragment.upwardsUserID;
import static com.clikfin.clikfinapplication.network.APIClient.LOANTAPPARTNERID;
import static com.clikfin.clikfinapplication.network.APIClient.LOANTAPPRODUCTID;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.constants.Constants;
import com.clikfin.clikfinapplication.externalRequests.Request.DocumentReportRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.DocumentStatusRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.DocumentUrlGenerateRequest;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocName;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentErrorResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse.Documents;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse.EnquireResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication.proofLoanTap.ProofJsonItem;
import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.upload.UploadDocResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.UpwardLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.docUpload.DocumentURLGenerationResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.documentRespone.Data;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.documentRespone.DocumentReportResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.upward.updateStatus.StatusDocumentResponse;
import com.clikfin.clikfinapplication.externalRequests.loantap.LoanTapUploadDocumentRequest;
import com.clikfin.clikfinapplication.externalRequests.loantap.UploadDocument1;
import com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest.DocumentsStatus;
import com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest.EnquireRequest;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.AppFileUtils;
import com.clikfin.clikfinapplication.util.BaaSEncryptDecrypt;
import com.clikfin.clikfinapplication.util.Common;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class DocumentUploadFragment extends Fragment {
    private static final String TAG = "LoanTapDocUploadFrag";
    private static int size = 0;
    LinearLayout layBankStatements;
    Button btnUploadDocDone;
    String proofJson = "[{\n" +
            "\t\t\"label\": \"Address Proof\",\n" +
            "\t\t\"value\": \"address_proof\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Current Address Proof\",\n" +
            "\t\t\"value\": \"current_address_proof\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Aadhaar Card\",\n" +
            "\t\t\"value\": \"aadhaar_card\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional Aadhaar Card Page\",\n" +
            "\t\t\"value\": \"aadhaar_card-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Statement Month 1\",\n" +
            "\t\t\"value\": \"bank_statement-month-1\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Statement Month 2\",\n" +
            "\t\t\"value\": \"bank_statement-month-2\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Statement Month 3\",\n" +
            "\t\t\"value\": \"bank_statement-month-3\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Statement Month 4\",\n" +
            "\t\t\"value\": \"bank_statement-month-4\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Statement Month 5\",\n" +
            "\t\t\"value\": \"bank_statement-month-5\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Statement Month 6\",\n" +
            "\t\t\"value\": \"bank_statement-month-6\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Statement Combined\",\n" +
            "\t\t\"value\": \"bank_statement-combined\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bank Verification Statement\",\n" +
            "\t\t\"value\": \"bank_verification_statement\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"paytmWallet Statement\",\n" +
            "\t\t\"value\": \"paytmwallet_statement\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional paytmWallet Statement\",\n" +
            "\t\t\"value\": \"paytmwallet_statement-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Credit Card Statement\",\n" +
            "\t\t\"value\": \"credit_card_statement\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Cibil Report\",\n" +
            "\t\t\"value\": \"cibil_report\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional Cibil Report\",\n" +
            "\t\t\"value\": \"cibil_report-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"CRIF Report\",\n" +
            "\t\t\"value\": \"crif_report\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"CRIF Verification\",\n" +
            "\t\t\"value\": \"crif_verification\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Cheque Full Amount\",\n" +
            "\t\t\"value\": \"cheque-full_amount\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Cheque EMI\",\n" +
            "\t\t\"value\": \"cheque-emi\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Cheque Cancelled\",\n" +
            "\t\t\"value\": \"cheque-cancelled\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"CAM\",\n" +
            "\t\t\"value\": \"cam\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Customer Photo\",\n" +
            "\t\t\"value\": \"customer_photo\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Driving License\",\n" +
            "\t\t\"value\": \"driving_licence\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Electricity Bill\",\n" +
            "\t\t\"value\": \"electricity_bill\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"FAM\",\n" +
            "\t\t\"value\": \"fam\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional FAM\",\n" +
            "\t\t\"value\": \"fam-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Form 16\",\n" +
            "\t\t\"value\": \"form_16\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional Form 16 Pages\",\n" +
            "\t\t\"value\": \"form_16-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Govt. Allotment Letter\",\n" +
            "\t\t\"value\": \"govt_allotment_letter\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Gas Receipt\",\n" +
            "\t\t\"value\": \"gas_receipt\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Gas Book\",\n" +
            "\t\t\"value\": \"gas_book\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Loan Agreement\",\n" +
            "\t\t\"value\": \"loan_agreement\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Loan Agreement Copy\",\n" +
            "\t\t\"value\": \"loan_agreement_copy\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Agreement eSigned\",\n" +
            "\t\t\"value\": \"agreement_esigned\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Nach Mandate\",\n" +
            "\t\t\"value\": \"nach_mandate\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Office FI Report\",\n" +
            "\t\t\"value\": \"office_fi_report\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"OSV ID Proof\",\n" +
            "\t\t\"value\": \"osv_id_proof\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"OSV Address Proof\",\n" +
            "\t\t\"value\": \"osv_address_proof\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Residence FI Photo 1\",\n" +
            "\t\t\"value\": \"residence_fi_photo_1\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Residence FI Photo 2\",\n" +
            "\t\t\"value\": \"residence_fi_photo_2\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Residence FI Photo 3\",\n" +
            "\t\t\"value\": \"residence_fi_photo_3\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Residence FI Photo 4\",\n" +
            "\t\t\"value\": \"residence_fi_photo_4\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Residence FI Photo 5\",\n" +
            "\t\t\"value\": \"residence_fi_photo_5\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Office FI Photo 1\",\n" +
            "\t\t\"value\": \"office_fi_photo_1\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Office FI Photo 2\",\n" +
            "\t\t\"value\": \"office_fi_photo_2\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Office FI Photo 3\",\n" +
            "\t\t\"value\": \"office_fi_photo_3\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Office FI Photo 4\",\n" +
            "\t\t\"value\": \"office_fi_photo_4\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Office FI Photo 5\",\n" +
            "\t\t\"value\": \"office_fi_photo_5\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Pan Card\",\n" +
            "\t\t\"value\": \"pan_card\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Passport\",\n" +
            "\t\t\"value\": \"passport\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional Passport Page\",\n" +
            "\t\t\"value\": \"passport-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Permanent address FI Report\",\n" +
            "\t\t\"value\": \"permanent_address_fi_report\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Photo\",\n" +
            "\t\t\"value\": \"photo\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional Photo\",\n" +
            "\t\t\"value\": \"photo-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Rent Agreement\",\n" +
            "\t\t\"value\": \"rent_agreement\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional Rent Agreement pages\",\n" +
            "\t\t\"value\": \"rent_agreement-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Residence FI Report\",\n" +
            "\t\t\"value\": \"residence_fi_report\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Salary Slip Month 1\",\n" +
            "\t\t\"value\": \"salary_slip-month-1\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Salary Slip Month 2\",\n" +
            "\t\t\"value\": \"salary_slip-month-2\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Salary Slip Month 3\",\n" +
            "\t\t\"value\": \"salary_slip-month-3\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Salary Slip Combined\",\n" +
            "\t\t\"value\": \"salary_slip-combined\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Sale Deed\",\n" +
            "\t\t\"value\": \"sale_deed\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Telephone Bill\",\n" +
            "\t\t\"value\": \"telephone_bill\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Voter ID\",\n" +
            "\t\t\"value\": \"voter_id\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Voter ID Back\",\n" +
            "\t\t\"value\": \"voter_id_back\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"FI Report Received\",\n" +
            "\t\t\"value\": \"fi_report_received\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Nach Received\",\n" +
            "\t\t\"value\": \"nach_received\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Agreement Received\",\n" +
            "\t\t\"value\": \"agreement_received\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Returns Month 1\",\n" +
            "\t\t\"value\": \"gst_returns-month-1\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Returns Month 2\",\n" +
            "\t\t\"value\": \"gst_returns-month-2\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Returns Month 3\",\n" +
            "\t\t\"value\": \"gst_returns-month-3\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Returns Month 4\",\n" +
            "\t\t\"value\": \"gst_returns-month-4\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Returns Month 5\",\n" +
            "\t\t\"value\": \"gst_returns-month-5\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Returns Month 6\",\n" +
            "\t\t\"value\": \"gst_returns-month-6\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Returns Combined\",\n" +
            "\t\t\"value\": \"gst_returns-combined\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"GST Certificate\",\n" +
            "\t\t\"value\": \"gst_certificate\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"RC Book\",\n" +
            "\t\t\"value\": \"rc_book\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Bike Insurance Copy\",\n" +
            "\t\t\"value\": \"bike_insurance_copy\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Invoice Copy\",\n" +
            "\t\t\"value\": \"invoice_copy\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Shop Act\",\n" +
            "\t\t\"value\": \"shop_act\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Latest ITR\",\n" +
            "\t\t\"value\": \"itr\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Additional ITR\",\n" +
            "\t\t\"value\": \"itr-xxxx\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"House Owners Sanction Letter\",\n" +
            "\t\t\"value\": \"house_owners_sanction_letter\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Booking Receipt\",\n" +
            "\t\t\"value\": \"booking_receipt\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Other\",\n" +
            "\t\t\"value\": \"other\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Udyog Aadhaar Certificate\",\n" +
            "\t\t\"value\": \"udyog_uaadhaar_certificate\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Shop Photo 1\",\n" +
            "\t\t\"value\": \"shop_photo_1\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Shop Photo 2\",\n" +
            "\t\t\"value\": \"shop_photo_2\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Shop Photo 3\",\n" +
            "\t\t\"value\": \"shop_photo_3\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Shop Photo 4\",\n" +
            "\t\t\"value\": \"shop_photo_4\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"VCIP Report\",\n" +
            "\t\t\"value\": \"vcip_report\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"Restructure Request Letter\",\n" +
            "\t\t\"value\": \"restructure_request_letter\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"CARD KYC File (pdf format)\",\n" +
            "\t\t\"value\": \"card_kyc_file\"\n" +
            "\t}\n" +
            "]";
    TextView tvPanUpload, tvAadharUpload, tvResidencyProofUpload, tvBankStatementUpload, tvPaySlip, tvPhotoUpload, tvCompanyIDUpload;
    final int PERMISSION_REQUES_CODE = 101;
    TextView tvIsPanMandatory, tvIsAadharFrontMandatory, tvIsAadharBackMandatory, tvIsResidencyProofMandatory, tvIs1MonthsBankStateMandatory, tvIs2MonthsBankStateMandatory, tvIs3MonthsBankStateMandatory, tvIs1MonthPaySlipMandatory, tvIs2MonthPaySlipMandatory, tvIs3MonthPaySlipMandatory, tvIsPhotoUploadMandatory, tvIsCompanyIDUploadMandatory;
    TextView tvUploadDocName, tvUploadDocMsg;
    Button btnSelectFileTOUpload, btnCancelFileToUpload, btnUploadFile;
    ImageView imgCamera;
    String fileType;
    static Uri fileUri;
    HashMap<String, Boolean> loanTapDocs = new HashMap<>();
    File file;
    CheckBox chkFilePassword;
    private ProgressDialog pDialog;
    public static final int SELECT_DOC_FILE_CODE = 1001;
    private static final int PICK_PDF_FILE = 2;

    EditText edFilePassword;
    Dialog dialog;
    static String uploadDocName, uploadDocType;
    public static UploadDocumentResponse documentResponse;
    ImageView imgPanUpload, imgAadharFrontUpload, imgAadharBackUpload, imgResidencyProofUpload, img_1MonthsBankStateUpload, img_2MonthsBankStateUpload, img_3MonthsBankStateUpload, img1MonthPaySlip, img2MonthPaySlip, img3MonthPaySlip, imgPhotoUpload, imgCompanyIDUpload;
    static FragmentActivity activity;
    static Context context;
    private Uri pickerInitialUri;
    private SharedPreferences sharedPreferences;
    private UpwardLoanResponse upwardResponse;
    private int upwardDocumentID;
    private SharedPreferences.Editor editor;
    LinearLayout adhaarLay, layBankState2, layBankState3, layImgPay2, layImgPay3;
    private String filePath = "";

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_documents, container, false);
        // layBankStatements = view.findViewById(R.id.layBankStatements);
        btnUploadDocDone = view.findViewById(R.id.btnUploadDocDone);
        tvPanUpload = view.findViewById(R.id.tvPanUpload);
        tvAadharUpload = view.findViewById(R.id.tvAadharUpload);
        tvResidencyProofUpload = view.findViewById(R.id.tvResidencyProofUpload);
        tvBankStatementUpload = view.findViewById(R.id.tvBankStatementUpload);
        tvPaySlip = view.findViewById(R.id.tvPaySlip);
        tvPhotoUpload = view.findViewById(R.id.tvPhotoUpload);
        tvCompanyIDUpload = view.findViewById(R.id.tvCompanyIDUpload);
        imgPanUpload = view.findViewById(R.id.imgPanUpload);
        imgAadharFrontUpload = view.findViewById(R.id.imgAadharFrontUpload);
        imgAadharBackUpload = view.findViewById(R.id.imgAadharBackUpload);
        imgResidencyProofUpload = view.findViewById(R.id.imgResidencyProofUpload);
        //tv3MonthsBankStateUpload = view.findViewById(R.id.tv3MonthsBankStateUpload);
        img_1MonthsBankStateUpload = view.findViewById(R.id.img_1MonthsBankStateUpload);
        img_2MonthsBankStateUpload = view.findViewById(R.id.img_2MonthsBankStateUpload);
        img_3MonthsBankStateUpload = view.findViewById(R.id.img_3MonthsBankStateUpload);
        img1MonthPaySlip = view.findViewById(R.id.img1MonthPaySlip);
        img2MonthPaySlip = view.findViewById(R.id.img2MonthPaySlip);
        img3MonthPaySlip = view.findViewById(R.id.img3MonthPaySlip);
        imgPhotoUpload = view.findViewById(R.id.imgPhotoUpload);
        imgCompanyIDUpload = view.findViewById(R.id.imgCompanyIDUpload);


        pickerInitialUri = Uri.fromFile(getActivity().getFilesDir());
        adhaarLay = view.findViewById(R.id.adhaarLay);
        layBankState2 = view.findViewById(R.id.layBankState2);
        layBankState3 = view.findViewById(R.id.layBankState3);
        layImgPay2 = view.findViewById(R.id.layImgPay2);
        layImgPay3 = view.findViewById(R.id.layImgPay3);

        tvIsPanMandatory = view.findViewById(R.id.tvIsPanMandatory);
        tvIsAadharFrontMandatory = view.findViewById(R.id.tvIsAadharFrontMandatory);
        tvIsAadharBackMandatory = view.findViewById(R.id.tvIsAadharBackMandatory);
        tvIsResidencyProofMandatory = view.findViewById(R.id.tvIsResidencyProofMandatory);
        tvIs1MonthsBankStateMandatory = view.findViewById(R.id.tvIs1MonthsBankStateMandatory);
        tvIs2MonthsBankStateMandatory = view.findViewById(R.id.tvIs2MonthsBankStateMandatory);
        tvIs3MonthsBankStateMandatory = view.findViewById(R.id.tvIs3MonthsBankStateMandatory);
        tvIs1MonthPaySlipMandatory = view.findViewById(R.id.tvIs1MonthPaySlipMandatory);
        tvIs2MonthPaySlipMandatory = view.findViewById(R.id.tvIs2MonthPaySlipMandatory);
        tvIs3MonthPaySlipMandatory = view.findViewById(R.id.tvIs3MonthPaySlipMandatory);
        tvIsPhotoUploadMandatory = view.findViewById(R.id.tvIsPhotoUploadMandatory);
        tvIsCompanyIDUploadMandatory = view.findViewById(R.id.tvIsCompanyIDUploadMandatory);
        sharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        editor = Common.getSharedPreferencesEditor(activity);
        if (sharedPreferences.getString(getString(R.string.loan_source), "").equals(getString(R.string.upward))) {
            if (sharedPreferences.getString(getString(R.string.upwardResponse), "") != null) {
                String upwardResponseStr = sharedPreferences.getString(getString(R.string.upwardResponse), "");
                upwardResponse = new Gson().fromJson(upwardResponseStr, UpwardLoanResponse.class);
                if ((upwardResponse != null && upwardResponse.getData().getErrorMessage().isEmpty()))
                    getDocumentUploadedReport();
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (sharedPreferences.getString("LOANTAPAPPID", "") != null) {
                        getLoanTapDocumentUploadedReport();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } /*else if (sharedPreferences.getString(getString(R.string.loan_source), "").equals(getString(R.string.loantap))) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//todo                    getLoanTapDocumentUploadedReport();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        if (Common.isNetworkConnected(context)) {
            getUploadDocument();
        } else {
            Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_SHORT).show();
        }

        checkAndRequestPermissions();

        imgPanUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_upload_pan_card), false)) {
                    uploadDocType = getString(R.string.doc_upload_pan_card);
                    upwardDocumentID = 3;
                    showUploadDialog(tvPanUpload.getText().toString());
                }
            }
        });
        imgAadharFrontUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_front), false)) {
                    uploadDocType = getString(R.string.doc_aadhar_front);
                    showUploadDialog(tvAadharUpload.getText().toString());
                    upwardDocumentID = 4;

                }
            }
        });
        imgAadharBackUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_back), false)) {
                    uploadDocType = getString(R.string.doc_aadhar_back);
                    showUploadDialog(tvAadharUpload.getText().toString());
                    upwardDocumentID = 5;
                }

            }
        });
        imgResidencyProofUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_current_residency_proof), false)) {
                    uploadDocType = getString(R.string.doc_current_residency_proof);
                    showUploadDialog(tvResidencyProofUpload.getText().toString());
                    upwardDocumentID = 6;

                }
            }
        });

        img_1MonthsBankStateUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_1), false)) {
                    uploadDocType = getString(R.string.doc_bank_statement_1);
                    showUploadDialog(tvBankStatementUpload.getText().toString());
                    upwardDocumentID = 12;
                }
            }
        });
        img_2MonthsBankStateUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_2), false)) {
                    uploadDocType = getString(R.string.doc_bank_statement_2);
                    showUploadDialog(tvBankStatementUpload.getText().toString());
                }
            }
        });
        img_3MonthsBankStateUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_3), false)) {
                    uploadDocType = getString(R.string.doc_bank_statement_3);
                    showUploadDialog(tvBankStatementUpload.getText().toString());
                }
            }
        });
        img1MonthPaySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_1), false)) {
                    uploadDocType = getString(R.string.doc_pay_slip_1);
                    showUploadDialog(tvPaySlip.getText().toString());
                    upwardDocumentID = 9;
                }
            }
        });
        img2MonthPaySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_2), false)) {
                    uploadDocType = getString(R.string.doc_pay_slip_2);
                    showUploadDialog(tvPaySlip.getText().toString());
                }
            }
        });
        img3MonthPaySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_3), false)) {
                    uploadDocType = getString(R.string.doc_pay_slip_3);
                    showUploadDialog(tvPaySlip.getText().toString());
                }
            }
        });
        imgPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_photo), false)) {
                    uploadDocType = getString(R.string.doc_photo);
                    showUploadDialog(tvPhotoUpload.getText().toString());
                    upwardDocumentID = 1;

                }
            }
        });
        imgCompanyIDUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getBoolean(getString(R.string.isUpload_company_id), false)) {
                    uploadDocType = getString(R.string.doc_company_id);
                    showUploadDialog(tvCompanyIDUpload.getText().toString());
                    upwardDocumentID = 2;
                }
            }
        });

        btnUploadDocDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString(getString(R.string.loan_source), "").equals(getString(R.string.upward)) && (upwardResponse != null && upwardResponse.getData().getErrorMessage().isEmpty())) {
                    getDocumentUploadedReport();
                    postAllDocumentUploadStatus();
                } else {
                    if (checkAllMandatoryDocUploaded()) {
                        postAllDocumentUploadStatus();
                    } else {
                        Toast.makeText(context, getString(R.string.upload_doc_msg), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_upload_documents));
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getLoanTapDocumentUploadedReport() {
        String url = APIClient.BASE_LOANTAP_URL + "enquire";
        Call<EnquireResponse> call = APIClient.getClient(APIClient.type.JSON)
                .enquireLoanTapDocuments(url, BaaSEncryptDecrypt.encrypt(String.valueOf(System.currentTimeMillis() / 1000L)), LOANTAPPRODUCTID, LOANTAPPARTNERID, createEnquireRequest());
        call.enqueue(new Callback<EnquireResponse>() {
            @Override
            public void onResponse(Call<EnquireResponse> call, Response<EnquireResponse> response) {
                EnquireResponse enquireResponse = response.body();
                if (enquireResponse.getDocumentsStatus() != null && enquireResponse.getDocumentsStatus().getAnswer() != null) {
                    Documents docsKycResponse = enquireResponse.getDocumentsStatus().getAnswer().getDocuments();
                    if (docsKycResponse.getKyc().getIsCompleted().equals("no")) {
                        if (docsKycResponse.getKyc().getAddressProof().getIsUploaded().equals("no")) {
                            loanTapDocs.put(docsKycResponse.getKyc().getAddressProof().getFileName(), false);
                        } else if (docsKycResponse.getOthers().getIsCompleted().equals("no")) {
                            loanTapDocs.put(docsKycResponse.getOthers().getFileName(), false);
                        } else if (docsKycResponse.getSalarySlip().getIsCompleted().equals("no")) {
                            loanTapDocs.put(docsKycResponse.getSalarySlip().getSalarySlipAll092021().getFileName(), false);
                        } else if (docsKycResponse.getBankStatement().getIsCompleted().equals("no")) {
                            if (docsKycResponse.getBankStatement().getIndividualFiles().getBankStatement072021().getIsUploaded().equals("no")) {
                                loanTapDocs.put(docsKycResponse.getBankStatement().getIndividualFiles().getBankStatement072021().getFileName(), false);
                            } else if (docsKycResponse.getBankStatement().getIndividualFiles().getBankStatement082021().getIsUploaded().equals("no")) {
                                loanTapDocs.put(docsKycResponse.getBankStatement().getIndividualFiles().getBankStatement082021().getFileName(), false);
                            } else if (docsKycResponse.getBankStatement().getIndividualFiles().getBankStatement092021().getIsUploaded().equals("no")) {
                                loanTapDocs.put(docsKycResponse.getBankStatement().getIndividualFiles().getBankStatement092021().getFileName(), false);
                            }
                        } else {
                            loanTapDocs.clear();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<EnquireResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private EnquireRequest createEnquireRequest() {
        EnquireRequest enquireRequest = new EnquireRequest();
        DocumentsStatus docStatus = new DocumentsStatus();
        docStatus.setApplicationId(sharedPreferences.getString("LOANTAPAPPID", ""));
        enquireRequest.setDocumentsStatus(docStatus);
        return enquireRequest;
    }


    private void postAllDocumentUploadStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/" + getString(R.string.under_review);
        Call<Void> call = APIClient.getClient(APIClient.type.JSON).postAllDocumentUploadStatus(url, authToken);
        call.enqueue(new APICallbackInterface<Void>(context) {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                super.onResponse(call, response);
                if (response.code() == 200) {
                    editor.putString(getString(R.string.loan_application_status), getString(R.string.under_review));
                    editor.apply();
                    replaceFragment(new LoanApplicationStatusFragment());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, "" + getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        //+++++++++++++ fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        int write = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUES_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUES_CODE: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for  permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //permissions are granted already
                    } else {
                        //ask permissions
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app OR you will exit from app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    int pid = android.os.Process.myPid();
                                                    android.os.Process.killProcess(pid);
                                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                                    intent.addCategory(Intent.CATEGORY_HOME);
                                                    startActivity(intent);
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
//                            Toast.makeText(context, "Go to settings and enable permissions", Toast.LENGTH_SHORT)
//                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Close App", okListener)
                .create()
                .show();
    }


    @Nullable
    public String createCopyAndReturnRealPath(
            @NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = context.getApplicationInfo().dataDir + File.separator + "temp_file" + getType(fileType);
        file = new File(filePath);
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }

        size = Integer.parseInt(String.valueOf(file.length() / 1024));
        Log.e("SIZE", "" + size);
        return file.getAbsolutePath();
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStorageDirectory(),
                "ClikFin");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(context, "Oops! Failed create "
                        + "ClikFin" + " directory", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == Constants.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    private void setIsDocumentUploadedImg() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_upload_pan_card), false)) {
            imgPanUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_current_residency_proof), false)) {
            imgResidencyProofUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_1), false)) {
            img1MonthPaySlip.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_2), false)) {
            img2MonthPaySlip.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_3), false)) {
            img3MonthPaySlip.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_1), false)) {
            img_1MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_2), false)) {
            img_2MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_3), false)) {
            img_3MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_front), false)) {
            imgAadharFrontUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_back), false)) {
            imgAadharBackUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_photo), false)) {
            imgPhotoUpload.setImageResource(R.drawable.ic_ok);
        }
        if (sharedPreferences.getBoolean(getString(R.string.isUpload_company_id), false)) {
            imgCompanyIDUpload.setImageResource(R.drawable.ic_ok);
        }
    }


    private void setMandatoryDocuments() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_upload_pan_card), true)) {
            tvIsPanMandatory.setText(tvIsPanMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_current_residency_proof), true)) {
            tvIsResidencyProofMandatory.setText(tvIsResidencyProofMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_1), true)) {
            tvIs1MonthPaySlipMandatory.setText(tvIs1MonthPaySlipMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_2), true)) {
            tvIs2MonthPaySlipMandatory.setText(tvIs2MonthPaySlipMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_3), true)) {
            tvIs3MonthPaySlipMandatory.setText(tvIs3MonthPaySlipMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_1), true)) {
            tvIs1MonthsBankStateMandatory.setText(tvIs1MonthsBankStateMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_2), true)) {
            tvIs2MonthsBankStateMandatory.setText(tvIs2MonthsBankStateMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_3), true)) {
            tvIs3MonthsBankStateMandatory.setText(tvIs3MonthsBankStateMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_front), true)) {
            tvIsAadharFrontMandatory.setText(tvIsAadharFrontMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_back), true)) {
            tvIsAadharBackMandatory.setText(tvIsAadharBackMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_photo), true)) {
            tvIsPhotoUploadMandatory.setText(tvIsPhotoUploadMandatory.getText() + " *");
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_company_id), true)) {
            tvIsCompanyIDUploadMandatory.setText(tvIsCompanyIDUploadMandatory.getText() + " *");
        }
    }

    private boolean checkAllMandatoryDocUploaded() {
        boolean returnValue = true;
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_upload_pan_card), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_upload_pan_card), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_current_residency_proof), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_current_residency_proof), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_1), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_1), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_2), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_2), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_pay_slip_3), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_pay_slip_3), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_1), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_1), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_2), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_2), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_bank_statement_3), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_bank_statement_3), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_front), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_front), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_aadhar_back), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_aadhar_back), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_photo), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_photo), false)) {
            return false;
        }
        if (sharedPreferences.getBoolean(getString(R.string.isMandatory_company_id), false) && !sharedPreferences.getBoolean(getString(R.string.isUpload_company_id), false)) {
            return false;
        }
        return returnValue;
    }


    /**
     * Upwards upload document url generation (Step-1)
     *
     * @param type
     */

    private void uploadDocumentUrl(String type) {
        showProgress(getActivity());
//        https://uat1.upwards.in/af/v1/customer/loan/document/
        String url = APIClient.BASE_UPWARD_PROD_URL + "loan/document/";
        Call<DocumentURLGenerationResponse> call = APIClient.getClient(APIClient.type.JSON)
                .UploadDocumentURLGeneration(url, upwardsUserID, upwardsAuthToken, createDocumentUrlRequest(type));
        call.enqueue(new Callback<DocumentURLGenerationResponse>() {
            @Override
            public void onResponse(Call<DocumentURLGenerationResponse> call, Response<DocumentURLGenerationResponse> response) {
                closeProgress();
                if (response.code() == 200 && response.body().getData().getErrorMessage().isEmpty()) {
                    uploadDocument(response.body());
                } else {
                    Log.e(TAG, "url generation failed upwards");
                }
            }

            @Override
            public void onFailure(Call<DocumentURLGenerationResponse> call, Throwable t) {
                closeProgress();
                t.printStackTrace();
            }
        });
    }


    /**
     * Document upload on S3 bucket URl by upwards
     *
     * @param body
     */

    private void uploadDocument(DocumentURLGenerationResponse body) {

        String url = body.getData().getDocument();
        RequestBody requestFile = null;

        if (file != null)
            requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)), file);
        Call<Void> call = APIClient.getClient(APIClient.type.JSON)
                .UploadDocumentUpward(url, requestFile);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Document Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    updateDocumentUploadStatus();
                } else {
                    closeProgress();
                    Toast.makeText(getActivity(), "Please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
//                closeProgress();
                t.printStackTrace();
            }
        });
    }


    /**
     * * Upwards  document upload status (Step-3)
     */
    private void updateDocumentUploadStatus() {
        String url = APIClient.BASE_UPWARD_PROD_URL + "loan/document/status/";
        Call<StatusDocumentResponse> call = APIClient.getClient(APIClient.type.JSON)
                .UploadDocumentStatusUpward(url, upwardsUserID, upwardsAuthToken, createUpdateStatusRequest());
        call.enqueue(new Callback<StatusDocumentResponse>() {
            @Override
            public void onResponse(Call<StatusDocumentResponse> call, Response<StatusDocumentResponse> response) {
                if (response.code() == 200) {
                    closeProgress();
                    dialog.dismiss();
                    updatePreferencesForDocuments();
//                    setIsDocumentUploadedImg();
                } else {
                    closeProgress();
                    Toast.makeText(getActivity(), "Please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusDocumentResponse> call, Throwable t) {
                closeProgress();
                t.printStackTrace();
            }
        });
    }

    //4a469082-8af7-41f7-940e-97453defa969
    private void callClikFinDocCall() {
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/upload/" + uploadDocType;
        try {
            postDocumentUpload(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePreferencesForDocuments() {
        if (upwardDocumentID == 3) {
            editor.putBoolean(getString(R.string.isUpload_upload_pan_card), true).apply();
        } else if (upwardDocumentID == 2) {
            editor.putBoolean(getString(R.string.isUpload_company_id), true).apply();
        } else if (upwardDocumentID == 1) {
            editor.putBoolean(getString(R.string.isUpload_photo), true).apply();
        } else if (upwardDocumentID == 9) {
            editor.putBoolean(getString(R.string.isUpload_pay_slip_1), true).apply();
        } else if (upwardDocumentID == 6) {
            editor.putBoolean(getString(R.string.isUpload_current_residency_proof), true).apply();
        } else if (upwardDocumentID == 12) {
            editor.putBoolean(getString(R.string.isUpload_bank_statement_1), true).apply();
        }
    }


    /**
     * Upward status update request body
     *
     * @return
     */
    private DocumentStatusRequest createUpdateStatusRequest() {
        DocumentStatusRequest docRequest = new DocumentStatusRequest();
        docRequest.setCustomerId(upwardResponse.getData().getLoanData().getCustomerId());
        docRequest.setLoanId(upwardResponse.getData().getLoanData().getLoanId());
        docRequest.setStatus("file_creation_success");
        docRequest.setDocumentTypeId(upwardDocumentID);
        return docRequest;
    }


    /**
     * Upward Request body for URL generation
     * 3289366-- 4680800;
     *
     * @param type
     * @return
     */
    private DocumentUrlGenerateRequest createDocumentUrlRequest(String type) {
        DocumentUrlGenerateRequest docRequest = new DocumentUrlGenerateRequest();
        docRequest.setCustomerId(upwardResponse.getData().getLoanData().getCustomerId());
        docRequest.setLoanId(upwardResponse.getData().getLoanData().getLoanId()); //4573670
        docRequest.setDocumentExtension(getType(type));
        docRequest.setDocumentTypeId(upwardDocumentID);
        Log.e("Document", "ID" + upwardDocumentID);
        return docRequest;
    }

    private String getType(String type) {
        if (type.endsWith(".jpg")) {
            return ".jpg";
        } else if (type.endsWith(".pdf")) {
            return ".pdf";
        } else if (type.endsWith(".jpeg")) {
            return ".jpeg";
        } else if (type.endsWith(".png")) {
            return ".png";
        } else if (type.endsWith(".jp2")) {
            return ".jp2";
        }
        return ".jpg";
    }


    private void postDocumentUpload(String url) throws IOException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
        RequestBody requestFile = null;
        if (file != null)
            requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("document", URLEncoder.encode(file.getName(), "utf-8"), requestFile);
        String uploadFileName = tvUploadDocName.getText().toString().replace("*", "");
        String uploadFilePassword = edFilePassword.getText().toString();

        Call<UploadDocumentResponse> call = APIClient.getClient(APIClient.type.JSON).putDocumentUpload(filePart, url, authToken, uploadFileName, uploadFilePassword);
        call.enqueue(new APICallbackInterface<UploadDocumentResponse>(context) {
            @Override
            public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {
                super.onResponse(call, response);

                switch (response.code()) {
                    case 201:
                        dialog.dismiss();
                        if (response.body() != null) {
                            UploadDocumentResponse uploadDocumentResponse = response.body();
                            editor.putBoolean(getString(R.string.isUpload_upload_pan_card), uploadDocumentResponse.getPAN_CARD().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_current_residency_proof), uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_1), uploadDocumentResponse.getBANK_STATEMENT_1().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_1), uploadDocumentResponse.getPAY_SLIP_1().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_2), uploadDocumentResponse.getPAY_SLIP_2().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_3), uploadDocumentResponse.getPAY_SLIP_3().isUploaded());
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


                            editor.apply();

                            documentResponse = response.body();

                            showDocumentUploadedImage(uploadDocumentResponse);
                        }
                        break;
                    case 403:
                    case 401:
                        dialog.dismiss();
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentManager.popBackStack();
                        fragmentTransaction.replace(R.id.content_frame, new LoginFragment());
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                    case 400:
                    case 413:
                        dialog.dismiss();
                        if (response.errorBody() != null) {
                            Converter<ResponseBody, UploadDocumentErrorResponse> PersonalDetailsConverter = APIClient.getRetrofit().responseBodyConverter(UploadDocumentErrorResponse.class, new Annotation[0]);
                            try {
                                Toast.makeText(context, PersonalDetailsConverter.convert(response.errorBody()).getError(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Common.logExceptionToCrashlaytics(e);
                            }
                        }
                        break;
                    case 409:
                        dialog.dismiss();
                        Toast.makeText(context, uploadDocName + " already uploaded", Toast.LENGTH_SHORT).show();
                        if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_current_residency_proof))) {
                            imgResidencyProofUpload.setImageResource(R.drawable.ic_ok);
                            editor.putBoolean(getString(R.string.isUpload_current_residency_proof), true);
                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_front))) {
                            imgAadharFrontUpload.setImageResource(R.drawable.ic_ok);
                            editor.putBoolean(getString(R.string.isUpload_aadhar_front), true);
                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_back))) {
                            editor.putBoolean(getString(R.string.isUpload_aadhar_back), true);
                            imgAadharBackUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_upload_pan_card))) {
                            editor.putBoolean(getString(R.string.isUpload_upload_pan_card), true);
                            imgPanUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_1))) {
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_1), true);
                            img_1MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_2))) {
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_2), true);
                            img_2MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_3))) {
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_3), true);
                            img_3MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_1))) {
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_1), true);
                            img1MonthPaySlip.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_2))) {
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_2), true);
                            img2MonthPaySlip.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_3))) {
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_3), true);
                            img3MonthPaySlip.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_photo))) {
                            editor.putBoolean(getString(R.string.isUpload_photo), true);
                            imgPhotoUpload.setImageResource(R.drawable.ic_ok);

                        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_company_id))) {
                            editor.putBoolean(getString(R.string.isUpload_company_id), true);
                            imgCompanyIDUpload.setImageResource(R.drawable.ic_ok);

                        }
                        editor.apply();
                        break;
                    case 500:
                        dialog.dismiss();
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
                super.onFailure(call, t);
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                Common.logAPIFailureToCrashlyatics(t);

            }
        });
    }

    private void showDocumentUploadedImage(UploadDocumentResponse uploadDocumentResponse) {
        if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_current_residency_proof))) {
            if (uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isUploaded()) {

                imgResidencyProofUpload.setImageResource(R.drawable.ic_ok);


                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_front))) {
            if (uploadDocumentResponse.getAADHAAR_CARD_FRONT().isUploaded()) {
                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
                imgAadharFrontUpload.setImageResource(R.drawable.ic_ok);

            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_aadhar_back))) {
            if (uploadDocumentResponse.getAADHAAR_CARD_BACK().isUploaded()) {
                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();

                imgAadharBackUpload.setImageResource(R.drawable.ic_ok);


            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_upload_pan_card))) {
            if (uploadDocumentResponse.getPAN_CARD().isUploaded()) {

                imgPanUpload.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_1))) {
            if (uploadDocumentResponse.getBANK_STATEMENT_1().isUploaded()) {

                img_1MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_2))) {
            if (uploadDocumentResponse.getBANK_STATEMENT_2().isUploaded()) {

                img_2MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_3))) {
            if (uploadDocumentResponse.getBANK_STATEMENT_3().isUploaded()) {

                img_3MonthsBankStateUpload.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_1))) {
            if (uploadDocumentResponse.getPAY_SLIP_1().isUploaded()) {

                img1MonthPaySlip.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_2))) {
            if (uploadDocumentResponse.getPAY_SLIP_2().isUploaded()) {

                img2MonthPaySlip.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_3))) {
            if (uploadDocumentResponse.getPAY_SLIP_3().isUploaded()) {

                img3MonthPaySlip.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_photo))) {
            if (uploadDocumentResponse.getPHOTO().isUploaded()) {

                imgPhotoUpload.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_company_id))) {
            if (uploadDocumentResponse.getCOMPANY_ID().isUploaded()) {


                imgCompanyIDUpload.setImageResource(R.drawable.ic_ok);

                Toast.makeText(context, getString(R.string.is_doc_upload), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showUploadDialog(String docName) {
        uploadDocName = docName;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_upload_doc);
        tvUploadDocName = dialog.findViewById(R.id.tvUploadDocName);
        tvUploadDocMsg = dialog.findViewById(R.id.tvUploadDocMsg);
        btnSelectFileTOUpload = dialog.findViewById(R.id.btnSelectFileToUpload);
        btnCancelFileToUpload = dialog.findViewById(R.id.btnCancelFileToUpload);
        btnUploadFile = dialog.findViewById(R.id.btnUploadFile);
        imgCamera = dialog.findViewById(R.id.imgCamera);
        chkFilePassword = dialog.findViewById(R.id.chkFilePassword);
        edFilePassword = dialog.findViewById(R.id.edFilePassword);
        tvUploadDocName.setText(docName);
        dialog.setCancelable(false);
        dialog.show();
        Intent picIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        picIntent.addCategory(Intent.CATEGORY_OPENABLE);
        picIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        picIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_1)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_2)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_bank_statement_3)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_1)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_2)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_pay_slip_3))) {
            imgCamera.setVisibility(View.GONE);
            picIntent.setType("application/pdf");
            tvUploadDocMsg.setText(getString(R.string.tv_upload_doc_msg));
        } else if (uploadDocType.equalsIgnoreCase(getString(R.string.doc_photo)) || uploadDocType.equalsIgnoreCase(getString(R.string.doc_company_id))) {
            picIntent.setType("image/*");
            tvUploadDocMsg.setText(getString(R.string.tv_upload_image_doc_msg));
        } else {
            String[] mimeTypes = {"image/*", "application/pdf"};
            imgCamera.setVisibility(View.VISIBLE);
            tvUploadDocMsg.setText("");
            picIntent.setType("*/*");
            picIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//            openFile();
        }
        chkFilePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edFilePassword.setVisibility(View.VISIBLE);
            } else {
                edFilePassword.setVisibility(View.GONE);
            }
        });
        btnSelectFileTOUpload.setOnClickListener(v -> {
            if (checkAndRequestPermissions()){
                picIntent.putExtra("return_data", true);
                startActivityForResult(picIntent, Constants.SELECT_DOC_FILE_CODE);
            }else{
                Toast.makeText(getActivity(),"Please grant camera and gallery permission from settings to proceed.",Toast.LENGTH_LONG).show();
            }

        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                    captureImage();
                if (checkAndRequestPermissions()){
                    dispatchTakePictureIntent();
                }else{
                    Toast.makeText(getActivity(),"Please grant camera and gallery permission from settings to proceed.",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancelFileToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUploadFile.setOnClickListener(v -> {

            if (file != null) {
                if (Common.isNetworkConnected(context)) {
                    if (sharedPreferences.getString(getString(R.string.loan_source), "").equals(getString(R.string.upward))) {
                        uploadDocumentUrl(getType(filePath));
//                    } else if (sharedPreferences.getString(getString(R.string.loan_source), "").equals(getString(R.string.loantap))) {
                        try {
                            Log.e(TAG, "Document Type--- " + uploadDocType);
                            uploadDocumentLoanTap(generateLoanTapUploadDocumentRequest(uploadDocType));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callClikFinDocCall();
                } else {
                    Common.networkDisconnectionDialog(context);
                }

            } else {
                Toast.makeText(context, "Please select file to upload", Toast.LENGTH_SHORT).show();
            }

        });

    }

    /**
     * LoanTap upload Document Requets
     *
     * @param uploadDocType
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LoanTapUploadDocumentRequest generateLoanTapUploadDocumentRequest(String uploadDocType) throws IOException {
        Path path
                = Paths.get(file.getPath());
        byte[] fileContent = Files.readAllBytes(path);

        LoanTapUploadDocumentRequest loanTapUploadRequest = new LoanTapUploadDocumentRequest();
        UploadDocument1 uploadDoc = new UploadDocument1();
        uploadDoc.setFile(Base64.getEncoder().withoutPadding().encodeToString(fileContent));
        uploadDoc.setExt(AppFileUtils.getFileExtension(file.getName()));
        uploadDoc.setFileName(getLoanTapDocType(uploadDocType));     //todo
        uploadDoc.setApplicationId(sharedPreferences.getString("LOANTAPAPPID", ""));
        loanTapUploadRequest.setUploadDocument1(uploadDoc);
        return loanTapUploadRequest;
    }

    /**
     * loantap document type
     *
     * @param uploadDocType
     * @return
     */
    private String getLoanTapDocType(String uploadDocType) {
        ProofJsonItem[] proofData = new Gson().fromJson(proofJson, ProofJsonItem[].class);
        if (uploadDocType.contains("PAY_SLIP")) {
            uploadDocType = uploadDocType.replace("PAY_SLIP", "SALARY_SLIP");
        }
        String[] uploadDocTypees = uploadDocType.replace("_", " ").split(" ");
        String combinedUploadString = uploadDocTypees.length > 1 ? (uploadDocTypees[0] + " " + uploadDocTypees[1]).toLowerCase() : uploadDocType;
        for (int i = 0; i < proofData.length; i++) {
            if (Pattern.compile(Pattern.quote(proofData[i].getLabel()), Pattern.CASE_INSENSITIVE).matcher(uploadDocType).find() || (proofData[i].getLabel().toLowerCase(Locale.ROOT).contains(combinedUploadString))) {
                Log.e(TAG, "getLoanTapDocType: " + proofData[i].getValue());
                return proofData[i].getValue();
            }
        }
        return "other";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadDocumentLoanTap(LoanTapUploadDocumentRequest uploadRequest) {
//        showProgress(getActivity());
        String url = APIClient.BASE_LOANTAP_URL + "transact";
        Call<UploadDocResponse> call = null;
        try {
            call = APIClient.getClient(APIClient.type.JSON)
                    .uploadLoanTapDocument(url, BaaSEncryptDecrypt.encrypt(String.valueOf(System.currentTimeMillis() / 1000L)), LOANTAPPRODUCTID, LOANTAPPARTNERID, uploadRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback<UploadDocResponse>() {
            @Override
            public void onResponse(Call<UploadDocResponse> call, Response<UploadDocResponse> response) {
                Log.d("Loan Tap", "Response " + response.isSuccessful());
//                closeProgress();
                if (response.body().getUploadDocument1().getAnswer() != null) {
                    Toast.makeText(getActivity(), "Document uploaded successfully", Toast.LENGTH_SHORT).show();
                    closeProgress();
                    dialog.dismiss();
                } else {
                    Log.e("Loan tap upload issue", "Error received" + response.code());
                }

            }

            @Override
            public void onFailure(Call<UploadDocResponse> call, Throwable t) {
                closeProgress();
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Bitmap bitmap = null;
        if (requestCode == Constants.SELECT_DOC_FILE_CODE) {
            if (data != null) {
                try {
                    fileUri = data.getData();
                    fileType = context.getContentResolver().getType(fileUri);
                    filePath = createCopyAndReturnRealPath(context, fileUri);
                    Log.e("PATH", "" + filePath);
                    if (size > 5000) {
                        tvUploadDocMsg.setText(getString(R.string.file_size_error));
                    } else if (!(fileType.contains("pdf") || fileType.contains("png") || fileType.contains("jpg") || fileType.contains("jpeg"))) {
                        tvUploadDocMsg.setText(getString(R.string.file_format_error));
                    } else {
                        btnSelectFileTOUpload.setVisibility(View.GONE);
                        btnUploadFile.setVisibility(View.VISIBLE);
                        tvUploadDocMsg.setText(filePath);

                    }
                    if (fileType.contains("pdf")) {
                        chkFilePassword.setVisibility(View.VISIBLE);
                    } else {
                        chkFilePassword.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Common.logExceptionToCrashlaytics(e);
                }
            }

        }
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                fileUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "attachment", null));
                file = new File(getRealPathFromURI(fileUri));
                String fileType = context.getContentResolver().getType(fileUri);
                int size = Integer.parseInt(String.valueOf(file.length() / 1024));
                if (size > 5000) {
                    tvUploadDocMsg.setText(getString(R.string.file_size_error));
                } else if (!(fileType.contains("png") || fileType.contains("jpg") || fileType.contains("jpeg"))) {
                    tvUploadDocMsg.setText(getString(R.string.file_format_error));
                } else {
                    btnSelectFileTOUpload.setVisibility(View.GONE);
                    btnUploadFile.setVisibility(View.VISIBLE);
                    tvUploadDocMsg.setText(file.getPath());
                }

//            }
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cur = context.getContentResolver().query(uri, null, null, null, null);
        cur.moveToFirst();
        return cur.getString(cur.getColumnIndex("_data"));
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }
    }


    private void getUploadDocument() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/uploadDocument/type";
        Call<UploadDocName> call = APIClient.getClient(APIClient.type.JSON).getUploadDocument(url, authToken);
        call.enqueue(new APICallbackInterface<UploadDocName>(context) {
            @Override
            public void onResponse(Call<UploadDocName> call, Response<UploadDocName> response) {
                super.onResponse(call, response);
                if (response.body() != null) {
                    tvResidencyProofUpload.setText(response.body().getCURRENT_ADDRESS_PROOF());
                    setIsDocumentUploadedImg();
                    setMandatoryDocuments();
                }
            }

            @Override
            public void onFailure(Call<UploadDocName> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_SHORT).show();
                Common.logAPIFailureToCrashlyatics(t);
            }
        });
    }

    /**
     * System Chooser for PDF docs
     */
    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/pdf");
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        try {
            startActivityForResult(intent, PICK_PDF_FILE);
            return;
        } catch (ActivityNotFoundException anfe) {
            Log.w("TeST", "couldn't complete ACTION_OPEN_DOCUMENT, no activity found. falling back.");
        }
    }


    private void getDocumentUploadedReport() {
//        https://uat1.upwards.in/af/v1/customer/loan/documents/detail/
        String url = APIClient.BASE_UPWARD_PROD_URL + "loan/documents/detail/";
        Call<DocumentReportResponse> call = APIClient.getClient(APIClient.type.JSON)
                .getDocumentReport(url, upwardsUserID, upwardsAuthToken, createDocumentReportRequest());
        call.enqueue(new Callback<DocumentReportResponse>() {
            @Override
            public void onResponse(Call<DocumentReportResponse> call, Response<DocumentReportResponse> response) {
                if (response.code() == 200) {
                    if (checkIfMandatoryDocsUploaded(response.body().getData())) {
                        AllDocumentsUploaded();
                    } /*else {
//                        Toast.makeText(getActivity(), "Please upload all the documents", Toast.LENGTH_SHORT).show();
                        setIsDocumentUploadedImg();
                    }*/
                } else {
                    Log.e("All", "upward get documents uploaded failed");
                }
            }

            @Override
            public void onFailure(Call<DocumentReportResponse> call, Throwable t) {
                t.printStackTrace();
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, "" + getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * all documents uploaded
     */
    private void AllDocumentsUploaded() {
//        https://uat1.upwards.in/af/v1/customer/loan/document/submit/
        String url = APIClient.BASE_UPWARD_PROD_URL + "loan/document/submit/";
        Call<DocumentReportResponse> call = APIClient.getClient(APIClient.type.JSON)
                .getDocumentReport(url, upwardsUserID, upwardsAuthToken, createDocumentReportRequest());
        call.enqueue(new Callback<DocumentReportResponse>() {
            @Override
            public void onResponse(Call<DocumentReportResponse> call, Response<DocumentReportResponse> response) {
                if (response.code() == 200) {
                    Log.i("All", "documents uploaded successfully");
                    editor.putString(getString(R.string.loan_application_status), getString(R.string.under_review));
                    editor.apply();
                    replaceFragment(new LoanApplicationStatusFragment());
                } else {
                    Log.e("All", "upward documents uploaded failed");
                }
            }

            @Override
            public void onFailure(Call<DocumentReportResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private boolean checkIfMandatoryDocsUploaded(Data data) {
        if (data.getDocumentData().getJsonMember1().equalsIgnoreCase("file_creation_success")
                && data.getDocumentData().getJsonMember2().equalsIgnoreCase("file_creation_success")
                && data.getDocumentData().getJsonMember3().equalsIgnoreCase("file_creation_success")
                && data.getDocumentData().getJsonMember9().equalsIgnoreCase("file_creation_success")
                && data.getDocumentData().getJsonMember6().equalsIgnoreCase("file_creation_success")
                && data.getDocumentData().getJsonMember12().equalsIgnoreCase("file_creation_success")) {
            return true;
        } else {
            if (!data.getDocumentData().getJsonMember3().equalsIgnoreCase("file_creation_success")) {
                editor.putBoolean(getString(R.string.isUpload_upload_pan_card), false).apply();
                imgPanUpload.setImageResource(R.drawable.ic_upload);
            } else if (!data.getDocumentData().getJsonMember2().equalsIgnoreCase("file_creation_success")) {
                editor.putBoolean(getString(R.string.isUpload_company_id), false).apply();
                imgCompanyIDUpload.setImageResource(R.drawable.ic_upload);
            } else if (!data.getDocumentData().getJsonMember1().equalsIgnoreCase("file_creation_success")) {
                editor.putBoolean(getString(R.string.isUpload_photo), false).apply();
                imgPhotoUpload.setImageResource(R.drawable.ic_upload);
            } else if (!data.getDocumentData().getJsonMember9().equalsIgnoreCase("file_creation_success")) {
                editor.putBoolean(getString(R.string.isUpload_pay_slip_1), false).apply();
                img1MonthPaySlip.setImageResource(R.drawable.ic_upload);
            } else if (!data.getDocumentData().getJsonMember6().equalsIgnoreCase("file_creation_success")) {
                editor.putBoolean(getString(R.string.isUpload_current_residency_proof), false).apply();
                imgResidencyProofUpload.setImageResource(R.drawable.ic_upload);
            } else if (!data.getDocumentData().getJsonMember12().equalsIgnoreCase("file_creation_success")) {
                editor.putBoolean(getString(R.string.isUpload_bank_statement_1), false).apply();
                img_1MonthsBankStateUpload.setImageResource(R.drawable.ic_upload);
            }
        }
        return false;

    }

    private DocumentReportRequest createDocumentReportRequest() {
        DocumentReportRequest docRequest = new DocumentReportRequest();
        docRequest.setCustomerId(upwardResponse.getData().getLoanData().getCustomerId());
        docRequest.setLoanId(upwardResponse.getData().getLoanData().getLoanId());
        return docRequest;
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

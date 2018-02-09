package com.healthexpert.auth.doctor;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.healthexpert.auth.LoginActivity;
import com.healthexpert.common.Config;
import com.healthexpert.dashboard.MainActivity;
import com.healthexpert.R;
import com.healthexpert.common.BaseActivity;
import com.healthexpert.data.local.SharedPreferenceManager;
import com.healthexpert.data.remote.api.DoctorRestService;
import com.healthexpert.data.remote.models.requests.DoctorRegisterRequest;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;
import com.healthexpert.dispatcher.RetrofitObj;
import com.healthexpert.ui.widgets.BaseButton;
import com.healthexpert.ui.widgets.BaseEditText;
import com.healthexpert.ui.widgets.BaseRadioButton;
import com.healthexpert.ui.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;


/**
 * Created by Archish on 1/13/2017.
 */

public class DoctorRegisterActivity extends BaseActivity implements RegisterContract.RegisterView {
    BaseEditText etFullName, etEmailId, etPhoneNo, etPassword, etCPassword, etSpeciality, etCity, etPincode, etRegId, etExperince;
    BaseButton bRegister;
    RadioGroup rgGender;
    ImageView ivImage, ivCloseButton;
    FloatingActionButton fabAdd;
    FrameLayout fImage;

    BaseRadioButton rbGender;
    RegisterPresenter presenter;
    private static final int ADDRESS_CAMERA_IMAGE = 1850;
    private static final int ADDRESS_GALLERY_IMAGE = 1851;
    String path = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case ADDRESS_CAMERA_IMAGE:
                    Toast.makeText(getApplicationContext(), "Image upload success", Toast.LENGTH_SHORT).show();
                    Uri cameraImageUri = Uri.fromFile(new File(path));
                    Picasso.with(getApplicationContext()).load(cameraImageUri).fit().into(ivImage);
                    fImage.setVisibility(View.VISIBLE);
                    break;
                case ADDRESS_GALLERY_IMAGE:
                    Uri selectedImageGallery = data.getData();
                    fImage.setVisibility(View.VISIBLE);
                    path = getRealPathFromURI(selectedImageGallery);
                    Picasso.with(getApplicationContext()).load(selectedImageGallery).fit().into(ivImage);
                    fImage.setVisibility(View.VISIBLE);

            }

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void showImageDialog() {
        final Dialog dialog = new Dialog(DoctorRegisterActivity.this);
        dialog.setContentView(R.layout.dialog_image);
        dialog.setTitle("Select Image");
        BaseTextView tvTakePhoto = (BaseTextView) dialog.findViewById(R.id.tvTakePhoto);
        BaseTextView tvGallery = (BaseTextView) dialog.findViewById(R.id.tvGallery);
        dialog.show();
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                dialog.dismiss();
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, ADDRESS_GALLERY_IMAGE);
                dialog.dismiss();
            }
        });

    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + Config.IMAGE_DIRECTORY_NAME);

        if (!folder.exists()) {
            folder.mkdir();
        }
        final Calendar c = Calendar.getInstance();
        String dateTime = c.get(Calendar.DAY_OF_MONTH) + "-"
                + ((c.get(Calendar.MONTH)) + 1) + "-"
                + c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.HOUR) + "-"
                + c.get(Calendar.MINUTE) + "-"
                + c.get(Calendar.SECOND);
        path = String.format(Environment.getExternalStorageDirectory() + "/" + Config.IMAGE_DIRECTORY_NAME + "/%s.png",
                dateTime);

        File photo = new File(path);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, ADDRESS_CAMERA_IMAGE);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        if (new SharedPreferenceManager(getApplicationContext()).getMainPage() != 0) {
            Intent i = new Intent(DoctorRegisterActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        initViews();
        DoctorRestService doctorRestService = RetrofitObj.getInstance().create(DoctorRestService.class);
        presenter = new RegisterPresenter(doctorRestService, this);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean status = validate();
                if (status) {
                    showProgressDialog();
                    presenter.registerDoctor(new DoctorRegisterRequest(etFullName.getText().toString(), etEmailId.getText().toString(),
                            etSpeciality.getText().toString(), etCity.getText().toString(), etPincode.getText().toString()
                            , etPhoneNo.getText().toString(), etPassword.getText().toString(), etRegId.getText().toString()
                            , rbGender.getText().toString(), etExperince.getText().toString()));
                }


            }
        });

    }

    private void initViews() {
        etFullName = (BaseEditText) findViewById(R.id.etFullName);
        etPhoneNo = (BaseEditText) findViewById(R.id.etPhoneno);
        etEmailId = (BaseEditText) findViewById(R.id.etEmailId);
        etCity = (BaseEditText) findViewById(R.id.etCity);
        etPincode = (BaseEditText) findViewById(R.id.etPincode);
        etSpeciality = (BaseEditText) findViewById(R.id.etSpeciality);
        etPassword = (BaseEditText) findViewById(R.id.etPassword);
        etCPassword = (BaseEditText) findViewById(R.id.etCPassword);
        etRegId = (BaseEditText) findViewById(R.id.etRegisterationNo);
        etExperince = (BaseEditText) findViewById(R.id.etExperience);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        bRegister = (BaseButton) findViewById(R.id.bRegister);
        ivCloseButton = (ImageView) findViewById(R.id.ivCloseButton);
        fImage = (FrameLayout) findViewById(R.id.fImage);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });
        ivCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fImage.setVisibility(View.GONE);
                path = "";
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = rgGender.getCheckedRadioButtonId();
                rbGender = (BaseRadioButton) findViewById(selectedId);

            }
        });

    }

    private boolean validate() {
        if (etFullName.getText().toString().isEmpty()) {
            etFullName.setError("First name cannot be empty");
            etFullName.setFocusable(true);
            return false;
        } else if (etCity.getText().toString().isEmpty()) {
            etCity.setError("City cannot be empty");
            etCity.setFocusable(true);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmailId.getText().toString()).matches()) {
            etEmailId.setError("Invalid Email id");
            etEmailId.setFocusable(true);

            return false;
        } else if (etPhoneNo.getText().toString().isEmpty() || etPhoneNo
                .getText().length() < 10) {
            etPhoneNo.setError("Mobile number should be of 10 digits");
            etPhoneNo.setFocusable(true);

            return false;
        } else if (etPassword.getText().toString().isEmpty() || etPassword.getText().length() < 6) {
            etPassword.setError("Password length should be more than 6 characters");
            etPassword.setFocusable(true);
            return false;
        } else if (!etPassword.getText().toString().equals(etCPassword.getText().toString())) {
            etCPassword.setError("Password doesn't match");
            etCPassword.setFocusable(true);
            return false;
        }

        return true;
    }


    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
        Log.d("ServerException", String.valueOf(e));
    }

    @Override
    public void onDoctorRegister(UserRegisterResponse userResponse) {
        dismissProgressDialog();
        boolean status = userResponse.isStatus();
        if (status) {
            Toast.makeText(DoctorRegisterActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(DoctorRegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(DoctorRegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPatientRegister(UserRegisterResponse userResponse) {

    }
}

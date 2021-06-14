package com.wa.rumbo.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.callbacks.GetUserProfileCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.GetUserProfileModel;
import com.wa.rumbo.model.User_Post_Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;
import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;


public class Profile_and_Settings_Frag extends Fragment {

    @BindView(R.id.tv_resume_key)
    TextView tv_resume_key;

    @BindView(R.id.tv_reg_email)
    TextView tv_reg_email;

    @BindView(R.id.tv_blocklist)
    TextView tv_blocklist;

    @BindView(R.id.tvEditEmail)
    TextView tvEditEmail;

    @BindView(R.id.tvChangeImage)
    TextView tvChangeImage;

    @BindView(R.id.tv_preservation)
    TextView tvSave;
    @BindView(R.id.edt_nick_name)
    EditText etName;

    @BindView(R.id.edt_self_intro_comment)
    EditText etIntroduction;

    @BindView(R.id.civ_user_profile_pic)
    CircleImageView ivProfile;

    String userProfileImage = "";

    @BindView(R.id.img_back)
    ImageView ivBack;
    private int PERMISSION_REQUEST_CODE = 101;
    Animation clickAnimation;
    Retrofit retrofit = RetrofitInstance.getClient();

    Dialog mDialog;
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    CommonData commonData;

    GetUserProfileModel.UserDetails userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_and_settings, container, false);

        ButterKnife.bind(this, view);
        UsefullData.setLocale(getActivity());
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        mDialog = UsefullData.getProgressDialog(getActivity());
        commonData = new CommonData(getActivity());

        tvEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new ChangeEmailPasswordFragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();

            }
        });

        tv_resume_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Resume_Key_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();

            }
        });

        tv_reg_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Register_EmailAddress_Fragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        tv_blocklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new MyPageBlockList_Fragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                getActivity().onBackPressed();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
               /* if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_your_nick_name), Toast.LENGTH_SHORT).show();
                } else if (etIntroduction.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_your_introduction), Toast.LENGTH_SHORT).show();
                } else {*/


                    if (!userProfileImage.isEmpty() && userProfileImage.equals("")) {
                        userProfileImage = userData.getImage();
                    }
                    new Api(getActivity()).updateUserProfile(etName.getText().toString(), userData.getEmail(), userData.getPassword(), etIntroduction.getText().toString(), userProfileImage, "false");
               // }
            }
        });
        tvChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                if (checkPermission()) {
                    selectImage();
                } else {
                    requestPermission();
                }
            }
        });

        new Api(getActivity()).getUserProfile(commonData.getString(USER_ID),true, new GetUserProfileCallback() {
            @Override
            public void onResponse(GetUserProfileModel model) {
                etName.setText(model.getUserDetails().get(0).getUserName());
                etIntroduction.setText(model.getUserDetails().get(0).getIntroduction());

                UsefullData.decodeBase64AndSetCircleImage(getActivity(), model.getUserDetails().get(0).getImage(), ivProfile);

                userData = new GetUserProfileModel.UserDetails();
                userData.setUserId(model.getUserDetails().get(0).getUserId());
                userData.setUserName(model.getUserDetails().get(0).getUserName());
                userData.setEmail(model.getUserDetails().get(0).getEmail());
                userData.setPassword(model.getUserDetails().get(0).getPassword());
                userData.setImage(model.getUserDetails().get(0).getImage());
                userData.setIntroduction(model.getUserDetails().get(0).getIntroduction());
            }
        });
        return view;

    }

    private void selectImage() {
        final CharSequence[] options = {getActivity().getResources().getString(R.string.take_photo), getActivity().getResources().getString(R.string.choose_from_gallery), getActivity().getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getResources().getString(R.string.add_photo));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getActivity().getResources().getString(R.string.take_photo))) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals(getActivity().getResources().getString(R.string.choose_from_gallery))) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals(getActivity().getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                    }

                    // Snackbar.make(getActivity(), "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        // Snackbar.make(getActivity(), "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                              /*  showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });*/
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");

                    ivProfile.setImageBitmap(bitmap);
                    Uri myUri = getImageUriFromBitmap(bitmap);
                    userProfileImage = myUri.toString();

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    ivProfile.setImageURI(selectedImage);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //    userProfileImage =  getBase64String(bitmap);
                    Uri myUri = getImageUriFromBitmap(bitmap);
                    userProfileImage = myUri.toString();
                }
                break;
        }
    }

    public Uri getImageUriFromBitmap(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getBase64String(Bitmap imageBitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void decodeBase64AndSetImage(String completeImageData, CircleImageView imageView) {
        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            imageView.setImageURI(imageUri);
        }
    }


    /* public void getUserPostList() {
     *//*  final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message*//*
        mDialog.show();

        Call call = register_interfac.getUserPostList(commonData.getString(USER_ID), commonData.getString(TOKEN), commonData.getString(USER_ID));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Log.e("profile details == ", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("profile details", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }*/
}
package com.chrisstek.geekquizz;

import static com.chrisstek.geekquizz.utils.PermissionUtils.isPermissionReadExternalStorageApproved;
import static com.chrisstek.geekquizz.utils.UtilInternetConnection.isOnline;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.chrisstek.geekquizz.client.QuizzClient;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.User;
import com.chrisstek.geekquizz.interactor.LoginInteractor;
import com.chrisstek.geekquizz.utils.ImageFilePath;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.chrisstek.geekquizz.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel mainActivityViewModel;
    private AdView mAdview;
    private static final String APP_NAME = "AppName";
    private static final int PICK_IMAGE = 100;
    private TextView tvUserName;
    private TextView tvEmail;
    private TextView tvGems;
    private TextView tvTotalScore;
    private CircleImageView imgAvatar;
    private ImageView imgDragonBalls;
    private User userActual;
    private ImageView imgShenglong;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel.MainActivityViewModelFactory mainActivityViewModelFactory = new MainActivityViewModel.MainActivityViewModelFactory(new LoginInteractor(new QuizzClient()),((GeekQuizzApplication) getApplication()).appContainer.purchaseRepository);
        mainActivityViewModel = new ViewModelProvider(this, mainActivityViewModelFactory).get(MainActivityViewModel.class);

        // Allows billing to refresh purchases during onResume
        getLifecycle().addObserver(mainActivityViewModel.getBillingLifecycleObserver());

        //setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_purchase)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        tvEmail = headerView.findViewById(R.id.tv_email);
        tvUserName = headerView.findViewById(R.id.tv_username);
        tvGems = headerView.findViewById(R.id.tv_gemas);
        tvTotalScore = headerView.findViewById(R.id.tv_score);
        imgAvatar = headerView.findViewById(R.id.imgAvatar);
        imgDragonBalls = headerView.findViewById(R.id.imgEsferas);
        userActual = (User) getIntent().getSerializableExtra("user");
        if (Build.VERSION.SDK_INT >= 23 && checkExternalStoragePermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        }
        imgShenglong = findViewById(R.id.shenglong);
        frameLayout = findViewById(R.id.mainFrame);

        mainActivityViewModel.getMessages().observe(this, resId -> {
            Snackbar snackbar = Snackbar.make(binding.drawerLayout, getString(resId), Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
        mainActivityViewModel.getMessagesViewModel().observe(this, message -> {
            Snackbar snackbar = Snackbar.make(binding.drawerLayout, message, Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
        //Set default Avatar
        mainActivityViewModel.setImageAvatar(BitmapFactory.decodeResource(getResources(), R.drawable.ic_account_circle_white_48dp));
        cargarPublicidad();
        imgDragonBalls.setOnClickListener(v -> {
            if (userActual.getEsferas() == 7) {
                invokeShenlong(userActual.getUserName(),userActual.getPassword(),userActual.getIdUser(), userActual.getCoins() + 10000);
            } else {
                mainActivityViewModel.setErrorMessage(getString(R.string.msgErrorShenlong));
            }
        });
        imgAvatar.setOnClickListener( v -> {
            openGallery(userActual.getUserName(),userActual.getPassword(),userActual.getIdUser());
        });
        setUser();
    }


    private void setUser(){
        tvUserName.setText(userActual.getUserName());
        tvEmail.setText(userActual.getEmail());
        tvGems.setText(String.valueOf(userActual.getCoins()));
        tvTotalScore.setText(getString(R.string.score, String.valueOf(userActual.getTotalScore())));
        decodeAvatar(userActual);
        renderSferas(userActual);
    }

    private boolean checkExternalStoragePermission() {
        return ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private void cargarPublicidad() {
        if (isOnline(getApplicationContext())) {
            MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

                }
            });
            mAdview = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdview.loadAd(adRequest);
            mAdview.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // Load the next interstitial.
                    mAdview.loadAd(new AdRequest.Builder().build());
                }
            });

        } else {
            ImageView imgPubli = findViewById(R.id.img_publi_no_internet);
            imgPubli.setVisibility(View.VISIBLE);
        }
    }

    public void refreshUserData(String userName, String pass) {
        if (isOnline(getApplicationContext())) {
            mainActivityViewModel.onLogin(userName, pass);
            renderLogin();
        } else {
            mainActivityViewModel.setErrorMessage(getString(R.string.noInternet));
        }
    }

    public void renderLogin() {
        LoginResponse loginResponse = mainActivityViewModel.getLoginResponse().getValue();
        if (loginResponse != null) {

            User user = loginResponse.getUser();
            if (user != null) {
                String sName = getIntent().getStringExtra("name");
                String sEmail = getIntent().getStringExtra("email");
                tvUserName.setText((sName != null) ? sName : user.getName());
                tvEmail.setText((sEmail != null) ? sEmail : user.getEmail());
                tvTotalScore.setText(getString(R.string.score, String.valueOf(user.getTotalScore())));
                tvGems.setText(String.valueOf(user.getCoins()));
                renderSferas(user);
                userActual = user;
                if (!user.getUrlImageUser().equals("")){
                    decodeAvatar(user);
                } else {
                    imgAvatar.setImageBitmap(mainActivityViewModel.getImgAvatar());
                }
            }
        }
    }

    private void renderSferas(User user){
        switch (user.getEsferas()) {
            case 1:
                imgDragonBalls.setVisibility(View.VISIBLE);
                imgDragonBalls.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.esferas1));
                break;
            case 2:
                imgDragonBalls.setVisibility(View.VISIBLE);
                imgDragonBalls.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.esferas2));
                break;
            case 3:
                imgDragonBalls.setVisibility(View.VISIBLE);
                imgDragonBalls.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.esferas3));
                break;
            case 4:
                imgDragonBalls.setVisibility(View.VISIBLE);
                imgDragonBalls.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.esferas4));
                break;
            case 5:
                imgDragonBalls.setVisibility(View.VISIBLE);
                imgDragonBalls.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.esferas5));
                break;
            case 6:
                imgDragonBalls.setVisibility(View.VISIBLE);
                imgDragonBalls.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.esferas6));
                break;
            case 7:
                imgDragonBalls.setVisibility(View.VISIBLE);
                imgDragonBalls.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.esferas7));
                break;
            default:
                imgDragonBalls.setVisibility(View.INVISIBLE);
                break;
        }
    }
    private void decodeAvatar(User user){
        if (user.getUrlImageUser() == null || user.getUrlImageUser().isEmpty()){
            imgAvatar.setImageBitmap(mainActivityViewModel.getImgAvatar());
        } else {
            byte[] decodedAvatar = Base64.decode(user.getUrlImageUser(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedAvatar, 0, decodedAvatar.length);
            imgAvatar.setImageBitmap(decodedByte);
        }
    }

    private void openGallery(String userName, String pass, int idUser) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (isPermissionReadExternalStorageApproved(getApplicationContext())) {galleryFilter(userName, pass, idUser);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            galleryFilter(userName, pass, idUser);
        }
    }

    public void galleryFilter(String userName, String pass, int idUser) {
        List<Intent> targetGalleryIntents = new ArrayList<>();
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        PackageManager pm = getApplicationContext().getPackageManager();
        List<ResolveInfo> resInfos = pm.queryIntentActivities(galleryIntent, 0);
        if (!resInfos.isEmpty()) {
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;

                if (!packageName.contains("com.google.android.apps.photos") && !packageName.equals("com.google.android.apps.plus")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.putExtra(APP_NAME, resInfo.loadLabel(pm).toString());
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.setPackage(packageName);
                    targetGalleryIntents.add(intent);
                }
            }

            if (!targetGalleryIntents.isEmpty()) {
                Collections.sort(targetGalleryIntents, (o1, o2) -> o1.getStringExtra(APP_NAME).compareTo(o2.getStringExtra(APP_NAME)));
                Intent chooserIntent = Intent.createChooser(targetGalleryIntents.remove(0), "Abrir Galeria");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetGalleryIntents.toArray(new Parcelable[]{}));
                mGetContent.launch(chooserIntent);

            } else {
                mainActivityViewModel.setErrorMessage("No se encontro la galeria");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                try {
                    Uri imageUri = Objects.requireNonNull(result.getData()).getData();

                    String selectedImagePath;
                    if (Build.VERSION.SDK_INT >= 23) {
                        selectedImagePath = ImageFilePath.getPath(getApplication(), imageUri);
                    } else {
                        selectedImagePath = imageUri.toString();
                    }
                    File fileImage = new File(Objects.requireNonNull(selectedImagePath));
                    Long lSizeImage = getImageSizeInKb(fileImage.length());
                    if (lSizeImage < 2000) {
                        if (isOnline(getApplicationContext())) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            mainActivityViewModel.setImageAvatar(bitmap);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageBytes = baos.toByteArray();
                            String sImgBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            Log.i("BASE64", sImgBase64);
                            mainActivityViewModel.setB64(sImgBase64);
                            if (null != mainActivityViewModel.getB64()){
                                mainActivityViewModel.onUpdateAvatar(userActual.getUserName(),userActual.getPassword(),userActual.getIdUser(),mainActivityViewModel.getB64());
                                refreshUserData(userActual.getUserName(),userActual.getPassword());
                            }
                        } else {
                            mainActivityViewModel.setErrorMessage(getString(R.string.noInternet));
                        }
                    } else {
                        mainActivityViewModel.setErrorMessage(getString(R.string.msgImagenError));
                    }
                } catch (IOException e) {
                    Log.e("Error-", e.getMessage());
                }
            }
        }

        public Long getImageSizeInKb(Long imageLength) {
            if (imageLength <= 0) {
                return 0L;
            } else {
                return imageLength / 1024;
            }
        }
    });

    private void invokeShenlong(String userName, String pass, int idUser, int gemas){
        mainActivityViewModel.onUpdateGems(userName, pass, idUser, gemas);
        refreshUserData(userName, pass);
        frameLayout.setVisibility(View.GONE);
        imgShenglong.setVisibility(View.VISIBLE);
        imgShenglong.setBackgroundResource(R.drawable.animation_shenlong);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) imgShenglong.getBackground();
        animationDrawable.setOneShot(true);

        if (!animationDrawable.isRunning()) {

            int totalFrameDuration = 0;
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                totalFrameDuration += animationDrawable.getDuration(i);
            }
            animationDrawable.start();

            new Handler().postDelayed(animationDrawable::stop, totalFrameDuration);

        }
        mainActivityViewModel.onUpdateEsferas(userName, pass, idUser, gemas);
        refreshUserData(userName, pass);
        imgShenglong.setOnClickListener(v -> {
            if (!animationDrawable.isRunning()) {
                imgShenglong.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
            Toast.makeText(getApplicationContext(), "Los permisos han sido otorgados correctamente", Toast.LENGTH_LONG).show();
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
            Toast.makeText(getApplicationContext(), "Debe de dar permisas para realizar esta funcion", Toast.LENGTH_LONG).show();
        }
    });
}
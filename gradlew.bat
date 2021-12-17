package agreeta.com.ltfarmerapplication.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import agreeta.com.ltfarmerapplication.R;
import agreeta.com.ltfarmerapplication.activities.ActivityLogin;
import agreeta.com.ltfarmerapplication.api.ApiListener;
import agreeta.com.ltfarmerapplication.api.HttpVolleyRequest;
import agreeta.com.ltfarmerapplication.app.MyApp;
import agreeta.com.ltfarmerapplication.base.BaseFragment;
import agreeta.com.ltfarmerapplication.models.DeviceDetailModel;
import agreeta.com.ltfarmerapplication.models.ModelRegisteredFarmer;
import agreeta.com.ltfarmerapplication.utils.Constants;
import agreeta.com.ltfarmerapplication.utils.IOUtils;
import agreeta.com.ltfarmerapplication.utils.SHA256Algorithm;


public class RegisterFragment extends BaseFragment {
    
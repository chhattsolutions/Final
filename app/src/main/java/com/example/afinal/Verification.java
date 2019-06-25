package com.example.afinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.afinal.Model.User;
import com.example.afinal.float_button.Addtwo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {

     Spinner Agent;
     EditText Company_name,Ph_no;
     Button btn_next,mBtnCreate;
    String mVerificationId;
     DatabaseReference reference;
     private HelpingMethods helpingMethods;
    FirebaseUser fuser;
    private CardView mStep1, mStep2, mStep3;
    StateProgressBar stateProgressBar;
    ImageView profileImage;
    private EditText mName, mEmail, mPassword, mPhone, codeText;
    private ProgressDialog mPrgress;
    private static final String TAG = "PhoneAuthActivity";
     HelpingMethods helpingMethod;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
     CountryCodePicker ccp;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helpingMethod = new HelpingMethods(Verification .this);
        getSupportActionBar().setTitle("Chhatt (چھت)");
        Agent= (Spinner) findViewById(R.id.corner_spinner1);
        Company_name=(EditText) findViewById(R.id.facing_spinner);
        Ph_no=(EditText) findViewById(R.id.view_spinner);
        mStep1 = findViewById(R.id.step1_container);
        mStep2 = findViewById(R.id.step2_container);
        mStep3 = findViewById(R.id.step3_container);
        stateProgressBar = findViewById(R.id.your_state_progress_bar_id);
        profileImage = findViewById(R.id.profile_image1);
        mBtnCreate = findViewById(R.id.button_create);
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        mName = findViewById(R.id.editText_name);
        mEmail = findViewById(R.id.editText_email);
        codeText = findViewById(R.id.pinView);
        mAuth = FirebaseAuth.getInstance();
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(Ph_no);

        btn_next=(Button) findViewById(R.id.next_button);
        mPrgress = new ProgressDialog(this);
        String[] AgentList = new String[]{"Select Number Of Agent", "1", "2", "3", "4", "5",
                "6", "7"};
        ArrayAdapter<String> categoryArray = new ArrayAdapter<String>(Verification.this, android.R.layout.simple_spinner_item, AgentList);
        categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Agent.setAdapter(categoryArray);

        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             final String Companyname=Company_name.getText().toString();
                final String agentno=Agent.getSelectedItem().toString();
                final String phone=Ph_no.getText().toString();
                fuser= FirebaseAuth.getInstance().getCurrentUser();
                reference=FirebaseDatabase.getInstance().getReference("Info").child(fuser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("infoid",fuser.getUid());
                        hashMap.put("Agent",agentno);
                        hashMap.put("Company",Companyname);
                        hashMap.put("Phone",phone);
                        reference.setValue(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(a);
                finish();


            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mPhone.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {

                }


            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "onCodeSent:" + s);
                mVerificationId = s;
                mResendToken = forceResendingToken;
            }
        };



    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Verification.this, "Mubarak ho", Toast.LENGTH_SHORT).show();
                            mAuth.getCurrentUser().delete();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                codeText.setError("Invalid code.");
                            }
                        }
                    }
                });

    }
    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks);
    }


    public void setupVerifycode(View view) {
        if (!TextUtils.isEmpty(codeText.getText().toString())) {
            String code = codeText.getText().toString();
            verifyPhoneNumberWithCode(mVerificationId, code);
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            mStep2.setVisibility(View.GONE);
            mStep3.setVisibility(View.VISIBLE);
            setenameemail();
        } else {
            helpingMethod.snackbarMessage("Please enter verification code.", view);
        }


    }

    private void setenameemail() {

        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                mName.setText(fuser.getDisplayName());
                mEmail.setText(fuser.getEmail());
                Glide.with(getApplicationContext()).load(fuser.getPhotoUrl().toString()).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    public void setupResendcode(View view){

    }


    public void confirmeed(View view) {
    Validate(view);
     if (Validate(view)){
         mPrgress.setTitle("Sending code");
         mPrgress.setMessage("Please wait while we send you a verification code");
         mPrgress.setCancelable(false);
         mPrgress.show();
         TextView p = findViewById(R.id.textView_phoneno);
         p.setText(ccp.getFullNumberWithPlus());
         startPhoneNumberVerification(ccp.getFullNumberWithPlus());
         mStep1.setVisibility(View.GONE);
         mStep2.setVisibility(View.VISIBLE);
         stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
         mPrgress.dismiss();
     }

    }

    private boolean Validate(View view) {

        if (TextUtils.isEmpty(Ph_no.getText().toString())){
            helpingMethod.snackbarMessage("Please enter your phone number.", view);
            return false;
        }
         if (Agent.getSelectedItem().toString().trim().equals("Select Number Of Agent")) {
            helpingMethod.snackbarMessage("Please select any number of agents.", view);
            return false;
        }
         if (TextUtils.isEmpty(Company_name.getText().toString())){
            helpingMethod.snackbarMessage("Please enter your Company name.", view);
            return false;
        }
        else{
            return true;
        }
    }

    public void setupSignup(View view) {

    }
}

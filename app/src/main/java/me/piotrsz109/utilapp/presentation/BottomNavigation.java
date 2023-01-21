package me.piotrsz109.utilapp.presentation;

import static android.hardware.biometrics.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED;
import static android.hardware.biometrics.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE;
import static android.hardware.biometrics.BiometricPrompt.BIOMETRIC_ERROR_HW_UNAVAILABLE;

import android.content.Intent;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.Executor;

import me.piotrsz109.utilapp.R;

public class BottomNavigation extends Fragment {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private BottomNavigationView navigationView;

    public BottomNavigation() {
        // Required empty public constructor
    }

    public static BottomNavigation newInstance(int defaultItemId) {
        BottomNavigation fragment = new BottomNavigation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        int currentItem = R.id.weatherPage;
        if(getActivity() instanceof NotesActivity)
            currentItem = R.id.notesPage;

        navigationView = view.findViewById(R.id.bottomMenu);

        navigationView.setSelectedItemId(currentItem);

        int finalCurrentItem = currentItem;
        navigationView.setOnItemSelectedListener(item -> {
            if (finalCurrentItem == item.getItemId()) return true;

            switch (item.getItemId()) {
                case R.id.weatherPage:
                    startActivity(new Intent(view.getContext(), WeatherActivity.class));
                    break;
                case R.id.notesPage:
                    goToNotes();
                    break;
            }
            return true;
        });

        return view;
    }

    public void setWeatherIconAsActive() {
        navigationView.setSelectedItemId(R.id.weatherPage);
    }

    private void goToNotes() {
        executor = ContextCompat.getMainExecutor(this.getContext());

        BottomNavigation nav = this;
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                nav.setWeatherIconAsActive();
                switch (errorCode) {
                    case BIOMETRIC_ERROR_NONE_ENROLLED:
                        Toast.makeText(BottomNavigation.this.getContext(), getText(R.string.no_biometrics), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BottomNavigation.this.getContext(), NotesActivity.class));
                        break;
                    case BIOMETRIC_ERROR_NO_HARDWARE:
                        Toast.makeText(BottomNavigation.this.getContext(), getText(R.string.no_biometric_hardware), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BottomNavigation.this.getContext(), NotesActivity.class));
                        break;
                    case BIOMETRIC_ERROR_HW_UNAVAILABLE:
                        Toast.makeText(BottomNavigation.this.getContext(), getText(R.string.biometric_hardware_unavailable), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(BottomNavigation.this.getContext(), NotesActivity.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(BottomNavigation.this.getContext(), getText(R.string.authentication_failed), Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getText(R.string.biometrics_title))
                .setDescription(getText(R.string.biometrics_description))
                .setConfirmationRequired(false)
                //.setNegativeButtonText(getString(R.string.NegativeBiometrics))
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG
                        | BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.DEVICE_CREDENTIAL).build();

        biometricPrompt.authenticate(promptInfo);
    }
}
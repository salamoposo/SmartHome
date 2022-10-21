package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Switch lampu, kipas, ac, pintu;
    TextView statusKunci, statusLampu, statusAc, statusKipas;
    ImageView logoPintu, logoAc, logoLampu, logoKipas, profile;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref_kontrol, ref_ac, ref_kipas, ref_lampu, ref_pintu;
    String path = "kontrol";
    String Nyala = "ON", Mati = "OFF";
    int i ;


    @Override
    public void onBackPressed() {
        i++;
        if (i==1){
            Toast.makeText(this, "Tekan sekali lagi untuk keluar!", Toast.LENGTH_SHORT).show();
        }else if(i==2){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lampu = findViewById(R.id.sw_lampu1);
        kipas = findViewById(R.id.sw_kipas);
        ac = findViewById(R.id.sw_ac);
        pintu = findViewById(R.id.sw_kuncipintu);

        logoAc = findViewById(R.id.logoac);
        logoKipas = findViewById(R.id.logokipas);
        logoPintu = findViewById(R.id.logopintu);
        logoLampu = findViewById(R.id.logolampu);
        profile = findViewById(R.id.profile);

        statusAc = findViewById(R.id.textstatus_ac);
        statusKipas = findViewById(R.id.status_kipas);
        statusKunci = findViewById(R.id.status_kunci);
        statusLampu = findViewById(R.id.status_lampu1);

        ref_pintu = database.getReference(path).child("pintu");
        ref_ac = database.getReference(path).child("ac");
        ref_kipas = database.getReference(path).child("kipas");
        ref_lampu = database.getReference(path).child("lampu");
        ref_kontrol = database.getReference(path);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                finish();
            }
        });

        ref_kontrol.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StatusKontrol statusKontrol = snapshot.getValue(StatusKontrol.class);
                if (statusKontrol != null) {
                    String slampu = statusKontrol.lampu;
                    String skipas = statusKontrol.kipas;
                    String spintu = statusKontrol.pintu;
                    String sac = statusKontrol.ac;

                    if (Objects.equals(slampu, Nyala)) {
                        lampu.setChecked(true);
                    } else {
                        lampu.setChecked(false);
                    }

                    if (Objects.equals(skipas, Nyala)) {
                        kipas.setChecked(true);
                    } else {
                        kipas.setChecked(false);
                    }

                    if (Objects.equals(spintu, Nyala)) {
                        pintu.setChecked(true);
                    } else {
                        pintu.setChecked(false);
                    }

                    if (Objects.equals(sac, Nyala)) {
                        ac.setChecked(true);
                    } else {
                        ac.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lampu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (lampu.isChecked()) {
                    ref_lampu.setValue(Nyala).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                logoLampu.setImageResource(R.drawable.lamp_on);
                                statusLampu.setText("Nyala");
//                                Toast.makeText(MainActivity.this, "Lampu Nyala", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Gagal, Koneksi Buruk", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    ref_lampu.setValue(Mati);
                    logoLampu.setImageResource(R.drawable.lampoff);
                    statusLampu.setText("Mati");
//                    Toast.makeText(MainActivity.this, "Lampu Mati", Toast.LENGTH_SHORT).show();
                }
            }
        });

        kipas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (kipas.isChecked()) {
                    ref_kipas.setValue(Nyala).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                logoKipas.setImageResource(R.drawable.fan_on);
                                statusKipas.setText("Nyala");
//                                Toast.makeText(MainActivity.this, "Kipas Nyala", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Gagal, Koneksi Buruk", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    ref_kipas.setValue(Mati);
                    logoKipas.setImageResource(R.drawable.fan_off);
                    statusKipas.setText("Mati");
//                    Toast.makeText(MainActivity.this, "Kipas Mati", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ac.isChecked()) {
                    ref_ac.setValue(Nyala).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                logoAc.setImageResource(R.drawable.ac_on);
                                statusAc.setText("Nyala");
//                                Toast.makeText(MainActivity.this, "AC Nyala", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Gagal, Koneksi Buruk", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    ref_ac.setValue(Mati);
                    logoAc.setImageResource(R.drawable.ac_off);
                    statusAc.setText("Mati");
                }
            }
        });

        pintu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (pintu.isChecked()) {
                    ref_pintu.setValue(Nyala).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                logoPintu.setImageResource(R.drawable.door_open);
                                statusKunci.setText("Terbuka");
//                                Toast.makeText(MainActivity.this, "Pintu Terbuka", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Gagal, Koneksi Buruk", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ref_pintu.setValue(Mati);
                            pintu.setChecked(false);
                            logoPintu.setImageResource(R.drawable.door_close);
                            statusKunci.setText("Tertutup");
//                            Toast.makeText(MainActivity.this, "Pintu Terkunci", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);
                }
            }
        });

    }
}
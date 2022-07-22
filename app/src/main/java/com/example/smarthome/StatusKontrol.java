package com.example.smarthome;

public class StatusKontrol {
    public String ac, kipas, lampu, pintu;

    public StatusKontrol() {

    }

    public StatusKontrol(String Ac, String Kipas, String Pintu, String Lampu) {
        this.ac = Ac;
        this.kipas = Kipas;
        this.pintu = Pintu;
        this.lampu = Lampu;
    }
}

package com.example.vickyynico.wakeonlandv;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private EditText etIP, etMAC;
    private Button btnEncender;
    private String ip, mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setupUI();

        btnEncender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = etIP.getText().toString();
                mac = etMAC.getText().toString();
                if (mac.length() != 12 && !mac.equalsIgnoreCase("") && !ip.equalsIgnoreCase("") && !mac.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Datos invalidos", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.wakeup(ip, mac);
                }
            }
        });
    }

    private void setupUI() {
        etIP = (EditText) findViewById(R.id.etIP);
        etMAC = (EditText) findViewById(R.id.etMAC);
        btnEncender = (Button) findViewById(R.id.btnEncender);
    }

    private static byte[] getMacBytes(String mac) throws IllegalArgumentException {
        //Verifico que la MAC venga bien y lo convierto en un array de bytes
        byte[] bytes = new byte[6];
        if (mac.length() != 12) {
            throw new IllegalArgumentException("MAC invalido");
        }
        try {
            String hex;
            for (int i = 0; i < 6; i++) {
                hex = mac.substring(i * 2, i * 2 + 2);
                bytes[i] = (byte) Integer.parseInt(hex, 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hexadecimal invalido ");
        }
        return bytes;
    }

    public static void wakeup(String broadcastIP, String mac) {
        if (mac == null)
            return;

        try {
            byte[] macBytes = getMacBytes(mac);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }
            InetAddress address = InetAddress.getByName(broadcastIP);   //leo, y obtengo la ip en un formato determinado
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);    //creo un paquete de datagrama con datos, longitud de los datos, ip y puerto
            DatagramSocket socket = new DatagramSocket();   //creo un socket para enviar el paquete
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCotacao = findViewById(R.id.button_orcamento);
        Button buttonPedidos = findViewById(R.id.button_orcamento_antigos);
        Button buttonConfiguracao = findViewById(R.id.button_configuracoes);
        Button buttonFilamentos = findViewById(R.id.button_tiposdefilamento);

        final Intent cotacao = new Intent(this, Cotacao.class);

        //Intent pedidos = new Intent(this, Pedidos.class);

       // Intent configuracao = new Intent(this, Configuracao.class);

        //Intent filamentos = new Intent(this, Filamentos.class);

        buttonCotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cotacao);
            }
        });
        buttonPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(pedidos);
            }
        });
        buttonConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(configuracao);
            }
        });
        buttonFilamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(filamentos);
            }
        });

    }
}

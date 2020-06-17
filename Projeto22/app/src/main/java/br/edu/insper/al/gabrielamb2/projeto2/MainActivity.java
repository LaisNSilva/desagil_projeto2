package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonCotacao = findViewById(R.id.button_orcamento);
        Button buttonPedidos = findViewById(R.id.button_orcamento_antigos);
        Button buttonConfiguracao = findViewById(R.id.button_configuracoes);

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo_titulo);

        final Intent cotacao = new Intent(this, Cotacao.class);

        final Intent pedidos = new Intent(this, Pedidos.class);

        final Intent configuracao = new Intent(this, Configuracao.class);


        buttonCotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cotacao);
            }
        });
        buttonPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(pedidos);
            }
        });
        buttonConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(configuracao);
            }
        });

    }
}

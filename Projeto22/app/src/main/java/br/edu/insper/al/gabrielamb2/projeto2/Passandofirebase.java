package br.edu.insper.al.gabrielamb2.projeto2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Passandofirebase {
    int clicou;


    public Passandofirebase(int clicou){
        this.clicou= clicou;
    }
    public void atualizando(String cliente1, String infill1, Boolean supportRemoval1,Boolean vaporPolishing1,String layer1,String impressoras1,String materiais1,String mao_de_obra1,String peso1,String tempo1,String valor1) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Obtém uma referência para o caminho /Cliente do banco de dados.
        final DatabaseReference referenceCliente = database.getReference(clicou+ "/Cliente");

        // Obtém uma referência para o caminho /Infill do banco de dados.
        final DatabaseReference referenceInfill = database.getReference(clicou+ "/Infill (%)");

        // Obtém uma referência para o caminho /Support do banco de dados.
        final DatabaseReference referenceSupoort = database.getReference(clicou+ "/Supoort Removal");

        // Obtém uma referência para o caminho /Support do banco de dados.
        final DatabaseReference referencePolishing = database.getReference(clicou+ "/Vapor Polishing");

        // Obtém uma referência para o caminho /Layer do banco de dados.
        final DatabaseReference referenceLayer = database.getReference(clicou+ "/Layer");

        // Obtém uma referência para o caminho /Mão de Obra do banco de dados.
        final DatabaseReference referenceMaodeObra = database.getReference(clicou+ "/Mão de Obra");

        // Obtém uma referência para o caminho /Impressora do banco de dados.
        final DatabaseReference referenceimpressoras = database.getReference(clicou+ "/Impressora");

        // Obtém uma referência para o caminho /Impressora do banco de dados.
        final DatabaseReference referencemateriais = database.getReference(clicou+ "/Material");

        // Obtém uma referência para o caminho /peso do banco de dados.
        final DatabaseReference referencepeso = database.getReference(clicou+ "/Peso");

        // Obtém uma referência para o caminho /tempo do banco de dados.
        final DatabaseReference referencetempo = database.getReference(clicou+ "/Tempo");

        // Obtém uma referência para o caminho /valor do banco de dados.
        final DatabaseReference referencevalor = database.getReference(clicou+ "/Valor");

        referenceCliente.setValue(cliente1);
        referenceInfill.setValue(infill1);
        referenceSupoort.setValue(supportRemoval1);
        referencePolishing.setValue(vaporPolishing1);
        referenceLayer.setValue(layer1);
        referenceimpressoras.setValue(impressoras1);
        referencemateriais.setValue(materiais1);
        referenceMaodeObra.setValue(mao_de_obra1);
        referencepeso.setValue(peso1);
        referencetempo.setValue(tempo1);
        referencevalor.setValue(valor1);

    }
}

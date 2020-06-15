package br.edu.insper.al.gabrielamb2.projeto2;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RequestMulti {
    private HashMap<String, String> fields;
    private HashMap<String, HashMap> files;
    private String boundary;

    public RequestMulti(HashMap fields, HashMap files, String boundary) {
        this.fields = new HashMap<>();
        this.files = new HashMap<>();
        this.boundary = null;
    }

    public String transformada(String texto) {
        texto.replace("\0", "_");
        texto.replace("\r", "_");
        texto.replace("\n", "_");
        texto.replace("\"", "_");

        return texto;
    }


    public String buildMultipartPost(HashMap<String, Object> fields, HashMap<String, HashMap<String, String>> files, String boundary) {
        String output = "\n" + "\n";
        String material = "";
        String color = "";
        String layerHeight = "";
        String infillPercentage = "";
        String shipping = "";
        String configFile = "";
        String density = "";

        for (Map.Entry<String, Object> dic_interable : fields.entrySet()) {
            String key = dic_interable.getKey();
            String value = dic_interable.getValue().toString();
            String new_key = transformada(key);
            String new_value = transformada(value);

            if (new_key.equals("material")) {
                material += boundary + "\n" + "Content-Disposition: form-data; " +
                        "name=\"" + new_key + "\"\n\n" + new_value + "\n";

            } else if (new_key.equals("color")) {
                color += boundary + "\n" + "Content-Disposition: form-data; " +
                        "name=\"" + new_key + "\"\n\n" + new_value + "\n";

            } else if (new_key.equals("layerHeight")) {
                layerHeight += boundary + "\n" + "Content-Disposition: form-data; " +
                        "name=\"" + new_key + "\"\n\n" + new_value + "\n";

            } else if (new_key.equals("infillPercentage")) {
                infillPercentage += boundary + "\n" + "Content-Disposition: form-data; " +
                        "name=\"" + new_key + "\"\n\n" + new_value + "\n";

            } else if (new_key.equals("shipping")) {
                shipping += boundary + "\n" + "Content-Disposition: form-data; " +
                        "name=\"" + new_key + "\"\n\n" + new_value + "\n";

            } else if (new_key.equals("configFile")) {
                configFile += boundary + "\n" + "Content-Disposition: form-data; " +
                        "name=\"" + new_key + "\"\n\n" + new_value + "\n";

            } else if (new_key.equals("density")) {
                density += boundary + "\n" + "Content-Disposition: form-data; " +
                        "name=\"" + new_key + "\"\n\n" + new_value + "\n";
            }
        }
        output += material + color + layerHeight + infillPercentage + shipping + configFile + density;

        for (Map.Entry<String, HashMap<String, String>> dic_interable_files : files.entrySet()) {
            String key_files = dic_interable_files.getKey();
            HashMap value_files = dic_interable_files.getValue();

            String new_key_files = transformada(key_files);

            String name = transformada(value_files.get("name").toString());
            String type = transformada(value_files.get("type").toString());

            try {
                String tmp_name = (String) value_files.get("tmp_name");
                //byte[] tmp_name_byte = Files.readAllBytes((Path) value_files.get("tmp_name"));
                //String tmp_name = tmp_name_byte.toString();
                output += boundary + "\n" + "Content-Disposition: form-data; name=\"" + new_key_files + "\"; filename=\"" + name + "\"\n" + "Content-Type: " + type + "\n\n" + tmp_name + "\n";
            } catch (Exception e) {
                System.out.println("File error");

            }

        }
        return output + boundary + "--";
    }

    public String Request(Object request, String boundary) throws IOException {
        HttpParams params = new BasicHttpParams();
        HttpClient httpclient = new DefaultHttpClient(params);
        HttpPost post = new HttpPost("http://api.3dpartprice.com");
        HttpEntity entity = new StringEntity(request.toString());
        post.setEntity(entity);
        post.setHeader("Content-Type", "multipart/form-data; boundary=" + boundary.substring(2));
        HttpResponse response = httpclient.execute(post);
        HttpEntity resposta = response.getEntity();
        Log.d("request", response.getAllHeaders().toString());
        String responseString = EntityUtils.toString(resposta, "UTF-8");
        return responseString;

    }

    public String getJsonTempo(String jsonObject, String vel_impressora){
        String possivel = null;
        String tempo_final = null;

        try {
            for (int i = 0; i < jsonObject.length() - 14; i++) {
                String palavra = jsonObject.substring(i, i + 13);
                if (palavra.equals("printDuration")) {
                    possivel = jsonObject.substring(i + 13 + 25, i + 13 + 25 + 15);
                    break;
                }
            }
            if (possivel != null) {
                for (int c = 0; c < possivel.length(); c++) {
                    char letra = possivel.charAt(c);
                    if (letra == '"') {
                        String tempo = possivel.substring(0, c);
                        int velocidade = Integer.parseInt(vel_impressora);
                        int tempo_inteiro = Integer.parseInt(tempo)/60; //minutos
                        int tempo_variado = (tempo_inteiro*50)/velocidade;
                        tempo_final = String.valueOf(tempo_variado);
                        break;

                    }
                }
            }
        }catch (Exception e){
            System.out.println("");
        }
        return tempo_final;
    }

    public String getJsonPeso(String jsonObject){
        String peso = null;
        String pesopeca = null;
        try {
            for (int e = 0; e < jsonObject.length() - 60; e++) {
                String pesopalavra = jsonObject.substring(e, e + 12);
                if (pesopalavra.equals("filamentUsed")) {
                    pesopeca = jsonObject.substring(e + 11 + 22, e + 11 + 22 + 15);
                    break;
                }
            }
            if (pesopeca != null) {
                for (int d = 0; d < pesopeca.length(); d++) {
                    char pesoletra = pesopeca.charAt(d);
                    if (pesoletra == '.') {
                        peso = pesopeca.substring(1, d+3);
                        break;
                    }
                }
            }
        }catch (Exception e){
            System.out.println("COLOCAR AVISO");
        }
        return peso;
    }

    public Double calculaPreço(String tempo_string, String peso_string, String mao_de_obra_string, String hora_maquina_string, String preço_por_quilo_string){
        Double tempo = Double.valueOf(tempo_string)/60;  //horas
        Double peso = Double.valueOf(peso_string)/1000; // quilogramas
        Double preço_por_quilo = Double.valueOf(preço_por_quilo_string)/1000; // quilogramas
        Double mao_de_obra = Double.valueOf(mao_de_obra_string); // reais
        Double hora_maquina = Double.valueOf(hora_maquina_string); //horas

        Double preço = (tempo * hora_maquina) + (peso * preço_por_quilo) + mao_de_obra;

        return preço;

    }
}


package br.edu.insper.al.gabrielamb2.projetodelta;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Cep {
    //vai se comunicar com API via internet
    private static String readStream(InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) throws MalformedURLException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }
    public static Localizar localizar(String cep) throws JSONException, MalformedURLException {
        String resposta = request("http://viacep.com.br/ws/"+ cep +"/json/");
        JSONObject obj = new JSONObject(resposta);
        String loc = obj.getString("localidade");
        String nome = obj.getString("bairro");
        return new Localizar (loc, nome);
        //"http://viacep.com.br/ws/"+ cep +"/json/")
    }
}

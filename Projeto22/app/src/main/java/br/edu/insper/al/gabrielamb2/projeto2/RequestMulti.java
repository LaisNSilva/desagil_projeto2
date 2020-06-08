package br.edu.insper.al.gabrielamb2.projeto2;

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
    private HashMap<String, String>fields;
    private HashMap<String, HashMap>files;
    private String boundary;

    public RequestMulti(HashMap fields, HashMap files, String boundary){
        this.fields = new HashMap<>();
        this.files = new HashMap<>();
        this.boundary = null;
    }

    public String transformada(String texto){
        texto.replace("\0", "_");
        texto.replace("\r", "_");
        texto.replace("\n", "_");
        texto.replace("\"", "_");

        return texto;
    }


    public String buildMultipartPost(HashMap<String, Object> fields, HashMap<String, HashMap<String, String>> files, String boundary){
        String output = "";
        for (Map.Entry<String,Object> dic_interable : fields.entrySet()){
            String key = dic_interable.getKey();
            String value = dic_interable.getValue().toString();

            String new_key = transformada(key);
            String new_value = transformada(value);

            output += boundary + "\n" + "Content-Disposition: form-data; " +
                    "name=\"" + new_key + "\"\n\n" + new_value + "\n";
        }

        for (Map.Entry<String, HashMap<String, String>> dic_interable_files : files.entrySet()){
            String key_files = dic_interable_files.getKey();
            HashMap value_files = dic_interable_files.getValue();

            String new_key_files = transformada(key_files);

            String name = transformada(value_files.get("name").toString());
            String type = transformada(value_files.get("type").toString());

            try{
                String tmp_name = (String) value_files.get("tmp_name");
                output += boundary + "\n" + "Content-Disposition: form-data; name=\"" + new_key_files + "\"; filename=\"" + name + "\"\n" + "Content-Type: " + type + "\n\n" + tmp_name + "\n";
            }catch (Exception e){
                System.out.println("File error");

            }

        }
        return output+boundary+"--";
    }
    public String Request(Object request, String boundary) throws IOException {
        HttpParams params = new BasicHttpParams();
        HttpClient httpclient = new DefaultHttpClient(params);
        HttpPost post = new HttpPost("http://api.3dpartprice.com");
        HttpEntity entity = new StringEntity(request.toString());
        post.setEntity(entity);
        post.setHeader("Content-Type", "multipart/form-data; boundary="+boundary.substring(2));
        HttpResponse response = httpclient.execute(post);
        HttpEntity resposta = response.getEntity();
        String responseString = EntityUtils.toString(resposta, "UTF-8");
        return responseString;

    }
}

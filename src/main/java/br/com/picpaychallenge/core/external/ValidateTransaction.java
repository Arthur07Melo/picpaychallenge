package br.com.picpaychallenge.core.external;

import java.net.HttpURLConnection;
import java.net.URL;

public class ValidateTransaction {
    public boolean execute() throws Exception{
        URL url = new URL("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        if(status == 200) return true;
        return false;
    }
}

package GandyClient;

import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataManager {
	public JsonObject capesJson = null;
	public JsonObject youtubersJson = null;
	
	public DataManager () {
		
	}
	
	public void init () {
		fetchCapesData();
		fetchYoutuberData();
	}

	public void fetchCapesData () {
		String databaseUrl = Constants.CAPES_DATA_URL;
		/* SEND REQ TO FETCH CAPE DATA */
    	JsonParser jsonParser = new JsonParser();
		System.out.println("Reading: " + databaseUrl);
		TrustManager[] trustAllCerts = new TrustManager[]{
    		    new X509TrustManager() {
    		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    		            return null;
    		        }
    		        public void checkClientTrusted(
    		            java.security.cert.X509Certificate[] certs, String authType) {
    		        }
    		        public void checkServerTrusted(
    		            java.security.cert.X509Certificate[] certs, String authType) {
    		        }
    		    }
    		};

    		// Install the all-trusting trust manager
    		try {
    		    SSLContext sc = SSLContext.getInstance("SSL");
    		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    		} catch (Exception e) {
    		}
    	HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    	try {
    		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			HttpsURLConnection connection = (HttpsURLConnection) new URL(databaseUrl).openConnection();
			connection.connect();
			capesJson = jsonParser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void fetchYoutuberData () {
		String databaseUrl = Constants.YOUTUBER_DATA_URL;
		/* SEND REQ TO FETCH CAPE DATA */
    	JsonParser jsonParser = new JsonParser();
		System.out.println("Reading: " + databaseUrl);
		TrustManager[] trustAllCerts = new TrustManager[]{
    		    new X509TrustManager() {
    		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    		            return null;
    		        }
    		        public void checkClientTrusted(
    		            java.security.cert.X509Certificate[] certs, String authType) {
    		        }
    		        public void checkServerTrusted(
    		            java.security.cert.X509Certificate[] certs, String authType) {
    		        }
    		    }
    		};

    		// Install the all-trusting trust manager
    		try {
    		    SSLContext sc = SSLContext.getInstance("SSL");
    		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    		} catch (Exception e) {
    		}
    	HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    	try {
    		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			HttpsURLConnection connection = (HttpsURLConnection) new URL(databaseUrl).openConnection();
			connection.connect();
			youtubersJson = jsonParser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public JsonObject getCapesData () {
		return capesJson;
	}
	
	public JsonObject getYoutuberData () {
		return youtubersJson;
	}
	
}

package com.dynatrace.plugin.nginx;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.ssl.HttpsURLConnection;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;


public class NginxPlusMonitoringConnection {
	private HttpsURLConnection connection;

	public NginxPlusMonitoringConnection(String protocol, String host, int port, String file) throws IOException {
		try {
			URL url = new URL(protocol, host, port, file);
			this.connection = (HttpsURLConnection)url.openConnection();
			this.connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10));
			this.connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
		} catch(MalformedURLException e) {
			throw e;
		}
	}

	public JSONObject getStatusJson() throws IllegalArgumentException, IOException, JSONException {
		String header = connection.getHeaderField(HttpHeaders.CONTENT_TYPE);
		if (!MediaType.APPLICATION_JSON.equals(header)) {
			throw new IllegalArgumentException("Invalid response header, expected " + MediaType.APPLICATION_JSON + ", but got " + header);
		}
		InputStream inputStream = connection.getInputStream();
		String charset = "UTF-8";
		Scanner InputStreamScanner = new Scanner(inputStream, charset);
		String jsonString = InputStreamScanner.useDelimiter("\\A").next();
		InputStreamScanner.close();
		return new JSONObject(jsonString);
	}
}

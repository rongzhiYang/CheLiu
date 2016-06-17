package com.ccvee.cheliu.app.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ccvee.cheliu.BuildConfig;
import com.ccvee.cheliu.app.utils.StringUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class HttpService {

	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(30 * 1000);
		client.setTimeout(10 * 1000);
	}

	public static void getByText(String url, Map<String, String> params,
			TextHttpResponseHandler res) {
		LogUrl(url, params);
		if (params == null || params.size() <= 0) {
			client.get(url, res);
		} else {
			client.get(url, new RequestParams(params), res);
		}
	}

	public static void postByText(String url, Map<String, String> params,
			TextHttpResponseHandler res) {
		LogUrl(url, params);
		if (params == null || params.size() <= 0) {
			client.post(url, res);
		} else {
			client.post(url, new RequestParams(params), res);
		}
	}

	public static void postByFile(Context context, String url,
			RequestParams params, TextHttpResponseHandler res) {
		LogUrl(url, null);
		client.post(context, url, new RequestParams(params), res);
	}

	public static void LogUrl(String str, Map<String, String> params) {
		if (BuildConfig.DEBUG) {
			Log.i("==url==", StringUtil._MakeURL(str, params));
		}
	}

	public static void upload(final String actionUrl,
			final Map<String, String> params, final Map<String, File> files,
			final TextHttpResponseHandler res) {
		new Thread() {
			public void run() {
				try {
					JSONObject result = upload(actionUrl, params, files);
					if (res != null) {
						res.onSuccess(200, null, result.toString());
					}
				} catch (Exception e) {
					if (res != null) {
						res.onFailure(0, null, e.getMessage(), e);
					}
					e.printStackTrace();
				}
			};
		}.start();

	}

	public static JSONObject upload(String actionUrl,
			Map<String, String> params, Map<String, File> files)
			throws Exception {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000);
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);
		conn.setRequestMethod("POST"); // Post方式
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据
		if (files != null)
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: multipart/form-data; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			}
		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// 得到响应码
		@SuppressWarnings("unused")
		boolean success = conn.getResponseCode() == 200;
		InputStream in = conn.getInputStream();
		InputStreamReader isReader = new InputStreamReader(in);
		BufferedReader bufReader = new BufferedReader(isReader);
		String line = null;
		String data = "";
		while ((line = bufReader.readLine()) != null)
			data += line;

		outStream.close();
		conn.disconnect();
		return new JSONObject(data);
	}
}

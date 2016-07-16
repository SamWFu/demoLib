package httpClient;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName httpClient
 * @Date 16/7/12上午11:11
 * @Description 描述
 */
public class myhttp {

	/**
	 * 发送GET请求
	 *
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendGet(String url, Map<String, String> parameters) {

		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		String params = "";// 编码之后的参数
		try {
//			System.out.println("开始请求");
			long startInt = new Date().getTime();
			params = checkParam(parameters);
			String full_url = url + "?" + params;
			System.out.println(full_url);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("accept-encoding", "gzip,deflate");
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
			Map<String, List<String>> headers = httpConn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : headers.keySet()) {
				System.out.println(key + "\t：\t" + headers.get(key));
			}

			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
//			System.out.println("响应消息"+httpConn.getContentLength());
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}

			long endInt = new Date().getTime();
			System.out.println("请求结束");
			System.out.println("返回值的大小"+result.getBytes().length);
			float mb = (float) (result.getBytes().length/1024.00/1024.00);
			System.out.println("返回值总的大小"+mb + "MB");
			System.out.println(endInt-startInt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送POST请求
	 *
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters) {

		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		String params = "";// 编码之后的参数
		try {
            System.out.println("开始请求");
            long startInt = new Date().getTime();
            params = checkParam(parameters);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			httpConn.getContentLength();
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}

            long endInt = new Date().getTime();
            System.out.println("请求结束");
            System.out.println("返回值的大小"+result.getBytes().length);
            System.out.println("耗时"+(endInt-startInt));
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static String checkParam(Map<String, String> parameters) throws UnsupportedEncodingException {

		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		if (parameters.size() == 1) {
			for (String name : parameters.keySet()) {
				sb.append(name).append("=")
						.append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
			}
			params = sb.toString();
		} else {
			for (String name : parameters.keySet()) {
				sb.append(name).append("=")
						.append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
						.append("&");
			}
			String temp_params = sb.toString();
			params = temp_params.substring(0, temp_params.length() - 1);
		}
		return params;
	}
}

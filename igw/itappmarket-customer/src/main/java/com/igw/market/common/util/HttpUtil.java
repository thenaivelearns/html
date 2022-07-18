package com.igw.market.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.igw.market.config.Constant;

import net.sf.json.JSONObject;

/**
 * http 请求
 *
 */
public class HttpUtil {

	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	public static String uploadInterecptor = "/uploadFileList.do";


	public static String get(String urlStr) {
		URL url = null;
		HttpURLConnection con = null;
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		InputStream is = null;
		String result;
		try {
			url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(30000);
			con.setReadTimeout(60000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("GET");
			// 发送请求
			con.connect();
			is = con.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((result = br.readLine()) != null) {
				buffer.append(result);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 关闭连接
			con.disconnect();
		}
		return buffer.toString();
	}

	public static ResultMessage downLoadFromUrl(String urlStr, String savePath) {
		ByteArrayOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			// 获取下载的文件名
			String fileName = con.getHeaderField("Content-disposition");
			fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
			fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("fileName=") + 9), "UTF-8");
			InputStream inputStream = con.getInputStream();
			byte[] bufferOut = new byte[1024];
			int len = 0;
			bos = new ByteArrayOutputStream();
			while ((len = inputStream.read(bufferOut)) != -1) {
				bos.write(bufferOut, 0, len);
			}
			byte[] getData = bos.toByteArray();
			// 文件保存地址
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			File file = new File(saveDir + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(getData);
			return ResultMessage.ok(saveDir + File.separator + fileName);
		} catch (Exception e) {
			logger.error("下载文件异常", e);
			return ResultMessage.fail(e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("IO流异常", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("IO流异常", e);
				}
			}
		}
	}

	/**
	 * 批量上传文件
	 * 
	 * @param urlStr
	 * @param params
	 *            {system:'',user:''}
	 * @param uploadFileList
	 *            ['文件路径','']
	 * @return
	 */
	public static String httpPostUploadFileList(String urlStr, Map<String, String> params,
			List<Map<String, String>> uploadFileList) {
		URL url = null;
		HttpURLConnection con = null;
		OutputStream os = null;
		StringBuffer subffer = new StringBuffer();
		try {
			url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(30000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);

			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			con.setRequestProperty("Charsert", "UTF-8");
			String boundary = "--------WebKitFormBoundaryyG2unN5TfbH";

			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			os = new DataOutputStream(con.getOutputStream());
			if (params != null) {
				for (Entry<String, String> e : params.entrySet()) {
					subffer.append("\r\n--" + boundary + "\r\n");
					subffer.append("Content-Disposition: form-data; name=\"" + e.getKey() + "\"");
					subffer.append("\r\n\r\n");
					subffer.append(e.getValue());
				}
				os.write(subffer.toString().getBytes("UTF-8"));
			}

			String contentType = "";
			if (uploadFileList != null && uploadFileList.size() > 0) {
				for (int i = 0; i < uploadFileList.size(); i++) {
					Map<String, String> map = uploadFileList.get(i);
					File file = new File(map.get("fpath"));
					String fileName = map.get("fname");
					contentType = new MimetypesFileTypeMap().getContentType(file);

					if (!"".equals(contentType)) {
						if (fileName.endsWith(".png")) {
							contentType = "image/png";
						} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
								|| fileName.endsWith(".jpe")) {
							contentType = "image/jpeg";
						} else if (fileName.endsWith(".gif")) {
							contentType = "image/gif";
						} else if (fileName.endsWith(".ico")) {
							contentType = "image/image/x-icon";
						}
					}
					if (contentType == null || "".equals("contentType")) {
						contentType = "application/octet-stream";
					}
					StringBuffer newSbuffer = new StringBuffer();
					newSbuffer.append("\r\n--" + boundary + "\r\n");
					newSbuffer.append("Content-Disposition: form-data; name=\"" + "files" + "\";filename=\"" + fileName
							+ "\"\r\n");
					newSbuffer.append("Content-Type:" + contentType);
					newSbuffer.append("\r\n\r\n");
					os.write(newSbuffer.toString().getBytes());
					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						os.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}
			os.write(("\r\n--" + boundary + "--\r\n").getBytes("UTF-8"));
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String result;
			while ((result = br.readLine()) != null) {
				buffer.append(result);
				buffer.append("\n");
			}
			System.err.println(buffer);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 上传单个文件--入参文件流
	 * 
	 * @param urlStr
	 * @param params
	 *            {system:'',user:''}
	 * @param file
	 *            文件流
	 * @return
	 */
	public static String httpPostUploadFileList(String urlStr, Map<String, String> params,
			MultipartFile multipartFile) {
		URL url = null;
		HttpURLConnection con = null;
		OutputStream os = null;
		StringBuffer subffer = new StringBuffer();
		URI  delUrl = null;
		try {
			url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(30000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);

			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			con.setRequestProperty("Charsert", "UTF-8");
			String boundary = "--------WebKitFormBoundaryyG2unN5TfbH";

			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			os = new DataOutputStream(con.getOutputStream());
			if (params != null) {
				for (Entry<String, String> e : params.entrySet()) {
					subffer.append("\r\n--" + boundary + "\r\n");
					subffer.append("Content-Disposition: form-data; name=\"" + e.getKey() + "\"");
					subffer.append("\r\n\r\n");
					subffer.append(e.getValue());
				}
				os.write(subffer.toString().getBytes("UTF-8"));
			}
			File file = multipartFileToFile(multipartFile);
			String fileName = file.getName();
			String contentType = new MimetypesFileTypeMap().getContentType(file);
			if (!"".equals(contentType)) {
				if (fileName.endsWith(".png")) {
					contentType = "image/png";
				} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpe")) {
					contentType = "image/jpeg";
				} else if (fileName.endsWith(".gif")) {
					contentType = "image/gif";
				} else if (fileName.endsWith(".ico")) {
					contentType = "image/image/x-icon";
				}
			}
			if (contentType == null || "".equals("contentType")) {
				contentType = "application/octet-stream";
			}
			StringBuffer newSbuffer = new StringBuffer();
			newSbuffer.append("\r\n--" + boundary + "\r\n");
			newSbuffer.append(
					"Content-Disposition: form-data; name=\"" + "files" + "\";filename=\"" + fileName + "\"\r\n");
			newSbuffer.append("Content-Type:" + contentType);
			newSbuffer.append("\r\n\r\n");
			os.write(newSbuffer.toString().getBytes());
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				os.write(bufferOut, 0, bytes);
			}
			//删除临时文件路径
			delUrl=file.toURI();
			in.close();
			os.write(("\r\n--" + boundary + "--\r\n").getBytes("UTF-8"));
			os.flush();
		} catch (Exception e) {
			logger.error("上传文件异常：",e);	
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				//删除临时文件
				File del = new File(delUrl);  
				del.delete();
			} catch (IOException e) {
				logger.error("上传文件关闭流异常：",e);	
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String result;
			while ((result = br.readLine()) != null) {
				buffer.append(result);
				buffer.append("\n");
			}
		} catch (Exception e) {
				logger.error("上传文件读取返回内容异常：",e);	
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("上传文件关闭流异常：",e);	
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * java调http请求
	 * 
	 * @param httpUrl
	 *            请求
	 * @param param
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public static ResultMessage<Object> doGetHttp(String httpUrl, String param) {
		final ResultMessage<Object> msg = new ResultMessage<Object>();

		if (StringUtils.isNotEmpty(param)) {
			httpUrl += "?" + param;
		}
		try {
			URL url = new URL(httpUrl);
			// 获取连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			// 解析
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			String content = new String(sb);
			br.close();
			// 获取返回信息
			msg.setMsgCode(Constant.SUCCESS);
			msg.setData(content);
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsgCode(Constant.ERROR);
			msg.setMsgDesc(e.toString());
			logger.error("调http请求异常：", e);
		}
		return msg;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static Map<String, Object> httpRequest(String requestUrl, String requestMethod, String outputStr) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			logger.info("HttpRequestUtil has  address==>" + requestUrl);
			URL url = new URL(requestUrl);
			// 获取连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			os.flush();
			os.close();

			// 解析
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String content = new String(sb);
			br.close();
			// 获取返回信息
			if (!"".equals(content)) {
				logger.info("HttpRequestUtil has parameter==>" + content);
				map = (Map<String, Object>) JSON.parse(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "1");
			map.put("description", e.getMessage());
		}
		return map;
	}

	/**
	 * MultipartFile 转 File
	 *
	 * @param file
	 * @throws Exception
	 */
	public static File multipartFileToFile(MultipartFile file) throws Exception {

		File toFile = null;
		if (file.equals("") || file.getSize() <= 0) {
			file = null;
		} else {
			InputStream ins = null;
			ins = file.getInputStream();
			toFile = new File(file.getOriginalFilename());
			inputStreamToFile(ins, toFile);
			ins.close();
		}
		
		return toFile;

	} 
	// 获取流文件
	private static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

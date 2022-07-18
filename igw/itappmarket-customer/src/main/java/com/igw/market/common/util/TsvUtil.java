package com.igw.market.common.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.igw.market.institutional.util.FileServerUtil;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import net.sf.json.JSONObject;

/**
 * 生成TSV文件
 * 
 * @author liwen
 *
 */
public class TsvUtil {

	private static final Logger logger = Logger.getLogger(TsvUtil.class);

	/**
	 * 获取TSV格式数据
	 * 
	 * @param filePath 文件路径
	 * @return List<String[]>
	 */
	public static List<String[]> readTsv(String filePath) {
		List<String[]> allRows = new ArrayList<String[]>();
		try {
			// 创建tsv解析器settings配置对象
			TsvParserSettings settings = new TsvParserSettings();
			settings.getFormat().setLineSeparator("\n");
			TsvParser parser = new TsvParser(settings);
			DataInputStream in = new DataInputStream(new FileInputStream(new File(filePath)));
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));// 这里如果csv文件编码格式是utf-8,改成utf-8即可
			allRows = parser.parseAll(br);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allRows;
	}

	/**
	 * TSV 数据导出
	 * 
	 * @param path
	 * @param list
	 * @param fileName
	 * @throws IOException
	 */
	public static void outTSV(String sourceFile, String path, String fileName, String role) {
		// 如果目录不存在 则创建
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		FileWriter fw = null;
		try {
			// 获取文件数据
			List<String[]> list = readTsv(sourceFile);

			fw = new FileWriter(path + "/" + fileName);
			StringBuffer sb = new StringBuffer();

			// 清单数据
			for (int i = 0; i < list.size(); i++) {
				String[] datas = list.get(i);
				// 过滤条件
				if (role.equals(datas[21])) {
					for (int j = 0; j < datas.length; j++) {
						// 添加数据，最后一个数据则换行
						if (j < datas.length - 1) {
							sb.append(datas[j] + "\t");
						} else {
							sb.append(datas[j] + "\r\n");
						}
					}
				}
			}
			logger.info("新文件路径:" + path + "/" + fileName);
			File okFile = new File(path + "/" + fileName);
			okFile.createNewFile();

			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		HttpUtil hu = new HttpUtil();
		String fileUrl = "http://10.86.130.102:8089/ItApp-Common-FileServer/file_info/get_file_detail_by_fname/v1";
		ResultMessage msg = hu.doGetHttp(fileUrl,"fname=execution_aggr_F006229F0001_6_20210511.tsv" + "&fileUploadDate=20210512");
		System.out.println(msg.getData());
		if("Y".equals(msg.getMsgCode())) {
			
			JSONObject js=JSONObject.fromObject(msg.getData());
			System.out.println(js);
			JSONArray ja = JSONArray.parseArray(js.get("obj")+"");

			if(ja.size() > 0) {
				JSONObject json = JSONObject.fromObject(ja.get(0));
				String loadUrl = "http://10.86.130.102:8089/ItApp-Common-FileServer/file_info/download/v1?fid=";
				ResultMessage fileMsg= FileServerUtil.downLoadFromUrl(loadUrl+json.getString("fid"),"e:/liwen/");
				System.out.println(fileMsg);
			}
		}
//		String loadUrl = "http://10.86.130.102:8089/ItApp-Common-FileServer/file_info/download/v1?fid=";
//		ResultMessage fileMsg= FileServerUtil.downLoadFromUrl(loadUrl+"25773","e:/liwen/");
//		System.out.println(fileMsg);
	}
}

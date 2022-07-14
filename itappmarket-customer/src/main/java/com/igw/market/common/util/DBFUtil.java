package com.igw.market.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DBFUtil {

	/**
	 *	 获取模板生成新文件
	 * @param jsonArr 数据集
	 * @param fileName 新文件名
	 * @param templatePath 模板名
	 * @param reportPath 导出路径
	 * @return
	 */
	public static boolean outputDBF(JSONArray jsonArr, String fileName, String templatePath,String reportPath) {

		InputStream fis = null;
		OutputStream fos = null;
		
		//定义dbf列顺序
		List<String> headList = new ArrayList<>();
		try {
			// 读取文件的输入流
			fis = new FileInputStream(templatePath);
			// 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
			DBFReader reader = new DBFReader(fis);
			// 获得dbf字段数量
			int fieldsCount = reader.getFieldCount();
			
			//定义输出流
			DBFWriter writer = new DBFWriter();
			// 定义DBF文件字段
            DBFField[] fields = new DBFField[fieldsCount];
			// 取出字段信息
			for (int i = 0; i < fieldsCount; i++) {
				//所有字段用字符型
				DBFField df = reader.getField(i);
				df.setDataType(DBFField.FIELD_TYPE_C);
				fields[i] = df;
				// 接口取出表头为小写
				headList.add(df.getName().toLowerCase());
			}
			writer.setFields(fields);
			
			//定义数据集
			for (int z = 0; z < jsonArr.size(); z++) {
				//定义行数据数组
				Object[] rowData = new Object[fieldsCount];
				JSONObject jobejct = (JSONObject)jsonArr.get(z);
				// 通过 模板表头获取数据
				for (int i = 0; i < headList.size(); i++) {
					String key = headList.get(i);
					String value = jobejct.getString(key);
					
					rowData[i]  = "null".equals(value) ? "" : value;
				}
				//数据写入dbf行
				writer.addRecord(rowData);
			}
			//文件夹不存在创建
			File destFile = new File(reportPath);
			if(!destFile.exists()) {
				destFile.mkdirs();
			}
			// 定义输出流，并关联的一个文件
			fos = new FileOutputStream(reportPath+fileName);
			// 定义输出编码
			writer.setCharactersetName("GBK");
			// 写入数据
			writer.write(fos);
			
			// 生成文件
			File okFile=new File(reportPath+fileName);
			okFile.createNewFile();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (Exception e) {
				
			}
		}
		return true;
	}
}

package com.igw.market.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import com.igw.market.common.domain.Messages;

public class FileUtil {

	private static String MESSAGE = "";

	/**
	 * text转String
	 * 
	 * @param path 文件路径
	 * @return
	 */
	public static String getTextToString(String path) {
		StringBuffer sb = new StringBuffer();
		try {
			Scanner in = new Scanner(new File(path));
			while (in.hasNextLine()) {
				sb.append(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * xml转String
	 * 
	 * @param doc
	 * @return
	 */
	public static String getXmlToString(String path) {
		String xmlStr = null;
		try {
			// XML转字符串
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document document = db.parse(new File(path));

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty("encoding", "GBK");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(document), new StreamResult(bos));
			xmlStr = bos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlStr;
	}

	/**
	 * 
	 * 获取目录下所有文件
	 * 
	 * @param realpath
	 * @param files
	 * @return
	 */
	public static List<File> getFiles(String realpath) {
		List<File> files = new ArrayList<File>();
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath());
				} else {
					files.add(file);
				}
			}
		}
		return files;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName  待复制的文件名
	 * @param descFileName 目标文件名
	 * @param overlay      如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);

		// 判断源文件是否存在
		if (!srcFile.exists()) {
			MESSAGE = "源文件：" + srcFileName + "不存在！";
			JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		} else if (!srcFile.isFile()) {
			MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
			JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFileName).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 单个文件拷贝
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static Messages fileCopy(String oldPath, String newPath) {
		Messages msg = new Messages();

		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			File f1 = (new File(oldPath));
			if (f1.exists()) {
				fi = new FileInputStream(f1);

				File f2 = new File(newPath);
				if (!f2.getParentFile().exists()) {
					// 目标文件所在目录不存在
					if (f2.getParentFile().mkdirs()) {
						fo = new FileOutputStream(f2);
					}
				} else {
					fo = new FileOutputStream(f2);
				}
				in = fi.getChannel();
				out = fo.getChannel();
				in.transferTo(0, in.size(), out);
			} else {
				msg.setMsgCode("N");
				msg.setMsgDesc("文件" + oldPath + "不存在");
				System.out.println("文件" + oldPath + "不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
			msg.setMsgCode("N");
			msg.setMsgDesc(e.toString());
			return msg;
		} finally {
			try {
				if (fi != null)
					fi.close();
				if (in != null)
					in.close();
				if (fo != null)
					fo.close();
				if (out != null)
					out.close();
				// File f2 = new File(newPath);
				// f2.setLastModified(time);
			} catch (IOException e) {
				e.printStackTrace();
				msg.setMsgCode("N");
				msg.setMsgDesc(e.toString());
				return msg;
			}
		}
		msg.setMsgCode("Y");
		return msg;
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName  待复制目录的目录名
	 * @param destDirName 目标目录名
	 * @param overlay     如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
		// 判断源目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			MESSAGE = "复制目录失败：源目录" + srcDirName + "不存在！";
			JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		} else if (!srcDir.isDirectory()) {
			MESSAGE = "复制目录失败：" + srcDirName + "不是目录！";
			JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		}

		// 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// 如果目标文件夹存在
		if (destDir.exists()) {
			// 如果允许覆盖则删除已存在的目标目录
			if (overlay) {
				new File(destDirName).delete();
			} else {
				MESSAGE = "复制目录失败：目的目录" + destDirName + "已存在！";
				JOptionPane.showMessageDialog(null, MESSAGE);
				return false;
			}
		} else {
			// 创建目的目录
			System.out.println("目的目录不存在，准备创建。。。");
			if (!destDir.mkdirs()) {
				System.out.println("复制目录失败：创建目的目录失败！");
				return false;
			}
		}

		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 复制文件
			if (files[i].isFile()) {
				flag = FileUtil.copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = FileUtil.copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			MESSAGE = "复制目录" + srcDirName + "至" + destDirName + "失败！";
			JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 写入文档
	 * 
	 * @param sb
	 * @param file
	 * @return
	 */
	public boolean swrite(StringBuffer sb, File file) {
		try {
			BufferedWriter weiter = new BufferedWriter(new FileWriter(file));
			weiter.write(sb.toString());
			weiter.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return true;
	}

	/**
	 * 文件夹是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createFolder(String filePath) {
		File destDir = new File(filePath);
		boolean t = false;
		if (destDir.exists()) {
			t = destDir.mkdirs();
		}
		return t;
	}

	// 保存文件
	public static void saveFile(String newsRootPath, String filename, File picFile) {
		try {
			File newsFileRoot = new File(newsRootPath);
			if (!newsFileRoot.exists()) {
				newsFileRoot.mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(newsRootPath + filename);
			FileInputStream fis = new FileInputStream(picFile);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = fis.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获取excel文件第一个sheet的所有数据,包括xls和xlsx
	 * 
	 * @param path 文件路径
	 * @return List<Object[]>
	 */
	public static List<String[]> readExcel(String path) {
		File file = new File(path);
		List<String[]> returnList = new ArrayList<String[]>();
		Workbook wb = null;
		try {
			if (path.endsWith("xls")) {
				wb = new HSSFWorkbook(new FileInputStream(file));
			} else if (path.endsWith("xlsx")) {
				wb = new XSSFWorkbook(new FileInputStream(file));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取第一个sheet的内容
		Sheet sheet = wb.getSheetAt(0);
		// 遍历行
		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			Row rowIn = null;
			rowIn = sheet.getRow(i);
			// 遍历列
			int colNum = rowIn.getLastCellNum();
			String[] rowValues = new String[colNum];
			for (int j = 0; j < colNum; j++) {
				Cell cell = rowIn.getCell(j);
				int type = cell.getCellType();
				String cellString = "";
				switch (type) {
				case Cell.CELL_TYPE_NUMERIC:
					cellString = String.valueOf(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					cellString = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					cellString = String.valueOf(cell.getBooleanCellValue());
					break;
				default:
					break;
				}
				rowValues[j] = cellString;
			}
			returnList.add(rowValues);
		}
		return returnList;
	}

	/**
	 * 获取excel文件第一个sheet的所有数据,包括xls和xlsx
	 * 
	 * @param path 文件路径
	 * @return List<Object[]>
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static List<String[]> readExcel(File file) throws FileNotFoundException, IOException {
		List<String[]> returnList = new ArrayList<String[]>();
		Workbook wb = null;
		if (file.getName().endsWith("xls")) {
			wb = new HSSFWorkbook(new FileInputStream(file));
		} else if (file.getName().endsWith("xlsx")) {
			wb = new XSSFWorkbook(new FileInputStream(file));
		}
		// 获取第一个sheet的内容
		Sheet sheet = wb.getSheetAt(0);
		// 遍历行
		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			Row rowIn = null;
			rowIn = sheet.getRow(i);
			// 遍历列
			int colNum = rowIn.getLastCellNum();
			String[] rowValues = new String[colNum];
			for (int j = 0; j < colNum; j++) {
				Cell cell = rowIn.getCell(j);
				int type = cell.getCellType();
				String cellString = "";
				switch (type) {
				case Cell.CELL_TYPE_NUMERIC:
					cellString = String.valueOf(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					cellString = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					cellString = String.valueOf(cell.getBooleanCellValue());
					break;
				default:
					break;
				}
				rowValues[j] = cellString;
			}
			returnList.add(rowValues);
		}
		return returnList;
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

	/**
	 * 打包文件，输入文件路径list，输出zip文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void fileToZip(List<String> oldPaths, String newPath) {
		byte[] buf = new byte[1024];
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(newPath));
			for (String oldPath : oldPaths) {
				File f = new File(oldPath);
				FileInputStream fis = null;
				if (f.isDirectory()) {
					File[] fs = f.listFiles();
					for (File temp : fs) {
						if (!temp.isDirectory()) {
							fis = new FileInputStream(temp);
							out.putNextEntry(new ZipEntry(temp.getName()));
							int len;
							while ((len = fis.read(buf)) > 0) {
								out.write(buf, 0, len);
							}
							fis.close();
						}
					}
				} else {
					fis = new FileInputStream(f);
					out.putNextEntry(new ZipEntry(f.getName()));
					int len;
					while ((len = fis.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					fis.close();
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.closeEntry();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}

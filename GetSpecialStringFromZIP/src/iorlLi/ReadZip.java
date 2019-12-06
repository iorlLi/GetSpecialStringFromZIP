package iorlLi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ReadZip {
	public static void main(String[] args) throws IOException {
		String path = "C:\\TEMP\\";
		readDirectionAllZip(path);
	}

	private static void readDirectionAllZip(String path) throws FileNotFoundException, IOException {
		File targetFile = new File(path);
		File[] files = targetFile.listFiles();// 获取目录下的所有文件或文件夹
		if (files == null) {// 如果目录为空，直接退出
			return;
		}
		// 遍历，目录下的所有文件
		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".zip")) {
				if(f.getName().indexOf("o") > -1 || f.getName().indexOf("stmtapi") > -1) {
					readZip(f.getAbsolutePath());
				}
			} else if (f.isDirectory()) {
				//System.out.println(f.getAbsolutePath());
				readDirectionAllZip(f.getAbsolutePath());
			}
		}
	}

	/**
	 * 读取该zip的内容
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void readZip(String absolutepath) throws IOException, FileNotFoundException {
		@SuppressWarnings("resource")
		ZipFile zipfile = new ZipFile(absolutepath);
		InputStream in = new BufferedInputStream(new FileInputStream(absolutepath));
		Charset gbk = Charset.forName("gbk");
		@SuppressWarnings("resource")
		ZipInputStream zipinputstream = new ZipInputStream(in, gbk);
		ZipEntry ze;
		while ((ze = zipinputstream.getNextEntry()) != null) {
			if (ze.toString().endsWith("log")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(zipfile.getInputStream(ze)));
				String line;
				while ((line = br.readLine()) != null) {

					if (line.indexOf("李白") > -1) {
						System.out.println(absolutepath);
					}
				}
				br.close();
			}
			System.out.println();
		}
		zipinputstream.closeEntry();
	}
}

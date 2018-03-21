//package markdownPic;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MarkdownPic {
	static String path = "C:\\Users\\ZhenZhong\\Desktop\\pic";
	static List<String> oldPath = new ArrayList<String>();
	static List<String> newPath = new ArrayList<String>();
	static List<String> diff = new ArrayList<String>();

	public static void main(String[] args) {

		traverseFolder2(path);
		oldPath.addAll(newPath);

		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			getPath();
			comparePaths(oldPath, newPath);

			if (diff.size() == 0) {
				continue;
			} else {
				System.out.println("准备上传图片");

				try {
					String cmd = "cmd /k start upload.cmd";
					Runtime.getRuntime().exec(cmd).waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			for (String ds : diff) {
				int index = ds.lastIndexOf("\\");
				String uploadName = urlEncode(ds.substring(index+1,ds.length()));
				System.out.println("https://github.com/zz133110/MarkDownPic/blob/master/"+uploadName+"?raw=true");
			}
				oldPath.addAll(diff);
				diff.clear();
			}

		}
	}
	
	public static String urlEncode(String in){
		  try {
				String mytext = java.net.URLEncoder.encode(in,"utf-8");
				return mytext;
			  } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return "";
	}

	public static void comparePaths(List<String> oldPath, List<String> newPath) {
		diff.clear();
		for (String news : newPath) {
			int f = 0;// 0代表未找到
			for (String old : oldPath) {
				if (news.equals(old)) {
					f = 1;// 找到
					break;
				}
			}
			if (f == 0) {
				diff.add(news);
			}
		}
	}

	public static void getPath() {
		newPath.clear();
		traverseFolder2(path);
	}

	public static void traverseFolder2(String path) {

		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						String folderName = file2.getAbsolutePath();
						int index = folderName.lastIndexOf("\\");
						folderName = folderName.substring(index + 1, folderName.length());
						if (folderName.equals(".git")) {
							continue;
						}
						traverseFolder2(file2.getAbsolutePath());
					} else {
						newPath.add(file2.getAbsolutePath());
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
	}
}

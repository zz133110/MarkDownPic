 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MarkdownPic {
	static String picTxt = "C:\\Users\\ZhenZhong\\Desktop\\pic.txt";
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
				System.out.println("׼���ϴ�ͼƬ");

				executive("cmd /c upload.cmd") ;
				
				 System.out.println("�ϴ������");
				
			for (String ds : diff) {
				int index = ds.lastIndexOf("\\");
				String uploadName = urlEncode(ds.substring(index+1,ds.length()));
				System.out.println("https://github.com/zz133110/MarkDownPic/blob/master/"+uploadName+"?raw=true");
				
				File f = new File(picTxt);    
		        if (!f.exists()) {    
		            System.out.print("�ļ�������");    
		            try {
						f.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// �������򴴽�    
		        }  
		        
		        try {
					BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
					  output.write("https://github.com/zz133110/MarkDownPic/blob/master/"+uploadName+"?raw=true");  
					  output.write("\n");
					  output.flush();
					  output.close();  
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//true,��׷��д��text�ı�
		      
			}
				oldPath.addAll(diff);
				diff.clear();
			}

		}
	}
	
	public static List<String> executive(String path) {
		String line = null;
		List<String> sb = new ArrayList<String>();
		Runtime runtime = Runtime.getRuntime();
		try {

			Process process = runtime.exec(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = bufferedReader.readLine()) != null) {
				sb.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
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
			int f = 0;// 0����δ�ҵ�
			for (String old : oldPath) {
				if (news.equals(old)) {
					f = 1;// �ҵ�
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
			System.out.println("�ļ�������!");
		}
	}
}

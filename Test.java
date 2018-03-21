 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		String path = MarkdownPic.path;
		// System.out.println(executive("cmd /c git"));
		// System.out.println(executive("pwd"));
//
	try {
			String cmd = "cmd /c start upload.cmd";
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finish"); 
		
	}

	public static void print(List<String> msg) {
		for (String s : msg) {
			System.out.println(s);
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

}

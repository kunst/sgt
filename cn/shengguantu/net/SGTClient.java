package cn.shengguantu.net;

//以下为客户端主程序
import java.io.*;
import java.net.Socket;
import java.lang.*;

public class SGTClient {
	private Socket con;// 客户端连接socket
	private OutputStream out;
	private String sen;
	private byte b[];

	public SGTClient() {
		clientInit();
	}

	public void clientInit() {
		try {
			con = new Socket("localhost", 10015);
			con.setSoTimeout(10000);
			b = new byte[1024];
			OutputStream out = con.getOutputStream();
			sen = "hello serve,以TCP方式发送数据!";
			b = sen.getBytes();
			out.write(b);
			out.flush();
			out.close();
			con.close();
		} catch (IOException ie) {
			ie.toString();
		}
	}

	public static void main(String args[]) {
		new SGTClient();
	}
}

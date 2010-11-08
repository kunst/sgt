package cn.shengguantu.net;

//以下为会话主程序
//应该特别注意,如果客户端先关闭,会话socket中可能抛出socketexception:connection reset
//这应该在程序中进行处理,这也是较易忽略的问题.
import java.io.*;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SGTInteractiveServer implements Runnable {
	private Socket s;
	private InputStream in;
	private String rev, temp;
	private byte b[];
	private int len;

	public SGTInteractiveServer(Socket ss) {
		s = ss;
		b = new byte[1024];
		try {
			in = s.getInputStream();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		rev = "";
	}

	public void run() {
		try {
			while (s.isConnected() == true) {
				if ((len = in.read(b)) != -1) {
					temp = new String(b, 0, len);
					rev += temp;
					System.out.print(rev);
					temp = null;
					Thread.sleep(1000);
				}
			}
			in.close();
			s.close();
			System.out.println("会话socket已断开!");
		} catch (SocketException se) {
			System.out.println("客户端已断开!");
			System.exit(0);
		} catch (IOException io) {
			io.printStackTrace();
			System.exit(0);
		} catch (InterruptedException ire) {
			ire.printStackTrace();
		}
	}
}

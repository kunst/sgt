package cn.shengguantu.net;

import java.io.*;
import java.net.*;

import java.io.*;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;

//主程序一直处于监听状态,有连接则启动一个线程进行处理,以实现多个客户端
public class SGTServer {
	private ServerSocket ss;
	private boolean listening = true;

	public SGTServer() {
		Init();// 初始化
		lisn();// 启动监听
	}

	public void Init() {
		try {
			ss = new ServerSocket(10015, 10);
		} catch (IOException ie) {
			System.out.println("无法在10015端口监听");
			ie.printStackTrace();
		}
	}

	public void lisn() {
		try {
			while (listening)
				new Thread(new SGTInteractiveServer(ss.accept())).start();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new SGTServer();
	}
}



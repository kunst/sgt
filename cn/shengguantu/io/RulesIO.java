/**
 * 
 */
package cn.shengguantu.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * @author rwf
 *
 */
public class RulesIO {
	private static String readFromFile(FileInputStream fistream)
    {
        String returnString = null;
        InputStream is =  fistream;
        if (is != null)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch = 0;
            try
            {
                while ((ch = is.read()) != -1)
                {
                    baos.write(ch);
                }
                byte[] data = baos.toByteArray();
                returnString = new String(data, "UTF-8");
               
                is.close();
               baos.flush();
                baos.close(); 
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return returnString;
    }

	public static Vector<String> load(String path) 
	{
		File file = new File(path);
		String content = "";
		try 
		{
			FileInputStream fistream = new FileInputStream(file);
			content = readFromFile(fistream);
			
		} 
		catch (Exception e1) 
		{
			System.out.println("打开文件时有错误 open file error");
			return null;
		}
		//System.out.println(content);
		Vector<String> rulesInOneString = new Vector<String>();
		String[] s = content.split("\r\n");
		rulesInOneString.clear();
		for(int i = 0; i<s.length; i++)
		{
			rulesInOneString.add(s[i]);
		}
		return rulesInOneString;
	}
}


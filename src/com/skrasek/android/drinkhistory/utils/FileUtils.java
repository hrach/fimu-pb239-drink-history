package com.skrasek.android.drinkhistory.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;

public class FileUtils{
	
	public static String FindFile(String Path,String FileName)
	{
		File dir = new File(Path);
		for(int i=dir.list().length-1;i>=0;i--)
		{
			File f = new File(Path+"/"+dir.list()[i]);
			if(f.isDirectory())
			{
				String file = FindFile(Path+"/"+dir.list()[i],FileName);
				if(file.length()>0)
				{
					return file;
				}
			}
			else if(f.isFile())
			{
				if(f.getName().equals(FileName))
				{
					return f.getAbsolutePath();
				}
			}
		}
		
		return "";
	}
	
	public static void copyFile(Context AppContext,int ID,File out) throws Exception
	{
		InputStream fis  = AppContext.getResources().openRawResource(ID);
	    FileOutputStream fos = new FileOutputStream(out);
	    
	    
	    try {
	        byte[] buf = new byte[1024];
	        int i = 0;
	        while ((i = fis.read(buf)) != -1) {
	            fos.write(buf, 0, i);
	        }
	    } 
	    catch (Exception e) {
	        throw e;
	    }
	    finally {
	        if (fis != null) fis.close();
	        if (fos != null) fos.close();
	    }
	}
	
	
	  public static void copyFile(File in, File out) throws Exception {
	    FileInputStream fis  = new FileInputStream(in);
	    FileOutputStream fos = new FileOutputStream(out);
	    try {
	        byte[] buf = new byte[1024];
	        int i = 0;
	        while ((i = fis.read(buf)) != -1) {
	            fos.write(buf, 0, i);
	        }
	    } 
	    catch (Exception e) {
	        throw e;
	    }
	    finally {
	        if (fis != null) fis.close();
	        if (fos != null) fos.close();
	    }
	  }
	  
	}

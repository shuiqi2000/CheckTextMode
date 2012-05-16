package com.swallow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.HashSet;

public class CheckTextMode {

	public enum TextMode {BINARY,MSDOS,UNIX,MAC,PUZZLE,ERROR};
	public Set<String> extNameSet = new HashSet<String>();
	public boolean isDisplayAllFilesInfo = false;
	private int mSDOSTagNumber = 0;
	private int aPPTagNumber = 0;
	private int uNIXTagNumber = 0;
	private int pUZZLETagNumber = 0;
	private int bINARYNumber = 0;
	private int eRRORNumber = 0;
	private int mACTagNumber = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CheckTextMode checker = new CheckTextMode();
		checker.loadTextExtNameList();
		String filePath = ".";
		System.out.println("Check Text Mode version 1.0");
		for(String argument:args){
			if (argument.charAt(0) != '-'){
				filePath = argument;
			}else if (argument.equals("-a")){
				checker.isDisplayAllFilesInfo = true;
			}
		}
		
		
		if (filePath != null){
			File file = new File(filePath);
			checker.checkFilesTTINFolder(file);
			checker.printTagNumbers();
		} else{
			System.out.println("Error: file path is null");
		}

	}
	
	public void loadTextExtNameList(){
		InputStream is =null;
		try {
		    is = this.getClass().getClassLoader().getResourceAsStream("com/swallow/TextExtNameList.ini");
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		   
			String extItem = br.readLine();
			while (extItem != null){
				extNameSet.add(extItem);
				extItem = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public int checkFilesTTINFolder(File curdir){	
		if (curdir.isDirectory()){
			File[] files = curdir.listFiles();
			for (File file: files){
			   checkFilesTTINFolder(file);
			}
		} else {
			TextMode tm = checkTextType(curdir);	
		}
		return 0;
	}
	
	public TextMode checkTextType(File file){
		if (file.isDirectory()){
			return TextMode.ERROR;
		}
		
		String fileName = file.getName();
		int dotPos = fileName.lastIndexOf(".");
		if (dotPos < 0){
			bINARYNumber ++;
			return TextMode.BINARY;
		}
		
		String extName = fileName.substring(dotPos + 1);
		
		if(extNameSet.contains(extName)){
			return checkTextMode(file);
		}else{
			bINARYNumber ++;
			return TextMode.BINARY;
		}
		
	}
	
	public TextMode checkTextMode(File file){
		FileInputStream fs = null;
		try {		
		    fs = new FileInputStream(file);
			byte previousByte = 0;
			byte curByte = 0;
			int MSDOSTag = 0;
			int APPTag = 0;
			int UNIXTag = 0;
			while ((curByte = (byte)fs.read()) != -1){
				if (curByte == 0){
					continue;
				}
				if (previousByte == '\r'){
					if (curByte == '\n'){
						MSDOSTag ++;
					}else{
						APPTag ++;
					}
				} else {
				    if (curByte == '\n' ){
				    	UNIXTag ++;
				    }
				}
				previousByte = curByte;
				
			}
			
			if (MSDOSTag > 0 && APPTag == 0 && UNIXTag ==0){
				mSDOSTagNumber ++;
				if(this.isDisplayAllFilesInfo){
					System.out.println(TextMode.MSDOS.toString() + " - " + file.getPath());
				}
				return TextMode.MSDOS;
			} else if (MSDOSTag == 0 && APPTag > 0 && UNIXTag ==0){
				mACTagNumber ++;
				if(this.isDisplayAllFilesInfo){
					System.out.println(TextMode.MAC.toString() + " - " + file.getPath());
				}
				return TextMode.MAC;
			} else if (MSDOSTag == 0 && APPTag == 0 && UNIXTag > 0){
				uNIXTagNumber ++;
				if(this.isDisplayAllFilesInfo){
					System.out.println(TextMode.UNIX.toString() + " - " + file.getPath());
				}
				return TextMode.UNIX;
			} else {
				pUZZLETagNumber ++;
				if(this.isDisplayAllFilesInfo){
					System.out.println(TextMode.PUZZLE.toString() + " M" 
							+ MSDOSTag + " U" + UNIXTag + " A" + APPTag +" - " + file.getPath());
				}
			    return TextMode.PUZZLE;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TextMode.ERROR;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return TextMode.ERROR;
		} finally{
			if (fs != null){
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void printTagNumbers(){
		System.out.println("");
		System.out.println("Total:");
		System.out.println("MSDOS Number:" + this.mSDOSTagNumber);
		System.out.println("UNIX Number:" + this.uNIXTagNumber);
		System.out.println("MAC Number:" + this.mACTagNumber);
		System.out.println("PUZZLE Number:" + this.pUZZLETagNumber);
		System.out.println("BINARY Number:" + this.bINARYNumber);
	}

}

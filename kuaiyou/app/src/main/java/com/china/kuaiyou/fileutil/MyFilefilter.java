package com.china.kuaiyou.fileutil;

import java.io.File;
import java.io.FileFilter;

/**
 * 
 * 黎潇自己定义的FILE过滤器
 * 
 */
public class MyFilefilter implements FileFilter{
	private String filterstring;
	private String[] filterstrings;
	private int bs=0;
	public MyFilefilter(String filterstring){
		bs=1;
		if(filterstring.startsWith(".")){
		this.filterstring=filterstring.substring(1).toString();
		}else{
			this.filterstring=filterstring;
		}
	}
	
	public MyFilefilter(String[] filterstrings){
		bs=2;
		this.filterstrings=filterstrings;
	}
	@Override
	public boolean accept(File pathname) {
		// TODO Auto-generated method stub
		   String filename = pathname.getName();  
		if(bs==1){
	        if (filename.lastIndexOf(filterstring) != -1) {
	            return true;  
	        }            
		}
		if(bs==2){
			for(int i=0;i<filterstrings.length;i++){
				if (filename.lastIndexOf(filterstrings[i]) != -1) {  
		            return true;  
		        }   
			}
		}
		return false; 
	}

}

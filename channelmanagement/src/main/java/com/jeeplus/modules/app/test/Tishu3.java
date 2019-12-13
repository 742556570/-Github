package com.jeeplus.modules.app.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.jeeplus.common.utils.DateUtils;

public class Tishu3 {
	
	
	public static void main(String[] args) throws Exception {
		List<String> f1_lines = FileUtils.readLines(new File("d:/f3.txt"));
		
		
		List<String> f2_lines = FileUtils.readLines(new File("d:/f4.txt"));
		
		
		Map<String, List<String>> maps = new HashMap<String,List<String>>();
		
		
		for (String f1_line : f1_lines) {
			String[] cols = f1_line.split("\t");
			String key = cols[3];
			List<String> tmpList = maps.get(key);
			if(tmpList == null) {
				tmpList = new ArrayList<String>();
				maps.put(key, tmpList);
			}
			tmpList.add(f1_line);
			
		}
		
		for (String f2_line : f2_lines) {
			String[] cols = f2_line.split("\t");
			String key = cols[1];
			List<String> tmpList = maps.get(key);
			if(tmpList == null) {
				tmpList = new ArrayList<String>();
				System.out.println(f2_line);
				continue;
			}
			
			String result = "-\t"+ cols[0] + "\t" + cols[2] + "\t" + cols[1] + "\t" + "-" + "\t" + cols[3] + "\t-";
			tmpList.add(result);
		}
		
		List<String> fianl = new ArrayList<String>();
		Set<String> kes = maps.keySet();
		
		for (String key : kes) {
			List<String> tmp = maps.get(key);
			Collections.sort(tmp, new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					// TODO Auto-generated method stub
					String[] tmp1 = o1.split("\t");
					String[] tmp2 = o2.split("\t");
					
					Date t1 = DateUtils.parseDate(tmp1[5]);
					Date t2 = DateUtils.parseDate(tmp2[5]);
					if(t1.getTime() > t2.getTime() ) {
						return 1;
					}else {
						return -1;
					}
					
				}
				
			});
			
			
			
			for (String string : tmp) {
				fianl.add(string);
			}
		}
		
		File finalfile = new File("d:/final1.txt");
		if(finalfile.exists()) {
			finalfile.delete();
		}
		finalfile.createNewFile();
		
		FileUtils.writeLines(finalfile, fianl);
	}

}

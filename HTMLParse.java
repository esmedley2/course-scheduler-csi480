package Parser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;

public class HTMLParse {
	
	private ArrayList<String> catalog = new ArrayList<String>();
	
	public HTMLParse(String semester, ArrayList<String> dept, ArrayList<String> deptCode) {
		
		try {
			
			for (int i = 0; i < dept.size(); i++) {
				
				//https://admin.washcoll.edu/schedules/16FAschedules.html
				URL url = new URL("https://admin.washcoll.edu/schedules/" + semester + "schedules.html");
				Scanner f_in = new Scanner(url.openStream());
				
				String currentDept = dept.get(i);
				String currentDeptCode = deptCode.get(i);
				String temp;
				
				catalog.add("Classes in department: " +  currentDept);
				
				f_in.useDelimiter("<PRE>");
				while (f_in.hasNext()) {
					temp = f_in.next();
					if (temp.contains(currentDept)) {
						String[] courses = temp.split("blank\">|<span|<TR>|</SPAN");
						for (String string : courses) {
							if (string.startsWith(currentDeptCode)) {
								catalog.add(string);
								System.out.println(string);
								System.out.println();
							}
						}
 					}
				}

				catalog.add("End courses in department: " + currentDept);
				f_in.close();				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getCatalog() {
		return catalog;
	}
	/*
	 * Tester:
	public static void main(String[] args) {
		ArrayList<String> depts = new ArrayList<String>();
		ArrayList<String> deptCodes = new ArrayList<String>();
		deptCodes.add("ANT");
		depts.add("ANTHROPOLOGY");
		HTMLParse test = new HTMLParse("16FA", depts, deptCodes);
	}*/
}

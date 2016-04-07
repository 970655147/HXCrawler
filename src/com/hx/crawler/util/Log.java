/**
 * file name : Log.java
 * created at : 8:10:53 PM Apr 22, 2015
 * created by 970655147
 */

package com.hx.crawler.util;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

// 打印数据相关的类
public class Log {
	
	// 可配置的常量
	// 以及输出流, 错误流, 以及默认是否换行
	public static String HORIZON_LINES = "-----------------------------------";
	public static String HORIZON_STARTS = "-----------------------------------";
	public static String GOT_THERE = "get there...";
	
	public static PrintStream outStream = System.out;
	public static PrintStream errStream = System.err;
	public static String DEFAULT_SEP_WHILE_CRLF = " ";
	public static String DEFAULT_SEP_WHILE_NO_CRLF = ", ";
	public static String DEFAULT_SEP_WHILE_TWO_DIMEN = " ";
	public static boolean outputAppendCRLF = false;
	public static boolean errputAppendCRLF = false;
	
	// 标准输出
	public static void log(boolean appendCRLF) {
		log(GOT_THERE, appendCRLF);
	}
	public static void log() {
		log(GOT_THERE, outputAppendCRLF);
	}
	public static void log(String obj, boolean appendCRLF) {
		outStream.print(obj);
		if(appendCRLF) {
			outStream.println();
		}
	}
	public static void log(String obj) {
		log(obj, outputAppendCRLF);
	}
	public static void log(Object obj, boolean appendCRLF) {
		log(obj.toString(), appendCRLF );
	}
	public static void log(Object obj) {
		log(obj.toString(), outputAppendCRLF );
	}
	
	// 错误输出
	public static void err(boolean appendCRLF) {
		err(GOT_THERE, appendCRLF);
	}
	public static void err() {
		err(GOT_THERE, errputAppendCRLF);
	}
	public static void err(String obj, boolean appendCRLF) {
		errStream.print(obj);
		if(appendCRLF) {
			errStream.println();
		}
	}
	public static void err(String obj) {
		err(obj, errputAppendCRLF);
	}
	public static void err(Object obj, boolean appendCRLF) {
		err(obj.toString(), appendCRLF );
	}
	public static void err(Object obj) {
		err(obj, errputAppendCRLF );
	}
	
	// 打印迭代器中的元素
	public static <T> void log(Iterator<T> it, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			while(it.hasNext()) {
				Tools.appendCRLF(sb, (it.next().toString() + sep) );
			}
		} else {
			while(it.hasNext()) {
				Tools.append(sb, it.next().toString() + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static <T> void log(Iterator<T> it, String sep, boolean appendCRLF) {
		log(it, sep, true, appendCRLF);
	}
	public static <T> void log(Iterator<T> it, boolean appendCRLF) {
		if(appendCRLF) {
			log(it, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(it, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void log(Iterator<T> it) {
		log(it, outputAppendCRLF);
	}
	public static <T> void log(Iterator<T> it, String sep) {
		log(it, sep, outputAppendCRLF);
	}
	
	public static <T> void err(Iterator<T> it, String sep, boolean appendCRLF) {
		log(it, sep, false, appendCRLF);
	}
	public static <T> void err(Iterator<T> it, boolean appendCRLF) {
		if(appendCRLF) {
			err(it, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(it, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void err(Iterator<T> it) {
		err(it, errputAppendCRLF);
	}
	public static <T> void err(Iterator<T> it, String sep) {
		err(it, sep, errputAppendCRLF);
	}
			
	// 打印List
	public static <T> void log(List<T> ls, String sep, boolean appendCRLF) {
		log(ls.iterator(), sep, appendCRLF);
	}
	public static <T> void log(List<T> ls, boolean appendCRLF) {
		log(ls.iterator(), appendCRLF);
	}
	public static <T> void log(List<T> ls) {
		log(ls.iterator(), outputAppendCRLF);
	}
	public static <T> void log(List<T> ls, String sep) {
		log(ls.iterator(), sep, outputAppendCRLF);
	}
	
	public static <T> void err(List<T> ls, String sep, boolean appendCRLF) {
		err(ls.iterator(), sep, appendCRLF);
	}
	public static <T> void err(List<T> ls, boolean appendCRLF) {
		err(ls.iterator(), appendCRLF);
	}
	public static <T> void err(List<T> ls) {
		err(ls.iterator(), errputAppendCRLF);
	}
	public static <T> void err(List<T> ls, String sep) {
		err(ls.iterator(), sep, errputAppendCRLF);
	}
	
	// 打印int[], long[], double[], boolean[], Object[]
	public static void log(int[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(int obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(int obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(int[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(int[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(int[] ls) {
		log(ls, outputAppendCRLF);
	}
	public static void log(int[] ls, String sep) {
		log(ls, sep, outputAppendCRLF);
	}
	
	public static void err(int[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(int[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(int[] ls) {
		err(ls, errputAppendCRLF);
	}
	public static void err(int[] ls, String sep) {
		err(ls, sep, errputAppendCRLF);
	}
	
	public static void log(long[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(long obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(long obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(long[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(long[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(long[] ls) {
		log(ls, outputAppendCRLF);
	}
	public static void log(long[] ls, String sep) {
		log(ls, sep, outputAppendCRLF);
	}
	
	public static void err(long[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(long[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(long[] ls) {
		err(ls, errputAppendCRLF);
	}
	public static void err(long[] ls, String sep) {
		err(ls, sep, errputAppendCRLF);
	}
	
	public static void log(double[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(double obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(double obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(double[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(double[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(double[] ls) {
		log(ls, outputAppendCRLF);
	}
	public static void log(double[] ls, String sep) {
		log(ls, sep, outputAppendCRLF);
	}
	
	public static void err(double[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(double[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(double[] ls) {
		err(ls, errputAppendCRLF);
	}
	public static void err(double[] ls, String sep) {
		err(ls, sep, errputAppendCRLF);
	}
	
	public static void log(boolean[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(boolean obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(boolean obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(boolean[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(boolean[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(boolean[] ls) {
		log(ls, outputAppendCRLF);
	}
	public static void log(boolean[] ls, String sep) {
		log(ls, sep, outputAppendCRLF);
	}
	
	public static void err(boolean[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(boolean[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(boolean[] ls) {
		err(ls, errputAppendCRLF);
	}
	public static void err(boolean[] ls, String sep) {
		err(ls, sep, errputAppendCRLF);
	}
	
	public static <T> void log(T[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(Object obj : ls) {
				Tools.appendCRLF(sb, (obj.toString() + sep) );
			}
		} else {
			for(Object obj : ls) {
				Tools.append(sb, obj.toString() + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static <T> void log(T[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static <T> void log(T[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void log(T[] ls) {
		log(ls, outputAppendCRLF);
	}
	public static <T> void log(T[] ls, String sep) {
		log(ls, sep, outputAppendCRLF);
	}
	
	public static <T> void err(T[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static <T> void err(T[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void err(T[] ls) {
		err(ls, errputAppendCRLF);
	}
	public static <T> void err(T[] ls, String sep) {
		err(ls, sep, errputAppendCRLF);
	}
	
	// 打印int[][], long[][], double[][], boolean[][], Object[][]  格式如下 
	// 1 2 3 
	// 2 1 3
	// 3 2 1
	public static void log(int[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			int[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(int[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(int[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(int[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(int[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}

	public static void log(long[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			long[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(long[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(long[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(long[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(long[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}

	public static void log(double[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			double[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(double[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(double[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(double[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(double[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void log(boolean[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			boolean[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(boolean[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(boolean[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(boolean[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(boolean[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static <T> void log(T[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			Object[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static <T> void log(T[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static <T> void log(T[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static <T> void err(T[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static <T> void err(T[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}

	// 打印一条水平线
	public static void logHorizon(int n, boolean isOutStream) {
		StringBuilder sb = new StringBuilder(n * (HORIZON_LINES.length() + 2) );
		for(int i=0; i<n; i++) {
			Tools.appendCRLF(sb, HORIZON_LINES);
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void logHorizon(int n) {
		logHorizon(n, true);
	}
	public static void logHorizon() {
		logHorizon(1);
	}
	
	public static void errHorizon(int n) {
		logHorizon(n, false);
	}
	public static void errHorizon() {
		errHorizon(1);
	}
	
	// 键入一个/ n个回车
	public static void enter() {
		enter(1);
	}
	public static void enter(int n) {
		for(int i=0; i<n; i++ ) {
			log(Tools.EMPTY_STR, true);
		}
	}

	// 按照给定的iterator迭代出来的索引, 打印arr中的元素
	public static void log(int[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(int[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(int[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(int[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(int[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void log(long[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(long[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(long[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(long[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(long[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void log(double[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(double[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(double[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(double[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(double[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void log(boolean[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(boolean[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(boolean[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(boolean[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(boolean[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static <T> void log(T[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	public static <T> void log(T[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static <T> void log(T[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static <T> void err(T[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static <T> void err(T[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}

	// 打印两个int, long, double, boolean, Object
	public static void log(int row, int col) {
		log(row + DEFAULT_SEP_WHILE_NO_CRLF + col);
	}
	public static void log(long row, long col) {
		log(row + DEFAULT_SEP_WHILE_NO_CRLF + col);
	}
	public static void log(double row, double col) {
		log(row + DEFAULT_SEP_WHILE_NO_CRLF + col);
	}
	public static void log(boolean bool01, boolean bool02) {
		log(String.valueOf(bool01) + DEFAULT_SEP_WHILE_NO_CRLF + String.valueOf(bool02) );
	}
	public static <T1, T2> void log(T1 row, T2 col) {
		log(row.toString() + DEFAULT_SEP_WHILE_NO_CRLF + col.toString() );
	}
	
	// 打印自定义的主题
	public static void logForPage(String page) {
		logFor(page, "page", HORIZON_LINES, HORIZON_LINES);
	}
	public static void logForThemes(String theme) {
		logFor(theme, "theme", HORIZON_STARTS, HORIZON_STARTS);
	}
	public static void logFor(String subject, String subjectKey, String before, String after) {
		log(before + " [ " + subjectKey + " : " + subject + " ] " + after);
	}
	
}

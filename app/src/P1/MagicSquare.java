package P1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MagicSquare {
	
	public static boolean IsSquare(ArrayList<ArrayList<Integer>> square) {//判断得到的数组是否为nXn矩阵
		
		for(ArrayList<Integer> k : square) {
			if(k.size()!=square.size()) {//行的长度是否为列的长度
				System.out.println("Row's length is not equal to line's length!");
				return false;
			}
		}
		return true;
	}
	
	public static boolean generateMagicSquare(int n) {

		String filename="6.txt";
		int magic[][] = new int[n][n];//初始一个n*n数组
		int row = 0, col = n / 2, i, j, square = n * n;//从（0，（n-1）/2）的位置开始生成
		//按行列式思想。每次赋值一个斜对角线上的值，可以避免重复赋值。
		//同时每一行
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;//先将当前位置赋值	
			if (i % n == 0)//计算下一次循环位置
				row++;//完成一轮斜放元素，跳到下一个斜对角线开始
			else {
				if (row == 0)//如果当前位置在第一行，则其下一个斜对角位置在最后一行，需要回转，避免越界
					row = n - 1;
				else
					row--;//非第一行情况，则行数减一
				if (col == (n - 1))//如果当前位置在最后一列，则其下一个斜对角位置在第一列，需要回转，避免越界
					col = 0;
				else//非最后一列情况，则，列数加一
					col++;
			}
		}

		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}
		
		try {
			PrintWriter out = new PrintWriter(
								new BufferedWriter(
								new OutputStreamWriter(
									new FileOutputStream("src\\P1\\txt\\"+filename))));
			
			for(i=0;i<n;i++) {
				for(j=0;j<n-1;j++) {
					out.print(magic[i][j]);
					out.print("\t");
				}
				out.print(magic[i][j]);
				if(i!=n-1) {
					out.print("\n");
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PrintWriter error opening the file:"+filename);
		}
		
		return true;
	}

	
	public static boolean SetSquare(ArrayList<ArrayList<Integer>> square,String filename) {
		String line;
		int value=0;
		try {
			BufferedReader in = new BufferedReader(
									new InputStreamReader(
										new FileInputStream(filename)));
			
			
			while((line=in.readLine())!=null) {//每次读入一行
				ArrayList<Integer> LineValue = new ArrayList<Integer>();
				String[] myline=line.split("\t");//利用split()拆分这一行
				for(String k : myline) {//for-each遍历的得到的字符串数组
					try {
						value=Integer.valueOf(k);//把字符串转为整形
					}catch(NumberFormatException e) {//捕获非法异常，返回false,并打出错误信息
						System.out.println("The numbers are not splited by '\\t'.");
						in.close();
						return false;
					}
					if(Math.abs(value-(int)value)>1e-6) {
						System.out.println("The numbers are not integer.");
						in.close();
						return false;
					}
					if(value<=0) {
						System.out.println("The numbers are not positive integer.");
						in.close();
						return false;
					}
					LineValue.add(value);
				}
				square.add(LineValue);
				
			}
			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Caught!");             
		}
		return true;
	}
	
	public static boolean isLegalMagicSquare(String filename) {
		ArrayList<ArrayList<Integer>> square = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> myline;
		int sum=0;
		int diagonal=0;
		int back_diagonal=0;
		if(!SetSquare(square, filename) || !IsSquare(square)) {
			return false;
		}
		int length=square.size();
		int [] a= new int[length];
		for(int j=0;j<length;j++) {
			sum +=square.get(0).get(j);
		}
		a[0]=sum;
		for(int i=1;i<length;i++) {
			myline=square.get(i);
			for(int j=0;j<myline.size();j++) {
				a[i]+=myline.get(j);
			}
		}
		for(int i=0;i<a.length-1;i++) {
			if(a[i]!=sum) {
				System.out.println("The Sum Of line is not a constant.");
				return false;
			}
		}
		
		for(int i=0;i<length;i++) {
			myline=square.get(i);
			for(int j=0;j<length;j++) {
				a[j]+=myline.get(j);
			}
		}
		for(int i=0;i<a.length-1;i++) {
			if(a[i]!=2*sum) {
				System.out.println("The SUM Of row is not a constant.");
				return false;
			}
		}
		
		for(int i=0;i<length;i++) {
			diagonal+=square.get(i).get(i);
			back_diagonal+=square.get(i).get(length-1-i);
		}
		if(back_diagonal!=sum || diagonal!=sum) {
			System.out.println("The sum Of diagonal is not a constant.");
			return false;
		}
		return true;
	}
	
	public static void ValueAdd(ArrayList<ArrayList<Integer>> square) {
		ArrayList<Integer> LineValue = new ArrayList<Integer>();
		LineValue.add(1);
		LineValue.add(10);
		square.add(LineValue);
	}
	
	public static void main(String[] args) {
		int n;
		String[] txt = {"1.txt","2.txt","3.txt","4.txt","5.txt","6.txt"};
		System.out.println("Input a number to generate a magicsquare!\nNum:");
		try (Scanner in = new Scanner(System.in)) {
			n=in.nextInt();
			try{
				generateMagicSquare(n);
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println(false);
				System.out.println("The function is over.");
				System.exit(1);
			}catch(NegativeArraySizeException e) {
				System.out.println(false);
				System.out.println("The function is over.");
				System.exit(1);
			}
			n=in.nextInt();			
		}
		for(String k :txt) {
			if(isLegalMagicSquare("src\\P1\\txt\\"+k)){
				System.out.println("The Square in "+k+" is a MagicSquare!");
			}
			else {
				System.out.println("The Square in "+k+" is not a MagicSquare!");
			}
		}
		
			
	}	
}

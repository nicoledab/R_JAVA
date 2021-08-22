package test.rjava;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;

public class connMain {
	
	public static void main(String[] args) {
		
		RConnection conn = null;
		try {
			conn = new RConnection();
			System.out.println(conn);
			
			//1
			REXP r = conn.eval("R.version.string");
			String ver = r.asString();  //�ϳ��� �� ������ 
			System.out.println(ver);  // r version 4.1.0(2021~) 
			
			//2
			r= conn.eval("month.abb");
			String [] month = r.asStrings();  // ���� ����(�������� ������)  
			for(String m : month) {
				System.out.println(m);  
			}
			
			/*
			int [] num = {1,2,3,4,5,6,7,8,9};
			conn.assign("r_num" , num);  // r_num <-c(1,2,3,4,5,6,7,8,9)	
			r = conn.eval("r_num+10");
			num = r.asIntegers();
			for(int n :num) {
				System.out.print(n+" "); // 11 12 13 14 15 16 17 18 19 
			}
			*/
			
		  // ����� �޴°� �� ��Ȯ�� 
			double [] num = {1,2,3,4,5,6,7,8,9};
			conn.assign("r_num" , num);  // r_num <-c(1,2,3,4,5,6,7,8,9)	
			conn.eval("result <- r_num+10");
			double [] i = conn.eval("result").asDoubles();
			for(double n : i) {
				System.out.print(n+" "); // 11.0 12.0 ~~ 18.0 19.0 
			}
			
			
			// ==========================
			// ������ ������ ������
			conn.eval("name <- c('aaa','bbb','ccc')");
			conn.eval("age <- c(20,25,42)");
			conn.eval("df <- data.frame(name, age)");
			RList list = conn.eval("df").asList();
			
			// ����(��) �����ֱ�
			String [] col = list.at(0).asStrings();
			for(String c :col) {
				System.out.println(c);   //bbb  ccc
				
			}
			
			
			conn.eval("sc <- read.csv('c:/r/score.csv' , header = T)");
	         RList sc = conn.eval("sc").asList();
	         for(int x = 0 ; x < sc.size() ; x++) {
	            String [] val =sc.at(x).asStrings();
	            for(String v : val) {
	               System.out.print(v+" ");
	            }
	           System.out.println();
	         }
			
	         // INSTALL�� R�� ���� ���� �ϱ�!         Rserve �����غ� .. 
	         conn.eval("library(rvest)");    //���⼭���� ������ 
	         conn.eval("url <- 'https://movie.naver.com/movie/point/af/list.nhn?&page='");
	         conn.eval("total_title <-NULL");
	         conn.eval("total_score <-NULL");
	         conn.eval("for(i in 1:3){ "
	               + "naver_movie_url <- paste(url,i,sep=''); "
	               + "html <- read_html(naver_movie_url); "
	               + "nodes <- html_nodes(html , 'td.title > a.movie'); "
	               + "title <- html_text(nodes); "
	               + "total_title <- c(total_title,title); "
	               + "nodes <- html_nodes(html , 'td.title > div > em'); "
	               + "score <- html_text(nodes); "
	               + "score <- as.numeric(score); "
	               + "total_score <- c(total_score, score); "
	               + "}");
	         int [] tt = conn.eval("total_score").asIntegers();
	         System.out.println("=====movie======"+tt.length);
			
	         
	         
	         // �̹��� ���� (r�� ��Ʈ ������ ) 
	         // String path = 
	         conn.eval("png('c:/save/test.png')");
	         conn.eval("plot(1:10)");
	         conn.eval("dev.off()");
	         

	         
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
	}
}

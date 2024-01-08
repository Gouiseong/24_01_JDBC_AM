package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	public void run() {
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");

				int actionResult = doAction(conn, sc, cmd);

				if (actionResult == -1) {
					System.out.println("==프로그램 종료==");
					sc.close();
					break;
				}

			} catch (SQLException e) {
				System.out.println("에러 1 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int doAction(Connection conn, Scanner sc, String cmd) {

		if (cmd.equals("exit")) {
			return -1;
		}

		if (cmd.equals("article write")) {
			System.out.println("==글쓰기==");
			System.out.print("제목 : ");
			String title = sc.nextLine();
			System.out.print("내용 : ");
			String body = sc.nextLine();

			PreparedStatement pstmt = null;

			try {
				String sql = "INSERT INTO article ";
				sql += "SET regDate = NOW(),";
				sql += "updateDate = NOW(),";
				sql += "title = '" + title + "',";
				sql += "`body`= '" + body + "';";

				System.out.println(sql);

				pstmt = conn.prepareStatement(sql);

				int affectedRow = pstmt.executeUpdate();

				System.out.println(affectedRow + "열에 적용됨");

			} catch (SQLException e) {
				System.out.println("에러 2: " + e);
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else if (cmd.equals("article list")) {
			System.out.println("==목록==");

			PreparedStatement pstmt = null;
			ResultSet rs = null;

			List<Article> articles = new ArrayList<>();

			try {
				String sql = "SELECT *";
				sql += " FROM article";
				sql += " ORDER BY id DESC;";

				System.out.println(sql);

				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery(sql);

				while (rs.next()) {
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");

					Article article = new Article(id, regDate, updateDate, title, body);

					articles.add(article);
				}

			} catch (SQLException e) {
				System.out.println("에러 3 : " + e);
			} finally {
				try {
					if (rs != null && !rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (articles.size() == 0) {
				System.out.println("게시글이 없습니다");
				return 0;
			}

			System.out.println("  번호  /   제목  ");
			for (Article article : articles) {
				System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
			}
		} else if (cmd.startsWith("article modify")) {

			int id = 0;

			try {
				id = Integer.parseInt(cmd.split(" ")[2]);
			} catch (Exception e) {
				System.out.println("번호는 정수로 입력해");
				return 0;
			}

			System.out.println("==수정==");
			System.out.print("새 제목 : ");
			String title = sc.nextLine().trim();
			System.out.println("새 내용 : ");
			String body = sc.nextLine().trim();

			PreparedStatement pstmt = null;

			try {
				String sql = "UPDATE article";
				sql += " SET updateDate = NOW()";
				if (title.length() > 0) {
					sql += " ,title = '" + title + "'";
				}
				if (body.length() > 0) {
					sql += " ,`body` = '" + body + "'";
				}
				sql += " WHERE id = " + id + ";";

				System.out.println(sql);

				pstmt = conn.prepareStatement(sql);

				pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("에러 4 : " + e);
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			System.out.println(id + "번 글이 수정되었습니다.");
		}
		return 0;
	}
}
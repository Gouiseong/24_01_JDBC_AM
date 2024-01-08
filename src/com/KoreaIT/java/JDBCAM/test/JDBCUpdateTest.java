package com.KoreaIT.java.JDBCAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.Article;

public class JDBCUpdateTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");

			System.out.print("수정할 게시글 번호 : ");
			String num = sc.nextLine();
			System.out.print("수정할 제목 : ");
			String title = sc.nextLine();
			System.out.print("수정할 내용 : ");
			String body = sc.nextLine();
			String sql = "update article";
			sql += " set title ='" + title + "',";
			sql += " `body`='" + body + "'";
			sql += " where id=" + num + ";";

			System.out.println(sql);

			pstmt = conn.prepareStatement(sql);

//			rs = pstmt.executeQuery(sql);

			int result = pstmt.executeUpdate();
			System.out.println(result);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
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
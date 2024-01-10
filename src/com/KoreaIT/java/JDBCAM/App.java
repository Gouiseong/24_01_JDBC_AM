package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;
import com.KoreaIT.java.JDBCAM.Member;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;
import com.KoreaIT.java.JDBCAM.util.Util;

public class App {
	List<Member> members = new ArrayList<>();
	int lastId = 0;

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

			SecSql sql = new SecSql();

			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW(),");
			sql.append("updateDate = NOW(),");
			sql.append("title = ?,", title);
			sql.append("`body`= ?;", body);

			int id = DBUtil.insert(conn, sql);

			System.out.println(id + "번 글이 생성되었습니다");

		} else if (cmd.equals("article list")) {
			System.out.println("==목록==");

			List<Article> articles = new ArrayList<>();

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC;");

			List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> articleMap : articleListMap) {
				articles.add(new Article(articleMap));
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

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?;", id);

			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

			if (articleMap.isEmpty()) {
				System.out.println(id + "번 글은 없습니다.");
				return 0;
			}

			System.out.println("==수정==");
			System.out.print("새 제목 : ");
			String title = sc.nextLine().trim();
			System.out.println("새 내용 : ");
			String body = sc.nextLine().trim();

			sql = new SecSql();

			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW()");
			if (title.length() > 0) {
				sql.append(",title = ?", title);
			}
			if (body.length() > 0) {
				sql.append(",`body`= ?", body);
			}
			sql.append("WHERE id = ?;", id);

			DBUtil.update(conn, sql);

			System.out.println(id + "번 글이 수정되었습니다.");
		} else if (cmd.startsWith("article detail")) {

			int id = 0;

			try {
				id = Integer.parseInt(cmd.split(" ")[2]);
			} catch (Exception e) {
				System.out.println("번호는 정수로 입력해");
				return 0;
			}

			System.out.println("==상세보기==");

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?;", id);

			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

			if (articleMap.isEmpty()) {
				System.out.println(id + "번 글은 없습니다.");
				return 0;
			}

			Article article = new Article(articleMap);

			System.out.println("번호 : " + article.getId());
			System.out.println("작성날짜 : " + Util.getNowDate_TimeStr(article.getRegDate()));
			System.out.println("수정날짜 : " + Util.getNowDate_TimeStr(article.getUpdateDate()));
			System.out.println("제목 : " + article.getTitle());
			System.out.println("내용 : " + article.getBody());

		} else if (cmd.startsWith("article delete")) {

			int id = 0;

			try {
				id = Integer.parseInt(cmd.split(" ")[2]);
			} catch (Exception e) {
				System.out.println("번호는 정수로 입력해");
				return 0;
			}

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?;", id);

			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

			if (articleMap.isEmpty()) {
				System.out.println(id + "번 글은 없습니다.");
				return 0;
			}

			sql = new SecSql();

			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?;", id);

			DBUtil.delete(conn, sql);

			System.out.println(id + "번 글이 삭제되었습니다.");
		} else if (cmd.equals("member join")) {
			System.out.println(" == 회원 가입 == ");
			int id = lastId + 1;
			String loginId = "";
			while (true) {
				System.out.print("아이디 : ");
				loginId = sc.nextLine().trim();
				if (isJoinableLoginId(loginId) == false) {
					System.out.println("이미 있는 아이디입니다.");
					continue;
				} else if (loginId == "") {
					System.out.println("필수 입력 정보입니다.");
					continue;
				} else if(loginId.contains(" ")) {
					System.out.println("공백 없이 입력해주세요");
					continue;
				}
				break;
			}
			String loginPw = "";
			while (true) {
				while (true) {
					System.out.print("비밀번호 : ");
					loginPw = sc.nextLine().trim();
					if (loginPw == "") {
						System.out.println("필수 입력 정보입니다.");
						continue;
					}else if(loginPw.contains(" ")) {
						System.out.println("공백 없이 입력해주세요");
						continue;
					}
					break;
				}
				System.out.print("비밀번호 확인 : ");
				String loginPwCheck = sc.nextLine();
				if (loginPw.equals(loginPwCheck) == false) {
					System.out.println("비번 틀림");
					continue;
				}
				break;
			}
			String name="";
			while(true) {
				System.out.print("이름 : ");
				name = sc.nextLine().trim();
				if(name=="") {
					System.out.println("필수 입력 정보입니다.");
					continue;
				}else if(name.contains(" ")) {
					System.out.println("공백 없이 입력해주세요");
					continue;
				}
				break;
			}

			LocalDateTime now = LocalDateTime.now();
			String regDate = Util.getNowDate_TimeStr(now);
			String updateDate = Util.getNowDate_TimeStr(now);


			lastId++;
			Member member = new Member(id, regDate, updateDate, loginId, loginPw, name);
			members.add(member);
			
			SecSql sql = new SecSql();
						
			sql.append("INSERT INTO `member`");
			sql.append("SET regDate = ?,",regDate);
			sql.append("updateDate = ?,",updateDate);
			sql.append("loginId = ?,", loginId);
			sql.append("loginPw= ?,", loginPw);
			sql.append("name = ?;",name);

			int memberNum= DBUtil.insert(conn, sql);
			System.out.printf("%d번 회원이 가입되었습니다.\n",memberNum);

		}

		return 0;
	}

	private boolean isJoinableLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return false;
			}
		}
		return true;
	}
}
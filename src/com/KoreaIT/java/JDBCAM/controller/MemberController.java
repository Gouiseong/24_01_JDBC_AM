package com.KoreaIT.java.JDBCAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class MemberController {
	Connection conn;
	Scanner sc;

	public MemberController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}

	public void doJoin() {
		String loginId = null;
		String loginPw = null;
		String loginPwConfirm = null;
		String name = null;

		System.out.println("==회원가입==");
		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0 || loginId.contains(" ")) {
				System.out.println("아이디 똑바로 입력해");
				continue;
			}

			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?;", loginId);

			boolean isLoginIdDup = DBUtil.selectRowBooleanValue(conn, sql);

			if (isLoginIdDup) {
				System.out.println(loginId + "는(은) 이미 사용중");
				continue;
			}

			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0 || loginPw.contains(" ")) {
				System.out.println("비밀번호 똑바로 입력해");
				continue;
			}

			boolean loginPwCheck = true;

			while (true) {
				System.out.print("비밀번호 확인: ");
				loginPwConfirm = sc.nextLine().trim();

				if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
					System.out.println("확인 똑바로 입력해");
					continue;
				}

				if (loginPw.equals(loginPwConfirm) == false) {
					System.out.println("일치하지 않아");
					loginPwCheck = false;
				}
				break;
			}

			if (loginPwCheck) {
				break;
			}
		}
		while (true) {
			System.out.print("이름  : ");
			name = sc.nextLine().trim();

			if (name.length() == 0 || name.contains(" ")) {
				System.out.println("이름 똑바로 입력해");
				continue;
			}
			break;
		}

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("loginId = ?,", loginId);
		sql.append("loginPw = ?,", loginPw);
		sql.append("`name` = ?;", name);

		int id = DBUtil.insert(conn, sql);

		System.out.println(id + "번 회원이 가입 되었습니다");

	}

}
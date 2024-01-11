package com.KoreaIT.java.JDBCAM.container;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.controller.ArticleController;
import com.KoreaIT.java.JDBCAM.controller.MemberController;
import com.KoreaIT.java.JDBCAM.dao.ArticleDao;
import com.KoreaIT.java.JDBCAM.dao.MemberDao;
import com.KoreaIT.java.JDBCAM.service.ArticleService;
import com.KoreaIT.java.JDBCAM.service.MemberService;
import com.KoreaIT.java.JDBCAM.session.Session;

public class Container {

	public static ArticleController articleController;
	public static MemberController memberController;

	public static ArticleService articleService;
	public static MemberService memberService;

	public static ArticleDao articleDao;
	public static MemberDao memberDao;

	public static Scanner sc;

	public static Connection conn;

	public static Session session;

	public static void init() {
		sc = new Scanner(System.in);

		session = new Session();

		articleDao = new ArticleDao();
		memberDao = new MemberDao();

		articleService = new ArticleService();
		memberService = new MemberService();

		articleController = new ArticleController();
		memberController = new MemberController();

	}

}
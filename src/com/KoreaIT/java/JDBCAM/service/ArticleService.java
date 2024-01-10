package com.KoreaIT.java.JDBCAM.service;

import java.sql.Connection;

import com.KoreaIT.java.JDBCAM.dao.ArticleDao;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
	}

	public int doWrite(String title, String body) {
		return articleDao.doWrite(title, body);
	}

}
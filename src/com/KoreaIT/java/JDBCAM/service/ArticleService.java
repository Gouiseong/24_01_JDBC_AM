package com.KoreaIT.java.JDBCAM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.dao.ArticleDao;
import com.KoreaIT.java.JDBCAM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		this.articleDao = Container.articleDao;
	}

	public int doWrite(int memberId, String title, String body) {
		return articleDao.doWrite(memberId, title, body);
	}

	public List<Article> getForPrintArticles(int page, int itemsInPage, String searchKeyword) {
		int limitFrom = (page - 1) * itemsInPage;
		int limitTake = itemsInPage;

		Map<String, Object> args = new HashMap<>();
		args.put("searchKeyword", searchKeyword);
		args.put("limitTake", limitTake);
		args.put("limitFrom", limitFrom);

		return articleDao.getForPrintArticles(args);
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public void doUpdate(int id, String title, String body) {
		articleDao.doUpdate(id, title, body);
	}

}
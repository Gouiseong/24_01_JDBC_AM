package com.KoreaIT.java.JDBCAM.dao;

import java.util.Map;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.dto.Member;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class MemberDao {

	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?;", loginId);

		Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

		if (memberMap.isEmpty()) {
			return null;
		}

		return new Member(memberMap);
	}

	public boolean isLoginIdDup(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?;", loginId);

		return DBUtil.selectRowBooleanValue(Container.conn, sql);
	}

	public int doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("loginId = ?,", loginId);
		sql.append("loginPw = ?,", loginPw);
		sql.append("`name` = ?;", name);

		return DBUtil.insert(Container.conn, sql);
	}

}
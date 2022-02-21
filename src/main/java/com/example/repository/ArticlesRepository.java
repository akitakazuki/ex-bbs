package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Articles;

@Repository
public class ArticlesRepository {
	
	private static final RowMapper<Articles> ARTICLES_ROW_MAPPER =(rs,i)->{
		Articles articles = new Articles();
		articles.setId(rs.getInt("id"));
		articles.setName(rs.getString("name"));
		articles.setContent(rs.getString("content"));
		return articles;
	};
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	/**
	 * 記事投稿機能
	 * @param name 投稿者名
	 * @param content　投稿内容
	 */
	public void insert(Articles articles) {
		String sql = "INSERT INTO articles (name,content) VALUES (:name,:content)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", articles.getName()).addValue("content", articles.getContent());
		template.update(sql,param);
	}
	
	/**
	 * 全記事表示機能
	 * @return articlesList　掲示板表示内容（投稿者、投稿内容）
	 */
	public List<Articles> findAll(){
		String sql = "SELECT * FROM articles ORDER BY id DESC";
		List<Articles> articlesList = template.query(sql,ARTICLES_ROW_MAPPER);
		return articlesList;
	}
	
	/**
	 * 記事削除機能
	 * @param 投稿者ID
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM articles WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}

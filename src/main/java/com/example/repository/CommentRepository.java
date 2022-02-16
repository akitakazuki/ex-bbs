package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


import com.example.domain.Comment;

@Repository
public class CommentRepository {
	
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER =(rs,i)->{
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
		};
		
		@Autowired
		private NamedParameterJdbcTemplate template;
		
		/**
		 * コメント表示機能
		 * @param　投稿者ID
		 * @return commentList　コメント表示内容
		 */
		public List<Comment> findByArticleId(int articleId){
			String sql = "SELECT * FROM comments WHERE article_id = :articleId";
			SqlParameterSource param = new MapSqlParameterSource().addValue("articleId",articleId);
			List<Comment> commentList = template.query(sql,param,COMMENT_ROW_MAPPER);	
			return commentList;
		}
		
		/**
		 * コメント投稿機能
		 * @param name コメント内容（全情報）
		 */
		public void insert(Comment comment) {
			String sql = "INSERT INTO comments (name,content,article_id) VALUES (:name,:content,:article_id)";
			SqlParameterSource param = new MapSqlParameterSource().addValue("name", comment.getName()).addValue("content", comment.getContent()).addValue("article_id", comment.getArticleId());
			template.update(sql,param);
		}
		
		/**
		 * コメント削除機能
		 * @param　投稿者ID
		 */
		public void deleteByArticleId(Integer articleId) {
			String sql = "DELETE FROM comments WHERE article_id = :articleId";
			SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
			template.update(sql,param);
		}
		
}
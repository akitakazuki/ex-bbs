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
		
		
		public List<Comment> findByArticleId(int articleId){
			String sql = "SELECT name,content FROM comments AS c "
					+ "JOIN articles AS a "
					+ "ON a.id = c.:articles_id "
					+ "ORDER BY id DESC";
			SqlParameterSource param = new MapSqlParameterSource().addValue("articles_id",articleId);
			List<Comment> commentList = template.query(sql,param,COMMENT_ROW_MAPPER);	
			return commentList;
		}
	
}

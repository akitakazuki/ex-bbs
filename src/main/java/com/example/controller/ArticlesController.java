package com.example.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Articles;
import com.example.domain.Comment;
import com.example.form.ArticlesForm;
import com.example.form.CommentForm;
import com.example.service.ArticlesService;
import com.example.service.CommentService;


@Controller
@RequestMapping("/bbs")
public class ArticlesController {
	
	@ModelAttribute
 	public ArticlesForm articlesForm() {
		return new ArticlesForm();
 	}
	@ModelAttribute
 	public CommentForm commentForm() {
		return new CommentForm();
 	}
	
	@Autowired
	private ArticlesService articlesService;
	@Autowired
	private CommentService commentService;
	
	
	
	@RequestMapping("")
	public String index(Model model) {
		List<Articles> articleList = articlesService.findAll();
		
		for (Articles article : articleList) {
			List<Comment> commentList = commentService.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		model.addAttribute("articleList",articleList);
	
		return "bbs";
	}
	
	@RequestMapping("/insert-articles")
	public String insertArticle(Articles articles, ArticlesForm articlesForm,Model model) {
		articles.setName(articlesForm.getName());
		articles.setContent(articlesForm.getContent());
		
		articlesService.insert(articles);
		List<Articles> articleList = articlesService.findAll();;
		model.addAttribute("articleList",articleList);
		return "bbs";
	}
	
	@RequestMapping("/insert-comment")
	public String insertComment(Comment comment,CommentForm commentForm,Model model) {
		comment.setName(commentForm.getName());
		comment.setArticleId(commentForm.getArticleId());
		comment.setContent(commentForm.getContent());
		return "bbs";
	}
	
	@RequestMapping("/delete")
	public String deleteArticle(Integer articleId) {
		articlesService.deleteByArticleId(articleId);
		return "bbs";
	}
	
}

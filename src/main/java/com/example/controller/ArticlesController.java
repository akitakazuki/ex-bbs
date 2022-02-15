package com.example.controller;

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
	
	
	
	@RequestMapping("/list")
	public String list(CommentForm commentForm ,Model model) {
		List<Articles> articleList = articlesService.showAll();
		model.addAttribute("articleList",articleList) ;
		
//		List<Comment> commentList = commentService.showComment(commentForm.getId());
//		model.addAttribute("commentList",commentList);
		return "bbs";
	}
	
	@RequestMapping("/insert")
	public String input(Articles articles, ArticlesForm articlesForm,Model model) {
		articles.setName(articlesForm.getName());
		articles.setContent(articlesForm.getContent());
		articlesService.inputData(articles);
		List<Articles> articleList = articlesService.showAll();;
		model.addAttribute("articleList",articleList) ;
		return "bbs";
	}

}

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

	/**
	 * @return　投稿フォームオブジェクトがリクエストスコープに格納
	 */
	@ModelAttribute
	public ArticlesForm articlesForm() {
		return new ArticlesForm();
	}
	/**
	 * @return　コメントフォームオブジェクトがリクエストスコープに格納
	 */
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
		
		//すべての投稿情報から投稿者IDを取得し、検索したコメント情報をarticleドメインに格納する
		for (Articles article : articleList) {
			List<Comment> commentList = commentService.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		//
		model.addAttribute("articleList", articleList);
		return "bbs";
	}
	
	/**
	 * 記事投稿を操作する
	 * @param articleId 投稿者ID
	 * @return　topページにリダイレクト
	 */
	@RequestMapping("/insert-articles")
	public String insertArticle(Articles articles, ArticlesForm articlesForm, Model model) {
		articles.setName(articlesForm.getName());
		articles.setContent(articlesForm.getContent());

		articlesService.insert(articles);
		List<Articles> articleList = articlesService.findAll();
		model.addAttribute("articleList", articleList);
		return "redirect:/bbs";
	}
	
	
	/**
	 * コメントデータを操作する
	 * @param comment　
	 * @param commentForm
	 * @param model
	 * @return　topページにリダイレクト
	 */
	@RequestMapping("/insert-comment")
	public String insertComment(Comment comment, CommentForm commentForm, Model model) {
		comment.setName(commentForm.getName());
		comment.setContent(commentForm.getContent());
		comment.setArticleId(commentForm.getArticleId());
		commentService.insert(comment);
		return "redirect:/bbs";
	}

	/**
	 * 記事データ、コメントデータを削除する
	 * @param articleId 投稿者ID
	 * @return　topページにリダイレクト
	 */
	@RequestMapping("/delete")
	public String deleteArticle(Integer articleId) {
		commentService.deleteByArticleId(articleId);
		articlesService.deleteByArticleId(articleId);
		return "redirect:/bbs";
	}

}

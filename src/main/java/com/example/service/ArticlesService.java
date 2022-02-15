package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Articles;
import com.example.repository.ArticlesRepository;

@Service
public class ArticlesService {
	
	@Autowired
	private ArticlesRepository repository;
	
	/**
	 * 記事一覧を表示する
	 * @return 記事一覧表示
	 */
	public List<Articles> showAll() {
		return repository.findAll();
	}
	
	/**
	 * @param name　入力された投稿者名
	 * @param content　入力された投稿内容
	 */
	public void inputData(Articles articles){
		repository.insert(articles);
	}
}

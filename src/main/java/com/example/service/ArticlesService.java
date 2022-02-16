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
	
	public List<Articles> findAll() {
		return repository.findAll();
	}
	
	public void insert(Articles articles){
		repository.insert(articles);
	}
	
	public void deleteByArticleId(Integer id){
		repository.deleteById(id);
	}
	
}

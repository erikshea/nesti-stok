package controller;

import java.awt.BorderLayout;

public class ArticleDirectory extends BaseDirectory {

	public ArticleDirectory(MainWindowControl c) {
		super(c);
		
		var articleList = new ArticleList();
		this.add(articleList, BorderLayout.WEST);
		
	}
}
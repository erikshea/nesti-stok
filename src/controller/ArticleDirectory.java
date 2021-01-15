package controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class ArticleDirectory extends BaseDirectory {

	public ArticleDirectory(MainWindowControl c) {
		super(c);

		var articleList = new ArticleList(c);
		this.add(articleList, BorderLayout.CENTER);

		
	}
}
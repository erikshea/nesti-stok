package controller;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class ArticleDirectory extends BaseDirectory {
	protected ArticleList entityList;
	
	public ArticleList getEntityList() {
		return entityList;
	}
	
	public ArticleDirectory(MainWindowControl c) {
		super(c);

		this.entityList = new ArticleList(c);
		this.add(this.entityList, BorderLayout.CENTER);

		
	}
}
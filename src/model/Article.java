package model;


public class Article {
	protected int userId;
	protected String articleCode;
	protected String name;
	protected String ean;
	protected float weight;
	protected float quantity;
	protected int stock;
	protected Product product;
	
	
	public Offers[] getOffers(Supplier supplier) {
		return null;
	}
	
	public Offers[] getOrdersArticle(Supplier supplier) {
		return null;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getArticleCode() {
		return articleCode;
	}
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}

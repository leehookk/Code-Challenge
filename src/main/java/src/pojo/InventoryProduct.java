package src.pojo;

public class InventoryProduct {

	private int productId;
	private String productName;
	private int quantity;

	public InventoryProduct() {
		super();
	}

	public InventoryProduct(int productId, String productName, int quantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public String toString() {
		return "InventoryProduct [productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
				+ "]";
	}
}

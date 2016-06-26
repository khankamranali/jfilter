package gk.jfilter.sample.product;

import gk.jfilter.JFilter;

import java.util.ArrayList;
import java.util.List;

public class SimpleMapReduceMain {

	public static void main(String[] args) throws Exception {
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i <= 100000; ++i) {
			Product product = new Product(i);
			product.addSku(new Sku("RedApple", i * 10));
			product.addSku(new Sku("RedApple", i * 10));
			product.addSku(new Sku("GreenApple", i));
			products.add(product);
		}

		JFilter<Product> filter = new JFilter<Product>(products, Product.class);
		
		/** Select Skus where product code is less than equals to 5 and Sku price is 30. */
		List<Sku> skus = filter.filter("{'code':{'$le':'?1'}}", 5).<Sku> map("skus").filter("{'price':'?1'}", 30).out(new ArrayList<Sku>());

		/** Select prices of Skus where product code is less than equals to 5. */
		List<Integer> prices = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").out(new ArrayList<Integer>());

		/** Select Skus where sku price is less than equals to 10.m */
		filter.<Sku> map("skus").filter("{'price':{'$le':'?1'}}", 10).out(skus);

		/** Select max price of Skus where product code is less than equals to 5.*/
		Integer max = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").max();
		
		/** Select min price of Skus where product code is less than equals to 5.*/
		Integer min = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").min();
		
		/** Select sum of  prices of Skus where product code is less than equals to 5.*/
		Integer sum = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").sum();
		
	}

}

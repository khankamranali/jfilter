package gk.jfilter.sample.product;

import gk.jfilter.JFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i <= 1000000; ++i) {
			Product product = new Product(i);
			product.addSku(new Sku("RedApple", i * 10));
			product.addSku(new Sku("RedApple", i * 10));
			product.addSku(new Sku("GreenApple", i));
			products.add(product);
		}

		filter1(products);
		filter1(products);
		filter2(products);
		filter3(products);
		filter4(products);
		filter5(products);
		filter6(products);
	}

	private static void filter1(Collection<Product> products) {
		/** Select all products where product code equals to 5 */
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);

		long stime = System.currentTimeMillis();
		
		Map<String, Integer> parameters = new HashMap<String, Integer>();
		parameters.put("code", 5);
		
		List<Product> fp = filter.filter("{'code':'?code'}", parameters).out(new ArrayList<Product>());
		
		long etime = System.currentTimeMillis();
		for (Product p : fp) {
			System.out.println(p);
		}
		System.out.println("Filter1 Time in millis:" + (etime - stime));
	}

	private static void filter2(Collection<Product> products) {
		/** Select all products where product code in 5, 10 or 100 */
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);

		long stime = System.currentTimeMillis();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<Integer> codes = new ArrayList<Integer>();
		codes.add(5);
		codes.add(10);
		codes.add(100);
		parameters.put("codes", codes);
		
		List<Product> fp = filter.filter("{'code': {'$in':'?codes'}}", parameters).out(new ArrayList<Product>());
		
		long etime = System.currentTimeMillis();
		for (Product p : fp) {
			System.out.println(p);
		}
		System.out.println("Filter2 Time in millis:" + (etime - stime));
	}

	private static void filter3(Collection<Product> products) {
		/**
		 * Select all products where product code equals to 5 or sku price less
		 * than to 60
		 */
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);

		long stime = System.currentTimeMillis();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", 5);
		parameters.put("price", 60);
		
		List<Product> fp = filter.filter("{ '$and':[{'code': '?code'}, {'skus.price':{'$le':'?price'}}]}", parameters)
				                       .out(new ArrayList<Product>());
		
		long etime = System.currentTimeMillis();
		
		for (Product p : fp) {
			System.out.println(p);
		}
		System.out.println("Filter3 Time in millis:" + (etime - stime));
	}
	
	private static void filter4(Collection<Product> products) {
		/**
		 * Select all products where any of the sku price 500 or sku price is less
		 * than 60
		 */
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);

		long stime = System.currentTimeMillis();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("price1", 60);
		parameters.put("price2", 500);
		
		List<Product> fp = filter.filter("{'skus': { '$or': [{'price':{'$le':'?price1'}}, {'price':'?price2'}]}}", parameters)
									   .out(new ArrayList<Product>());
		
		long etime = System.currentTimeMillis();
		
		for (Product p : fp) {
			System.out.println(p);
		}
		System.out.println("Filter4 Time in millis:" + (etime - stime));
	}

	private static void filter5(Collection<Product> products) {
		/**
		 * Select all products where any of the sku price in 20 and 40 and sku
		 * code is RedApple
		 */
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);

		long stime = System.currentTimeMillis();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<Integer> prices = new ArrayList<Integer>();
		prices.add(20);
		prices.add(40);
		parameters.put("prices", prices);
		parameters.put("skuCode", "RedApple");
		
		List<Product> fp = filter.filter("{'skus': {'$and':[{'price':{'$in':'?prices'}}, {'code':'?skuCode'}]}}", parameters)
									   .out(new ArrayList<Product>());
		
		long etime = System.currentTimeMillis();
		
		for (Product p : fp) {
			System.out.println(p);
		}
		System.out.println("Filter5 Time in millis:" + (etime - stime));
	}

	private static void filter6(Collection<Product> products) {
		/**
		 * Select all products where product code is 10 or sku price in 20 and
		 * 40 and sku code is RedApple
		 */
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);

		long stime = System.currentTimeMillis();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<Integer> prices = new ArrayList<Integer>();
		prices.add(20);
		prices.add(40);
		parameters.put("prices", prices);
		parameters.put("skuCode", "RedApple");
		parameters.put("code", 5);
		
		List<Product> fp = filter.filter("{'$and':[{'code':'?code'},{'skus': {'$and':[{'price':{'$in':'?prices'}}, {'code':'?skuCode'}]}}]}", parameters)
									   .out(new ArrayList<Product>());
		
		long etime = System.currentTimeMillis();
		
		for (Product p : fp) {
			System.out.println(p);
		}
		System.out.println("Filter6 Time in millis:" + (etime - stime));
	}

}

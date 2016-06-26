package gk.jfilter.sample.product;

import gk.jfilter.JFilter;
import gk.jfilter.sample.product.Product;
import gk.jfilter.sample.product.Sku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductMapReduceMain {

	public static void main(String[] args) throws Exception {
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i <= 100000; ++i) {
			Product product = new Product(i);
			product.addSku(new Sku("RedApple", i * 10));
			product.addSku(new Sku("RedApple", i * 10));
			product.addSku(new Sku("GreenApple", i));
			products.add(product);
		}

		filterFilter(products);
		filterMap(products);
		filterMapReduce(products);
		mapReduce(products);
		mapFilter(products);
	}

	private static void filterFilter(Collection<Product> products) {

		long stime = System.currentTimeMillis();
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);
		List<Sku> skus = filter.filter("{'code':{'$le':'?1'}}", 5).<Sku> map("skus").filter("{'price':'?1'}", 30).out(new ArrayList<Sku>());
		long etime = System.currentTimeMillis();

		for (Sku p : skus) {
			System.out.println(p);
		}
		System.out.println("Filter1 jfilter Time in millis:" + (etime - stime));
	}

	private static void filterMap(Collection<Product> products) {
		long stime = System.currentTimeMillis();
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);
		List<Integer> prices = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").out(new ArrayList<Integer>());
		long etime = System.currentTimeMillis();

		for (Integer i : prices) {
			System.out.println(i);
		}
		System.out.println("Filter2 jfilter Time in millis:" + (etime - stime));
	}

	private static void filterMapReduce(Collection<Product> products) {
		long stime = System.currentTimeMillis();

		JFilter<Product> filter = new JFilter<Product>(products, Product.class);
		Integer max = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").max();
		Integer min = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").min();
		Integer sum = filter.filter("{'code':{'$le':'?1'}}", 5).<Integer> map("skus.price").sum();

		long etime = System.currentTimeMillis();

		System.out.println("max: " + max);
		System.out.println("min: " + min);
		System.out.println("sum: " + sum);

		System.out.println("Filter3 jfilter Time in millis:" + (etime - stime));
	}

	private static void mapReduce(Collection<Product> products) {
		long stime = System.currentTimeMillis();

		JFilter<Product> filter = new JFilter<Product>(products, Product.class);
		Integer max = filter.<Integer> map("skus.price").max();
		Integer min = filter.<Integer> map("skus.price").min();
		Integer sum = filter.<Integer> map("skus.price").sum();

		long etime = System.currentTimeMillis();

		System.out.println("max: " + max);
		System.out.println("min: " + min);
		System.out.println("sum: " + sum);

		System.out.println("Filter4 jfilter Time in millis:" + (etime - stime));
	}

	private static void mapFilter(Collection<Product> products) {
		long stime = System.currentTimeMillis();
		JFilter<Product> filter = new JFilter<Product>(products, Product.class);
		List<Sku> skus = new ArrayList<Sku>();
		filter.<Sku> map("skus").filter("{'price':{'$le':'?1'}}", 10).out(skus);
		long etime = System.currentTimeMillis();

		for (Sku s : skus) {
			System.out.println(s);
		}
		System.out.println("Filter5 jfilter Time in millis:" + (etime - stime));
	}

}

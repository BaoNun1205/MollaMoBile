package vn.nun.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.nun.models.Category;
import vn.nun.models.Product;
import vn.nun.repository.ProductRepository;
import vn.nun.services.CategoryService;
import vn.nun.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Override
	public List<Product> getAll() {
		return this.productRepository.findAll();
	}
	@Override
	public Boolean create(Product product) {
		try {
			this.productRepository.save(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Product findById(Integer id) {
		return this.productRepository.findById(id).get();
	}

	@Override
	public Boolean update(Product product) {
		try {
			this.productRepository.save(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.productRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Product> findByKeyword(String keyword) {
		return productRepository.findByProductNameContainingIgnoreCase(keyword);
	}

	@Override
	public List<Product> findByKeyword(String keyword, Pageable pageable) {
		return productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
	}

	@Override
	public List<Product> filterByPriceAndCategory(List<String> filters, Category category, String keyword) {
		if (filters == null || filters.isEmpty()) {
			// Nếu không có bộ lọc, trả về tất cả các sản phẩm
			return category.getProducts();
		}

		Set<Product> filteredProducts = new HashSet<>();

		for (String filter : filters) {
			Double minPrice = null;
			Double maxPrice = null;

			switch (filter) {
				case "price-1":
					minPrice = 0.00;
					maxPrice = 200.00;
					break;
				case "price-2":
					minPrice = 200.00;
					maxPrice = 400.00;
					break;
				case "price-3":
					minPrice = 400.00;
					maxPrice = 600.00;
					break;
				case "price-4":
					minPrice = 600.00;
					maxPrice = 800.00;
					break;
				case "price-5":
					minPrice = 800.00;
					maxPrice = 1200.00;
					break;
				case "price-6":
					minPrice = 1200.00;
					// Không cần thiết đặt maxPrice vì không giới hạn trên
					break;
				default:
					break;
			}

			if (minPrice != null && maxPrice != null) {
				if (category == null){
					filteredProducts.addAll(productRepository.findByPriceBetweenAndProductNameContainingIgnoreCase(minPrice, maxPrice, keyword));
				} else {
					filteredProducts.addAll(productRepository.findByPriceBetweenAndCategoryAndProductNameContainingIgnoreCase(minPrice, maxPrice, category, keyword));
				}
			} else if (minPrice != null) {
				if (category == null){
					filteredProducts.addAll(productRepository.findByPriceGreaterThanEqualAndProductNameContainingIgnoreCase(minPrice, keyword));
				} else {
					filteredProducts.addAll(productRepository.findByPriceGreaterThanEqualAndCategoryAndProductNameContainingIgnoreCase(minPrice, category, keyword));
				}
			} else if (maxPrice != null) {
				if (category == null){
					filteredProducts.addAll(productRepository.findByPriceLessThanEqualAndProductNameContainingIgnoreCase(maxPrice, keyword));
				} else {
					filteredProducts.addAll(productRepository.findByPriceLessThanEqualAndCategoryAndProductNameContainingIgnoreCase(maxPrice, category, keyword));
				}
			}
		}
		List<Product> sortedProducts = filteredProducts.stream()
				.sorted(Comparator.comparingDouble(Product::getPrice))
				.collect(Collectors.toList());

		return sortedProducts;
	}

	@Override
	public Page<Product> filterByPriceAndCategory(List<String> filters, List<Category> categories, String keyword, Pageable pageable) {
		Set<Product> filteredProducts = new HashSet<>();

		// Xử lý các bộ lọc giá
		for (String filter : filters) {
			Double minPrice = null;
			Double maxPrice = null;

			switch (filter) {
				case "price-1":
					minPrice = 0.00;
					maxPrice = 200.00;
					break;
				case "price-2":
					minPrice = 200.00;
					maxPrice = 400.00;
					break;
				case "price-3":
					minPrice = 400.00;
					maxPrice = 600.00;
					break;
				case "price-4":
					minPrice = 600.00;
					maxPrice = 800.00;
					break;
				case "price-5":
					minPrice = 800.00;
					maxPrice = 1200.00;
					break;
				case "price-6":
					minPrice = 1200.00;
					break;
				default:
					break;
			}

			if (minPrice != null && maxPrice != null) {
				if (categories.isEmpty()) {
					filteredProducts.addAll(productRepository.findByPriceBetweenAndProductNameContainingIgnoreCase(minPrice, maxPrice, keyword));
				} else {
					for (Category category : categories) {
						filteredProducts.addAll(productRepository.findByPriceBetweenAndCategoryAndProductNameContainingIgnoreCase(minPrice, maxPrice, category, keyword));
					}
				}
			} else if (minPrice != null) {
				if (categories.isEmpty()) {
					filteredProducts.addAll(productRepository.findByPriceGreaterThanEqualAndProductNameContainingIgnoreCase(minPrice, keyword));
				} else {
					for (Category category : categories) {
						filteredProducts.addAll(productRepository.findByPriceGreaterThanEqualAndCategoryAndProductNameContainingIgnoreCase(minPrice, category, keyword));
					}
				}
			} else if (maxPrice != null) {
				if (categories.isEmpty()) {
					filteredProducts.addAll(productRepository.findByPriceLessThanEqualAndProductNameContainingIgnoreCase(maxPrice, keyword));
				} else {
					for (Category category : categories) {
						filteredProducts.addAll(productRepository.findByPriceLessThanEqualAndCategoryAndProductNameContainingIgnoreCase(maxPrice, category, keyword));
					}
				}
			}
		}

		// Sắp xếp sản phẩm theo giá
		List<Product> sortedProducts = new ArrayList<>(filteredProducts);
		sortedProducts.sort(Comparator.comparingDouble(Product::getPrice));

		// Phân trang
		Integer start = (int) pageable.getOffset();
		Integer end = (int) Math.min(start + pageable.getPageSize(), sortedProducts.size());

		List<Product> pagedProducts = sortedProducts.subList(start, end);

		return new PageImpl<>(pagedProducts, pageable, sortedProducts.size());
	}



	@Override
	public List<Product> filterByPriceSliderAndCategory(Double minPrice, Double maxPrice, Category category, String keyword) {
		Set<Product> filteredProducts = new HashSet<>();
		if (category == null){
			filteredProducts.addAll(productRepository.findByPriceBetweenAndProductNameContainingIgnoreCase(minPrice, maxPrice, keyword));
		} else {
			filteredProducts.addAll(productRepository.findByPriceBetweenAndCategoryAndProductNameContainingIgnoreCase(minPrice, maxPrice, category, keyword));
		}
		List<Product> sortedProducts = filteredProducts.stream()
				.sorted(Comparator.comparingDouble(Product::getPrice))
				.collect(Collectors.toList());
		return sortedProducts;
	}

	@Override
	public Page<Product> filterByPriceSliderAndCategory(Double minPrice, Double maxPrice, List<Category> categories, String keyword, Pageable pageable) {
		Set<Product> filteredProducts = new HashSet<>();

		if (categories.isEmpty()) {
			// Nếu không có danh mục, lấy sản phẩm theo giá và từ khóa
			filteredProducts.addAll(productRepository.findByPriceBetweenAndProductNameContainingIgnoreCase(minPrice, maxPrice, keyword));
		} else {
			// Nếu có danh mục, lọc sản phẩm theo giá, danh mục và từ khóa
			for (Category category : categories) {
				filteredProducts.addAll(productRepository.findByPriceBetweenAndCategoryAndProductNameContainingIgnoreCase(minPrice, maxPrice, category, keyword));
			}
		}

		// Sắp xếp sản phẩm theo giá
		List<Product> sortedProducts = new ArrayList<>(filteredProducts);
		sortedProducts.sort(Comparator.comparingDouble(Product::getPrice));

		// Phân trang
		Integer start = (int) pageable.getOffset();
		Integer end = (int) Math.min(start + pageable.getPageSize(), sortedProducts.size());

		List<Product> pagedProducts = sortedProducts.subList(start, end);

		return new PageImpl<>(pagedProducts, pageable, sortedProducts.size());
	}


}

function filterProducts(categoryId) {
    // Lấy tất cả các checkbox được chọn
    const selectedFilters = Array.from(document.querySelectorAll('.filter-item input[type="checkbox"]:checked')).map(checkbox => {
        return checkbox.id;
    });

    // Gửi yêu cầu AJAX với các bộ lọc đã chọn
    fetch('/category/filter-products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({ filters: selectedFilters, categoryId: categoryId })
    })
        .then(response => response.json())
        .then(data => {
            // Cập nhật danh sách sản phẩm
            const productList = document.getElementById('product-list');
            productList.innerHTML = '';

            data.products.forEach(product => {
                const productHtml = `
                <div class="col-6 col-md-4 col-lg-4" id="product-${product.id}">
                    <div class="product product-7 text-center">
                        <figure class="product-media">
                            <span class="product-label label-new">New</span>
                            <a href="/product/${product.id}">
                                <img src="/uploads/${product.images[0].image}" alt="Product image" class="product-image">
                            </a>

                            <div class="product-action-vertical">
                                <a href="#" class="btn-product-icon btn-wishlist btn-expandable"><span>add to wishlist</span></a>
                                <a href="popup/quickView.html" class="btn-product-icon btn-quickview" title="Quick view"><span>Quick view</span></a>
                                <a href="#" class="btn-product-icon btn-compare" title="Compare"><span>Compare</span></a>
                            </div><!-- End .product-action-vertical -->

                            <div class="product-action">
                                <a href="javascript:void(0);" onclick="addToCart(${product.id}); return false;" class="btn-product btn-cart"><span>add to cart</span></a>
                            </div><!-- End .product-action -->
                        </figure><!-- End .product-media -->

                        <div class="product-body">
                            <div class="product-cat">
                                <a href="#">Women</a>
                            </div><!-- End .product-cat -->
                            <h3 class="product-title"><a href="product.html">${product.productName}</a></h3><!-- End .product-title -->
                            <div class="product-price">
                                $${product.price}
                            </div><!-- End .product-price -->
                            <div class="ratings-container">
                                <div class="ratings">
                                    <div class="ratings-val" style="width: 20%;"></div><!-- End .ratings-val -->
                                </div><!-- End .ratings -->
                                <span class="ratings-text">( 2 Reviews )</span>
                            </div><!-- End .ratings-container -->

                            <div class="product-nav product-nav-thumbs">
                                <a href="#" class="active">
                                    <img src="/uploads/${product.images[0].image}" alt="product desc">
                                </a>
                            </div><!-- End .product-nav -->
                        </div><!-- End .product-body -->
                    </div><!-- End .product -->
                </div><!-- End .col-sm-6 col-lg-4 -->
            `;
                productList.insertAdjacentHTML('beforeend', productHtml);
            });
        })
        .catch(error => console.error('Error:', error));
}

function filterByPriceSliderAndCategoryId(minPrice, maxPrice, categoryId) {
    // Lấy tất cả các checkbox được chọn
    const selectedFilters = Array.from(document.querySelectorAll('.filter-item input[type="checkbox"]:checked')).map(checkbox => {
        return checkbox.id;
    });

    // Gửi yêu cầu AJAX với các bộ lọc đã chọn và minPrice, maxPrice
    fetch('/category/filter-products-by-price-slider', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({ filters: selectedFilters, minPrice: minPrice, maxPrice: maxPrice, categoryId: categoryId })
    })
        .then(response => response.json())
        .then(data => {
            // Cập nhật danh sách sản phẩm
            const productList = document.getElementById('product-list');
            productList.innerHTML = '';

            data.products.forEach(product => {
                const productHtml = `
                <div class="col-6 col-md-4 col-lg-4" id="product-${product.id}">
                    <div class="product product-7 text-center">
                        <figure class="product-media">
                            <span class="product-label label-new">New</span>
                            <a href="/product/${product.id}">
                                <img src="/uploads/${product.images[0].image}" alt="Product image" class="product-image">
                            </a>

                            <div class="product-action-vertical">
                                <a href="#" class="btn-product-icon btn-wishlist btn-expandable"><span>add to wishlist</span></a>
                                <a href="popup/quickView.html" class="btn-product-icon btn-quickview" title="Quick view"><span>Quick view</span></a>
                                <a href="#" class="btn-product-icon btn-compare" title="Compare"><span>Compare</span></a>
                            </div><!-- End .product-action-vertical -->

                            <div class="product-action">
                                <a href="javascript:void(0);" onclick="addToCart(${product.id}); return false;" class="btn-product btn-cart"><span>add to cart</span></a>
                            </div><!-- End .product-action -->
                        </figure><!-- End .product-media -->

                        <div class="product-body">
                            <div class="product-cat">
                                <a href="#">Women</a>
                            </div><!-- End .product-cat -->
                            <h3 class="product-title"><a href="product.html">${product.productName}</a></h3><!-- End .product-title -->
                            <div class="product-price">
                                $${product.price}
                            </div><!-- End .product-price -->
                            <div class="ratings-container">
                                <div class="ratings">
                                    <div class="ratings-val" style="width: 20%;"></div><!-- End .ratings-val -->
                                </div><!-- End .ratings -->
                                <span class="ratings-text">( 2 Reviews )</span>
                            </div><!-- End .ratings-container -->

                            <div class="product-nav product-nav-thumbs">
                                <a href="#" class="active">
                                    <img src="/uploads/${product.images[0].image}" alt="product desc">
                                </a>
                            </div><!-- End .product-nav -->
                        </div><!-- End .product-body -->
                    </div><!-- End .product -->
                </div><!-- End .col-sm-6 col-lg-4 -->
            `;
                productList.insertAdjacentHTML('beforeend', productHtml);
            });
        })
        .catch(error => console.error('Error:', error));
}

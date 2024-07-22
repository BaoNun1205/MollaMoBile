function filterProducts(minPrice, maxPrice, categoryId) {
    let selectedFilters = null;
    if (minPrice == null && maxPrice == null){
        // Lấy tất cả các checkbox được chọn
        selectedFilters = Array.from(document.querySelectorAll('.filter-item input[type="checkbox"]:checked')).map(checkbox => {
            return checkbox.id;
        });
    }

    console.log('Min: ',minPrice)
    console.log('Max: ',maxPrice)

    // Gửi yêu cầu AJAX với các bộ lọc đã chọn
    fetch('/category/filter-products', {
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

document.addEventListener('DOMContentLoaded', function() {
    // Khởi tạo thanh slider với giá trị mặc định
    initializePriceSlider();

    // Cài đặt sự kiện cho các checkbox
    setupCheckboxListeners();
});

let isCheckboxChanging = false; // Biến cờ để theo dõi sự thay đổi của checkbox

function initializePriceSlider(minPrice = 0, maxPrice = 2000) {
    var priceSlider = document.getElementById('price-slider');

    if (priceSlider == null) return;

    noUiSlider.create(priceSlider, {
        start: [minPrice, maxPrice],
        connect: true,
        step: 50,
        margin: 200,
        range: {
            'min': minPrice,
            'max': maxPrice
        },
        tooltips: true,
        format: wNumb({
            decimals: 0,
            prefix: '$'
        })
    });

    // Cập nhật thanh slider và gọi filterProducts khi slider thay đổi
    priceSlider.noUiSlider.on('update', function(values, handle) {
        if (isCheckboxChanging) return; // Nếu đang thay đổi checkbox, không xử lý slider

        var currentMinPrice = parseFloat(values[0].replace('$', ''));
        var currentMaxPrice = parseFloat(values[1].replace('$', ''));
        $('#filter-price-range').text(values.join(' - '));

        // Gửi yêu cầu AJAX với giá trị của thanh slider và không có các bộ lọc checkbox
        const categoryId = parseInt(document.getElementById('widget-5').getAttribute('data-category-id'), 10);
        filterProducts(currentMinPrice, currentMaxPrice, categoryId);

        // Bỏ chọn tất cả các checkbox khi slider thay đổi
        document.querySelectorAll('.filter-item input[type="checkbox"]:checked').forEach(checkbox => {
            checkbox.checked = false;
        });
    });
}

function setupCheckboxListeners() {
    document.querySelectorAll('.filter-item input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            isCheckboxChanging = true; // Đánh dấu đang thay đổi checkbox

            const checkedCheckboxes = document.querySelectorAll('.filter-item input[type="checkbox"]:checked');
            const isAnyCheckboxChecked = checkedCheckboxes.length > 0;

            if (isAnyCheckboxChecked) {
                // Đặt lại thanh slider về giá trị mặc định
                var priceSlider = document.getElementById('price-slider');
                if (priceSlider && priceSlider.noUiSlider) {
                    priceSlider.noUiSlider.set([0, 2000]);
                }

                // Gửi yêu cầu AJAX với giá trị mặc định của slider và các bộ lọc checkbox hiện tại
                const categoryId = parseInt(document.getElementById('widget-5').getAttribute('data-category-id'), 10);
                filterProducts(null, null, categoryId);
            } else {
                // Nếu không có checkbox nào được chọn, chỉ lọc theo slider
                var priceSlider = document.getElementById('price-slider');
                if (priceSlider && priceSlider.noUiSlider) {
                    var values = priceSlider.noUiSlider.get();
                    var minPrice = parseFloat(values[0].replace('$', ''));
                    var maxPrice = parseFloat(values[1].replace('$', ''));

                    const categoryId = parseInt(document.getElementById('widget-5').getAttribute('data-category-id'), 10);
                    filterProducts(minPrice, maxPrice, categoryId);
                }
            }

            isCheckboxChanging = false; // Đánh dấu đã xong việc thay đổi checkbox
        });
    });
}






function getSelectedCategoryIds() {
    // Lấy tất cả các checkbox được chọn
    const selectedCheckboxes = document.querySelectorAll('.filter-cate input[type="checkbox"]:checked');

    // Chuyển id của các checkbox được chọn thành số và đưa vào mảng
    const categoryIds = Array.from(selectedCheckboxes).map(checkbox => {
        return parseInt(checkbox.id.replace('cat-', ''), 10);
    });

    return categoryIds;
}

function filterProducts(minPrice, maxPrice, categoryIds, isAuthenticated) {
    let selectedFilters = null;
    if (minPrice == null && maxPrice == null){
        // Lấy tất cả các checkbox gia được chọn
        selectedFilters = Array.from(document.querySelectorAll('.filter-price-item input[type="checkbox"]:checked')).map(checkbox => {
            return checkbox.id;
        });
    }

    // Lấy giá trị tìm kiếm từ ô input
    const keyword = document.querySelector('input[name="q"]').value;

    // Gửi yêu cầu AJAX với các bộ lọc đã chọn
    fetch('/shop/filter-products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({ filters: selectedFilters, minPrice: minPrice, maxPrice: maxPrice, categoryIds: categoryIds, keyword: keyword })
    })
        .then(response => response.json())
        .then(data => {
            // Cập nhật số lượng sản phẩm
            const productQuantity = document.querySelector('.product-quantity');
            if (productQuantity) {
                productQuantity.textContent = data.totalCount; // Giả sử `data.totalCount` là số lượng sản phẩm
            }

            // Cập nhật danh sách sản phẩm
            // const productList = document.getElementById('product-list');
            const productListEmpty = document.getElementById('products-empty');
            productListEmpty.innerHTML = '';

            if (data.totalCount > 0) {
                // Tạo phần tử chứa danh sách sản phẩm
                const productListHtml = `
                    <div class="row" id="product-list">
                        ${data.products.map(product => `
                            <div class="col-6 col-md-4 col-lg-4" id="product-${product.id}">
                                <div class="product product-7 text-center">
                                    <figure class="product-media">
                                        <span class="product-label label-new">New</span>
                                        <a href="/product/${product.id}">
                                            <img src="/uploads/${product.images[0].image}" alt="Product image" class="product-image">
                                        </a>
            
                                        <div class="product-action-vertical">
                                            ${isAuthenticated
                    ? `<a href="javascript:void(0);" onclick="addToWishlist(${product.id}); return false;" class="btn-product-icon btn-wishlist btn-expandable"><span>add to wishlist</span></a>`
                    : `<a href="/login" class="btn-product-icon btn-wishlist btn-expandable"><span>add to wishlist</span></a>`
                }          
                                            <a href="popup/quickView.html" class="btn-product-icon btn-quickview" title="Quick view"><span>Quick view</span></a>
                                            <a href="#" class="btn-product-icon btn-compare" title="Compare"><span>Compare</span></a>
                                        </div><!-- End .product-action-vertical -->
            
                                        <div class="product-action">
                                            ${isAuthenticated
                    ? `<a href="javascript:void(0);" onclick="addToCart(${product.id}); return false;" class="btn-product btn-cart" title="Add to cart"><span>add to cart</span></a>`
                    : `<a href="/login" class="btn-product btn-cart" title="Add to cart"><span>add to cart</span></a>`
                }
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
                                            ${product.images.map((image, index) => `
                                                <a href="#" class="${index === 0 ? 'active' : ''}">
                                                    <img src="/uploads/${image.image}" alt="product desc">
                                                </a>
                                            `).join('')}
                                        </div><!-- End .product-nav -->
                                    </div><!-- End .product-body -->
                                </div><!-- End .product -->
                            </div><!-- End .col-sm-6 col-lg-4 -->
                        `).join('')}
                    </div><!-- End .row -->
                `;
                // Chèn HTML vào phần tử chứa sản phẩm
                productListEmpty.innerHTML = productListHtml;
            }
            else {
                const productHtml = `<div class="error-content text-center" style="background-image: url(/fe/images/backgrounds/error-bg.jpg)">
                         <div>
                              <h1 class="error-title">List product empty</h1><!-- End .error-title -->
                              <p>No suitable products were found</p>                         
                         </div><!-- End .container -->
                    </div><!-- End .error-content text-center -->            
                `;
                productListEmpty.insertAdjacentHTML('beforeend', productHtml);
            }
        })
        .catch(error => console.error('Error:', error));
}

const categoryId = parseInt(document.getElementById('widget-5').getAttribute('data-category-id'), 10);
const isAuthenticated = (document.getElementById('widget-5').getAttribute('data-isAuthenticated') === 'true');

document.addEventListener('DOMContentLoaded', function() {
    // Khởi tạo thanh slider với giá trị mặc định
    initializePriceSlider();

    // Cài đặt sự kiện cho các checkbox
    setupCheckboxListeners();

    // Cài đặt sự kiện cho "Clean All"
    document.querySelector('.sidebar-filter-clear').addEventListener('click', function(event) {
        event.preventDefault(); // Loại bỏ hành vi mặc định

        // Bỏ chọn tất cả các checkbox price
        document.querySelectorAll('.filter-price-item input[type="checkbox"]').forEach(checkbox => {
            checkbox.checked = false;
        });

        // Bỏ chọn tất cả các checkbox category
        document.querySelectorAll('.filter-cate input[type="checkbox"]').forEach(checkbox => {
            checkbox.checked = false;
        });

        // Đặt lại thanh slider về giá trị mặc định
        var priceSlider = document.getElementById('price-slider');
        if (priceSlider && priceSlider.noUiSlider) {
            priceSlider.noUiSlider.set([0, 2000]);
        }

        // Gửi yêu cầu AJAX để lấy tất cả sản phẩm
        filterProducts(0, 2000, null, isAuthenticated);
    });
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

    const categoryIds = getSelectedCategoryIds(); //lay ra cac checkbox category duoc check

    // Cập nhật thanh slider và gọi filterProducts khi slider thay đổi
    priceSlider.noUiSlider.on('update', function(values, handle) {
        if (isCheckboxChanging) return; // Nếu đang thay đổi checkbox, không xử lý slider

        var currentMinPrice = parseFloat(values[0].replace('$', ''));
        var currentMaxPrice = parseFloat(values[1].replace('$', ''));
        $('#filter-price-range').text(values.join(' - '));

        // Gửi yêu cầu AJAX với giá trị của thanh slider và không có các bộ lọc checkbox
        const isAuthenticated = (document.getElementById('widget-5').getAttribute('data-isAuthenticated') === 'true');
        filterProducts(currentMinPrice, currentMaxPrice, categoryIds, isAuthenticated);

        // Bỏ chọn tất cả các checkbox khi slider thay đổi
        document.querySelectorAll('.filter-price-item input[type="checkbox"]:checked').forEach(checkbox => {
            checkbox.checked = false;
        });
    });
}

//them su kien cho cac checkbox gia
function setupCheckboxListeners() {
    // Sự kiện cho các checkbox danh mục
    document.querySelectorAll('.filter-cate input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            handleCheckboxChange();
        });
    });

    // Sự kiện cho các checkbox giá
    document.querySelectorAll('.filter-price-item input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            handleCheckboxChange();
        });
    });
}

function handleCheckboxChange() {
    isCheckboxChanging = true; // Đánh dấu đang thay đổi checkbox

    const categoryIds = getSelectedCategoryIds(); // Lấy ra các checkbox category được check
    console.log(categoryIds)
    const checkedPriceCheckboxes = document.querySelectorAll('.filter-price-item input[type="checkbox"]:checked'); // Lấy ra các checkbox giá được check
    console.log(checkedPriceCheckboxes)
    const isAnyPriceCheckboxChecked = checkedPriceCheckboxes.length > 0;
    const isAnyCategoryCheckboxChecked = categoryIds.length > 0;

    var priceSlider = document.getElementById('price-slider');
    if (priceSlider && priceSlider.noUiSlider) {
        var values = priceSlider.noUiSlider.get();
        var minPrice = parseFloat(values[0].replace('$', ''));
        var maxPrice = parseFloat(values[1].replace('$', ''));
    }

    // Nếu có checkbox giá và không có checkbox danh mục
    if (isAnyPriceCheckboxChecked && !isAnyCategoryCheckboxChecked) {
        // Đặt lại thanh slider về giá trị mặc định
        if (priceSlider && priceSlider.noUiSlider) {
            priceSlider.noUiSlider.set([0, 2000]);
        }

        // Gửi yêu cầu AJAX với giá trị mặc định của slider và các bộ lọc checkbox giá hiện tại
        filterProducts(null, null, null, isAuthenticated); // min, max của slider là null, categoryIds là null
    }
    // Nếu không có checkbox giá và có checkbox danh mục
    else if (!isAnyPriceCheckboxChecked && isAnyCategoryCheckboxChecked) {
        // Gửi yêu cầu AJAX với giá trị mặc định của slider và các bộ lọc checkbox giá hiện tại
        filterProducts(minPrice, maxPrice, categoryIds, isAuthenticated); // min, max của slider là null, categoryIds là null
    }
    // Nếu cả hai đều có
    else if (isAnyPriceCheckboxChecked && isAnyCategoryCheckboxChecked) {
        // Đặt lại thanh slider về giá trị mặc định
        if (priceSlider && priceSlider.noUiSlider) {
            priceSlider.noUiSlider.set([0, 2000]);
        }

        // Gửi yêu cầu AJAX với giá trị mặc định của slider và các bộ lọc checkbox category hiện tại
        filterProducts(null, null, categoryIds, isAuthenticated); // min, max của slider là null
    }
    // Nếu cả hai đều không có
    else {
        // Lọc theo slider
        if (priceSlider && priceSlider.noUiSlider) {
            filterProducts(minPrice, maxPrice, null, isAuthenticated);
        }
    }

    isCheckboxChanging = false; // Đánh dấu đã xong việc thay đổi checkbox
}






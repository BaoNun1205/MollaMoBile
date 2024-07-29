function deleteCartItem(id) {
    fetch(`/view-cart/delete-item/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                window.location.reload(); // Reload lại trang
            } else {
                // Xử lý lỗi
                console.error('Failed to delete item');
            }
        })
        .catch(error => console.error('Error:', error));
}

function deleteWishlistItem(id) {
    fetch(`/wish-list/delete-item/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                window.location.reload(); // Reload lại trang
            } else {
                // Xử lý lỗi
                console.error('Failed to delete item');
            }
        })
        .catch(error => console.error('Error:', error));
}

function addToCart(id) {
    fetch(`/product/add-cart/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                window.location.reload(); // Reload lại trang
            } else {
                // Xử lý lỗi
                console.error('Failed to add to cart');
            }
        })
        .catch(error => console.error('Error:', error));
}

function addToWishlist(id) {
    fetch(`/wish-list/add-wish-list/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                window.location.reload(); // Reload lại trang
            } else {
                // Xử lý lỗi
                console.error('Failed to add to wishlist');
            }
        })
        .catch(error => console.error('Error:', error));
}

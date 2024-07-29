document.addEventListener('DOMContentLoaded', function() {
    const quantityInputs = document.querySelectorAll('.quantity-input');
    const shippingOptions = document.querySelectorAll('.shipping-option');

    function updateCartTotal() {
        let cartTotal = 0;
        document.querySelectorAll('tr').forEach(row => {
            const priceCol = row.querySelector('.price-col');
            const totalCol = row.querySelector('.total-col');
            const quantityInput = row.querySelector('.quantity-input');

            if (priceCol && totalCol && quantityInput) {
                // Kiểm tra giá trị data-price và giá trị của quantityInput
                const price = parseFloat(priceCol.dataset.price);
                const quantity = parseInt(quantityInput.value);

                // Kiểm tra nếu price và quantity là số hợp lệ
                if (!isNaN(price) && !isNaN(quantity) && quantity > 0) {
                    const total = price * quantity;
                    totalCol.textContent = `$${total.toFixed(2)}`;
                    cartTotal += total;
                } else {
                    totalCol.textContent = '$0.00';
                }
            }
        });

        // Tính giá vận chuyển
        let shippingCost = 0;
        const selectedShipping = document.querySelector('.shipping-option:checked');
        if (selectedShipping) {
            shippingCost = parseFloat(selectedShipping.dataset.price);
        }

        // Cập nhật tổng tiền giỏ hàng
        const cartTotalElement = document.querySelector('#cart-total');
        if (cartTotalElement) {
            cartTotalElement.textContent = `$${cartTotal.toFixed(2)}`;
        }

        // Cập nhật tổng tiền giỏ hàng cuối cùng
        cartTotal += shippingCost;

        // Cập nhật tổng tiền giỏ hàng
        const cartTotalFinalElement = document.querySelector('#cart-total-final');
        if (cartTotalElement) {
            cartTotalFinalElement.textContent = `$${cartTotal.toFixed(2)}`;
        }
    }

    quantityInputs.forEach(input => {
        input.addEventListener('input', updateCartTotal);
    });

    shippingOptions.forEach(option => {
        option.addEventListener('change', updateCartTotal);
    });

    // Gọi hàm cập nhật tổng tiền giỏ hàng ngay khi trang được tải
    updateCartTotal();
});
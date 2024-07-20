document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('add-cart-item-form');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    document.getElementById('qty').value = 1;
                    window.location.reload(); // Reload lại trang
                } else {
                    // Xử lý lỗi
                    console.error('Add product failed');
                }
            })
            .catch(error => console.error('Error:', error));
    });
});

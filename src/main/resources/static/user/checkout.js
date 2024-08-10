document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('#place-order');
    const errorMessageDiv = document.getElementById('error-message');

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Ngan hanh dong gui mac dinh

        const formData = new FormData(form); // Lay du lieu tu form

        fetch(form.action, {
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json', // Chap nhan JSON tra ve
            },
        })
            .then(response => {
                if (response.ok) {
                    // Neu thanh cong, chuyen den trang account
                    window.location.href = '/account';
                } else {
                    // Xử lý lỗi nếu phản hồi không thành công
                    return response.json()
                        .then(data => {
                            errorMessageDiv.textContent = data.message || 'An unexpected error occurred.';
                            $('#error-modal').modal('show'); // Hien thi modal
                        })
                        .catch(jsonError => {
                            console.error('Error parsing JSON:', jsonError);
                            errorMessageDiv.textContent = 'An unexpected error occurred. Unable to parse error response.';
                            $('#error-modal').modal('show'); // Hien thi modal
                        });
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An unexpected error occurred. Please try again later.');
            });
    });
});

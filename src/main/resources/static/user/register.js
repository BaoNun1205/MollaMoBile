document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('register-form');

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
                    window.location.href = '/login';
                } else {
                    // Xử lý lỗi
                    console.error('Save change address failed');
                }
            })
            .catch(error => console.error('Error:', error));
    });
});

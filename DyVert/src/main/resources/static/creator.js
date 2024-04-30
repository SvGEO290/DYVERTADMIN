 document.querySelectorAll('.edit-card').forEach(item => {
        item.addEventListener('click', event => {
            const cardId = item.getAttribute('data-card-id');
            window.location.href = '/card/edit/' + cardId; // Redirect to edit page with card ID
        });
    });
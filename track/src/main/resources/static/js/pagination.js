document.addEventListener('DOMContentLoaded', function() {
    const paginationLinks = document.querySelectorAll('.page-link');
    const currentUrl = window.location.pathname;

    paginationLinks.forEach(link => {
        if (link.getAttribute('href') === currentUrl) {
            link.parentElement.classList.add('active');
        }
    });
});
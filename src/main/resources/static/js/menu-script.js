var headerText =
    '<div class="headerContainer">' +
    '<form action="/" method="get">' +
    '<div class="navbar">' +
    '<div class="container">' +
    '<a href="/bcross/main" class="navbar-brand"><img src="bcross/static/images/book.jpg" style="max-width: 50px; max-height: 50px"> Book crossing</a>' +
    '<div class="navbar-wrap">' +
    '<ul class="navbar-menu">' +
    '<li><a href="/bcross/main">Главная</a></li>' +
    '<li><a href="/bcross/cities">Места</a></li>' +
    '<li><a href="/bcross/books">Книги</a></li>' +
    '<li><a href="#">Новости</a></li>' +
    '<li><a href="#">Войти</a></li>' +
    '<li><a href="#">Регистрация</a></li>' +
    '</ul>' +
    '<a href="#" class="callback">Обратная связь</a>' +
    '</div>' +
    '</div>' +
    '</div>' +
    '</form>' +
    '</div>';

function setHeader() {
    var header = document.createElement("div");
    header.innerHTML = headerText;
    document.body.insertAdjacentElement('afterbegin', header);
}
setHeader()
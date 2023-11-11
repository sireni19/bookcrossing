var headerText =
    '<div>' +
    '<form action="/" method="get">' +
    '<nav class="navbar">\n' +
    '    <div class="container">\n' +
    '        <a href="#" class="navbar-brand"><img src="images/book.jpg"> Book crossing</a>\n' +
    '        <div class="navbar-wrap">\n' +
    '            <ul class="navbar-menu">\n' +
    '                <li><a href="#">Contacts</a></li>\n' +
    '                <li><a href="/bcross/books">Books</a></li>\n' +
    '                <li><a href="#">Locations</a></li>\n' +
    '                <li><a href="#">Login</a></li>\n' +
    '                <li><a href="#">Registration</a></li>\n' +
    '            </ul>\n' +
    '            <a href="#" class="callback">Feedback</a>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '</nav>' +
    '</form>' +
    '</div>';

function setHeader() {
    var header = document.createElement("div");
    header.innerHTML = headerText;
    document.body.insertAdjacentElement('afterbegin', header);
}

setHeader();
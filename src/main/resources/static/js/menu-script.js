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
    '<li><a href="/bcross/login">Войти</a></li>' +
    '<li><a href="/bcross/register">Регистрация</a></li>' +
    '<li><a href="/bcross/logout ">Выйти</a></li>' +
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

    // Обработчик события для ссылки "Обратная связь"
    document.querySelector('.callback').addEventListener('click', function (e) {
        e.preventDefault(); // Предотвращаем переход по ссылке

        // Создание всплывающего окна
        var popup = document.createElement('div');
        popup.classList.add('popup');

        // Создание формы обратной связи
        var form = document.createElement('form');
        form.action = '/bcross/send-feedback'; // Путь к серверному методу обработки формы
        form.method = 'get';

        // Создание крестика для закрытия формы
        var closeBtn = document.createElement('span');
        closeBtn.innerHTML = '&times;';
        closeBtn.classList.add('close-btn');
        closeBtn.addEventListener('click', function () {
            document.body.removeChild(popup); // Удаление всплывающего окна при клике на крестик
        });
        popup.appendChild(closeBtn);
        popup.appendChild(form);

        // Поле ввода "Тема письма"
        var subjectInput = document.createElement('input');
        subjectInput.type = 'text';
        subjectInput.name = 'subject';
        subjectInput.placeholder = 'Тема письма';
        form.appendChild(subjectInput);

        // Поле ввода "Содержимое письма"
        var messageInput = document.createElement('textarea');
        messageInput.name = 'message';
        messageInput.placeholder = 'Содержимое письма';
        form.appendChild(messageInput);

        // Создание кнопки отправки формы
        var submitBtn = document.createElement('input');
        submitBtn.type = 'submit';
        submitBtn.value = 'Отправить';
        form.appendChild(submitBtn);

        // Добавление формы во всплывающее окно
        popup.appendChild(form);

        // Добавление всплывающего окна на страницу
        document.body.appendChild(popup);

        // Центрирование всплывающего окна
        popup.style.display = 'flex';
        popup.style.justifyContent = 'center';
        popup.style.alignItems = 'center';
        popup.style.position = 'fixed';
        popup.style.top = '0';
        popup.style.left = '0';
        popup.style.width = '100%';
        popup.style.height = '100%';
        popup.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
        popup.style.zIndex = '999';

        // Стили для формы
        form.style.background = '#fff';
        form.style.padding = '20px';
        form.style.borderRadius = '5px';
        form.style.width = '400px';

        // Стили для полей ввода
        subjectInput.style.width = '100%';
        subjectInput.style.marginBottom = '10px';
        messageInput.style.width = '100%';
        messageInput.style.height = '100px';
        messageInput.style.marginBottom = '10px';
        submitBtn.style.width = '100%';

        // Стили для закрывающего крестика
        closeBtn.style.position = 'relative';
        closeBtn.style.float = 'right';
        closeBtn.style.marginLeft = '10px';
        closeBtn.style.cursor = 'pointer';
        closeBtn.style.fontSize = '50px';
        closeBtn.style.color = '#000';
        closeBtn.style.fontWeight = 'bold';

    });
}

// Вызов функции для добавления шапки и функциональности
setHeader();
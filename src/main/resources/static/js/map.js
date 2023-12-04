ymaps.ready(initMap);
var placemarks = [
    {
        latitude: 59.95043472253597,
        longitude: 30.316137735761316,
        hintContent: '<div class="app__hint">Адрес</div>',
        balloonContent: [
            '<div class="app__balloon">',
            '<img class="app__pic-img" src="bcross/static/images/4.png" alt="Место"/>',
            'Добро пожаловать, воспользуйтесь услугами буккроссинга нашего заведения',
            '</div>'
        ]
    },
    {

        latitude: 59.944117546520964,
        longitude: 30.307114791785867,
        hintContent: '<div class="app__hint">Адрес 2</div>',
        balloonContent: [
            '<div class="app__balloon">',
            '<img class="app__pic-img" src="bcross/static/images/4.png" alt="Место"/>',
            'Добро пожаловать, воспользуйтесь услугами буккроссинга нашего заведения 2',
            '</div>'
        ]
    }

], geoObjects = [];

function initMap() {
    const map = new ymaps.Map('app', {
        center: [59.95043472253597, 30.316137735761316],
        zoom: 15,
        behaviors: ['drag', 'zoom', 'scrollZoom'],
    });
    for (var i = 0; i < placemarks.length; i++) {
        geoObjects[i] = new ymaps.Placemark([placemarks[i].latitude, placemarks[i].longitude],
            {
            hintContent: placemarks[i].hintContent,
            balloonContent: placemarks[i].balloonContent.join('')
        });
        // map.geoObjects.add(placemark);
    }

    map.controls.add('zoomControl');
    var clusterer = new ymaps.Clusterer({});

    map.geoObjects.add(clusterer);
    clusterer.add(geoObjects);
    const schemeLayer = new ymaps.layer.tileContainerStorage.get('yandex#map');
    map.layers.add(schemeLayer);
}

// var placemark = new ymaps.Placemark([59.94153259607485,30.30457689451589],{
//     hintContent: '<div class="app__hint">Адрес</div>',
//     balloonContent: [
//         '<div class="app__balloon">',
//         '<img class="app__pic-img" src="bcross/static/images/4.png" alt="Место"/>',
//         'Добро пожаловать, воспользуйтесь услугами буккроссинга нашего заведения',
//         '</div>'
//     ].join('')
// });
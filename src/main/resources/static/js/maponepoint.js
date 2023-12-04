ymaps.ready(initMap);


var coordinates = document.currentScript.getAttribute("xy");
var address = document.currentScript.getAttribute("address");
var coordinatesArray = coordinates.split(" ");
var lon = parseFloat(coordinatesArray[0]);
var lat = parseFloat(coordinatesArray[1]);

var placemarks = [
    {
        latitude: lat,
        longitude: lon,
        hintContent: '<div class="app__hint">' + address + '</div>',
        balloonContent: [
            '<div class="app__balloon">',
            '<img class="app__pic-img" src="bcross/static/images/4.png" alt="Место"/>',
            'Добро пожаловать, воспользуйтесь услугами буккроссинга нашего заведения',
            '</div>'
        ]
    },
];

var geoObjects = [];

function initMap() {
    const map = new ymaps.Map('app', {
        center: [lat,lon],
        zoom: 15,
        behaviors: ['drag', 'zoom', 'scrollZoom'],
    });

    for (var i = 0; i < placemarks.length; i++) {
        geoObjects[i] = new ymaps.Placemark([placemarks[i].latitude, placemarks[i].longitude], {
            hintContent: placemarks[i].hintContent,
            balloonContent: placemarks[i].balloonContent.join('')
        });
    }

    map.controls.add('zoomControl');
    var clusterer = new ymaps.Clusterer({});
    map.geoObjects.add(clusterer);
    clusterer.add(geoObjects);

    const schemeLayer = new ymaps.layer.tileContainerStorage.get('yandex#map');
    map.layers.add(schemeLayer);
}

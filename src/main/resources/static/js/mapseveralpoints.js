var addressesCoordinates = JSON.parse(window.addressesCoordinatesJson);
var placemarks = [];


for (var address in addressesCoordinates) {
    var coordinates = addressesCoordinates[address];
    var coordinatesArray = coordinates.split(" ");
    var lon = parseFloat(coordinatesArray[0]);
    var lat = parseFloat(coordinatesArray[1]);
    console.log(lon)
    console.log(lat)

    placemarks.push({
        latitude: lat,
        longitude: lon,
        hintContent: '<div class="app__hint">' + address + '</div>',
        balloonContent: [
            '<div class="app__balloon">',
            '<img class="app__pic-img" src="bcross/static/images/4.png" alt="Место"/>',
            'Добро пожаловать, воспользуйтесь услугами буккроссинга нашего заведения',
            '</div>'
        ]
    });
}

function initMap() {
    const map = new ymaps.Map('app', {
        center: [lat, lon],
        zoom: 15,
        behaviors: ['drag', 'zoom', 'scrollZoom'],
    });

    var geoObjects = [];

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

    const schemeLayer = ymaps.layer.tileContainerStorage.get('yandex#map');
    map.layers.add(schemeLayer);
}

ymaps.ready(initMap);
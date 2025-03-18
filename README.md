VERSION JDK = 17
VERSION SPRING BOOT = 3.3.9

APIs externas:
No se pudo trabajar con la API de Spotify porque el servicio que devuelve los generos se encuentra deprecated, y no esta funcionando en el momento. (Ver más en https://developer.spotify.com/blog/2024-11-27-changes-to-the-web-api)
Por lo tanto se decidio realizar la integracion con la api de Last.fm, la cual devuelve un listado de generos. Esta API requiere tambien de unas claves privadas para su funcionamiento.

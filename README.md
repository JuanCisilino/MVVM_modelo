# MVVM_modelo
App de modelo MVVM con Retrofit para consumir Api de la Bolsa de valores y mostrar las cotizaciones

# Widget
Posee un widget de los precios de las 3 divisas mas usadas en el pais, dolar blue, dolar oficial y dolar minorista para una rapida consulta.

# Clean architecture
La app fue realizada con una arquitectura mvvm con injeccion de dependencias a traves de Hilt y mapeo de datos, un organizado sistema donde los casos de uso logran las consultas y a su vez han pasado por un proceso de testeo Unit Testing.

# CI/CD
La app cuenta con integracion continua implementada, ante un action push o pull request se realiza un buildeo de la app para comprobar que no se suba codigo que no funciona.

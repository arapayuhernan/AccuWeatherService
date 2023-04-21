Arapayu Hernan Federico - arapayu.hernan@gmail.com

Para poder utilizar la api con el servicio hay que modificar la apikey que esta en el application.properties con la key del usuario 
Para poder ver la documentacion configure la app con swagger http://localhost:8080/swagger-ui/index.html

La app tiene algunos errores que no pude ver con mucho tiempo pero cumple con lo indicado en el test o al menos eso creo, se podria haber mapeado con un mapper utilizando dtos etc.

la App trae de AccuWeather el servicio current conditions que es un GET en la siguiente uri http://dataservice.accuweather.com/currentconditions/v1/{id_location},
lo que hice fue basicamente consumir eso y obtener algunos datos que me interesaban como si era de dia o no , si estaba lloviendo y que temperatura habia en ese momento.

 
# Seccion del informe - Kristel
Requerimientos: RF-01 Registro, RF-02 Inicio/cierre de sesion, RF-11 Boletin y encuesta

## Estrategia: que se automatizo y que no, y por que
Se escribieron 14 casos de prueba (RF-01: 6, RF-02: 4, RF-11: 4), de los cuales 9 son negativos o de limite. Antes de automatizar, los 14 casos se ejecutaron manualmente en el sitio para confirmar los mensajes reales; todos coincidieron con el resultado esperado o se ajustaron al texto real.

Se automatizaron los casos de mayor prioridad y valor de negocio:
- CP-001 Registro exitoso y CP-002 Registro con correo repetido (RF-01).
- CP-004 Registro con correo de formato invalido (RF-01), caso negativo extra.
- CP-007 Login con credenciales validas y CP-008 Login con credenciales invalidas (RF-02).
- CP-010 Cierre de sesion y acceso bloqueado a Mi cuenta (RF-02), caso extra.
- CP-011 Suscripcion al boletin con correo valido (RF-11).

Se dejaron como pruebas manuales:
- CP-009, porque es una variante del error de credenciales ya cubierto en CP-008.
- CP-012, porque es de prioridad Media y su validacion se repite en otros formularios.
- CP-013 y CP-014, porque son de prioridad Baja y el tiempo se invirtio en flujos criticos.

Para soportar las pruebas se construyeron tres paginas con Page Factory: RegistroPage, LoginPage y BoletinEncuestaPage.

## Retos de automatizacion y como se resolvieron
- **Datos unicos:** el registro rechaza correos repetidos, por lo que cada prueba genera su correo con System.currentTimeMillis().
- **Independencia de las pruebas:** los casos de login necesitan un usuario existente. En lugar de depender de otra prueba o de un usuario fijo, cada caso crea su propio usuario y cierra sesion antes de empezar.
- **Mensajes dinamicos:** el aviso del boletin aparece por AJAX sin recargar la pagina. Se uso WebDriverWait para esperar a que el mensaje sea visible y tenga texto, sin usar Thread.sleep.
- **Localizador con parametro:** los errores de cada campo del registro dependen del nombre del campo, y @FindBy no acepta parametros. Se uso By.cssSelector dentro de RegistroPage, con un comentario que lo justifica.
- **Ausencia de elementos:** para comprobar que no hay sesion iniciada, el enlace Log out no existe en la pagina, asi que el metodo captura NoSuchElementException y devuelve false.
- **Entorno:** Selenium Manager no pudo descargar chromedriver por un error de conexion (TLS) en la red, por lo que se instalo manualmente y se agrego al PATH.

## Hallazgos (resumen; detalle en docs/hallazgos/hallazgos-kristel.md)
- **H-K01 (RF-11 / CP-014, severidad Baja):** el requerimiento indica que se puede votar en la encuesta de la comunidad, pero el sitio solo permite votar a usuarios registrados. Un visitante recibe el aviso "Only registered users can vote." y el voto no se registra.
- Los demas casos de RF-01, RF-02 y RF-11 se comportaron segun el requerimiento. Por ejemplo, al cerrar sesion e intentar entrar a /customer/info, el sitio redirige a la pantalla de inicio de sesion.

## Aprendizajes y mejoras posibles
- Explorar el sitio a mano antes de automatizar evito escribir aserciones con mensajes incorrectos.
- Separar paginas (localizadores y acciones) de pruebas (datos y aserciones) hace que un cambio en la interfaz solo se corrija en un lugar.
- Las esperas explicitas hacen las pruebas mas estables que las pausas fijas.
- **Mejora:** mover la generacion de correos unicos a una clase utilitaria compartida, porque hoy se repite en varias pruebas.
- **Mejora:** usar @ParameterizedTest para automatizar con poco codigo los casos de validacion de campos (CP-003, CP-005, CP-006).
- **Mejora:** ejecutar en modo headless para correr las pruebas mas rapido o en integracion continua.
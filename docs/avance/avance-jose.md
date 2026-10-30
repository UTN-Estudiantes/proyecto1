# Avance - Jose (RF-09 Proceso de compra, RF-10 Cuenta del usuario)

Ultima actualizacion: 30 de octubre de 2026.
Tipo de prueba: funcional de **caja negra**, automatizada sobre la interfaz web (no hay acceso al codigo fuente del sitio).

---

## 1. Resumen de lo realizado

| Fecha | Commit | Que se hizo |
|---|---|---|
| 28 oct | `chore: estructura inicial...` | Estructura del repositorio, casos de prueba, guia del equipo |
| 29 oct | `build: pom con Java 17...` | Proyecto Maven `Proyecto01` con Java 17, Selenium 4, JUnit 5 y surefire; paquetes `base`, `pages`, `tests` |
| 29 oct | `feat(base): BaseTest...` | Clase `base/BaseTest`: navegador limpio antes de cada prueba y cierre al final (dependencia D1 del equipo) |
| 30 oct | `test(rf-09): CP-042...` | `pages/CheckoutPage` completa y primera prueba `tests/CompraTest` (CP-042) **pasando** |

### Estado de los casos de Jose

| Caso | Titulo | Automatizado | Estado |
|---|---|---|---|
| CP-042 | Compra completa como invitado | Si (`CompraTest.compraComoInvitado`) | **Pasa** |
| CP-043 | Compra completa como usuario registrado | Pendiente (necesita `CuentaPage`) | Pendiente |
| CP-044 | Checkout sin aceptar terminos | Pendiente (la pagina ya tiene `obtenerAvisoTerminos()`) | Pendiente |
| CP-045 | Facturacion con campos vacios / sin pais / correo invalido | Opcional | Mensajes reales ya confirmados (seccion 4) |
| CP-050 | Ver y editar informacion de la cuenta | Pendiente | Pendiente |
| CP-052 | Cambiar contrasena con clave actual incorrecta | Pendiente | Pendiente |

---

## 2. Como esta organizado el codigo

| Archivo | Responsabilidad |
|---|---|
| `Proyecto01/pom.xml` | Java 17, `selenium-java` 4.x, `junit-jupiter` 5.x (scope test), `maven-surefire-plugin` 3.x |
| `base/BaseTest.java` | `@BeforeEach`: abre Chrome, maximiza y entra a `URL_SITIO`. `@AfterEach`: `quit()` si el driver no es null. Las pruebas heredan de esta clase |
| `pages/CheckoutPage.java` | Elementos con `@FindBy` y metodos de negocio del carrito y del checkout. Sin aserciones ni datos fijos |
| `tests/CompraTest.java` | Pruebas de RF-09. Las aserciones van aqui |

**Ejecutar:** dentro de `Proyecto01`: `mvn test` (todas) o `mvn test -Dtest=CompraTest` (solo compra).

### Metodos de `CheckoutPage`

| Metodo | Devuelve | Que hace |
|---|---|---|
| `agregarProductoAlCarrito(String urlProducto, int idProducto)` | `CheckoutPage` | Abre el producto, pulsa su boton Add to cart (localizador con parametro), espera la barra de confirmacion y va al carrito |
| `aceptarTerminosYContinuar()` | `CheckoutPage` | Marca terminos y pulsa Checkout |
| `continuarComoInvitado()` | `CheckoutPage` | Espera y pulsa Checkout as Guest |
| `completarFacturacion(nombre, apellido, correo, pais, ciudad, direccion, codigoPostal, telefono)` | `CheckoutPage` | Llena el paso 1 (pais con `Select`) y continua |
| `continuarDireccionEnvio()` | `CheckoutPage` | Paso 2 |
| `continuarMetodoEnvio()` | `CheckoutPage` | Paso 3 |
| `continuarMetodoPago()` | `CheckoutPage` | Paso 4 |
| `continuarInfoPago()` | `CheckoutPage` | Paso 5 |
| `confirmarOrden()` | `CheckoutPage` | Paso 6 |
| `obtenerMensajeExito()` | `String` | Texto del mensaje final (para la asercion) |
| `obtenerAvisoTerminos()` | `String` | Texto del aviso de terminos (para CP-044) |

---

## 3. Localizadores encontrados

Fuente: inspeccion manual en Chrome (F12) sobre https://demowebshop.tricentis.com.
"Confirmado" = visto en el HTML; "Probado" = usado en una prueba que pasa.

### Producto y carrito

| Pantalla | Elemento | Localizador | Estado |
|---|---|---|---|
| Listado de productos | Add to cart del listado | clase `product-box-add-to-cart-button` (onclick con `/addproducttocart/catalog/{id}/1/1`) | Confirmado |
| Pagina de producto | Add to cart | id `add-to-cart-button-{idProducto}` (ej. `add-to-cart-button-31`, `-72`) | Probado (31) |
| Pagina de producto | Cantidad | id `addtocart_{idProducto}_EnteredQuantity` | Confirmado |
| Pagina de producto | Agregar a comparar | clase `add-to-compare-list-button` | Confirmado |
| Cualquier pagina | Barra "producto agregado" | id `bar-notification` | Probado |
| Encabezado | Enlace Shopping cart | clase `cart-label` | Probado |
| Carrito | Casilla de terminos | id `termsofservice` | Probado |
| Carrito | Boton Checkout | id `checkout` | Probado |
| Carrito | Aviso de terminos | id `terms-of-service-warning-box` | Confirmado |
| Carrito | Total | clase `order-total` | Confirmado |
| Carrito | Cupon / gift card | clases `discount-coupon-code`, `gift-card-coupon-code` | Confirmado (fuera de alcance) |
| Carrito | Estimar envio (pais, estado, CP) | ids `CountryId`, `StateProvinceId`, `ZipPostalCode` | Confirmado (NO es el formulario de facturacion) |

### Pagina de login del checkout

| Elemento | Localizador | Estado |
|---|---|---|
| Checkout as Guest | clase `checkout-as-guest-button` | Probado |
| Register | clase `register-button` | Confirmado |
| Correo / clave (usuario existente) | ids `Email`, `Password` | Confirmado |
| Log in | clase `login-button` | Confirmado |

### Checkout (una sola pagina con 6 pasos)

| Paso | Elemento | Localizador | Estado |
|---|---|---|---|
| 1 Facturacion | Nombre | id `BillingNewAddress_FirstName` | Probado |
| 1 | Apellido | id `BillingNewAddress_LastName` | Probado |
| 1 | Correo | id `BillingNewAddress_Email` | Probado |
| 1 | Empresa (opcional) | id `BillingNewAddress_Company` | Confirmado |
| 1 | Pais (lista) | id `BillingNewAddress_CountryId` | Probado |
| 1 | Estado (lista, se recarga por AJAX) | id `BillingNewAddress_StateProvinceId` | Confirmado |
| 1 | Ciudad | id `BillingNewAddress_City` | Probado |
| 1 | Direccion 1 | id `BillingNewAddress_Address1` | Probado |
| 1 | Codigo postal | id `BillingNewAddress_ZipPostalCode` | Probado |
| 1 | Telefono | id `BillingNewAddress_PhoneNumber` | Probado |
| 1 | Continue | css `#billing-buttons-container .new-address-next-step-button` | Probado |
| 2 Direccion de envio | Continue | css `#shipping-buttons-container .new-address-next-step-button` | Probado |
| 3 Metodo de envio | Continue | clase `shipping-method-next-step-button` | Probado |
| 4 Metodo de pago | Continue | clase `payment-method-next-step-button` | Probado |
| 5 Informacion de pago | Continue | clase `payment-info-next-step-button` | Probado |
| 6 Confirmar | Confirm | clase `confirm-order-next-step-button` | Probado |
| Orden completada | Mensaje de exito | css `.order-completed .title strong` | Probado |
| Orden completada | Numero de orden | css `.order-completed .details li` (texto "Order number: ...") | Confirmado |

### Producto configurable (Build your own computer, id 72) - informacion para Andrey (RF-06)

| Opcion | Localizador (radio buttons) | Valor por defecto |
|---|---|---|
| Processor | name `product_attribute_72_5_18` (Slow / Medium +15 / Fast +100) | Medium marcado |
| RAM | name `product_attribute_72_6_19` (2 GB / 4 GB +20 / 8 GB +60) | 2 GB marcado |
| HDD | name `product_attribute_72_3_20` (320 GB / 400 GB +100) | 320 GB marcado |

---

## 4. Mensajes reales del sitio

| Donde | Mensaje |
|---|---|
| Carrito, Checkout sin terminos | Please accept the terms of service before the next step. |
| Facturacion, nombre vacio | First name is required. |
| Facturacion, apellido vacio | Last name is required. |
| Facturacion, correo vacio | Email is required. |
| Facturacion, correo invalido | Wrong email |
| Facturacion, sin pais | Country is required. |
| Facturacion, ciudad vacia | City is required |
| Facturacion, direccion vacia | Street address is required |
| Facturacion, codigo postal vacio | Zip / postal code is required |
| Facturacion, telefono vacio | Phone is required |
| Producto, cantidad no numerica | The field Qty must be a number. |
| Orden completada | Your order has been successfully processed! |

---

## 5. Decisiones tecnicas (para el informe y la defensa)

1. **Navegador nuevo por cada prueba** (`@BeforeEach` / `@AfterEach` en `BaseTest`): cada prueba empieza sin carrito ni sesion de otra, por eso son independientes y repetibles.
2. **Producto simple para comprar** (14.1-inch Laptop, id 31, URL `/141-inch-laptop`): se agrega directo. *Build your own computer* exige opciones (RF-06, de Andrey) y haria depender la compra de otra parte.
3. **Localizador con parametro**: el id del boton Add to cart cambia por producto y `@FindBy` no acepta parametros; se usa `driver.findElement(By.id("add-to-cart-button-" + idProducto))` con comentario que lo justifica (permitido por el enunciado).
4. **Clase repetida en dos botones**: `new-address-next-step-button` aparece en el paso 1 y en el paso 2. Se usa CSS con el contenedor (`#billing-buttons-container ...`, `#shipping-buttons-container ...`) para no pulsar el boton equivocado.
5. **Esperas explicitas** (`WebDriverWait` de 10 s + `ExpectedConditions`), nunca `Thread.sleep`: cada paso del checkout carga por AJAX y esta oculto (`display:none`) hasta su turno. Se espera `elementToBeClickable` antes de cada clic y `visibilityOf` antes de leer un mensaje.
6. **Espera de la barra `bar-notification`** despues de agregar al carrito: el producto se agrega por AJAX; sin esperar, a veces se llega al carrito vacio.
7. **`Select` despues del wait**: `new Select(...)` busca el elemento al crearse; si se crea antes de que cargue el formulario, falla.
8. **Datos unicos**: correo `qa<milisegundos>@mail.com` con `System.currentTimeMillis()` en cada ejecucion.
9. **Paginas sin aserciones**: los metodos `obtener...()` devuelven `String` y la asercion (`assertEquals`) esta en la prueba.

---

## 6. Hallazgos

| ID | Requerimiento / caso | Observacion | Accion |
|---|---|---|---|
| H-J01 | RF-06 / CP-035 (de Andrey) | En *Build your own computer* las opciones obligatorias vienen **marcadas por defecto** (Medium, 2 GB, 320 GB). El caso "agregar sin elegir opciones" probablemente no se puede reproducir. | Avisar a Andrey para que ajuste el caso o lo reporte como hallazgo |
| H-J02 | RF-09 | Para invitado, despues de facturacion aparece el paso **Shipping address** aunque la direccion sea la misma; hay que pulsar Continue en ese paso. | Comportamiento documentado; cubierto en `continuarDireccionEnvio()` |
| H-J03 | RF-09 | En facturacion, el estado/provincia no es obligatorio para paises fuera de EE. UU. ("Other (Non US)"). | Informativo |

---

## 7. Datos de prueba usados

| Dato | Valor |
|---|---|
| Producto simple | 14.1-inch Laptop, URL `https://demowebshop.tricentis.com/141-inch-laptop`, id 31 |
| Facturacion | Ana / Prueba / `qa<ms>@mail.com` / Costa Rica / San Jose / Calle 1 / 10101 / 88888888 |
| Pais Costa Rica | value `23` en la lista de paises |

---

## 8. Pendientes

1. CP-044 en `CompraTest` (falta un metodo que solo pulse Checkout sin marcar terminos).
2. `pages/CuentaPage`: registro propio, editar informacion, cambiar contrasena (inspeccionar los ids de esas pantallas).
3. CP-043, CP-050 y CP-052.
4. Actualizar `casos-de-prueba` (estado de CP-042 = Pasa; mensajes reales de CP-045).
5. Seccion del informe: `docs/informe/seccion-jose.md` (usar la seccion 5 de este documento).

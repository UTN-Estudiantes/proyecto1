# Proyecto S7 ISW-622
Automatización de pruebas de una tienda en línea con Selenium, Page Factory y POM

## Profesor

- José David Carvajal Jiménez

## Integrantes

- Emily María Alfaro González
- Katlyn Yiliana Fernández Ramos
- Melanni Michel Díaz Ugalde
- Yesenia Michelle Pérez Rodríguez

Sitio bajo prueba: https://demowebshop.tricentis.com

Estructura:
```
src/test/java/cr/ac/utn/proyecto/
├── base/   BaseTest (Chrome limpio por prueba) y Visual (pausa solo para observar)
├── pages/  SUS clases de página con Page Factory
└── tests/  SUS pruebas automatizadas con POM
```

Ejecutar desde la carpeta que contiene pom.xml:
```
mvn test                   # todas las pruebas
mvn test -Dtest=NombreDeSuClase
mvn test -Dritmo=0         # sin pausas visuales
mvn test -Dheadless=true   # sin ventana
```
Complete este README con la información de su grupo y cómo ejecutar su proyecto.

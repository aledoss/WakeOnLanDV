# Wake On Lan - DV

## Objetivo
Encender una computadora mediante un celular Android de manera remota (sin estar conectado en la misma red).

## Cómo funciona
* Se envía un “paquete mágico” a la red donde se encuentra la máquina a encender. Esta lo recibe y se enciende.
* Paquete mágico: cadena de bytes que contienen la IP y la MAC hacia donde se debe entregar el paquete. Se envía como un datagrama UDP al puerto 7 o 9 a la placa de red Ethernet. Este paquete no proporciona una confirmación de entrega.
* Para que la computadora pueda recibir este paquete, se deberá realizar una serie de configuraciones.

## Configuraciones
* Habilitar en la BIOS, en la solapa Power, y habilitar la opción de Power On by PCI Card.
* En el Administrador de Dispositivos, en Adaptadores de Red, 	pestaña Avanzada, habilitar “Wake on Magic Packet”. También en la pestaña Power Managment, “permitir…”.
* Dependiendo del router, en opciones avanzadas, en “Forwarding”, agregar la ip de la pc, junto al puerto público y privado 9. Obtener la ip que brinda el router.
* Obtener la MAC mediante el comando ipconfig /all.
* En la aplicación mobile completar la IP y la MAC con la información obtenida.

## Pre-requisitos Android
* Min Android SDK 15
* Android Build Tools v24.0.1
* Compile Sdk Version 24

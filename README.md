# abmClient-java.coder

Entitie: Cree las entidades para transformar en una tabla de bases de datos. 
*Las relaciones @One To Many para client a Product.
*Las relaciones @One to Many para client a Invoice. 
*Las relaciones @One to Many para Product a InvoiceDetail. 
*Las relaciones @One to Many para Invoice a InvoiceDetail. 
*Las relaciones @Many to One para Product a Client
*Las relaciones @Many to One para Invoice a Client.
*Las relaciones @Many to One para InvoiceDetail a Product.
*Las relaciones @Many to One para InvoiceDetail a Invoice.


Repositorios: Interfaces que extienden de otra clase (Jpa Repository) 

Servicios: Donde se necesita "inyectar" una dependencia en privado,  de los repositories con @Autowired. 

Controllers: Clase que conecta los servicios y genera mapeo de datos. Utilizando decoradores para definir las rutas  @PostMapping, @GetMapping, @DeleteMapping, @PatchMapping.. 

ProductController:
POST /api/v1/products para crear un producto.
GET /api/v1/products para leer productos.
GET /api/v1/products/:pid para leer un producto.
PUT /api/v1/products/:pid para actualizar un producto.

ClientController: 
POST /api/v1/auth/register para registrar (crear) un cliente.
PUT /api/v1/auth/me para actualizar el perfil de un cliente.

InvoiceController:
OST /api/v1/invoices para generar el comprobante con el total a pagar por un cliente por sus productos en el carrito
GET /api/v1/invoices/:cid para leer el comprobante del cliente

Aplicando en el Postman (Interfaz) : Post para crear , Get obtener, Delete borrar.  
...Proximamente necesita nueva actualizacion.. 

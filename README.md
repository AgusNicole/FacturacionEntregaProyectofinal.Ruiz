# abmClient-java.coder

Proyecto e commerce  final de Coder, para administrar las ventas de un comercio.  
Carpetas :
Entities: Cree las entidades para transformar en una tabla de bases de datos: Client, Product, Cart, Invoice. 

Repositorios: Interfaces que extienden de otra clase (Jpa Repository)

Servicios: Donde se necesita "inyectar" una dependencia en privado,  de los repositories con @Autowired e implementar la logica. 

Controllers: Clases que conecta los servicios y genera mapeo de datos. Utilizando decoradores para definir las rutas  @PostMapping, @GetMapping, @DeleteMapping, @PutMapping.. 

Rutas: 
ProductController:
POST /api/v1/products para crear un producto.
GET /api/v1/products para leer productos.
GET /api/v1/products/:pid para leer un producto.
PUT /api/v1/products/:pid para actualizar un producto.

ClientController: 
POST /api/v1/auth/register para registrar (crear) un cliente.
PUT /api/v1/auth/me para actualizar el perfil de un cliente.

InvoiceController:
POST /api/v1/invoices para generar el comprobante con el total a pagar por un cliente por sus productos en el carrito (no entregados), además cambia esa propiedad (delivered) de false a true.
GET /api/v1/invoices/:cid para leer el comprobante del cliente.

CartController:
POST /api/v1/carts/:clid/:pid/:q para agregar un producto de un cliente al carrito (delivered en false)
DELETE /api/v1/carts/:cid para quitar un producto de un cliente al carrito
GET /api/v1/carts/:clid para leer los productos de un cliente al carrito (con delivered en false)




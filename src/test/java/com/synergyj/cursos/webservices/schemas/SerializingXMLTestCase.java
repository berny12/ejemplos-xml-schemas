/**
 * Fecha de creación: 01/01/2011 21:14:29
 *
 * Copyright (c) 2011 SynergyJ. 
 * Todos los derechos reservados.
 *
 * Este software es información pueder ser mofificado, utilizado
 * haciendo referencia al autor intelectual.
 */
package com.synergyj.cursos.webservices.schemas;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.xmlbeans.XmlOptions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synergyj.cursos.webservices.container.XMLBFacturaDocument;
import com.synergyj.cursos.webservices.ordenes.XMLBCliente;
import com.synergyj.cursos.webservices.ordenes.XMLBFactura;
import com.synergyj.cursos.webservices.ordenes.XMLBListaProdFactura;
import com.synergyj.cursos.webservices.ordenes.XMLBOrdenCompra;
import com.synergyj.cursos.webservices.prodorden.XMLBProducto;

/**
 * Este ejemplo muestra el uso del api de xmlbeans para crear y almacenar un documento
 * XML.
 * @author Jorge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */
public class SerializingXMLTestCase {

	private XmlOptions xmlOptions;

	private static final String XML_FILE = "xml/ejemploFactura.xml";

	/**
	 * Logger para todas las instancias de la clase
	 */
	private static final Logger logger = LoggerFactory.getLogger(SerializingXMLTestCase.class);

	/**
	 * Personaliza la generación del documento XML.
	 */
	@Before
	public void setup() {
		xmlOptions = new XmlOptions();
		// Marca que sólo se pongan al inicio los"namespaces"
		xmlOptions.setSaveAggressiveNamespaces();
		xmlOptions.setSavePrettyPrint();
		// Poner los nameSpaces primero
		xmlOptions.setSaveNamespacesFirst();
		// Indica que se pondrán "enters" entre los tags
		// Marca el nivel de identación al estar
		xmlOptions.setSavePrettyPrintIndent(2);
		// activado "pretty-print"
		xmlOptions.setCharacterEncoding("ISO-8859-1");

	}

	/**
	 * Crea un documento xml y lo guarda en disco.
	 */
	@Test
	public void creaXML() throws Exception {

		XMLBFactura factura;
		XMLBFacturaDocument document;
		FileOutputStream fos;

		logger.debug("Creando una nueva factura en {}", XML_FILE);
		// crea una instancia del documento asociado.
		document = XMLBFacturaDocument.Factory.newInstance();
		// a partir del documento se crea una nueva instancia de XMLBFactura
		factura = document.addNewXMLBFactura();

		logger.debug("Asignando valors a la instancia XMLBFactura generada");
		inicializaFactura(factura);

		logger.debug("validando el documento generado");
		XmlUtils.validaXML(factura);

		fos = new FileOutputStream(XML_FILE);
		document.save(fos, xmlOptions);
		fos.close();

	}

	/**
	 * @param factura
	 * @return
	 */
	private void inicializaFactura(XMLBFactura factura) {

		XMLBOrdenCompra orden;
		XMLBCliente cliente;
		XMLBProducto[] productosOrden;
		XMLBProducto productoOrden;
		com.synergyj.cursos.webservices.prodfactura.XMLBProducto productoFactura;
		com.synergyj.cursos.webservices.prodfactura.XMLBProducto[] productosFactura;
		XMLBListaProdFactura detalleFactura;
		double totalFactura;

		// Observar que no existe un Document para los elementos que no son el elemento
		// raiz
		orden = XMLBOrdenCompra.Factory.newInstance();
		cliente = XMLBCliente.Factory.newInstance();
		productosOrden = new XMLBProducto[3];

		logger.debug("inicializando los datos del cliente");
		cliente.setNombre("Mariana");
		cliente.setApellidoPaterno("López");
		cliente.setApellidoMaterno("Juarez");
		cliente.setSexo(XMLBCliente.Sexo.M);

		orden.setCliente(cliente);
		orden.setFechaOrden(Calendar.getInstance());
		orden.setNumeroOrden("1084938493943");

		logger.debug("creando lista de productos");
		// producto 1
		productoOrden = XMLBProducto.Factory.newInstance();
		productoOrden.setDescripcion("LAPICERO BEROL PUNTO 05");
		productoOrden.setNombreProducto("LAPICERO");
		productoOrden.setNumeroProducto(1293023);
		productosOrden[0] = productoOrden;
		// producto 2
		productoOrden = XMLBProducto.Factory.newInstance();
		productoOrden.setDescripcion("ESCRITORIO OFICINA MADERA COMPRIMIDA");
		productoOrden.setNombreProducto("ESCRITORIO EJECUTIVO");
		productoOrden.setNumeroProducto(454930);
		productosOrden[1] = productoOrden;
		// producto 3
		productoOrden = XMLBProducto.Factory.newInstance();
		productoOrden.setDescripcion("SILLA RECLINABLE PARA OFICINA");
		productoOrden.setNombreProducto("SILLA EJECUTIVA");
		productoOrden.setNumeroProducto(34532);
		productosOrden[2] = productoOrden;

		orden.setProductoArray(productosOrden);
		orden.setSucursal("Central");

		factura.setOrden(orden);
		logger.debug("calculando el detalle de la factura.");
		detalleFactura = XMLBListaProdFactura.Factory.newInstance();
		productosFactura = new com.synergyj.cursos.webservices.prodfactura.XMLBProducto[3];
		// producto 1
		productoFactura = com.synergyj.cursos.webservices.prodfactura.XMLBProducto.Factory
				.newInstance();
		productoFactura.setCantidad(1);
		productoFactura.setClaveProducto("LAPBEROL05");
		productoFactura.setPrecioUnitario(50);
		productoFactura.setIva(7.5);     //validaicon correcta en test
		//productoFactura.setIva(7.53434); //validacion erroneas en test
		productosFactura[0] = productoFactura;
		totalFactura = productoFactura.getPrecioUnitario() + productoFactura.getIva();
		// producto 2
		productoFactura = com.synergyj.cursos.webservices.prodfactura.XMLBProducto.Factory
				.newInstance();
		productoFactura.setCantidad(1);
		productoFactura.setClaveProducto("ESCRITOOFFMD1");
		productoFactura.setPrecioUnitario(3000);
		productoFactura.setIva(450);
		productosFactura[1] = productoFactura;
		totalFactura += productoFactura.getPrecioUnitario() + productoFactura.getIva();
		// producto 3
		productoFactura = com.synergyj.cursos.webservices.prodfactura.XMLBProducto.Factory
				.newInstance();
		productoFactura.setCantidad(1);
		productoFactura.setClaveProducto("SLEJEREE3");
		productoFactura.setPrecioUnitario(1000);
		productoFactura.setIva(150);
		productosFactura[2] = productoFactura;
		totalFactura += productoFactura.getPrecioUnitario() + productoFactura.getIva();

		detalleFactura.setProductoArray(productosFactura);
		// asignando totales
		factura.setDetalleFactura(detalleFactura);
		factura.setIva(totalFactura * 0.15);
		factura.setTotal(new BigDecimal(totalFactura));

		logger.debug("contenido de la factura inicializado.");
	}

}

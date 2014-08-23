/**
 * Fecha de creación: 01/01/2011 20:49:36
 *
 * Copyright (c) 2011 SynergyJ. 
 * Todos los derechos reservados.
 *
 * Este software es información pueder ser mofificado, utilizado
 * haciendo referencia al autor intelectual.
 */
package com.synergyj.cursos.webservices.schemas;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synergyj.cursos.webservices.container.XMLBFacturaDocument;
import com.synergyj.cursos.webservices.ordenes.XMLBFactura;

/**
 * Este ejemplo muestra el uso del api de xmlbeans para realizar el proceso de
 * deserialización leyendo el contenido de un documento xml cargando la información en las
 * clases generadas por el api.
 * @author Jorge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */
public class DeserializingXMLTestCase {

	/**
	 * Logger para todas las instancias de la clase
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DeserializingXMLTestCase.class);

	private static final String XML_FILE = "xml/ejemploFactura.xml";

	@Test
	public void deserializingXML() throws Exception {

		XMLBFacturaDocument document;
		XMLBFactura factura;

		logger.debug("Mostrando el contenido de la factura representad en el archivo {}",
				XML_FILE);

		// Todas las clases generadas por xmlbeans contienen una inner class llamada
		// Factory empleadas para realizar el proceso de binding xml <-> java
		document = XMLBFacturaDocument.Factory.parse(new File(XML_FILE));

		logger.debug("inicando proceso de deserializacion");
		// Cada document contiene una referencia al objeto xml a deserializar
		factura = document.getXMLBFactura();
		
		Assert.assertNotNull(factura);

		logger.debug("validando el documento parseado.");
		XmlUtils.validaXML(factura);

		logger.debug("Representacion xml: \n{}", document);
		logger.debug("monto total de la factura: {}", factura.getTotal());
		logger.debug("numero de productos adquiridos: {}", factura.getDetalleFactura()
				.getProductoArray().length
				);
		Assert.assertEquals(3, factura.getDetalleFactura()
        .getProductoArray().length);

	}
}

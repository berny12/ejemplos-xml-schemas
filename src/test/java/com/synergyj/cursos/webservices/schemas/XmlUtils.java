/**
 * Fecha de creación: 01/01/2011 22:59:12
 *
 * Copyright (c) 2011 SynergyJ. 
 * Todos los derechos reservados.
 *
 * Este software es información pueder ser mofificado, utilizado
 * haciendo referencia al autor intelectual.
 */
package com.synergyj.cursos.webservices.schemas;

import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase empleada para validar documentos XML con XMLBeans.
 * @author Jorge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */
public class XmlUtils {

	/**
	 * Logger para todas las instancias de la clase
	 */
	private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

	/**
	 * Valida cualquier objeto XMLBeans
	 */
	public static void validaXML(XmlObject xmlObject) {

		boolean valid;
		List<XmlError> listaErrores;
		XmlOptions xmlOptions;

		xmlOptions = new XmlOptions();
		listaErrores = new ArrayList<XmlError>();
		xmlOptions.setErrorListener(listaErrores);

		valid = xmlObject.validate(xmlOptions);

		if (!valid) {

			logger.error("El objeto XML no cumple con el esquema. Detalle del error:");

			for (XmlError xmlError : listaErrores) {
				logger.debug("Error: {}", xmlError);
			}
			Assert.fail();
		} else {
			logger.debug("el objeto XMl es valido, cumple con el esquema.");
		}

	}
}

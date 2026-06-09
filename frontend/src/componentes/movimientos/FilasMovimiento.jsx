import React from 'react';

// Producto 1: Transferencia Enviada (Rojo)
export const FilaTransferenciaEnviada = ({ movimiento }) => {
  
  // Método 1: Define la paleta de colores de este producto
  const asignarColor = () => {
    return {
      fondoTabla: "table-danger",
      colorTexto: "text-danger",
      icono: "bi-arrow-up-right text-danger"
    };
  };

  // Método 2: Define qué datos específicos debe mostrar
  const mostrarDatos = () => {
    return `Hacia CBU/Alias: ${movimiento.cuentaDestino?.alias || 'Externo'}`;
  };

  const estilos = asignarColor();

  return (
    <tr className={estilos.fondoTabla}>
      <td><i className={`bi ${estilos.icono} me-2`}></i>Transferencia Enviada</td>
      <td>{mostrarDatos()}</td>
      <td className={`${estilos.colorTexto} fw-bold`}>-${movimiento.monto}</td>
      <td>{movimiento.fecha || 'Reciente'}</td>
    </tr>
  );
};

// Producto 2: Transferencia Recibida (Verde)
export const FilaTransferenciaRecibida = ({ movimiento }) => {
  
  // Método 1: Define la paleta de colores de este producto
  const asignarColor = () => {
    return {
      fondoTabla: "table-success",
      colorTexto: "text-success",
      icono: "bi-arrow-down-left text-success"
    };
  };

  // Método 2: Define qué datos específicos debe mostrar
  const mostrarDatos = () => {
    return `Desde CBU/Alias: ${movimiento.cuentaOrigen?.alias || 'Externo'}`;
  };

  const estilos = asignarColor();

  return (
    <tr className={estilos.fondoTabla}>
      <td><i className={`bi ${estilos.icono} me-2`}></i>Transferencia Recibida</td>
      <td>{mostrarDatos()}</td>
      <td className={`${estilos.colorTexto} fw-bold`}>+${movimiento.monto}</td>
      <td>{movimiento.fecha || 'Reciente'}</td>
    </tr>
  );
};

// Producto 3: Orden de Extracción (Amarillo)
export const FilaOrdenExtraccion = ({ movimiento }) => {
  
  // Método 1: Define la paleta de colores de este producto
  const asignarColor = () => {
    return {
      fondoTabla: "table-warning",
      colorTexto: "text-warning",
      icono: "bi-cash-coin text-warning"
    };
  };

  // Método 2: Define qué datos específicos debe mostrar
  const mostrarDatos = () => {
    return `DNI Titular: ${movimiento.dni}`;
  };

  const estilos = asignarColor();

  return (
    <tr className={estilos.fondoTabla}>
      <td><i className={`bi ${estilos.icono} me-2`}></i>Extracción en Cajero</td>
      <td>{mostrarDatos()}</td>
      <td className={`${estilos.colorTexto} fw-bold`}>-${movimiento.monto_orden}</td>
      <td>Código: {movimiento.codigo}</td>
    </tr>
  );
};
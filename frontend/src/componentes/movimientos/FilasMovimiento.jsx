import React from 'react';

// Producto 1: Transferencia Enviada (Rojo)
export const FilaTransferenciaEnviada = ({ movimiento }) => {
  return (
    <tr className="table-danger">
      <td><i className="bi bi-arrow-up-right text-danger me-2"></i>Transferencia Enviada</td>
      <td>Hacia CBU/Alias: {movimiento.cuentaDestino?.alias || 'Externo'}</td>
      <td className="text-danger fw-bold">-${movimiento.monto}</td>
      <td>{movimiento.fecha || 'Reciente'}</td>
    </tr>
  );
};

// Producto 2: Transferencia Recibida (Verde)
export const FilaTransferenciaRecibida = ({ movimiento }) => {
  return (
    <tr className="table-success">
      <td><i className="bi bi-arrow-down-left text-success me-2"></i>Transferencia Recibida</td>
      <td>Desde CBU/Alias: {movimiento.cuentaOrigen?.alias || 'Externo'}</td>
      <td className="text-success fw-bold">+${movimiento.monto}</td>
      <td>{movimiento.fecha || 'Reciente'}</td>
    </tr>
  );
};

// Producto 3: Orden de Extracción (Amarillo)
export const FilaOrdenExtraccion = ({ movimiento }) => {
  return (
    <tr className="table-warning">
      <td><i className="bi bi-cash-coin text-warning me-2"></i>Extracción en Cajero</td>
      <td>DNI Titular: {movimiento.dni}</td>
      <td className="text-warning fw-bold">-${movimiento.monto_orden}</td>
      <td>Código: {movimiento.codigo}</td>
    </tr>
  );
};
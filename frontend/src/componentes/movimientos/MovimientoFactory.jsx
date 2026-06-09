import React from 'react';
import { 
  FilaTransferenciaEnviada, 
  FilaTransferenciaRecibida, 
  FilaOrdenExtraccion 
} from './FilasMovimiento';

export const MovimientoFactory = ({ movimiento, idCuentaActiva }) => {
  
  // Devuelve un booleano (true/false) validando la firma estructural del objeto
  const esOrdenExtraccion = (mov, id) => {
    return mov.monto_orden !== undefined;
  };

  // Evalúa la pertenencia de la cuenta y retorna el producto concreto correspondiente
  const tipoTransferencia = (mov, id) => {
    const idOrigen = mov.cuentaOrigen?.idCuenta || mov.cuentaOrigen;
    const esOrigen = parseInt(idOrigen) === parseInt(id);

    if (esOrigen) {
      return <FilaTransferenciaEnviada movimiento={mov} />;
    } else {
      return <FilaTransferenciaRecibida movimiento={mov} />;
    }
  };

  // Es el método principal de la Fábrica. Orquesta las validaciones privadas
  // y retorna polimórficamente la vista correcta.
  const crearFila = (mov) => {
    if (esOrdenExtraccion(mov, idCuentaActiva)) {
      return <FilaOrdenExtraccion movimiento={mov} />;
    } else {
      // Si no es extracción, delegamos al método que resuelve las transferencias
      return tipoTransferencia(mov, idCuentaActiva);
    }
  };

  // Finalmente, el componente de React simplemente ejecuta el método público
  return crearFila(movimiento);
};
import React from 'react';
import { 
  FilaTransferenciaEnviada, 
  FilaTransferenciaRecibida, 
  FilaOrdenExtraccion 
} from './FilasMovimiento';

export const MovimientoFactory = ({ movimiento, idCuentaActiva }) => {
  
  // 1. Lógica para detectar si el objeto es una Orden de Extracción
  // Sabemos que si tiene la propiedad 'monto_orden', es una extracción.
  if (movimiento.monto_orden !== undefined) {
    return <FilaOrdenExtraccion movimiento={movimiento} />;
  }

  // 2. Lógica para detectar el tipo de Transferencia
  // Si no es extracción, es transferencia. Comparamos los IDs para ver si la plata entró o salió.
  // Aseguramos de que ambos sean números (parseInt) para evitar errores de comparación.
  const idOrigen = movimiento.cuentaOrigen?.idCuenta || movimiento.cuentaOrigen;
  const esOrigen = parseInt(idOrigen) === parseInt(idCuentaActiva);

  if (esOrigen) {
    return <FilaTransferenciaEnviada movimiento={movimiento} />;
  } else {
    return <FilaTransferenciaRecibida movimiento={movimiento} />;
  }
};
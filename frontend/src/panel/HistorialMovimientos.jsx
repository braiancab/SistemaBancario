import React, { useEffect, useState } from "react";
import axios from "axios";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import { MovimientoFactory } from "../componentes/movimientos/MovimientoFactory";

function HistorialMovimientos() {
  const [movimientos, setMovimientos] = useState([]);

  // 1. Traemos TODAS las variables necesarias del localStorage
  const idCuenta = localStorage.getItem("idCuenta");
  const token = localStorage.getItem("token");
  const idCliente = localStorage.getItem("idCliente"); // Agregado para las extracciones, ya que se buscan por cliente
  useEffect(() => {
    // Si falta algún dato de sesión, no hacemos las peticiones
    // if (!idCuenta || !token || !idCliente) return;
    
    // 2. Petición de Transferencias
    const peticionTransferencias = axios.get(
      `http://localhost:8080/api/transferencias/cuenta/${idCuenta}`, 
      { headers: { Authorization: `Bearer ${token}` } }
    );

    // 3. Petición de Órdenes de Extracción 
    const peticionExtracciones = axios.get(
      `http://localhost:8080/api/ordenes_extraccion/historial/cliente/${idCliente}`, 
      { headers: { Authorization: `Bearer ${token}` } }
    );

    // 4. Promise.all para ejecutar en paralelo
    Promise.all([peticionTransferencias, peticionExtracciones])
      .then(([resTransferencias, resExtracciones]) => {
        
        let historialUnificado = [...resTransferencias.data, ...resExtracciones.data];

        // Ordenamos cronológicamente (de más nuevo a más viejo)
        historialUnificado.sort((a, b) => {
          const fechaA = new Date(a.fecha || a.fechaCreacion || 0);
          const fechaB = new Date(b.fecha || b.fechaCreacion || 0);
          return fechaB - fechaA; 
        });

        setMovimientos(historialUnificado);
      })
      .catch((err) => {
        console.error("Error al cargar el historial unificado:", err);
      });

  }, [idCuenta, token, idCliente]); // Agregamos idCliente a las dependencias

  // 5. PDF Inteligente (Sabe distinguir entre Transferencia y Extracción)
  const descargarPDFHistorial = () => {
    const doc = new jsPDF();
    doc.setFontSize(18);
    doc.text("Historial de Movimientos Unificado", 14, 20);

    const filas = movimientos.map((mov) => {
      // Si tiene monto_orden, es una Orden de Extracción
      if (mov.monto_orden !== undefined) {
        return [
          "Extracción en Cajero",
          `DNI Titular: ${mov.dni}`,
          `-$${mov.monto_orden}`,
          `Código: ${mov.codigo}`,
          "GENERADA" // O mov.estado si las extracciones manejan estado
        ];
      } else {
        // Si no, es una Transferencia
        const esOrigen = parseInt(mov.cuentaOrigen?.idCuenta) === parseInt(idCuenta);
        const tipo = esOrigen ? "Transferencia Enviada" : "Transferencia Recibida";
        const contraparte = esOrigen
          ? `${mov.cuentaDestino?.cliente?.nombre} ${mov.cuentaDestino?.cliente?.apellido}`
          : `${mov.cuentaOrigen?.cliente?.nombre} ${mov.cuentaOrigen?.cliente?.apellido}`;
        const montoSigno = esOrigen ? `-$${mov.monto}` : `+$${mov.monto}`;

        return [
          tipo,
          contraparte,
          montoSigno,
          mov.motivo?.motivo || "Sin motivo",
          mov.estado,
        ];
      }
    });

    autoTable(doc, {
      startY: 30,
      head: [["Tipo", "Detalle", "Monto", "Motivo / Cód", "Estado"]],
      body: filas,
    });

    doc.save("historial_movimientos.pdf");
  };

  return (
    <div className="container mt-4">
      <h2>Mi Historial de Movimientos</h2>
      
      <button
        className="btn btn-dark mb-4"
        onClick={descargarPDFHistorial}
        disabled={movimientos.length === 0}
      >
        Descargar PDF del Historial Completo
      </button>

      {movimientos.length === 0 ? (
        <p>No hay movimientos registrados.</p>
      ) : (
        <table className="table table-hover border">
          <thead className="table-dark">
            <tr>
              <th>Tipo de Operación</th>
              <th>Detalle</th>
              <th>Monto</th>
              <th>Fecha / Cód</th>
            </tr>
          </thead>
          <tbody>
            {movimientos.map((mov, index) => (
              /*
                 Como key, usamos idTransferencia, o id_extraccion, o el index de respaldo */
              <MovimientoFactory
                key={mov.idTransferencia || mov.id_extraccion || index} 
                movimiento={mov}
                idCuentaActiva={idCuenta}
              />
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default HistorialMovimientos;
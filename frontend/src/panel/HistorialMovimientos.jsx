import React, { useEffect, useState } from "react";
import axios from "axios";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import { MovimientoFactory } from "../componentes/movimientos/MovimientoFactory";

function HistorialMovimientos() {
  const [movimientos, setMovimientos] = useState([]);

  const idCuenta = localStorage.getItem("idCuenta");
  const token = localStorage.getItem("token");
  const idCliente = localStorage.getItem("idCliente");
  const [cantidadTransferencias, setCantidadTransferencias] = useState(0);
const [totalTransferido, setTotalTransferido] = useState(0);

useEffect(() => {
    axios
        .get(`http://localhost:8080/api/transferencias/estadisticas/total/${idCuenta}`)
        .then((response) => {
            setTotalTransferido(response.data);
        });
}, []);

console.log("idCuenta:", idCuenta);

useEffect(() => {
    axios
        .get(`http://localhost:8080/api/transferencias/estadisticas/cantidad/${idCuenta}`)
        .then((response) => {
            console.log("Respuesta cantidad:", response.data);
            setCantidadTransferencias(response.data);
        })
        .catch(error => {
            console.error(error);
        });
}, [idCuenta]);


  // ==========================================
  // MÉTODOS DEL DIAGRAMA UML
  // ==========================================

  // Método 1: peticionApi() -> Encargado exclusivo de traer los datos
  const peticionApi = () => {
    const peticionTransferencias = axios.get(
      `http://localhost:8080/api/transferencias/cuenta/${idCuenta}`, 
      { headers: { Authorization: `Bearer ${token}` } }
    ).catch((err) => {
      
      return { data: [] };
    });

    const peticionExtracciones = axios.get(
      `http://localhost:8080/api/ordenes_extraccion/historial/cliente/${idCliente}`, 
      { headers: { Authorization: `Bearer ${token}` } }
    ).catch((err) => {
      
      console.warn("Aviso: No se encontraron órdenes de extracción.");
      return { data: [] };
    });
    
    // Retorna la promesa combinada
    return Promise.all([peticionTransferencias, peticionExtracciones]);
  };

  // Método 2: unificarDatos() -> Encargado de juntar los arreglos
  const unificarDatos = (resTransferencias, resExtracciones) => {
    return [...resTransferencias.data, ...resExtracciones.data];
  };

  // Método 3: ordenarHistorial() -> Encargado de la lógica de fechas
// Método 3: agruparYOrdenar() -> Encargado de la lógica de negocio del arreglo
  const ordenarHistorial = (historialDesordenado) => {
    return historialDesordenado.sort((a, b) => {
      
      // 1. Asignamos un "peso" para agrupar por categoría
      // Si tiene monto_orden es Extracción (Peso 1), sino es Transferencia (Peso 2)
      const pesoA = a.monto_orden !== undefined ? 1 : 2;
      const pesoB = b.monto_orden !== undefined ? 1 : 2;

      // Si son de distinta categoría, los separamos (Las extracciones quedarán arriba)
      if (pesoA !== pesoB) {
        return pesoA - pesoB;
      }

      // 2. Si son de la misma categoría, los ordenamos por ID (de mayor a menor)
      // Buscamos el ID correcto dependiendo de qué objeto estemos mirando
      const idA = a.id_extraccion || a.idTransferencia || 0;
      const idB = b.id_extraccion || b.idTransferencia || 0;

      // Orden descendente: los IDs más grandes (los últimos creados) arriba
      return idB - idA; 
    });
  };

  // Método 4: crearProductos() -> El Creador delegando a la Fábrica
  const crearProductos = () => {
    return movimientos.map((mov, index) => (
      <MovimientoFactory
        key={mov.idTransferencia || mov.id_extraccion || index} 
        movimiento={mov}
        idCuentaActiva={idCuenta}
      />
    ));
  };

  // ==========================================
  // ORQUESTADOR Y RENDERIZADO
  // ==========================================

  useEffect(() => {
    if (!idCuenta || !token || !idCliente) return;

    
    peticionApi()
      .then(([resTransferencias, resExtracciones]) => {
        const historialUnificado = unificarDatos(resTransferencias, resExtracciones);
        const historialOrdenado = ordenarHistorial(historialUnificado);
        
        setMovimientos(historialOrdenado);
      })
      .catch((err) => {
        console.error("Error al cargar el historial:", err);
      });

  }, [idCuenta, token, idCliente]);

  
  const descargarPDFHistorial = () => {
    const doc = new jsPDF();
    doc.setFontSize(18);
    doc.text("Historial de Movimientos Unificado", 14, 20);

    const filas = movimientos.map((mov) => {
      if (mov.monto_orden !== undefined) {
        return [
          "Extracción en Cajero",
          `DNI Titular: ${mov.dni}`,
          `-$${mov.monto_orden}`,
          `Código: ${mov.codigo}`,
          "GENERADA"
        ];
      } else {
        const esOrigen = parseInt(mov.cuentaOrigen?.idCuenta) === parseInt(idCuenta);
        const tipo = esOrigen ? "Transferencia Enviada" : "Transferencia Recibida";
        const contraparte = esOrigen
          ? `${mov.cuentaDestino?.cliente?.nombre} ${mov.cuentaDestino?.cliente?.apellido}`
          : `${mov.cuentaOrigen?.cliente?.nombre} ${mov.cuentaOrigen?.cliente?.apellido}`;
        const montoSigno = esOrigen ? `-$${mov.monto}` : `+$${mov.monto}`;

        return [ tipo, contraparte, montoSigno, mov.motivo?.motivo || "Sin motivo", mov.estado ];
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

      <p>
    Cantidad de transferencias realizadas:
    {cantidadTransferencias}
</p>
<p>
    Total transferido: $
    {totalTransferido}
</p>


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
            {/* Llamamos al método que delega la creación a la Fábrica */}
            {crearProductos()}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default HistorialMovimientos;
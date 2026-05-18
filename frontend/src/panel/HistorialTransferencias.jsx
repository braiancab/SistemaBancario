import { useEffect, useState } from "react";
import axios from "axios";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

function HistorialTransferencias() {
  const [transferencias, setTransferencias] = useState([]);
  const [enviadas, setEnviadas] = useState([]);
  const [recibidas, setRecibidas] = useState([]);

  const idCuenta = localStorage.getItem("idCuenta");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!idCuenta || !token) return;

    axios
      .get(`http://localhost:8080/api/transferencias/cuenta/${idCuenta}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setTransferencias(res.data);

        // separar transferencias por idcuenta
        const enviadas = res.data.filter(
          (t) => t.cuentaOrigen.idCuenta === Number(idCuenta),
        );

        const recibidas = res.data.filter(
          (t) => t.cuentaDestino.idCuenta === Number(idCuenta),
        );

        setEnviadas(enviadas);
        setRecibidas(recibidas);
      })
      .catch((err) => {
        console.error("Error al cargar transferencias:", err);
      });
  }, [idCuenta, token]);

  const descargarPDFEnviadas = () => {
    const doc = new jsPDF();

    doc.setFontSize(18);
    doc.text("Historial de Transferencias Enviadas", 14, 20);

    const filas = enviadas.map((t) => [
      `${t.cuentaDestino.cliente.nombre} ${t.cuentaDestino.cliente.apellido}`,
      `$${t.monto}`,
      t.motivo?.motivo || "Sin motivo",
      t.estado,
    ]);

    autoTable(doc, {
      startY: 30,
      head: [["Destino", "Monto", "Motivo", "Estado"]],
      body: filas,
    });

    doc.save("transferencias_enviadas.pdf");
  };

  const descargarPDFRecibidas = () => {
    const doc = new jsPDF();

    doc.setFontSize(18);
    doc.text("Historial de Transferencias Recibidas", 14, 20);

    const filas = recibidas.map((t) => [
      `${t.cuentaOrigen.cliente.nombre} ${t.cuentaOrigen.cliente.apellido}`,
      `$${t.monto}`,
      t.motivo?.motivo || "Sin motivo",
      t.estado,
    ]);

    autoTable(doc, {
      startY: 30,
      head: [["Origen", "Monto", "Motivo", "Estado"]],
      body: filas,
    });

    doc.save("transferencias_recibidas.pdf");
  };

  return (
    <div className="container mt-4">
      <h2>Historial de Transferencias</h2>

      {/*  ENVIADAS */}
      <div className="mt-4">
        <h4 className="text-danger">Transferencias Enviadas </h4>

        {enviadas.length === 0 ? (
          <p>No hay transferencias enviadas</p>
        ) : (
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>Destino</th>
                <th>Monto</th>
                <th>Motivo</th>
                <th>Estado</th>
              </tr>
            </thead>
            <tbody>
              {enviadas.map((t) => (
                <tr key={t.idTransferencia}>
                  <td>
                    {t.cuentaDestino.cliente.nombre}{" "}
                    {t.cuentaDestino.cliente.apellido}
                  </td>
                  <td>${t.monto}</td>
                  <td>{t.motivo?.motivo}</td>
                  <td>{t.estado}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        <button
          className="btn btn-danger mb-3"
          onClick={descargarPDFEnviadas}
          disabled={enviadas.length === 0}
        >
          Descargar PDF Enviadas
        </button>
      </div>

      {/*  RECIBIDAS */}
      <div className="mt-4">
        <h4 className="text-success">Transferencias Recibidas</h4>

        {recibidas.length === 0 ? (
          <p>No hay transferencias recibidas</p>
        ) : (
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>Origen</th>
                <th>Monto</th>
                <th>Motivo</th>
                <th>Estado</th>
              </tr>
            </thead>
            <tbody>
              {recibidas.map((t) => (
                <tr key={t.idTransferencia}>
                  <td>
                    {t.cuentaOrigen.cliente.nombre}{" "}
                    {t.cuentaOrigen.cliente.apellido}
                  </td>
                  <td>${t.monto}</td>
                  <td>{t.motivo?.motivo}</td>
                  <td>{t.estado}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        <button
          className="btn btn-success mb-3"
          onClick={descargarPDFRecibidas}
          disabled={recibidas.length === 0}
        >
          Descargar PDF Recibidas
        </button>
      </div>
    </div>
  );
}

export default HistorialTransferencias;

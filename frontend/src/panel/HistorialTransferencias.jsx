import { useEffect, useState } from "react";
import axios from "axios";

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

  return (
    <div className="container mt-4">
      <h2>Historial de Transferencias</h2>

      {/*  ENVIADAS */}
      <div className="mt-4">
        <h4 className="text-danger">Transferencias Enviadas</h4>
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
                  <td>{t.motivoTransferencia?.motivo}</td>
                  <td>{t.estado}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
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
                  <td>{t.motivoTransferencia?.motivo}</td>
                  <td>{t.estado}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default HistorialTransferencias;
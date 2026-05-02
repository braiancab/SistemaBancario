import { useState } from "react";
import { crearOrdenExtraccion } from "../servicio/ordenExtraccionServicio";

const SolicitarExtraccion = () => {
  const [monto, setMonto] = useState("");
  const [dni, setDni] = useState("");
  const [mensaje, setMensaje] = useState(null);
  const [ordenCreada, setOrdenCreada] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");
    const idCuenta = localStorage.getItem("idCuenta");

    if (!token || !idCuenta) {
      setMensaje({ tipo: "danger", texto: "Sesión inválida" });
      return;
    }
    if (!idCuenta) {
  setMensaje({ tipo: "danger", texto: "No hay cuenta activa" });
  return;
}
    if (monto <= 0) {
      setMensaje({ tipo: "danger", texto: "El monto debe ser mayor a 0" });
      return;
    }

    if (dni.length < 7) {
      setMensaje({ tipo: "danger", texto: "DNI inválido" });
      return;
    }

    try {
      const nuevaOrden = {
        monto_orden: parseFloat(monto),
        dni: dni,
        cuentaOrigen: {
          idCuenta: parseInt(idCuenta),
        },
      };

      const res = await crearOrdenExtraccion(nuevaOrden, token);

      setOrdenCreada(res.data);
      setMensaje({ tipo: "success", texto: "Orden generada correctamente" });

      // limpiar form
      setMonto("");
      setDni("");

    } catch (error) {
  console.error("ERROR COMPLETO:", error);
  console.error("RESPUESTA:", error.response);

  setMensaje({
    tipo: "danger",
    texto: error.response?.data?.message || "Error al generar la orden",
  });
}
  };

  return (
    <div className="container mt-4">
      <div className="card shadow">
        <div className="card-header bg-dark text-white">
          <h4>Solicitar Extracción</h4>
        </div>

        <div className="card-body">

          {mensaje && (
            <div className={`alert alert-${mensaje.tipo}`}>
              {mensaje.texto}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Monto</label>
              <input
                type="number"
                className="form-control"
                value={monto}
                onChange={(e) => setMonto(e.target.value)}
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">DNI</label>
              <input
                type="text"
                className="form-control"
                value={dni}
                onChange={(e) => setDni(e.target.value)}
                required
              />
            </div>

            <button className="btn btn-primary w-100">
              Generar Orden
            </button>
          </form>

        </div>
      </div>

      {/* RESULTADO */}
      {ordenCreada && (
        <div className="card mt-4 border-success">
          <div className="card-header bg-success text-white">
            Comprobante de Extracción
          </div>
          <div className="card-body">
            <p><strong>Código:</strong> {ordenCreada.codigo}</p>
            <p><strong>Monto:</strong> ${ordenCreada.monto_orden}</p>
            <p><strong>DNI:</strong> {ordenCreada.dni}</p>
            <p className="text-muted">
              Presentá este código en el cajero para retirar el dinero.
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default SolicitarExtraccion;
import { useState, useEffect } from "react";
import { crearOrdenExtraccion } from "../servicio/ordenExtraccionServicio";
import { getCuentaByCliente } from "../servicio/cuentaServicio";
import jsPDF from "jspdf";

const generarOrdenExtraccion = () => {
  const [monto, setMonto] = useState("");
  const [dni, setDni] = useState("");
  const [mensaje, setMensaje] = useState(null);
  const [ordenCreada, setOrdenCreada] = useState(null);
  //Constantes para elegir cuenta
  const [cuentas, setCuentas] = useState([]);
  const [cuentaSeleccionada, setCuentaSeleccionada] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");
    const idCliente = localStorage.getItem("idCliente");

    getCuentaByCliente(idCliente, token)
      .then((res) => {
        setCuentas(res.data);

        if (res.data.length > 0) {
          setCuentaSeleccionada(res.data[0].idCuenta);
        }
      })
      .catch(console.error);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensaje(null); // Limpiamos errores anteriores

    const token = localStorage.getItem("token");
    const idCuenta = cuentaSeleccionada;
    const idCliente = localStorage.getItem("idCliente");

    if (!token || !idCuenta) {
      setMensaje({
        tipo: "danger",
        texto: "Sesión inválida o no hay cuenta activa.",
      });
      return;
    }

    if (parseFloat(monto) <= 0) {
      setMensaje({ tipo: "danger", texto: "El monto debe ser mayor a 0." });
      return;
    }

    if (dni.length < 7) {
      setMensaje({ tipo: "danger", texto: "DNI inválido." });
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
      setMensaje({ tipo: "success", texto: "¡Orden generada correctamente!" });

      const cuentasActualizadas = await getCuentaByCliente(idCliente, token);

      setCuentas(cuentasActualizadas.data);
      // Limpiar form
      setMonto("");
      setDni("");
    } catch (error) {
      console.error("ERROR COMPLETO:", error);

      const errorMsg =
        error.response?.data?.message ||
        error.response?.data ||
        "Error al generar la orden.";

      setMensaje({
        tipo: "danger",

        texto:
          typeof errorMsg === "string"
            ? errorMsg
            : "Ocurrió un error inesperado.",
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
            <div className={`alert alert-${mensaje.tipo}`}>{mensaje.texto}</div>
          )}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Cuenta de origen</label>

              <select
                className="form-select"
                value={cuentaSeleccionada}
                onChange={(e) => setCuentaSeleccionada(e.target.value)}
              >
                {cuentas.map((cuenta) => (
                  <option key={cuenta.idCuenta} value={cuenta.idCuenta}>
                    {cuenta.cvu} - Saldo: ${cuenta.saldo}
                  </option>
                ))}
              </select>
            </div>

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

            <button
              className="btn btn-primary w-100"
              disabled={!cuentaSeleccionada}
            >
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
            <p>
              <strong>Código:</strong> {ordenCreada.codigo}
            </p>
            <p>
              <strong>Monto:</strong> ${ordenCreada.monto_orden}
            </p>
            <p>
              <strong>DNI:</strong> {ordenCreada.dni}
            </p>
            <p className="text-muted">
              Presentá este código en el cajero para retirar el dinero.
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default generarOrdenExtraccion;

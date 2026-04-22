import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import jsPDF from "jspdf";

function Transferencias() {
  const navigate = useNavigate();

  // Estados
  const [cuentaOrigen, setCuentaOrigen] = useState(null);
  const [cuentasDestino, setCuentasDestino] = useState([]);
  const [motivos, setMotivos] = useState([]);
  const [comprobante, setComprobante] = useState(null);

  const [formData, setFormData] = useState({
    cuentaDestino: "",
    monto: "",
    motivo: "",
  });

  // Cerrar sesión
  const handleCerrarSesion = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("idCliente");
    navigate("/");
  };

  // Cargar datos al iniciar
  useEffect(() => {
    const token = localStorage.getItem("token");
    const idCliente = localStorage.getItem("idCliente");

    if (!token || !idCliente) {
      navigate("/");
      return;
    }

    const headers = {
      Authorization: `Bearer ${token}`,
    };

    //  Datos de la cuenta del cliente logueado (Origen)
    axios
      .get(`http://localhost:8080/api/cuentas/cliente/${idCliente}`, {
        headers,
      })
      .then((response) => {
        // Asumimos que response.data es el objeto cuenta que mostraste
        setCuentaOrigen(response.data[0] || response.data);
      })
      .catch((error) => console.error("Error cuenta origen:", error));

    // Todas las cuentas del sistema (Destino)
    axios
      .get(`http://localhost:8080/api/cuentas/destino/${idCliente}`, {
        headers,
      })
      .then((response) => {
        console.log("Cuentas destino:", response.data);
        setCuentasDestino(response.data);
      })
      .catch((error) => console.error("Error cuentas destino:", error));
    // Motivos de transferencia
    axios
      .get(`http://localhost:8080/api/motivo_transferencia`, { headers })
      .then((response) => setMotivos(response.data))
      .catch((error) => console.error("Error motivos:", error));
  }, [navigate]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!cuentaOrigen) {
      alert("Cuenta origen no cargada");
      return;
    }

    const token = localStorage.getItem("token");

    axios
      .post(
        "http://localhost:8080/api/transferencias",
        {
          cuentaOrigen: Number(cuentaOrigen.idCuenta),
          cuentaDestino: Number(formData.cuentaDestino),
          monto: Number(formData.monto),
          motivo: Number(formData.motivo),
        },
        { headers: { Authorization: `Bearer ${token}` } },
      )
      .then((response) => {
        alert("Transferencia realizada correctamente");

        const destinoSeleccionado = cuentasDestino.find(
          (c) => c.idCuenta === Number(formData.cuentaDestino),
        );

        const motivoSeleccionado = motivos.find(
          (m) => m.idMotivo === Number(formData.motivo),
        );

        setComprobante({
          cuentaOrigen:
            cuentaOrigen.cliente.nombre + " " + cuentaOrigen.cliente.apellido,

          cuentaDestino:
            destinoSeleccionado.cliente.nombre +
            " " +
            destinoSeleccionado.cliente.apellido,

          aliasDestino: destinoSeleccionado.alias,

          monto: formData.monto,

          motivo: motivoSeleccionado.motivo,

          fecha: new Date().toLocaleString(),

          codigoOperacion: "TRX-" + Date.now(),
        });
      })
      .catch((error) => {
        console.error("Error completo:", error);

        if (error.response) {
          alert(error.response.data);
        } else {
          alert("Error de conexión con el servidor");
        }
      });
  };

  const descargarPDF = () => {
    const doc = new jsPDF();

    doc.setFontSize(18);
    doc.text("Comprobante de Transferencia", 20, 20);

    doc.setFontSize(12);

    doc.text(`Cuenta Origen: ${comprobante.cuentaOrigen}`, 20, 40);
    doc.text(`Cuenta Destino: ${comprobante.cuentaDestino}`, 20, 50);
    doc.text(`Monto: $${comprobante.monto}`, 20, 60);
    doc.text(`Motivo: ${comprobante.motivo}`, 20, 70);
    doc.text(`Fecha: ${comprobante.fecha}`, 20, 80);
    doc.text(`Código Operación: ${comprobante.codigoOperacion}`, 20, 90);

    doc.save("comprobante_transferencia.pdf");
  };

  return (
    <div className="container mt-5">
      <div className="row mb-4">
        <div className="col d-flex justify-content-between align-items-center">
          <h2>Mi Banco</h2>
          <button
            className="btn btn-outline-danger"
            onClick={handleCerrarSesion}
          >
            Cerrar Sesión
          </button>
        </div>
      </div>

      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow border-0">
            <div className="card-header bg-success text-white">
              <h4 className="mb-0">Transferencia a terceros</h4>
            </div>

            <div className="card-body">
              <form onSubmit={handleSubmit}>
                {/* 1. CUENTA ORIGEN: Muestra el nombre del titular logueado */}
                <div className="mb-3">
                  <label className="form-label fw-bold">
                    Cuenta Origen (Titular)
                  </label>
                  <input
                    type="text"
                    className="form-control bg-light"
                    value={
                      cuentaOrigen && cuentaOrigen.cliente
                        ? `${cuentaOrigen.cliente.nombre} ${cuentaOrigen.cliente.apellido} - ${cuentaOrigen.alias}`
                        : "Cargando datos..."
                    }
                    readOnly
                  />
                </div>

                {/* 2. CUENTA DESTINO: Lista titulares exceptuando al logueado */}
                <div className="mb-3">
                  <label className="form-label fw-bold">
                    Cuenta Destino (Titular / Alias)
                  </label>
                  <select
                    className="form-select"
                    name="cuentaDestino"
                    value={formData.cuentaDestino}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Seleccione cuenta destino</option>
                    {cuentasDestino
                      .filter((c) => c.idCuenta !== cuentaOrigen?.idCuenta)
                      .map((cuenta) => (
                        <option key={cuenta.idCuenta} value={cuenta.idCuenta}>
                          {cuenta.cliente.nombre} {cuenta.cliente.apellido} |
                          Alias: {cuenta.alias}
                        </option>
                      ))}
                  </select>
                </div>

                <div className="mb-3">
                  <label className="form-label fw-bold">
                    Monto a transferir
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    name="monto"
                    value={formData.monto}
                    onChange={handleChange}
                    required
                  />
                </div>

                {/* 3. MOTIVO: Trae los datos de la tabla Motivo_transferencia */}
                <div className="mb-4">
                  <label className="form-label fw-bold">
                    Motivo de transferencia
                  </label>
                  <select
                    className="form-select"
                    name="motivo"
                    value={formData.motivo}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Seleccione motivo</option>
                    {motivos.map((m) => (
                      <option key={m.idMotivo} value={m.idMotivo}>
                        {m.motivo}{" "}
                        {/* Ajustado según tu tabla Motivo_transferencia */}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="d-flex gap-3">
                  <button
                    type="submit"
                    className="btn btn-success"
                    disabled={!cuentaOrigen}
                  >
                    Transferir
                  </button>

                  {/* BOTÓN VOLVER: Navega al Dashboard */}
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={() => navigate("/dashboard")}
                  >
                    Volver
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      {comprobante && (
        <div className="alert alert-success mt-4">
          <h4>Transferencia Exitosa</h4>

          <p>
            <b>Origen:</b> {comprobante?.cuentaOrigen}
          </p>
          <p>
            <b>Destino:</b> {comprobante?.cuentaDestino}
          </p>
          <p>
            <b>Monto:</b> ${comprobante?.monto}
          </p>
          <p>
            <b>Motivo:</b> {comprobante.motivo}
          </p>
          <p>
            <b>Fecha:</b> {comprobante.fecha}
          </p>
          <p>
            <b>Código:</b> {comprobante.codigoOperacion}
          </p>

          <button className="btn btn-danger" onClick={descargarPDF}>
            Descargar PDF
          </button>
        </div>
      )}
    </div>
  );
}

export default Transferencias;

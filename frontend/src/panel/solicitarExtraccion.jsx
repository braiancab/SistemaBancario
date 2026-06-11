import { useState, useEffect } from "react";
import { crearOrdenExtraccion } from "../servicio/ordenExtraccionServicio";
import { getCuentaByCliente } from "../servicio/cuentaServicio";
import jsPDF from "jspdf";

const GenerarOrdenExtraccion = () => { 
  const [monto, setMonto] = useState("");
  const [dni, setDni] = useState("");
  const [mensaje, setMensaje] = useState(null);
  const [ordenCreada, setOrdenCreada] = useState(null);
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

 
  const descargarPDF = () => {
    if (!ordenCreada) return;

    const doc = new jsPDF();

    
    doc.setFont("helvetica", "bold");
    doc.setFontSize(18);
    doc.text("COMPROBANTE DE EXTRACOCCIÓN", 20, 20);
    
    doc.setLineWidth(0.5);
    doc.line(20, 25, 190, 25); 

    doc.setFont("helvetica", "normal");
    doc.setFontSize(12);
    
    doc.text(`Código de Extracción: ${ordenCreada.codigo}`, 20, 40);
    doc.text(`Monto solicitado: $${ordenCreada.monto_orden}`, 20, 50);
    doc.text(`DNI del beneficiario: ${ordenCreada.dni}`, 20, 60);
    
    doc.setFont("helvetica", "italic");
    doc.setFontSize(10);
    doc.setTextColor(100);
    doc.text("Presentá este código en el cajero automático para retirar tu dinero.", 20, 80);

    
    doc.save(`comprobante_extraccion_${ordenCreada.codigo}.pdf`);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensaje(null);

    const token = localStorage.getItem("token");
    const idCuenta = cuentaSeleccionada;
    const idCliente = localStorage.getItem("idCliente");

    if (!token || !idCuenta) {
      setMensaje({ tipo: "danger", texto: "Sesión inválida o no hay cuenta activa." });
      return;
    }

    if (parseFloat(monto) <= 0) {
      setMensaje({ tipo: "danger", texto: "El monto debe ser mayor a 0." });
      return;
    }

    if (dni.length !== 8) {
      setMensaje({ tipo: "danger", texto: "DNI inválido." });
      return;
    }

    try {
      const nuevaOrden = {
        monto_orden: parseFloat(monto),
        dni: dni,
        cuentaOrigen: { idCuenta: parseInt(idCuenta) },
      };

      const res = await crearOrdenExtraccion(nuevaOrden, token);
      setOrdenCreada(res.data);
      setMensaje({ tipo: "success", texto: "¡Orden generada correctamente!" });

      const cuentasActualizadas = await getCuentaByCliente(idCliente, token);
      setCuentas(cuentasActualizadas.data);
      setMonto("");
      setDni("");
    } catch (error) {
      console.error("ERROR COMPLETO:", error);
      const errorMsg = error.response?.data?.message || error.response?.data || "Error al generar la orden.";
      setMensaje({
        tipo: "danger",
        texto: typeof errorMsg === "string" ? errorMsg : "Ocurrió un error inesperado.",
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
          {mensaje && <div className={`alert alert-${mensaje.tipo}`}>{mensaje.texto}</div>}
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

            <button className="btn btn-primary w-100" disabled={!cuentaSeleccionada}>
              Generar Orden
            </button>
          </form>
        </div>
      </div>

      
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
            
            
            <button 
              className="btn btn-outline-success w-100 mt-2" 
              onClick={descargarPDF}
            >
              Descargar Comprobante (PDF)
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default GenerarOrdenExtraccion;
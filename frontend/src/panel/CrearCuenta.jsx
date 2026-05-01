import { useState } from "react";

import { crearCuenta } from "../servicio/cuentaServicio";

export const CrearCuenta = () => {
  const [idTipoCuenta, setIdTipoCuenta] = useState("");
  const [mensaje, setMensaje] = useState({ texto: "", tipo: "" });

  const tiposDisponibles = [
    { id: 1, nombre: "Caja de Ahorro" },
    { id: 2, nombre: "Cuenta Corriente" },
  ];

  const manejarEnvio = async (e) => {
    e.preventDefault();
    
    // Recuperamos el ID del cliente y el Token del localStorage
    const idCliente = localStorage.getItem("idCliente");
    const token = localStorage.getItem("token"); // Asumiendo que lo guardás como 'token'

    if (!idCliente || !token) {
      setMensaje({ texto: "Error: No se encontró la sesión del cliente o el token de seguridad.", tipo: "danger" });
      return;
    }

    try {
     const nuevaCuenta = {
        idCliente: parseInt(idCliente),
        idTipo: parseInt(idTipoCuenta),
        idEstado: 1 
      };

      //  Llamamos a tu servicio pasándole los datos y el token
      await crearCuenta(nuevaCuenta, token);
      
      setMensaje({ texto: "¡Cuenta creada exitosamente!", tipo: "success" });
      setIdTipoCuenta(""); 
      
    } catch (error) {
      console.error("Error al crear cuenta:", error);
      //  Manejar errores específicos (ej. 403 No autorizado)
      const errorMsg = error.response?.data?.message || "Hubo un error al intentar crear la cuenta.";
      setMensaje({ texto: errorMsg, tipo: "danger" });
    }
  };

  return (
    <div className="container mt-4">
      <div className="card shadow-sm">
        <div className="card-header bg-dark text-white">
          <h4 className="mb-0">Abrir Nueva Cuenta Bancaria</h4>
        </div>
        <div className="card-body">
          
          {mensaje.texto && (
            <div className={`alert alert-${mensaje.tipo}`} role="alert">
              {mensaje.texto}
            </div>
          )}

          <form onSubmit={manejarEnvio}>
            <div className="mb-3">
              <label className="form-label">Seleccione el Tipo de Cuenta:</label>
              <select 
                className="form-select" 
                value={idTipoCuenta} 
                onChange={(e) => setIdTipoCuenta(e.target.value)}
                required
              >
                <option value="" disabled>-- Elija una opción --</option>
                {tiposDisponibles.map((tipo) => (
                  <option key={tipo.id} value={tipo.id}>
                    {tipo.nombre}
                  </option>
                ))}
              </select>
            </div>

            <button type="submit" className="btn btn-success w-100">
              Confirmar y Crear Cuenta
            </button>
          </form>

        </div>
      </div>
    </div>
  );
};

export default CrearCuenta;
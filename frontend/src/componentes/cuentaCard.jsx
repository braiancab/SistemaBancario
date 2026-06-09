import { useState } from "react";
import axios from "axios";

const CuentaCard = ({ cuenta }) => {

  const [mostrarSaldo, setMostrarSaldo] = useState(false);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [password, setPassword] = useState("");
const token = localStorage.getItem("token");
  if (!cuenta) return null;

  const desactivarCuenta = async () => {
    try {
await axios.put(
  `http://localhost:8080/api/cuentas/${cuenta.idCuenta}/desactivar`,
  {
    password: password
  },
  {
    headers: {
      Authorization: `Bearer ${token}`
    }
  }
);
      alert("Cuenta desactivada correctamente");

      setMostrarModal(false);
      setPassword("");

      // Recargar la página para ver el nuevo estado
      window.location.reload();

    } catch (error) {
      alert(
        error.response?.data ||
        "No fue posible desactivar la cuenta"
      );
    }
  };

  return (
    <>
      <div className="card shadow border-0 mb-4">

        <div className="card-header bg-dark text-white">
          <h4>Mi Cuenta</h4>
        </div>

        <div className="card-body">

          <p>
            <strong>N° Cuenta:</strong> {cuenta.idCuenta}
          </p>

          <p>
            <strong>CVU:</strong> {cuenta.cvu}
          </p>

          <p>
            <strong>Alias:</strong> {cuenta.alias}
          </p>

          <p>
            <strong>Tipo:</strong> {cuenta.tipoCuenta?.tipo}
          </p>

          <p>
            <strong>Estado:</strong>{" "}
            <span
              className={
                cuenta.estadoCuenta?.estado === "activo"
                  ? "badge bg-success"
                  : "badge bg-danger"
              }
            >
              {cuenta.estadoCuenta?.estado}
            </span>
          </p>

          <p className="text-success fw-bold">
            <strong>Saldo:</strong>{" "}
            {mostrarSaldo
              ? `$ ${cuenta.saldo}`
              : "••••••"}
          </p>

          <div className="d-flex gap-2">

            <button
              className="btn btn-outline-primary btn-sm"
              onClick={() =>
                setMostrarSaldo(!mostrarSaldo)
              }
            >
              {mostrarSaldo
                ? "Ocultar Saldo"
                : "Ver Saldo"}
            </button>

            {cuenta.estadoCuenta?.estado === "activo" && (
              <button
                className="btn btn-danger btn-sm"
                onClick={() => setMostrarModal(true)}
              >
                Desactivar Cuenta
              </button>
            )}

          </div>

        </div>
      </div>

      {/* Modal */}

      {mostrarModal && (
        <>
          <div
            className="modal fade show d-block"
            tabIndex="-1"
          >
            <div className="modal-dialog">
              <div className="modal-content">

                <div className="modal-header">
                  <h5 className="modal-title">
                    Desactivar Cuenta
                  </h5>

                  <button
                    type="button"
                    className="btn-close"
                    onClick={() =>
                      setMostrarModal(false)
                    }
                  ></button>
                </div>

                <div className="modal-body">

                  <p>
                    Esta acción desactivará la cuenta.
                    Ingrese su contraseña para continuar.
                  </p>

                  <input
                    type="password"
                    className="form-control"
                    placeholder="Contraseña"
                    value={password}
                    onChange={(e) =>
                      setPassword(e.target.value)
                    }
                  />

                </div>

                <div className="modal-footer">

                  <button
                    className="btn btn-secondary"
                    onClick={() =>
                      setMostrarModal(false)
                    }
                  >
                    Cancelar
                  </button>

                  <button
                    className="btn btn-danger"
                    onClick={desactivarCuenta}
                  >
                    Confirmar
                  </button>

                </div>

              </div>
            </div>
          </div>

          <div className="modal-backdrop fade show"></div>
        </>
      )}
    </>
  );
};

export default CuentaCard;
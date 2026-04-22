import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";

function Dashboard() {
  const navigate = useNavigate();

  // Estados
  const [cliente, setCliente] = useState(null);
  const [cuenta, setCuenta] = useState(null);

  // Cerrar sesión
  const handleCerrarSesion = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("idCliente");
    navigate("/");
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    const idCliente = localStorage.getItem("idCliente");

    if (!token || !idCliente) {
      navigate("/");
      return;
    }

    // =========================
    // DATOS DEL CLIENTE
    // =========================
    axios
      .get(`http://localhost:8080/api/clientes/${idCliente}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setCliente(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener cliente:", error);
      });

    // =========================
    // DATOS DE LA CUENTA
    // =========================
    axios
      .get(`http://localhost:8080/api/cuentas/cliente/${idCliente}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        // devuelve lista de cuentas
        setCuenta(response.data[0]);
      })
      .catch((error) => {
        console.error("Error al obtener cuenta:", error);
        navigate("/");
      });
  }, [navigate]);

  return (
    <div className="container mt-5">
      {/* ENCABEZADO */}
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

      {/* DATOS CLIENTE */}
      {cliente && (
        <div className="row mb-4">
          <div className="col-md-12">
            <div className="card shadow border-0">
              <div className="card-header bg-primary text-white">
                <h4 className="mb-0">Datos del Cliente</h4>
              </div>

              <div className="card-body">
                <div className="row">
                  <div className="col-md-6 mb-3">
                    <strong>Nombre:</strong>
                    <p>{cliente.nombre}</p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>Apellido:</strong>
                    <p>{cliente.apellido}</p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>DNI:</strong>
                    <p>{cliente.dni}</p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>Email:</strong>
                    <p>{cliente.email}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* DATOS CUENTA */}
      {cuenta && (
        <div className="row mb-4">
          <div className="col-md-12">
            <div className="card shadow border-0">
              <div className="card-header bg-dark text-white">
                <h4 className="mb-0">Mi Cuenta Bancaria</h4>
              </div>

              <div className="card-body">
                <div className="row">
                  <div className="col-md-6 mb-3">
                    <strong>Número Cuenta:</strong>
                    <p>{cuenta.idCuenta}</p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>CVU:</strong>
                    <p>{cuenta.cvu}</p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>Alias:</strong>
                    <p>{cuenta.alias}</p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>Saldo:</strong>
                    <p className="text-success fw-bold">
                      $ {cuenta.saldo}
                    </p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>Estado:</strong>
                    <p>{cuenta.estadoCuenta?.estado || "Sin estado"}</p>
                  </div>

                  <div className="col-md-6 mb-3">
                    <strong>Tipo Cuenta:</strong>
                    <p>{cuenta.tipoCuenta?.tipo || "Sin tipo"}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* OPERACIONES */}
      <div className="row">
        <div className="col-md-12">
          <div className="card shadow">
            <div className="card-body">
              <h5 className="card-title mb-4">Operaciones Rápidas</h5>

              <div className="d-flex gap-3 flex-wrap">
                

                <button
                     className="btn btn-success btn-lg"
                       onClick={() => navigate("/transferencias")}
                  >
                     Transferir
                </button>

                <button className="btn btn-secondary btn-lg">
                  Extraer
                </button>

                <button className="btn btn-primary btn-lg">
                  Depositar
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
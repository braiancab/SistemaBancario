import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { getClienteById } from "../servicio/clienteServicio";
import { getCuentaByCliente } from "../servicio/cuentaServicio";

export const BarraNavegacion = () => {
  const navegar = useNavigate();
  const [nombreCliente, setNombreCliente] = useState("");
  const [tieneCuenta, setTieneCuenta] = useState(false);

  useEffect(() => {
    const idCliente = localStorage.getItem("idCliente");
    const token = localStorage.getItem("token");

    if (idCliente && token) {
      getClienteById(idCliente, token)
        .then((res) => setNombreCliente(res.data.nombre))
        .catch((err) => console.error("Error cliente:", err));

      getCuentaByCliente(idCliente, token)
        .then((res) => {
          if (res.data && res.data.length > 0) {
            setTieneCuenta(true);
          }
        })
        .catch((err) => console.error("Error cuenta:", err));
    }
  }, []);

  const cerrarSesion = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("idCliente");
    navegar("/login");
  };

  return (
    <nav 
      className="navbar navbar-expand-lg navbar-dark shadow mb-5 sticky-top" 
      style={{ backgroundColor: "#0a192f", borderBottom: "3px solid #0d6efd" }}
    >
      <div className="container py-1">
        
        {/* Logo con el celeste brillante que usamos en el pie */}
        <Link className="navbar-brand fw-bold fs-4" to="/dashboard" style={{ color: "#4facfe" }}>
          🏦 Mi Banco
        </Link>
        
        <button className="navbar-toggler border-0 shadow-none" type="button" data-bs-toggle="collapse" data-bs-target="#menuNavegacion">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="menuNavegacion">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0 gap-3 ms-lg-4">
            <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/dashboard">
                Inicio
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/crear-cuenta">
                Nueva Cuenta
              </Link>
            </li>
            <li className="nav-item">
              <Link 
                className={`nav-link fw-medium ${!tieneCuenta ? 'text-light-emphasis hover-white' : 'text-light hover-white'}`} 
                to={tieneCuenta ? "/transferencias" : "#"}
                title={!tieneCuenta ? "Primero debés abrir una cuenta" : ""}
              >
                Transferencias {!tieneCuenta && "🔒"}
              </Link>
            </li>
             <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/movimientos">
                Movimientos
              </Link>
            </li>



          </ul>

          <div className="d-flex align-items-center gap-4">
            {/* Saludo dinámico en blanco suave */}
            <span className="text-light fw-medium d-none d-lg-block">
              ¡Hola, <span style={{ color: "#9dcffa" }}>{nombreCliente || "Usuario"}</span>! 👋
            </span>

            <button
              className="btn btn-link text-light text-decoration-none fw-medium hover-white px-0 ms-3"
              onClick={cerrarSesion}
            >
              Cerrar Sesión <span className="ms-1"></span>
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default BarraNavegacion;
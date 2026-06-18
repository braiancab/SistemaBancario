import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { getClienteById } from "../servicio/clienteServicio";
import { useDashboard } from "../hooks/useDashboard"; 

export const BarraNavegacion = () => {
  const navegar = useNavigate();
  const [nombreCliente, setNombreCliente] = useState("");
  
  // Obtenemos 'cuenta' (la renombramos para que no choque) y 'recargar' del hook 
  const { cliente, cuentas: cuentaDelHook, recargar } = useDashboard(navegar);

  // Definimos 'tieneCuenta' dinámicamente. 
  // Si cuentaDelHook existe y tiene propiedades, es true.
  const tieneCuenta = cuentaDelHook && Object.keys(cuentaDelHook).length > 0;

  useEffect(() => {
    const actualizar = () => recargar();
    window.addEventListener("cuentaCreada", actualizar);
    return () => window.removeEventListener("cuentaCreada", actualizar);
  }, [recargar]);

  useEffect(() => {
    const idCliente = localStorage.getItem("idCliente");
    const token = localStorage.getItem("token");

    if (idCliente && token) {
      getClienteById(idCliente, token)
        .then((res) => setNombreCliente(res.data.nombre))
        .catch((err) => console.error("Error cliente:", err));
        
    }
  }, []);

  const cerrarSesion = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("idCliente");
    navegar("/login");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark shadow mb-5 sticky-top" style={{ backgroundColor: "#0a192f", borderBottom: "3px solid #0d6efd" }}>
      <div className="container py-1">
        <Link className="navbar-brand fw-bold fs-4" to="/dashboard" style={{ color: "#4facfe" }}>
          🏦 Mi Banco
        </Link>

        <button className="navbar-toggler border-0 shadow-none" type="button" data-bs-toggle="collapse" data-bs-target="#menuNavegacion">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="menuNavegacion">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0 gap-3 ms-lg-4">
            <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/dashboard">Inicio</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/crear-cuenta">Nueva Cuenta</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/movimientos">Movimientos</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/tarjetas">Tarjetas</Link>
            </li>
            <li className="nav-item">
              <Link
                className={`nav-link fw-medium ${!tieneCuenta ? "text-white-50 opacity-50" : "text-light hover-white"}`}
                to={tieneCuenta ? "/transferencias" : "#"}
                title={!tieneCuenta ? "Primero debés abrir una cuenta" : ""}
                onClick={(e) => {
                  if (!tieneCuenta) e.preventDefault();
                }}
              >
                Transferir {!tieneCuenta && "🔒"}
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link fw-medium text-light hover-white" to="/extraccion">Orden Extracción</Link>
            </li>
          </ul>

          <div className="d-flex align-items-center gap-4">
            <span className="text-light fw-medium d-none d-lg-block">
              ¡Hola, <span style={{ color: "#9dcffa" }}>{nombreCliente || cliente?.nombre || "Usuario"}</span>! 👋
            </span>
            <button className="btn btn-link text-light text-decoration-none fw-medium hover-white px-0 ms-3" onClick={cerrarSesion}>
              Cerrar Sesión
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default BarraNavegacion;
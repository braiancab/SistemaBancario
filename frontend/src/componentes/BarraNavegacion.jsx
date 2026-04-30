import { Link, useNavigate } from "react-router-dom";
// 1. Importamos useState y useEffect
import { useState, useEffect } from "react";
// 2. Importamos tu servicio de cliente que me mostraste antes
import { getClienteById } from "../servicio/clienteServicio";

export const BarraNavegacion = () => {
  const navegar = useNavigate();
  // 3. Creamos un estado para guardar el nombre (arranca vacío)
  const [nombreCliente, setNombreCliente] = useState("");

  // 4. Usamos useEffect para buscar el nombre apenas carga la barra
  useEffect(() => {
    const idCliente = localStorage.getItem("idCliente");
    const token = localStorage.getItem("token");

    if (idCliente && token) {
      // Llamamos a tu función de clienteServicio
      getClienteById(idCliente, token)
        .then((respuesta) => {
          // Guardamos el nombre que nos devuelve Java. 
          // OJO: Asumo que en tu modelo de Java se llama "nombre". 
          // Si se llama "nombres" o de otra forma, cambialo acá abajo:
          setNombreCliente(respuesta.data.nombre); 
        })
        .catch((error) => {
          console.error("Error al traer los datos del cliente:", error);
        });
    }
  }, []); // Los corchetes vacíos significan "ejecutar solo una vez al cargar"

  const cerrarSesion = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("idCliente");
    navegar("/login");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm mb-5 sticky-top">
      <div className="container py-1">
        
        <Link className="navbar-brand fw-bold text-primary fs-4" to="/dashboard">
          🏦 Mi Banco
        </Link>
        
        <button className="navbar-toggler border-0 shadow-none" type="button" data-bs-toggle="collapse" data-bs-target="#menuNavegacion">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="menuNavegacion">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0 gap-3 ms-lg-4">
            <li className="nav-item">
              <Link className="nav-link fw-medium text-dark rounded px-3 hover-bg-light" to="/dashboard">
                Inicio
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link fw-medium text-dark rounded px-3" to="/crear-cuenta">
                Nueva Cuenta
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link fw-medium text-dark rounded px-3" to="/transferencias">
                Transferencias
              </Link>
            </li>
          </ul>
          
          <div className="d-flex align-items-center gap-4">
            {/* 5. Mostramos el nombre dinámico. Si por alguna razón todavía no cargó, mostramos "Usuario" por defecto */}
            <span className="text-secondary fw-medium d-none d-lg-block">
              ¡Hola, {nombreCliente || "Usuario"}! 👋
            </span>
            <button 
              className="btn btn-danger fw-semibold rounded-pill px-4" 
              onClick={cerrarSesion}
            >
              Cerrar Sesión
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default BarraNavegacion;
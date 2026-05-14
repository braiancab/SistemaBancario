import { useEffect, useState, useCallback } from "react"; // Agregamos useCallback
import { getClienteById } from "../servicio/clienteServicio";
import { getCuentaByCliente } from "../servicio/cuentaServicio";

export const useDashboard = (navigate) => {
  const [cliente, setCliente] = useState(null);
  const [cuentas, setCuentas] = useState([]);

  //  lógica en una función "recargar" que podamos exportar
  const recargar = useCallback(() => {
    const token = localStorage.getItem("token");
    const idCliente = localStorage.getItem("idCliente");

    if (!token || !idCliente) {
      if (navigate) navigate("/");
      return;
    }

    getClienteById(idCliente, token)
      .then(res => setCliente(res.data))
      .catch(err => console.error("Error al obtener cliente:", err));

    getCuentaByCliente(idCliente, token)
      .then(res => {
        // 2. Guardamos el array completo que devuelve Java
        if (res.data) {
          setCuentas(res.data); 
          // Guardamos el ID de la primera cuenta por si lo necesitás en otro lado
          if (res.data.length > 0) {
            localStorage.setItem("idCuenta", res.data[0].idCuenta);
          }
        }
      })
      .catch(err => {
        console.error("Error cuentas:", err);
        setCuentas([]);
      });
  }, [navigate]);

  useEffect(() => {
    recargar();
  }, [recargar]);

  // 2. Ejecutamos al cargar por primera vez
  useEffect(() => {
    recargar();
  }, [recargar]);

  // 3. Devolvemos también la función recargar
  return { cliente, cuentas, recargar };
};
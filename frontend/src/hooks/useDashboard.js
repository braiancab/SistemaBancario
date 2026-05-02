import { useEffect, useState } from "react";
import { getClienteById } from "../servicio/clienteServicio";
import { getCuentaByCliente } from "../servicio/cuentaServicio";

export const useDashboard = (navigate) => {
  const [cliente, setCliente] = useState(null);
  const [cuenta, setCuenta] = useState(null);

  const cargarDatos = () => {
    const token = localStorage.getItem("token");
    const idCliente = localStorage.getItem("idCliente");

    if (!token || !idCliente) {
      navigate("/");
      return;
    }

    getClienteById(idCliente, token)
      .then(res => setCliente(res.data))
      .catch(err => console.error("Error cliente:", err));

    getCuentaByCliente(idCliente, token)
      .then(res => {
        if (res.data && res.data.length > 0) {
          const cuentaData = res.data[0];
          setCuenta(cuentaData);
          localStorage.setItem("idCuenta", cuentaData.idCuenta);
        } else {
          setCuenta(null);
        }
      })
      .catch(err => {
        console.error("Error cuenta:", err);
        setCuenta(null);
      });
  };

  useEffect(() => {
    cargarDatos();
  }, [navigate]);

  return { cliente, cuenta, recargar: cargarDatos };
};
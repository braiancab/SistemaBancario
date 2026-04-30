import { useEffect, useState } from "react";
import { getClienteById } from "../servicio/clienteServicio";
import { getCuentaByCliente } from "../servicio/cuentaServicio";

export const useDashboard = (navigate) => {
  const [cliente, setCliente] = useState(null);
  const [cuenta, setCuenta] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const idCliente = localStorage.getItem("idCliente");

    if (!token || !idCliente) {
      navigate("/");
      return;
    }

    // Cliente
    getClienteById(idCliente, token)
      .then(res => setCliente(res.data))
      .catch(err => console.error(err));

    // Cuenta
    getCuentaByCliente(idCliente, token)
      .then(res => {
       const cuentaData = res.data[0];
        setCuenta(cuentaData);

 localStorage.setItem("idCuenta", cuentaData.idCuenta);
  })

      .catch(err => {
        console.error(err);
        navigate("/");
      });

  }, [navigate]);

  return { cliente, cuenta };
};
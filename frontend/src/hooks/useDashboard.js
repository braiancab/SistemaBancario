import { useEffect, useState } from "react";
import { getClienteById } from "../servicio/clienteServicio";
import { getCuentaByCliente } from "../servicio/cuentaServicio";

export const useDashboard = (navigate) => {
  const [cliente, setCliente] = useState(null);
  const [cuenta, setCuenta] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const idCliente = localStorage.getItem("idCliente");

    // Si alguien entra sin token o sin ID, lo pateamos (esto está perfecto)
    if (!token || !idCliente) {
      navigate("/");
      return;
    }

    //  Buscamos el Cliente
    getClienteById(idCliente, token)
      .then(res => setCliente(res.data))
      .catch(err => {
        console.error("Error al obtener cliente:", err);
       
      });

    // 2. Buscamos la Cuenta
    getCuentaByCliente(idCliente, token)
      .then(res => {
        // Validamos que la respuesta tenga datos y no sea un array vacío
        if (res.data && res.data.length > 0) {
          const cuentaData = res.data[0];
          setCuenta(cuentaData);
          localStorage.setItem("idCuenta", cuentaData.idCuenta);
        } else {
          // Si es nuevo y no tiene cuentas, simplemente avisamos por consola
          // y seteamos la cuenta en null (sin patearlo)
          console.log("El cliente es nuevo y aún no tiene cuentas registradas.");
          setCuenta(null);
        }
      })
      .catch(err => {
        // Si Java devuelve un error (ej. 404), atrapamos el error pero NO usamos navigate("/")
        console.error("No se pudo cargar la cuenta bancaria:", err);
        setCuenta(null);
      });

  }, [navigate]);

  return { cliente, cuenta };
};
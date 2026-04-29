import axios from "axios";

const API_URL = "http://localhost:8080/api/cuentas";

export const getCuentaByCliente = (idCliente, token) => {
  return axios.get(`${API_URL}/cliente/${idCliente}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
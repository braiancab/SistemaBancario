import axios from "axios";

const API_URL = "http://localhost:8080/api/clientes";

export const getClienteById = (idCliente, token) => {
  return axios.get(`${API_URL}/${idCliente}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
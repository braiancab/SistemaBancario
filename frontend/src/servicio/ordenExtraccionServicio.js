import axios from "axios";

const API_URL = "http://localhost:8080/api/ordenes_extraccion";

export const crearOrdenExtraccion = (orden, token) => {
  return axios.post(API_URL, orden, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};